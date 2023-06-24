package de.kai_morich.simple_usb_terminal;

public class Utility {
    public static int getU32(byte[] inputData, int offset, int length) {
        int returnValue = 0;

        for (int index = 0; index < length; index++) {
            returnValue <<= 8;
            returnValue += (inputData[offset + index] & 0xFF);
        }

        return returnValue;
    }

    public static void setU32(int value, int offset, int length, byte[] returnValue) {
        for (int index = 0; index < length; index++) {
            returnValue[offset + length - 1 - index] = (byte) (value & 0xFF);
            value >>= 8;
        }
    }
}
