import environment.Setting;
import heapfile.HeapFileGenerator;

/**
 * @Author: Yipeng.Zhang
 * @Description: Main class, initialize heap file from csf file
 * @Date: Created in 23:18 2018/3/19
 */
public class dbload {
    private static String parameter1; //parameter for -p
    private static int pagesize; //the length of page
    private static String fileName;

    public static void main(String[] arg) {
        try {
            parameter1 = arg[0];
            pagesize = Integer.valueOf(arg[1]);
            fileName = arg[2];

            dbload dbload = new dbload();
            dbload.checkParameter(arg);
            dbload.initializeData(fileName);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void checkParameter(String[] arg) throws Exception {
        // Validate parameters
        if (pagesize <= 0)
            throw new Exception("Incorrect parameter!");

        //If all parameters are correct, initialize settings
        Setting setting = new Setting();
        setting.MAX_LENGTH = pagesize;
        setting.HEAP_FILE_NAME = "heap." + pagesize;
    }

    private void initializeData(String fileName) {
        HeapFileGenerator heapFileGenerator = new HeapFileGenerator();
        heapFileGenerator.initializeHeapFile(fileName);
    }


}
