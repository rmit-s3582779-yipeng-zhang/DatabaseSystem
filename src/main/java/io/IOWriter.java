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

    public void writeIOStream(String content) throws Exception {
        dataOutputStream.writeChars(content);
    }

    public void writeIOStream(Double content) throws Exception {
        dataOutputStream.writeDouble(content);
    }

    public void writeIOStream(int content) throws Exception {
        dataOutputStream.write(content);
    }

    public void close() throws Exception {
        dataOutputStream.close();
    }
}
