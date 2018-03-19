package exception;

import entity.Field;

/**
 * @Author: Yipeng.Zhang
 * @Description: An error has been happen when adding a new field
 * @Date: Created in 16:08 2018/3/19
 * @param:
 */
public class AddNewFiledException extends Exception{
    public AddNewFiledException(int recordID, Field newField) {
        System.err.println("Add new filed failed");
        System.err.println("Record id :" + recordID);
        System.err.println("Field : " + newField.getFiledName());
    }
}
