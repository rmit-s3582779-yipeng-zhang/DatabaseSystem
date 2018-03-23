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
            file = new File(root + File.separator + outputFilePath);
            dataOutputStream = new DataOutputStream(new FileOutputStream(file));
        } catch (Exception e) {
            System.err.println("Initialize output IO stream failed");
        }
    }

    public void writeBinary(byte[] bytes) throws Exception {
        try {
            dataOutputStream.write(bytes);
        } catch (Exception e) {
            throw new Exception("Write byte[] failed");
        }

    }

    public void writeString(String content) throws Exception {
        try {
            //content.getBytes("UTF-8");
            writeBinary(content.getBytes("UTF-8"));
        } catch (Exception e) {
            throw new Exception("Write String failed, content(" + content + ")");
        }
    }

    public void writeShort(Short content) throws Exception {
        try {
            dataOutputStream.writeShort(content);
        } catch (Exception e) {
            throw new Exception("Write Short failed, content(" + content + ")");
        }
    }

    public void writeLong(Long content) throws Exception {
        try {
            dataOutputStream.writeLong(content);
        } catch (Exception e) {
            throw new Exception("Write Long failed, content(" + content + ")");
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
            dataOutputStream.writeInt(content);
        } catch (Exception e) {
            throw new Exception("Write Integer failed, content(" + content + ")");
        }
    }

    public void close() throws Exception {
        try {
            dataOutputStream.close();
        } catch (Exception e) {
            throw new Exception("Close IOWriter failed");
        }
    }
}
