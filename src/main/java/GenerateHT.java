import environment.Setting;
import hashtable.HashTableGenerator;

/**
 * @Author: Yipeng.Zhang
 * @Description:
 * @Date: Created in 17:45 2018/5/12
 */
public class GenerateHT {

    private static String parameter1; //parameter for -p
    private static int pageSize; //the length of page
    private static int hashTableSize; //the length of block
    private static int mod; // mod - hash function
    private static String fileName;

    public static void main(String[] arg) {
        try {
            extractParameter(arg);

            GenerateHT generateHT = new GenerateHT();
            generateHT.checkParameter();
            generateHT.initializeData(fileName);
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
                }
                else if(arg[i].equals("-m")){
                    mod=Integer.valueOf(arg[++i]);
                    continue;
                }
                else if(arg[i].equals("-b")){
                    hashTableSize=Integer.valueOf(arg[++i]);
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
            throw new Exception("Page size cannot smaller than 1");

        //If all parameters are correct, initialize settings
        Setting setting = new Setting();
        setting.MAX_LENGTH = pageSize;
        setting.MAX_BLOCK_LENGTH = hashTableSize;
        setting.MOD = mod;
        setting.HEAP_FILE_NAME = "heap." + pageSize;
    }

    private void initializeData(String fileName) {
        HashTableGenerator hashTableGenerator=new HashTableGenerator();
    }
}
