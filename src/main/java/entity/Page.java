package entity;

import exception.*;
import java.util.ArrayList;

/**
 * @Author: Yipeng.Zhang
 * @Description: A page contain more than one record. The max length of records cannot be more than setting max length.
 * @Date: Created in 17:04 2018/3/19
 * @param:
 */
public class Page {

    private int length; // the current length of this page.
    private int maxLength; // the limit of max length of page.
    private ArrayList<Record> recordList;

    //Initialize Page Class
    public Page(int maxLength) {
        //initialize setting of Page
        this.length = 0;
        this.maxLength = maxLength;
        this.recordList = new ArrayList<Record>();
    }

    public boolean addRecord(Record newRecord) throws AddNewRecordException {
        //add a new record into this page
        try {
            if ((this.maxLength - this.length) > newRecord.getLength()) {
                if (this.recordList.add(newRecord)) {
                    this.length += newRecord.getLength();
                    return true;
                }
            }
        } catch (Exception e) {
            throw new AddNewRecordException(newRecord);
        }
        return false;
    }


}
