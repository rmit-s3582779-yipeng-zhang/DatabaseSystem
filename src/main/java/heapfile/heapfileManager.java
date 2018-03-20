package heapfile;

import entity.ContentType;
import entity.Field;
import entity.Page;
import entity.Record;
import environment.Setting;
import io.FileReader;
import io.IOWriter;

import java.util.ArrayList;

/**
 * @Author: Yipeng.Zhang
 * @Description: Initialize heap file
 * @Date: Created in 14:59 2018/3/19
 * @param:
 */
public class HeapFileManager {

    private int maxLength;

    public HeapFileManager() {
        this.maxLength = Setting.MAXLENGTH;
    }

    public void initializeHeapFile(String filePath) {
        ArrayList<Page> pageList = initializePageList(filePath);
        System.out.println("The number of page:" + pageList.size());
        generateHeapFile(pageList);
    }

    private void generateHeapFile(ArrayList<Page> pageList) {
        // write pageList into the heap file

        IOWriter ioWriter = new IOWriter(Setting.HEAP_FILE_NAME);

        for (Page page : pageList) {
            for (Record record : page.getRecordList()) {
                for (Field field : record.getFieldList()) {
                    try {
                        switch (field.getType()) {
                            case String:
                                ioWriter.writeString(field.getContent());
                            case Double:
                                ioWriter.writeDouble(Double.valueOf(field.getContent()));
                            case Integer:
                                ioWriter.writeInt(Integer.valueOf(field.getContent()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
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
                System.out.println(pageIndex + "," + recordIndex);
                line = fileReader.getNextLine();
                ArrayList<Field> fieldList = initializeRecord(line);
                newRecord = new Record(recordIndex++);
                newRecord.setFieldList(fieldList);
                // If this page is full, change to a new one
                if (newPage.getFreeSpace() > newRecord.getLength())
                    newPage.addRecord(newRecord);
                else {
                    pageList.add(newPage);
                    newPage = new Page(pageIndex++, maxLength);
                }
            } catch (Exception e) {
                System.err.println("Page Index: " + pageIndex);
                System.err.println("Record Index: " + recordIndex);
                e.printStackTrace();
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

        try {
            fieldList.add(new Field("REGISTER_NAME", filedString[0], ContentType.String));
            fieldList.add(new Field("BN_NAME", filedString[1], ContentType.String));
            fieldList.add(new Field("BN_STATUS", filedString[2], ContentType.String));
            fieldList.add(new Field("BN_REG_DT", filedString[3], ContentType.String));
            fieldList.add(new Field("BN_CANCEL_DT", filedString[4], ContentType.String));
            fieldList.add(new Field("BN_RENEW_DT", filedString[5], ContentType.String));
            fieldList.add(new Field("BN_STATE_NUM", filedString[6], ContentType.String));
            fieldList.add(new Field("BN_STATE_OF_REG", filedString[7], ContentType.String));
            fieldList.add(new Field("BN_ABN", filedString[8], ContentType.Integer));
        } catch (Exception e) {
            throw new Exception("Initialize record failed, missing field");
        }

        return fieldList;
    }

}
