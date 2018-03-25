package io;

import entity.Page;
import environment.Setting;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @Author: Yipeng.Zhang
 * @Description: Read data from file as an IO stream
 * @Date: Created in 22:09 2018/3/19
 */
public class IOReader {

    //private DataInputStream dataInputStream;
    private int pageIndex;
    private FileInputStream fileInputStream;
    private int pageSize;

    public IOReader(String inputFilePath) throws Exception{
        pageIndex = 0;
        pageSize = Setting.MAX_LENGTH;
        File file;
        try {
            file = new File(inputFilePath);
            fileInputStream = new FileInputStream(file);
        } catch (Exception e) {
            throw new FileNotFoundException("Initialize input IO stream failed! " +
                    "Please check if file " + inputFilePath + " is existed");
        }
    }

    public byte[]  nextPage() {
        byte[] buffer = new byte[pageSize];
        try {
            fileInputStream.read(buffer, pageIndex, pageSize);
        } catch (Exception e){
        }
        return buffer;
    }


    /*
    public IOReader(String outputFilePath) {
        // Initialize this class
        root = Setting.ROOT;
        maxLength = Setting.MAXLENGTH;

        try {
            file = new File(root + File.separator + outputFilePath);
            dataInputStream = new DataInputStream(new FileInputStream(file));
        } catch (Exception e) {
            System.err.println("Initialize input IO stream failed");
        }
    }

    public char[] readIOStream() throws Exception {
        // Read one page from the data file a time
        int index = 0;
        char content[] = new char[maxLength];
        while (index < maxLength) {
            content[index++] = dataInputStream.readChar();
        }
        return content;
    }

    */
}
