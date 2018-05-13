package environment;

import java.io.File;

/**
 * @Author: Yipeng.Zhang
 * @Description:
 * @Date: Created in 15:59 2018/3/19
 * @param:
 */
public class Setting {
    public static int MAX_LENGTH; //the length of page
    public static final int MAX_PAGE = 0; //just for testing, only generate the first page, 0 for all pages
    public static final String ENCODING = "UTF-8"; //CSV coding
    public static String SYSTEM;
    public static String ROOT;
    public static String HEAP_FILE_NAME;
    public static String INPUT_FILE_SEPARATOR = "\t";
    public static String HASH_FILE;
    public static int MAX_BLOCK_LENGTH = 6000;
    public static int MOD = 500;


    public Setting() {
        this.ROOT = System.getProperty("user.dir");
        checkOS();
    }

    private void checkOS() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win"))
            SYSTEM = "Win";
        else
            SYSTEM = "Linux";
    }

}
