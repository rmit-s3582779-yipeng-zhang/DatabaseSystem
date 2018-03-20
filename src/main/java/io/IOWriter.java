package io;

import environment.Setting;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @Author: Yipeng.Zhang
 * @Description: Write data from file as an IO stream
 * @Date: Created in 22:11 2018/3/19
 * @param:
 */
public class IOWriter {

    private DataOutputStream dataOutputStream;
    private File file;
    private String root;

    public IOWriter(String outputFilePath) {
        root = Setting.ROOT;

        try {
            file = new File(root + "\\" + outputFilePath);
            dataOutputStream = new DataOutputStream(new FileOutputStream(file));
        } catch (Exception e) {
            System.err.println("Initialize output IO stream failed");
        }
    }

    public void writeString(String content) throws Exception {
        try {
            dataOutputStream.writeChars(content);
        } catch (Exception e) {
            throw new Exception("Write String failed, content(" + content + ")");
        }
    }

    public void writeDouble(Double content) throws Exception {
        try {
            dataOutputStream.writeDouble(content);
        } catch (Exception e) {
            throw new Exception("Write Double failed, content(" + content + ")");
        }
    }

    public void writeInt(int content) throws Exception {
        try {
            dataOutputStream.write(content);
        } catch (Exception e) {
            throw new Exception("Write Integer failed, content(" + content + ")");
        }
    }

    public void close() throws Exception {
        dataOutputStream.close();
        throw new Exception("Close IOWriter failed");
    }
}
