package heapfile;

import entity.*;
import environment.Setting;
import io.IOReader;
import io.Serialize;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * @Author: Yipeng.Zhang
 * @Description:
 * @Date: Created in 23:41 2018/3/20
 */
public class HeapFileManager {

    private IOReader ioReader;
    private Translater translater;
    private HashTable hashTable;

    public HeapFileManager(String filePath) throws Exception {
        //hashTable = Serialize.deserialize();
        ioReader = new IOReader(filePath);
        translater = new Translater();
    }

    public void executeQueryHashTable(String query) {
        ioReader.restart();
        boolean ifFind = false; // if find any matched record
        Page page;
        Date startTime, finishTime; // To calculate time
        long timeCost; // Time cost
        System.out.println("Start search with Hash Table");
        startTime = new Date();
        int pageNumber = getPageID(query);
        page = findPage(pageNumber);
        if (page != null) {
            for (Record record : page.getRecordList()) {
                //showRecordDetail(record);
                if (record.getFieldList().get(1).getContent().contains(query)) {
                    ifFind = true;
                    showRecordDetail(record);
                }
            }
        } else {
            System.out.println("Search with Hash Table is finished.");
        }
        if (!ifFind)
            System.out.println("Can find any matched record");
        finishTime = new Date();
        timeCost = finishTime.getTime() - startTime.getTime();
        System.out.println("The time consuming of searching with Hash Table: " + timeCost + "ms");
    }

    public void executeQuery(String query) {
        ioReader.restart();
        boolean ifFind = false; // if find any matched record
        int index = 0;
        Page page;
        Date startTime, finishTime; // To calculate time
        long timeCost; // Time cost
        System.out.println("Start search");
        startTime = new Date();
        while (true) {
            page = nextPage(index++);
            if (page != null) {
                for (Record record : page.getRecordList()) {
                    //showRecordDetail(record);
                    if (record.getFieldList().get(1).getContent().contains(query)) {
                        ifFind = true;
                        showRecordDetail(record);
                    }
                }
            } else {
                System.out.println("Search is finished.");
                break;
            }
        }
        if (!ifFind)
            System.out.println("Can find any matched record");
        finishTime = new Date();
        timeCost = finishTime.getTime() - startTime.getTime();
        System.out.println("The time consuming of searching: " + timeCost + "ms");
    }

    private void showRecordDetail(Record record) {
        for (Field field : record.getFieldList()) {
            System.out.print(field.getContent() + " ");
        }
        System.out.println("");
    }

    private Page findPage(int pageID) {
        byte[] buffer = ioReader.findPage(pageID);
        Page page = generatePage(pageID, buffer);
        return page;
    }

    public Page nextPage(int pageID) {
        // Generate a new page by reading a next MAX_LENGTH length binary file
        byte[] buffer = ioReader.nextPage();
        Page page = generatePage(pageID, buffer);
        return page;
    }

    private Page generatePage(int pageID, byte[] buffer) {
        Page page = new Page(pageID, Setting.MAX_LENGTH);
        if (buffer != null) {
            short recordNumber = getRecordNumber(buffer);
            ArrayList<Integer> recordIndexList = getRecordIndexList(buffer, recordNumber);
            ArrayList<byte[]> recordByteList = getRecordByteList(buffer, recordIndexList);
            ArrayList<Record> recordList = gerRecordList(recordByteList);
            for (Record record : recordList) {
                page.addRecord(record);
            }
            return page;
        }
        return null;
    }

    /**
     * get record number
     *
     * @param buffer the byte[] of this page
     */
    private short getRecordNumber(byte[] buffer) {
        //extract record number from buffer
        byte[] recordNumberByte = new byte[2];
        System.arraycopy(buffer, 0, recordNumberByte, 0, 2);
        short recordNumber = translater.byteToShort(recordNumberByte);
        return recordNumber;
    }

    /**
     * get the record index list, these indexes point to the start address of each record
     *
     * @param buffer       the byte[] of this page
     * @param recordNumber the number of record in this page
     */
    private ArrayList<Integer> getRecordIndexList(byte[] buffer, short recordNumber) {
        //extract record index list from buffer
        byte[] recordNumberByte;
        int recordIndex;
        ArrayList<Integer> recordIndexList = new ArrayList<Integer>();
        // recordNumber = the number of record + 1, the last one is the end address of the last record
        for (int i = 0; i <= recordNumber; i++) {
            recordNumberByte = new byte[2];
            System.arraycopy(buffer, (2 + i * 2), recordNumberByte, 0, 2);
            recordIndex = translater.byteToShort(recordNumberByte);
            recordIndexList.add(recordIndex);
        }
        return recordIndexList;
    }

    /**
     * extract record byte[] list from buffer
     *
     * @param buffer          the byte[] of this page
     * @param recordIndexList record index, points to the start address of each record
     */
    private ArrayList<byte[]> getRecordByteList(byte[] buffer, ArrayList<Integer> recordIndexList) {
        ////extract record list from buffer
        int length; // the length of one record
        byte[] recordByte;
        ArrayList<byte[]> recordListString = new ArrayList<byte[]>();
        for (int i = 0; i < recordIndexList.size() - 1; i++) { // the last one is the end address of the last record
            length = recordIndexList.get(i + 1) - recordIndexList.get(i);
            recordByte = new byte[length];
            System.arraycopy(buffer, recordIndexList.get(i), recordByte, 0, length);
            recordListString.add(recordByte);
        }
        return recordListString;
    }

    private ArrayList<Record> gerRecordList(ArrayList<byte[]> recordByteList) {
        ArrayList<Record> recordList = new ArrayList<Record>();
        for (byte[] recordByte : recordByteList) {
            recordList.add(getRecord(recordByte));
        }
        return recordList;
    }

    private Record getRecord(byte[] recordByte) {
        Record record;

        // get recordID
        byte[] recordIDByte = new byte[2];
        System.arraycopy(recordByte, 0, recordIDByte, 0, 2);
        int recordID = translater.byteToShort(recordIDByte);

        //get Record index list
        record = new Record(recordID);
        ArrayList<Integer> fieldIndexList = getFieldIndexList(recordByte);

        //get fields of this record
        byte[] recordFieldByte;
        ArrayList<byte[]> fieldByteList = new ArrayList<byte[]>();
        for (int i = 0; i < fieldIndexList.size() - 1; i++) { // the last one is the end address of the last record
            int length = fieldIndexList.get(i + 1) - fieldIndexList.get(i);
            recordFieldByte = new byte[length];
            System.arraycopy(recordByte, fieldIndexList.get(i), recordFieldByte, 0, length);
            fieldByteList.add(recordFieldByte);
        }

        try {
            record.addField(new Field("REGISTER_NAME", new String(fieldByteList.get(0)), ContentType.String));
            record.addField(new Field("BN_NAME", new String(fieldByteList.get(1)), ContentType.String));
            record.addField(new Field("BN_STATUS", new String(fieldByteList.get(2)), ContentType.String));
            record.addField(new Field("BN_REG_DT", new String(fieldByteList.get(3)), ContentType.String));
            record.addField(new Field("BN_CANCEL_DT", new String(fieldByteList.get(4)), ContentType.String));
            record.addField(new Field("BN_RENEW_DT", new String(fieldByteList.get(5)), ContentType.String));
            record.addField(new Field("BN_STATE_NUM", new String(fieldByteList.get(6)), ContentType.String));
            record.addField(new Field("BN_STATE_OF_REG", new String(fieldByteList.get(7)), ContentType.String));
            record.addField(new Field("BN_ABN", String.valueOf(translater.byteToLong(fieldByteList.get(8))), ContentType.Long));
        } catch (Exception e) {
            System.err.println("Record " + recordID + " cannot be generated!");
        }
        return record;
    }

    /**
     * extract record indexes from the byte[] of one record
     *
     * @param recordByte the byte[] of this record
     */
    private ArrayList<Integer> getFieldIndexList(byte[] recordByte) {
        int startAddress = 4;
        byte[] index = new byte[2];
        ArrayList<Integer> fieldIndexList = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) {
            System.arraycopy(recordByte, startAddress, index, 0, 2);
            startAddress += 2;
            fieldIndexList.add((int) translater.byteToShort(index));
        }
        fieldIndexList.add(recordByte.length);// the end address of the last record
        return fieldIndexList;
    }

    private int getPageID(String name) {
        int pageNumber = hashTable.getValue(name);
        if (pageNumber == -1)
            System.out.println("Can not find this company!");
        return pageNumber;
    }
}
