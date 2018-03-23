package heapfile;

import entity.ContentType;
import entity.Field;
import entity.Page;
import entity.Record;
import environment.Setting;
import io.FileReader;
import io.IOWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * @Author: Yipeng.Zhang
 * @Description: Initialize heap file
 * @Date: Created in 14:59 2018/3/19
 */
public class HeapFileGenerator {

    private int maxLength;

    public HeapFileGenerator() {
        this.maxLength = Setting.MAX_LENGTH;
    }

    /**
     * Initialize heap file
     *
     * @param filePath file path of heap file
     */
    public void initializeHeapFile(String filePath) {
        Date startTime, finishTime; // To calculate time
        long timeCost; // Time cost

        // Start to read data from file
        System.out.print("Start to read the file");
        startTime = new Date();
        ArrayList<Page> pageList = initializePageList(filePath);
        finishTime = new Date();
        timeCost = finishTime.getTime() - startTime.getTime();
        System.out.println(""); // change to a new line
        System.out.println("The time consuming of reading file: " + timeCost + "ms");

        // Start to write data into heap file
        System.out.print("Start to write the heap file");
        startTime = new Date();
        generateHeapFile(pageList);
        finishTime = new Date();
        timeCost = finishTime.getTime() - startTime.getTime();
        System.out.println(""); // change to a new line
        System.out.println("The time consuming of writing heap file: " + timeCost + "ms");
        System.out.println("HeapFile has been generated");
        System.out.println("The number of page:" + pageList.size());
    }

    /**
     * write pageList into the heap file from prepared page list
     *
     * @param pageList prepared page list
     */
    private void generateHeapFile(ArrayList<Page> pageList) {
        IOWriter ioWriter = new IOWriter(Setting.HEAP_FILE_NAME);
        try {
            for (Page page : pageList) {
                if (page.getPageID() % 1000 == 0)
                    System.out.print("."); // keep connecting

                ioWriter.writeShort((short) page.getRecordList().size()); // first 2 byte is the number of record
                writeRecordIndex(page.getRecordList(), ioWriter); // record index list pointing to each record

                for (Record record : page.getRecordList()) {
                    ioWriter.writeInt(record.getRecordID());
                    writeFieldIndex(record, ioWriter); // field index list pointing to each field
                    for (Field field : record.getFieldList()) {
                        switch (field.getType()) {
                            case String:
                                ioWriter.writeString(field.getContent());
                                break;
                            case Long:
                                ioWriter.writeLong(Long.valueOf(field.getContent()));
                                break;
                            case Double:
                                ioWriter.writeDouble(Double.valueOf(field.getContent()));
                                break;
                            case Integer:
                                ioWriter.writeInt(Integer.valueOf(field.getContent()));
                                break;
                        }
                    }
                }
                //Fill the free space at the end of this page
                byte bytes[] = new byte[page.getFreeSpace()];
                Arrays.fill(bytes, (byte) 0);
                ioWriter.writeBinary(bytes);
            }
            ioWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * write record index into the head of heap file from prepared record list
     *
     * @param recordList prepared record list,
     * @param ioWriter   output io stream
     */
    private void writeRecordIndex(ArrayList<Record> recordList, IOWriter ioWriter) {
        short startAddress = (short) (2 + recordList.size() * 2 + 2);
        // record id(short 2), record list(short 2 * size), extra address of record for the end address (short 2)
        ArrayList<Short> indexList = new ArrayList<Short>();
        for (Record record : recordList) {
            indexList.add(startAddress);
            startAddress += (short) (record.getLength() - 2); // move index space
        }
        indexList.add(startAddress);// the end of the last record
        try {
            for (Short index : indexList) {
                ioWriter.writeShort(index);
            }
        } catch (Exception e) {
            System.err.println("Generate index list failed.");
        }
    }

    /**
     * write field index into the head of heap file from prepared record list
     *
     * @param record   prepared record list
     * @param ioWriter output io stream
     */
    private void writeFieldIndex(Record record, IOWriter ioWriter) {
        ArrayList<Field> fieldList = record.getFieldList();
        ArrayList<Short> indexList = new ArrayList<Short>();
        short startAddress = (short) (4); // recordID; int, 4 bytes
        startAddress += (short) (fieldList.size() * 2); // record index: short, 2 bytes
        for (Field field : fieldList) {
            indexList.add(startAddress);
            startAddress += field.getLength();
        }
        try {
            for (Short index : indexList) {
                ioWriter.writeShort(index);
            }
        } catch (Exception e) {
            System.err.println("Generate index list failed.");
        }
    }

    /**
     * read all data from filePath,initialize entity
     *
     * @param filePath csv file path
     */
    private ArrayList<Page> initializePageList(String filePath) {
        int pageIndex = 0;
        int recordIndex = 0;
        ArrayList<Page> pageList = new ArrayList<Page>();
        Page newPage = new Page(pageIndex++, maxLength);
        Record newRecord;

        // Read data from filePath
        FileReader fileReader = new FileReader(filePath);
        String line = fileReader.getNextLine(); // First line is attributes' name, skip
        while (line != null) {
            try {
                if (recordIndex % 100000 == 0)
                    System.out.print(".");
                line = fileReader.getNextLine();
                if (line == null)
                    continue;
                ArrayList<Field> fieldList = initializeRecord(line); // Split line
                newRecord = new Record(recordIndex++); // new Record
                newRecord.setFieldList(fieldList); // fill fields
                newRecord.setLength(newRecord.getLength() + 2); // extra 2 bytes for record index
                // If this page is full, change to a new one
                if (newPage.getFreeSpace() > newRecord.getLength())
                    newPage.addRecord(newRecord);
                else {
                    pageList.add(newPage);
                    newPage = new Page(pageIndex++, maxLength);
                    newPage.addRecord(newRecord);
                    //for testing, only read a part of data
                    if (Setting.MAX_LENGTH != 0 && pageIndex > Setting.MAX_PAGE)
                        return pageList;
                }
            } catch (Exception e) {
                System.err.println("Initialize this Page Failed");
                System.err.println("Page Index: " + pageIndex);
                System.err.println("Record Index: " + recordIndex);
                System.err.println(e.getMessage());
            }
        }
        return pageList;
    }

    /**
     * plit line into fields
     *
     * @param line a single line from csv file
     */
    private ArrayList<Field> initializeRecord(String line) throws Exception {
        ArrayList<Field> fieldList = new ArrayList<Field>();
        String separator = Setting.INPUT_FILE_SEPARATOR; //separator of input file
        String[] filedString = line.split(separator);

        //using null string to fill the missing field
        if (filedString.length < 9) {
            String[] fieldStringFixed = new String[9];
            for (int i = 0; i < filedString.length; i++)
                fieldStringFixed[i] = filedString[i];
            for (int i = filedString.length; i < 9; i++)
                fieldStringFixed[i] = "";
            filedString = fieldStringFixed;
        }

        if (filedString[8] == "" || filedString[8] == null)
            filedString[8] = "0";

        try {
            fieldList.add(new Field("REGISTER_NAME", filedString[0], ContentType.String));
            fieldList.add(new Field("BN_NAME", filedString[1], ContentType.String));
            fieldList.add(new Field("BN_STATUS", filedString[2], ContentType.String));
            fieldList.add(new Field("BN_REG_DT", filedString[3], ContentType.String));
            fieldList.add(new Field("BN_CANCEL_DT", filedString[4], ContentType.String));
            fieldList.add(new Field("BN_RENEW_DT", filedString[5], ContentType.String));
            fieldList.add(new Field("BN_STATE_NUM", filedString[6], ContentType.String));
            fieldList.add(new Field("BN_STATE_OF_REG", filedString[7], ContentType.String));
            fieldList.add(new Field("BN_ABN", filedString[8], ContentType.Long));
        } catch (Exception e) {
            throw new Exception("Initialize record failed, missing field");
        }

        return fieldList;
    }

}
