package environment;

/**
 * @Author: Yipeng.Zhang
 * @Description:
 * @Date: Created in 15:59 $ 2018/3/19
 * @param:
 */
public class Setting {
    private int maxLength;

    public Setting(){
        maxLength = 4096;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
