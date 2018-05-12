package entity;

import environment.Setting;

import java.io.Serializable;

/**
 * @Author: Yipeng.Zhang
 * @Description:
 * @Date: Created in 16:17 2018/4/16
 */
public class Block implements Serializable {
    private int index; // the current location
    private Bucket[] bucketList; // block list
    private Block nextBlock; // the next block

    public Block() {
        index = 0;
        bucketList = new Bucket[Setting.MAX_BLOCK_LENGTH];
    }

    public int getSize() {
        int size = index;
        if (nextBlock != null)
            size += nextBlock.getSize();
        return size;
    }

    public void insertBucket(Bucket newBucket) {
        if (index < Setting.MAX_BLOCK_LENGTH)
            bucketList[index++] = newBucket;
        else {
            if (nextBlock == null) {
                Block newBlock = new Block();
                nextBlock = newBlock;
            }
            nextBlock.insertBucket(newBucket);
        }
    }

    public int selectBucket(String name) {
        int pageNumber = -1;
        // select bucket in this block
        for (Bucket bucket : bucketList) {
            pageNumber = bucket.getValue(name);
            if (pageNumber != -1)
                return pageNumber;
        }
        // select bucket in the next block
        if (nextBlock != null)
            return nextBlock.selectBucket(name);
        // can't find this record
        return -1;
    }

}
