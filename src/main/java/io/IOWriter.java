package io;

import entity.Translater;
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
    private byte[] cache; // used to store page data
    private int index = 0; // current index of bytes to write
    private Translater translater;

    public IOWriter(String outputFilePath) {
        root = Setting.ROOT;
        cache = new byte[Setting.MAX_LENGTH];
        translater = new Translater();

        try {
            file = new File(root + File.separator + outputFilePath);
            dataOutputStream = new DataOutputStream(new FileOutputStream(file));
        } catch (Exception e) {
            System.err.println("Initialize output IO stream failed");
        }
    }

    public void writeBinary(byte[] bytes) throws Exception {
        try {
            System.arraycopy(bytes, 0, cache, index, bytes.length);
            index += bytes.length;
            //dataOutputStream.write(bytes);
        } catch (Exception e) {
            throw new Exception("Write byte[] failed");
        }

    }

    public void writeString(String content) throws Exception {
        try {
            byte[] bytes = content.getBytes("UTF-8");
            System.arraycopy(bytes, 0, cache, index, bytes.length);
            index += bytes.length;
            //content.getBytes("UTF-8");
            //writeBinary(content.getBytes("UTF-8"));
        } catch (Exception e) {
            throw new Exception("Write String failed, content(" + content + ")");
        }
    }

    public void writeShort(Short content) throws Exception {
        try {
            byte[] bytes = translater.shortToByte(content);
            System.arraycopy(bytes, 0, cache, index, bytes.length);
            index += 2;
            //dataOutputStream.writeShort(content);
        } catch (Exception e) {
            throw new Exception("Write Short failed, content(" + content + ")");
        }
    }

    public void writeLong(Long content) throws Exception {
        try {
            byte[] bytes = translater.longToByte(content);
            System.arraycopy(bytes, 0, cache, index, bytes.length);
            index += 8;
            //dataOutputStream.writeLong(content);
        } catch (Exception e) {
            throw new Exception("Write Long failed, content(" + content + ")");
        }
    }

    public void writeDouble(Double content) throws Exception {
        try {
            byte[] bytes = translater.doubleToByte(content);
            System.arraycopy(bytes, 0, cache, index, bytes.length);
            index += 8;
            //dataOutputStream.writeDouble(content);
        } catch (Exception e) {
            throw new Exception("Write Double failed, content(" + content + ")");
        }
    }

    public void writeInt(int content) throws Exception {
        try {
            byte[] bytes = translater.intToByte(content);
            System.arraycopy(bytes, 0, cache, index, bytes.length);
            index += 4;
            //dataOutputStream.writeInt(content);
        } catch (Exception e) {
            throw new Exception("Write Integer failed, content(" + content + ")");
        }
    }

    public void writeToDisk() throws Exception {
        try {
            dataOutputStream.write(cache);
            index = 0;
        } catch (Exception e) {
            throw new Exception("Write file failed");
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
