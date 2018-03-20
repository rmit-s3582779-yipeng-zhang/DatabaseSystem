package entity;

import java.util.ArrayList;

/**
 * @Author: Yipeng.Zhang
 * @Description: A page contain more than one record. The max length of records cannot be more than setting max length.
 * @Date: Created in 17:04 2018/3/19
 * @param:
 */
public class Page {

    private int pageID;
    private int length; // the current length of this page.
    private int maxLength; // the limit of max length of page.
    private int freeSpace; // maxLength - length
    private ArrayList<Record> recordList;

    //Initialize Page Class
    public Page(int pageID, int maxLength) {
        //initialize setting of Page
        this.pageID = pageID;
        this.length = 4; // separator "||"
        this.maxLength = maxLength;
        this.freeSpace = this.maxLength;
        this.recordList = new ArrayList<Record>();
    }

    public boolean addRecord(Record newRecord) {
        //add a new record into this page
        try {
            if ((this.maxLength - this.length) > newRecord.getLength()) {
                if (this.recordList.add(newRecord)) {
                    this.length += newRecord.getLength();
                    this.freeSpace = this.maxLength - this.length;
                    return true;
                }
            }
        } catch (Exception e) {

        }
        return false;
    }

    public int getPageID() {
        return pageID;
    }

    public int getFreeSpace() {
        return freeSpace;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public ArrayList<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(ArrayList<Record> recordList) {
        this.recordList = recordList;
    }
}
