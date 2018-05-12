package entity;

import environment.Setting;

import java.io.Serializable;

/**
 * @Author: Yipeng.Zhang
 * @Description:
 * @Date: Created in 17:40 2018/4/16
 */
public class HashTable implements Serializable {
    private int mod;
    private Block blockList[];

    public HashTable() {
        mod = Setting.MOD;
        blockList = new Block[mod];
        for (int i = 0; i < mod; i++)
            blockList[i] = new Block();
    }

    public void add(String name, int pageNumber) {
        int key = hash(name);
        Bucket bucket = new Bucket(name, pageNumber);
        blockList[key].insertBucket(bucket);
    }

    public int getValue(String name){
        int key = hash(name);
        return blockList[key].selectBucket(name);
    }

    private int hash(String str) {
        int key = 0;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            key = (key * 33) + chars[i];
        }
        if (key < 0)
            key = Math.abs(key);
        return key % mod;
    }

    /**
     * For testing, show the distribution of all blocks
     *
     */
    public void showDistribution() {
        int totalNumber = 0;
        for (int i = 0; i < mod; i++) {
            totalNumber += blockList[i].getSize();
        }
        for (int i = 0; i < mod; i++) {
            System.out.print(i + " : ");
            System.out.println(String.format("%.2f", ((double) blockList[i].getSize() * 100.0 / (double) totalNumber)));
        }
    }
}