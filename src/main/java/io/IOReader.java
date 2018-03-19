package io;

import environment.Setting;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * @Author: Yipeng.Zhang
 * @Description: Read data from file as an IO stream
 * @Date: Created in 22:09 2018/3/19
 * @param:
 */
public class IOReader {

    private DataInputStream dataInputStream;
    private File file;
    private String root;
    private int maxLength;

    public IOReader(String outputFilePath) {
        root = Setting.ROOT;
        maxLength = Setting.MAXLENGTH;

        try {
            file = new File(root + "\\" + outputFilePath);
            dataInputStream = new DataInputStream(new FileInputStream(file));
        } catch (Exception e) {
            System.err.println("Initialize input IO stream failed");
        }
    }

    public char[] readIOStream() throws Exception {
        int index = 0;
        char content[] = new char[maxLength];
        while (index < maxLength) {
            content[index++] = dataInputStream.readChar();
        }
        return content;
    }


}
