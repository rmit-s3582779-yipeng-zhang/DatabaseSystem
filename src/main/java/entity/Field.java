package entity;

/**
 * @Author: Yipeng.Zhang
 * @Description: contains basic information of the filed, length will be calculated after changing.
 * @Date: Created in 11:58 2018/3/19
 */
public class Field {

    private int length;
    private String filedName; //file name
    private String content; //file content
    private ContentType type; //file type

    public Field(String filedName, String content, ContentType type) {
        this.filedName = filedName;
        this.content = content;
        this.type = type;
        calLength();
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        calLength();
    }

    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
        this.type = type;
        calLength();
    }

    public int getLength() {
        return length;
    }

    private void calLength() {
        //calculate the binary length of this filed
        if (type == ContentType.String)
            this.length = content.getBytes().length; //Each char contains 1 bytes
        else if (type == ContentType.Integer)
            this.length = 4; //Integer is 4 byte
        else if (type == ContentType.Double)
            this.length = 8; //Double is 8 byte
        else if (type == ContentType.Long)
            this.length = 8; //Long is 8 byte
    }

}
