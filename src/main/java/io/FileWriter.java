package io;

import environment.Setting;

import java.io.*;

/**
 * @Author: Yipeng.Zhang
 * @Description: Write data from file ling by ling
 * @Date: Created in 22:04 2018/3/19
 * @param:
 */
public class FileWriter {
    private Writer writer;
    private String outputFilePath;
    private String root;

    public FileWriter(String outputFilePath) {

        root = Setting.ROOT;

        try {
            if (Setting.SYSTEM.equals("Win"))
                this.outputFilePath = root + "\\" + outputFilePath;

            if (Setting.SYSTEM.equals("Linux"))
                this.outputFilePath = root + "/" + outputFilePath;

        } catch (Exception e) {
            e.printStackTrace();
        }
        setUpWriter();
    }

    private void setUpWriter() {

        try {

            File file = new File(outputFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
            writer = new BufferedWriter(outputStreamWriter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void writeToFile(String content) {

        try {
            writer.write(content);
            writer.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {

        try {
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
