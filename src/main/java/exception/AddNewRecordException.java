package exception;


import entity.Record;

/**
 * @Author: Yipeng.Zhang
 * @Description:
 * @Date: Created in 18:27 2018/3/19
 * @param:
 */
public class AddNewRecordException extends Exception{
    public AddNewRecordException(Record newRecord) {
        System.err.println("Add new record failed");
        System.err.println("Record id :" + newRecord.getRecordID());
    }
}
