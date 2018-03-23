package entity;

import java.util.ArrayList;

/**
 * @Author: Yipeng.Zhang
 * @Description: A record contains more than one field.
 * @Date: Created in 12:02 2018/3/19
 */
public class Record {

    private int length; // the current length of this record.
    private int recordID; //record id
    private ArrayList<Field> fieldList; //field <field name, field content, filed type>

    //Initialize Record Class
    public Record(int recordID) {
        // recordID: int, 4 bytes
        length = 4;
        this.recordID = recordID;
        fieldList = new ArrayList<Field>();
    }

    public boolean addField(Field newField) {
        //add a new field into this record
        try {
            // using extra 2 bytes as a pointer to index the start address of this field
            this.length += newField.getLength() + 2; // the end of this field
            this.fieldList.add(newField);
            return true;
        } catch (Exception e) {
            System.err.println("Add new field failed in record " + recordID);
            return false;
        }
    }

    public boolean identify(String content) {
        for (Field field : fieldList) {
            if (field.getContent().equals(content))
                return true;
        }
        return false;
    }

    public boolean identify(String fieldName, String content) {
        for (Field field : fieldList) {
            if (field.getFiledName().equals(fieldName)) {
                if (field.getContent().equals(content))
                    return true;
            }
        }
        return false;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public ArrayList<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(ArrayList<Field> fieldList) {
        for (Field field : fieldList) {
            addField(field);
        }
    }

}
