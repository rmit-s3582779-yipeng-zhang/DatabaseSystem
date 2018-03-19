import exception.ParameterError;

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
        } catch (Exception e) {
            System.err.println("Incorrect parameter!");
        }
    }

    private void checkParameter(String[] arg) throws ParameterError {
        if (pagesize <= 0)
            throw new ParameterError();
    }


}
