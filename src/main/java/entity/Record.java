package entity;

import exception.*;

import java.util.ArrayList;

/**
 * @Author: Yipeng.Zhang
 * @Description: A record contains more than one field.
 * @Date: Created in 12:02 $ 2018/3/19
 * @param:
 */
public class Record {

    private int length; // the current length of this record.
    private int recordID; //record id
    private ArrayList<Field> fieldList; //field <field name, field content, filed type>

    public Record(int recordID) {
        this.recordID = recordID;
        this.fieldList = new ArrayList<Field>();
    }

    public boolean addField(Field newField) throws AddNewFiledException {
        try {
            this.fieldList.add(newField);
            this.length += newField.getLength();
            return true;
        } catch (Exception e) {
            System.err.println("Add new filed filed");
            System.err.println("Record id :" + this.recordID);
            System.err.println("Field : " + newField.getFiledName());
            throw new AddNewFiledException();
        }

    }


}
