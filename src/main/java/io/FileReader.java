package io;

import environment.Setting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @Author: Yipeng.Zhang
 * @Description: Read data from file ling by ling
 * @Date: Created in 21:53 2018/3/19
 * @param:
 */
public class FileReader {

    private String root;
    Scanner sc = null;

    public FileReader(String inputFilePath) {
        root = Setting.ROOT;
        try {
            FileInputStream inputStream = new FileInputStream(root + File.separator + inputFilePath);
            sc = new Scanner(inputStream, "UTF-8");
        } catch (Exception e) {
        }
    }

    public String getNextLine() {
        try {
            if (sc.hasNextLine())
                return (sc.nextLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
