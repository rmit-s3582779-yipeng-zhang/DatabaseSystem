import environment.Setting;
import heapfile.HeapFileGenerator;

/**
 * @Author: Yipeng.Zhang
 * @Description: Main class, initialize heap file from csf file
 * @Date: Created in 23:18 2018/3/19
 */
public class dbload {
    private static String parameter1; //parameter for -p
    private static int pageSize; //the length of page
    private static String fileName;

    public static void main(String[] arg) {
        try {
            extractParameter(arg);

            dbload dbload = new dbload();
            dbload.checkParameter();
            dbload.initializeData(fileName);
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
                fileName = arg[i];
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
        Setting.MAX_LENGTH = pageSize;
        Setting.HEAP_FILE_NAME = "heap." + pageSize;
    }

    private void initializeData(String fileName) {
        HeapFileGenerator heapFileGenerator = new HeapFileGenerator();
        heapFileGenerator.initializeHeapFile(fileName);
    }
}
