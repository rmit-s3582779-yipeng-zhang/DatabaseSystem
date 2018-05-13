import environment.Setting;
import hashtable.HashTableManager;
import heapfile.HeapFileManager;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @Author: Yipeng.Zhang
 * @Description: search record from record based on
 * @Date: Created in 8:51 2018/5/13
 */
public class dbqueryHT {

    private static String query;
    private static int pageSize; //the length of page
    private static int hashTableSize; //the length of block
    private static int mod; // mod - hash function

    public static void main(String[] arg) {
        try {
            dbqueryHT dbqueryHT = new dbqueryHT();
            dbqueryHT.extractParameter(arg);
            //dbqueryHT.checkParameter();
            //dbqueryHT.executeQuery(query);

            //automatically test hasptables
            for (int i = 1024; i <= 1024; i = i * 2) {
                mod = i;
                dbqueryHT.checkParameter();
                dbqueryHT.executeQuery(query);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * check parameters, if they are right, start executing query
     *
     * @param arg program arguments
     */
    private void extractParameter(String[] arg) throws Exception {

        // Validate parameters
        if (arg.length <= 0)
            throw new Exception("Incorrect parameter!");

        try {
            query = "";
            for (int i = 0; i < arg.length; i++) {
                if (arg[i].equals("-p")) {
                    pageSize = Integer.valueOf(arg[++i]);
                    continue;
                } else if (arg[i].equals("-m")) {
                    mod = Integer.valueOf(arg[++i]);
                    continue;
                } else if (arg[i].equals("-b")) {
                    hashTableSize = Integer.valueOf(arg[++i]);
                    continue;
                }
                //combine query
                query += arg[i] + " ";
            }

            query = query.substring(0, query.length() - 1);
        } catch (Exception e) {
            throw new Exception("Incorrect parameter!");
        }
    }

    private void checkParameter() throws Exception {
        // Validate parameters
        if (pageSize <= 0)
            throw new Exception("Page size cannot be smaller than 1");
        if (mod <= 0)
            throw new Exception("Mod cannot be smaller than 1");
        if (hashTableSize <= 0)
            throw new Exception("Block size cannot be smaller than 1");
        if (query.length() <= 0)
            throw new Exception("Query cannot be empty!");

        //If all parameters are correct, initialize settings
        Setting setting = new Setting();

        setting.MAX_LENGTH = pageSize;
        setting.MAX_BLOCK_LENGTH = hashTableSize;
        setting.MOD = mod;
        setting.HEAP_FILE_NAME = "heap." + pageSize;
        setting.HASH_FILE = setting.ROOT + File.separator + "HashTables." + pageSize
                + "." + hashTableSize + "." + mod + File.separator;
    }

    /**
     * start executing query
     *
     * @param query    query key word
     */
    private void executeQuery(String query) {
        try {
            HashTableManager hashTableManager=new HashTableManager();
            hashTableManager.executeQuery(query);
        } catch (Exception e) {
            System.err.println("Query execute field!");
        }
    }
}
