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

    public byte[] intToByte(int number) {
        byte[] targets = new byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((number >>> offset) & 0xff);
        }
        return targets;
    }

    public byte[] shortToByte(short number) {
        byte[] targets = new byte[2];
        for (int i = 0; i < 2; i++) {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((number >>> offset) & 0xff);
        }
        return targets;
    }

    public byte[] longToByte(long number) {
        byte[] targets = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((number >>> offset) & 0xff);
        }
        return targets;
    }

    public static byte[] doubleToByte(double number) {
        long value = Double.doubleToRawLongBits(number);
        byte[] targets = new byte[8];
        for (int i = 0; i < 8; i++) {
            targets[i] = (byte) ((value >> 8 * i) & 0xff);
        }
        return targets;
    }
}
