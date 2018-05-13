import environment.Setting;
import hashtable.HashTableGenerator;

import java.io.File;

/**
 * @Author: Yipeng.Zhang
 * @Description: search record from heap file
 * @Date: Created in 17:45 2018/5/12
 */
public class GenerateHT {

    private static String parameter1; //parameter for -p
    private static int pageSize; //the length of page
    private static int hashTableSize; //the length of block
    private static int mod; // mod - hash function

    public static void main(String[] arg) {
        try {
            GenerateHT generateHT = new GenerateHT();
            extractParameter(arg);
            generateHT.checkParameter();
            generateHT.initializeData();

            //automatically generate hasptables - just for testing
            /*
            for (int i = 65536; i <= 65536; i = i * 2) {
                mod = i;
                generateHT.checkParameter();
                generateHT.initializeData();
            }
            */
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * set setting based on input arg
     *
     * @param arg program arguments
     */
    private static void extractParameter(String[] arg) throws Exception {
        try {
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
            }
        } catch (Exception e) {
            throw new Exception("Incorrectly input parameter!");
        }

    }

    /**
     * Check parameters if they are correct
     */
    private void checkParameter() throws Exception {
        // Validate parameters
        if (pageSize <= 0)
            throw new Exception("Page size cannot be smaller than 1");
        if (mod <= 0)
            throw new Exception("Mod cannot be smaller than 1");
        if (hashTableSize <= 0)
            throw new Exception("Block size cannot be smaller than 1");

        //If all parameters are correct, initialize settings
        Setting setting = new Setting();
        setting.MAX_LENGTH = pageSize;
        setting.MAX_BLOCK_LENGTH = hashTableSize;
        setting.MOD = mod;
        setting.HEAP_FILE_NAME = "heap." + pageSize;
        setting.HASH_FILE = setting.ROOT + File.separator + "HashTables." + pageSize + "." + mod
                + "." + hashTableSize + File.separator;
    }

    private void initializeData() {
        HashTableGenerator hashTableGenerator = new HashTableGenerator();
        hashTableGenerator.loadPage();
    }
}
