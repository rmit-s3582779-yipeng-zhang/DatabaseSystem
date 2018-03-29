package io;

import environment.Setting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @Author: Yipeng.Zhang
 * @Description: Read data from file ling by ling
 * @Date: Created in 21:53 2018/3/19
 * @param:
 */
public class FileReader {

    private String line = "";
    private BufferedReader bufferedReader;
    private String root;

    public FileReader(String inputFilePath) {

        root = Setting.ROOT;
        try {
            File file=new File(root + File.separator + inputFilePath);
            if(file.isFile() && file.exists()){
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader read = new InputStreamReader(fileInputStream,Setting.ENCODING);
                bufferedReader = new BufferedReader(read);
            }
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
}
