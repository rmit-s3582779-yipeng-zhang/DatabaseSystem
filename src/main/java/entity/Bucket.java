package entity;

import java.io.Serializable;

/**
 * @Author: Yipeng.Zhang
 * @Description: Store a record
 * @Date: Created in 16:09 2018/4/16
 */
public class Bucket implements Serializable {
    private int key; // hash key
    private int pageNumber; // the page number stored this record

    /**
     * Bucket is used to store a record
     *
     * @param key        company name
     * @param pageNumber the number of page storing this record
     */
    public Bucket(int key, int pageNumber) {
        this.key = key;
        this.pageNumber = pageNumber;
    }

    /**
     * return the page number if this is the target, otherwise return -1
     *
     * @param key company name
     */
    public int getValue(int key) {
        if (this.key == key)
            return pageNumber;
        else
            return -1;
    }
}
