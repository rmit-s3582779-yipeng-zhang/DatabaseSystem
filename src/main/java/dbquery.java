import environment.Setting;
import heapfile.HeapFileManager;

import java.io.FileNotFoundException;

/**
 * @Author: Yipeng.Zhang
 * @Description:
 * @Date: Created in 22:50 2018/3/20
 */
public class dbquery {

    private static String query;

    public static void main(String[] arg) {
        try {
            dbquery dbquery = new dbquery();
            dbquery.checkParameter(arg);
            dbquery.executeQuery(Setting.HEAP_FILE_NAME, query);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * check parameters, if they are right, start executing query
     *
     * @param arg program arguments
     */
    private void checkParameter(String[] arg) throws Exception {
        // Validate parameters
        if (arg.length <= 0)
            throw new Exception("Incorrect parameter!");

        try {
            query = "";
            for (int i = 0; i < arg.length - 1; i++) {
                query += arg[i] + " ";
            }
            query = query.substring(0, query.length() - 1);

            Setting.MAX_LENGTH = Integer.valueOf(arg[arg.length - 1]);
            Setting.HEAP_FILE_NAME = "heap." + Setting.MAX_LENGTH;
        } catch (Exception e) {
            throw new Exception("Incorrect parameter!");
        }
    }

    /**
     *  start executing query
     *
     * @param fileName heap file name
     * @param query query key word
     */
    private void executeQuery(String fileName, String query) {
        try {
            HeapFileManager heapFileManager = new HeapFileManager(fileName);
            heapFileManager.executeQuery(query);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Query execute field!");
        }
    }
}
