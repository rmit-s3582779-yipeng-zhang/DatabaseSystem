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

    private void generateHeapFile(ArrayList<Page> pageList) {
        // write pageList into the heap file

        IOWriter ioWriter = new IOWriter(Setting.HEAP_FILE_NAME);
        try {
            for (Page page : pageList) {
                if (page.getPageID() % 1000 == 0)
                    System.out.print(".");
                for (Record record : page.getRecordList()) {
                    ioWriter.writeInt(record.getRecordID());
                    for (Field field : record.getFieldList()) {
                        switch (field.getType()) {
                            case String:
                                ioWriter.writeString(field.getContent());
                                break;
                            case Double:
                                ioWriter.writeDouble(Double.valueOf(field.getContent()));
                                break;
                            case Integer:
                                ioWriter.writeInt(Integer.valueOf(field.getContent()));
                                break;
                        }
                        ioWriter.writeString("\t");
                    }
                    ioWriter.writeString("\r\n");
                }
                //Fill the free space at the end of this page
                byte bytes[] = new byte[page.getFreeSpace()];
                Arrays.fill(bytes, (byte) 0);
                ioWriter.writeBinary(bytes);
                ioWriter.writeString("||");
            }
            ioWriter.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println(e.getLocalizedMessage());
        }
    }

    private ArrayList<Page> initializePageList(String filePath) {
        // Initialize entity
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
                // If this page is full, change to a new one
                if (newPage.getFreeSpace() > newRecord.getLength())
                    newPage.addRecord(newRecord);
                else {
                    pageList.add(newPage);
                    newPage = new Page(pageIndex++, maxLength);
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

    private ArrayList<Field> initializeRecord(String line) throws Exception {
        // Split line into fields
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
            fieldList.add(new Field("BN_ABN", filedString[8], ContentType.Double));
        } catch (Exception e) {
            throw new Exception("Initialize record failed, missing field");
        }

        return fieldList;
    }

}
