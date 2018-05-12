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
    private FileInputStream fileInputStream;
    private int pageSize;
    private File file;

    public IOReader(String inputFilePath) throws Exception {
        pageSize = Setting.MAX_LENGTH;
        try {
            file = new File(inputFilePath);
            fileInputStream = new FileInputStream(file);
        } catch (Exception e) {
            throw new FileNotFoundException("Initialize input IO stream failed! " +
                    "Please check if file " + inputFilePath + " is existed");
        }
    }

    public void restart() {
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * get the binary stream of the next page
     *
     * @param pageID the page number of target page
     */
    public byte[] findPage(int pageID) {
        byte[] buffer = new byte[pageSize];
        long startAddress = (long) pageID * (long) Setting.MAX_LENGTH;
        try {
            fileInputStream.skip(startAddress);
            if ((fileInputStream.read(buffer, 0, pageSize)) != -1)
                return buffer;
            else return null;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * get the binary stream of the next page
     */
    public byte[] nextPage() {
        byte[] buffer = new byte[pageSize];
        try {
            if ((fileInputStream.read(buffer, 0, pageSize)) != -1)
                return buffer;
            else return null;
        } catch (Exception e) {
        }
        return null;
    }
}
