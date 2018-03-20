package environment;

/**
 * @Author: Yipeng.Zhang
 * @Description:
 * @Date: Created in 15:59 2018/3/19
 * @param:
 */
public class Setting {
    public static int MAXLENGTH;
    public static String SYSTEM;
    public static String ROOT;
    public static String HEAP_FILE_NAME;
    public static String INPUT_FILE_SEPARATOR = "\t";


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
