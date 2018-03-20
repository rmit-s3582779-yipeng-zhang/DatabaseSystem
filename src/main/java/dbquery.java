import environment.Setting;
import heapfile.HeapFileManager;

/**
 * @Author: Yipeng.Zhang
 * @Description:
 * @Date: Created in 22:50 2018/3/20
 */
public class dbquery {

    private static int pagesize; //the length of page
    private static String fileName;

    public static void main(String[] arg) {
        try {
            fileName = arg[0];
            pagesize = Integer.valueOf(arg[1]);


            dbquery dbquery = new dbquery();
            dbquery.checkParameter(arg);
            dbquery.getQuery();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public dbquery() {

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

    private void getQuery() {
        String query = "Warby Wares";
        executeQuery(Setting.HEAP_FILE_NAME, query);
    }

    private void executeQuery(String fileName, String query) {
        HeapFileManager heapFileManager = new HeapFileManager(fileName);
        heapFileManager.executeQuery(query);
    }
}
