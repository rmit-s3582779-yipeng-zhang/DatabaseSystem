package entity;

import environment.Setting;
import io.Serialize;

import java.io.Serializable;

/**
 * @Author: Yipeng.Zhang
 * @Description:
 * @Date: Created in 17:40 2018/4/16
 */
public class HashTable implements Serializable {
    private int mod;
    private Block blockList[];
    private Serialize serialize;

    public HashTable() {
        serialize = new Serialize();
        mod = Setting.MOD;
        blockList = new Block[mod];
        for (int i = 0; i < mod; i++)
            blockList[i] = new Block(i, 0);
    }

    public void add(String name, int pageNumber) {
        int hashcode = hash(name);
        int key = hashcode % Setting.MOD;
        Bucket bucket = new Bucket(hashcode, pageNumber);
        if (!blockList[key].insertBucket(bucket)) {
            serialize.serializeFast(blockList[key]);
            blockList[key] = new Block(key, blockList[key].getChainIndex() + 1);
            blockList[key].insertBucket(bucket);
        }
    }

    public void storeHashTable() {
        int index = 0;
        for (Block block : blockList) {
            serialize.serializeFast(block);
            index++;
        }
    }

    public int getValue(String name) {
        int key = hash(name);
        return blockList[key].selectBucket(name);
    }

    public static int hash(String str) {
        int key = 0;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            key = (key * 33) + chars[i];
        }
        if (key < 0)
            key = Math.abs(key);
        return key;
    }

    /**
     * For testing, show the distribution of all blocks
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

    public void closeIO(){

    }
}
