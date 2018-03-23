package entity;

/**
 * @Author: Yipeng.Zhang
 * @Description:
 * @Date: Created in 16:28 2018/3/23
 */
public class Translater {
    public short byteToShort(byte[] b) {
        short result;
        result = (short) (b[0] << 8 | b[1] & 0xff);
        return result;
    }

    public long byteToLong(byte[] b) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result <<= 8;
            result |= (b[i] & 0xff);
        }
        return result;
    }
}
