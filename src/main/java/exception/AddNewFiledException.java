package exception;

/**
 * @Author: Yipeng.Zhang
 * @Description: An error has been happen when adding a new field
 * @Date: Created in 16:08 $ 2018/3/19
 * @param:
 */
public class AddNewFiledException extends Exception{
    public AddNewFiledException() {
        super("An error has been happen when adding a new field");
    }
}
