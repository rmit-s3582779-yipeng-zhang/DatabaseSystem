package io;

import entity.HashTable;
import environment.Setting;

import java.io.*;
import java.util.Date;

/**
 * @Author: Yipeng.Zhang
 * @Description:
 * @Date: Created in 11:20 2018/4/17
 */
public class Serialize {

    public static void serializeFast(HashTable hashTable) {
        ObjectOutputStream objectOutputStream = null;
        Date startTime, finishTime; // To calculate time
        long timeCost; // Time cost
        startTime = new Date();
        try {
            System.out.println("Writing Hash Table into disk.");
            RandomAccessFile raf = new RandomAccessFile(Setting.HASH_FILE, "rw");
            FileOutputStream fos = new FileOutputStream(raf.getFD());
            objectOutputStream = new ObjectOutputStream(fos);
            objectOutputStream.writeObject(hashTable);
            finishTime = new Date();
            timeCost = finishTime.getTime() - startTime.getTime();
            System.out.println("Hash Table has been written into disk. ("+ timeCost + "ms)");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can not generate Hash Table!");
        }
    }

    public static HashTable deserialize() {
        try {
            System.out.println("Reading Hash Table into memory.");
            HashTable hashTable;
            FileInputStream fileInputStream = new FileInputStream(Setting.HASH_FILE);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            hashTable = (HashTable) objectInputStream.readObject();
            System.out.println("Hash Table has been generated.");
            return hashTable;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can not read Hash Table!");
            return null;
        }
    }
}
