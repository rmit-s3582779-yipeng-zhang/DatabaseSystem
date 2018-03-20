import environment.Setting;
import heapfile.HeapFileManager;

/**
 * @Author: Yipeng.Zhang
 * @Description: Main class, initialize heap file from csf file
 * @Date: Created in 23:18 2018/3/19
 * @param: -p, pagesize, datafile
 */
public class dpload {
    private static String parameter1; //parameter for -p
    private static int pagesize; //the length of page
    private static String fileName;

    public static void main(String[] arg) {
        try {
            parameter1 = arg[0];
            pagesize = Integer.valueOf(arg[1]);
            fileName = arg[2];

            dpload dpload = new dpload();
            dpload.checkParameter(arg);
            dpload.initializeData(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkParameter(String[] arg) throws Exception {
        // Validate parameters
        if (pagesize <= 0)
            throw new Exception("Incorrect parameter!");

        //If all parameters are correct, initialize settings
        Setting setting = new Setting();
        setting.MAXLENGTH = pagesize;
        setting.HEAP_FILE_NAME = "heap." + pagesize;
    }

    private void initializeData(String fileName) {
        HeapFileManager heapFileManager = new HeapFileManager();
        heapFileManager.initializeHeapFile(fileName);
    }


}
