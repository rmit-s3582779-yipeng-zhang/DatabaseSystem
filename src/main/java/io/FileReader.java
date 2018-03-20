package io;

import environment.Setting;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

/**
 * @Author: Yipeng.Zhang
 * @Description: Read data from file ling by ling
 * @Date: Created in 21:53 2018/3/19
 * @param:
 */
public class FileReader {

    private String line = "";
    private String inputFilePath;
    private BufferedReader bufferedReader;
    private String root;

    public FileReader(String inputFilePath) {

        root = Setting.ROOT;

        this.inputFilePath = inputFilePath;
        try {
            bufferedReader = new BufferedReader(new java.io.FileReader(root + File.separator + inputFilePath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNextLine() {

        try {
            line = bufferedReader.readLine();
            if (line != null) {
                return line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {

        try {
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
