package entity;

/**
 * @Author: Yipeng.Zhang
 * @Description: contains basic information of the filed, length will be calculated after changing.
 * @Date: Created in 11:58 $ 2018/3/19
 * @param: Initialize filed
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

    public void setLength(int length) {
        this.length = length;
    }

    private void calLength() {
        //calculate the binary length of this filed

        if (type.equals("Integer"))
            this.length = 4;
        else if (type.equals("Double"))
            this.length = 8;
        else if (type.equals("String"))
            this.length = content.length() * 4;
    }

}
