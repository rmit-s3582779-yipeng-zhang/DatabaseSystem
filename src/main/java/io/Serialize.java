package io;

import entity.Block;
import environment.Setting;

import java.io.*;

/**
 * @Author: Yipeng.Zhang
 * @Description: write and read serialized file into disk
 * @Date: Created in 11:20 2018/4/17
 */
public class Serialize {
    public Serialize() {
        //check folder
        File directory = new File(Setting.HASH_FILE);
        if (!directory.exists())
            directory.mkdir();
    }

    public static void serializeFast(Block block) {
        ObjectOutputStream objectOutputStream;
        String fileName = Setting.HASH_FILE + "block." + block.getModIndex() + "." + block.getChainIndex();
        try {
            RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
            FileOutputStream fos = new FileOutputStream(raf.getFD());
            objectOutputStream = new ObjectOutputStream(fos);
            objectOutputStream.writeObject(block);
            objectOutputStream.close();
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Can not write block." + block.getModIndex() + "." + block.getChainIndex());
        }
    }

    public static Block deserialize(int modIndex, int chainIndex) {
        try {
            Block block;
            String fileName = Setting.HASH_FILE + "block." + modIndex + "." + chainIndex;
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            block = (Block) objectInputStream.readObject();
            return block;
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Can not read block." + modIndex + "." + chainIndex + "!");
            return null;
        }
    }
}
