package heapfile;

import entity.Page;
import io.FileReader;

/**
 * @Author: Yipeng.Zhang
 * @Description: Initialize heap file
 * @Date: Created in 14:59 2018/3/19
 * @param:
 */
public class HeapfileManager {

    private int pageIndex;

    public HeapfileManager() {
        this.pageIndex = 0;
    }

    private void initializePage() {
        Page newPage = new Page(pageIndex++);
    }

    private void initializeRecord() {

    }

    private void initializeField() {

    }

    public void readData(String filePath) {
        FileReader fileReader = new FileReader(filePath);
        String line = fileReader.getNextLine();
        while (line != null) {

        }
    }

}
