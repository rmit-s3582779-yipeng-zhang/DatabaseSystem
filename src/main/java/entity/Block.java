package entity;

import environment.Setting;

import java.io.Serializable;

/**
 * @Author: Yipeng.Zhang
 * @Description:
 * @Date: Created in 16:17 2018/4/16
 */
public class Block implements Serializable {
    private int modIndex; //the index of hashTable
    private int chainIndex; //the index of this overflow chain
    private int index; // the current location
    private Bucket[] bucketList; // block list
    private boolean nextBlock; // the next block

    public Block(int modIndex, int chainIndex) {
        index = 0;
        bucketList = new Bucket[Setting.MAX_BLOCK_LENGTH];
        this.modIndex = modIndex;
        this.chainIndex = chainIndex;
        nextBlock = false;
    }

    public int getSize() {
        int size = index + Setting.MAX_BLOCK_LENGTH * chainIndex;
        return size;
    }

    public boolean insertBucket(Bucket newBucket) {
        if (index < Setting.MAX_BLOCK_LENGTH) // still have space in this block
            bucketList[index++] = newBucket;
        else {
            nextBlock = true;
            return false;
        }
        return true;
    }

    public int selectBucket(String name) {
        int pageNumber = -1;
        // select bucket in this block
        for (Bucket bucket : bucketList) {
            pageNumber = bucket.getValue(HashTable.hash(name));
            if (pageNumber != -1)
                return pageNumber;
        }
        // select bucket in the next block
        //if (nextBlock != null)
        //   return nextBlock.selectBucket(name);
        // can't find this record
        return -1;
    }

    public int getModIndex() {
        return modIndex;
    }

    public int getChainIndex() {
        return chainIndex;
    }

    public boolean hasNextOne() {
        return nextBlock;
    }

}
