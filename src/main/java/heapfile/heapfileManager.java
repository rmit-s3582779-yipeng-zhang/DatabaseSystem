package heapfile;

import entity.Field;
import entity.Page;
import entity.Record;
import environment.Setting;
import io.IOReader;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @Author: Yipeng.Zhang
 * @Description:
 * @Date: Created in 23:41 2018/3/20
 */
public class HeapFileManager {

    private IOReader ioReader;
    private ArrayList<Page> pageList;

    public HeapFileManager(String filePath) {
        pageList = new ArrayList<Page>();
        ioReader = new IOReader(filePath);
    }

    public void executeQuery(String query) {
        Page page;
        while (true) {
            page = nextPage();
            for (Record record : page.getRecordList()) {
                if (record.getFieldList().get(1).equals(query))
                    showRecordDetail(record);
            }
        }
    }

    private void showRecordDetail(Record record) {
        for (Field field : record.getFieldList()) {
            System.out.println(field.getFiledName() + " : " + field.getContent());
        }
    }

    private Page nextPage() {
        byte[] buffer = ioReader.nextPage();
        String pageString = new String(buffer);
        String recordString[] = pageString.split("\r\n");
        //TODO: split pageString into record and filed
        Page page = null;
        return page;
    }



}
