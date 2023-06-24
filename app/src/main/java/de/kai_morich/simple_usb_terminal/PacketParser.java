package de.kai_morich.simple_usb_terminal;

import java.util.ArrayList;
import java.util.List;

public class PacketParser {
    private static int step = 0;
    private static List<Byte> saved = new ArrayList<>();
    private static long first = System.currentTimeMillis();

    public static byte[] addByte(byte aByte, StringBuilder debuggingLog) {
        byte[] returnValue = new byte[0];
        if (first + 1000 < System.currentTimeMillis()) {
            if (step != 0) {
                debuggingLog.append("데이터 수신 타임아웃: ").append(toHexString(saved)).append(" ");
            }
            step = 0;
        }
        saved.add(aByte);
        switch (step) {
            case 0:
                if (aByte == Define.Start1stByte) {
                    first = System.currentTimeMillis();
                    step++;
                    saved.clear();
                    saved.add(aByte);
                }
                break;

            case 1:
                if (aByte == Define.Start2ndByte) {
                    step++;
                }
                break;

            case 2:
                if (saved.size() >= Define.FrameType1Length) {
                    if (aByte == Define.EndByte) {
                        returnValue = toArray(saved);

                        Device one = new Device();
                        one.setByte(returnValue);
                        debuggingLog.append(String.format("Number: %d, Count: %d, TotalUsage: %d, Count2: %d", one.Number, one.Count, one.TotalUsage, one.Count2));
                    } else {
                        debuggingLog.append("마지막 데이터오류: ").append(toHexString(saved)).append(" ");
                    }

                    step = 0;
                }
                break;

            default:
                step = 0;
                break;
        }

        return returnValue;
    }

    private static byte[] toArray(List<Byte> byteList) {
        byte[] byteArray = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            byteArray[i] = byteList.get(i);
        }
        return byteArray;
    }

    private static String toHexString(List<Byte> byteList) {
        StringBuilder sb = new StringBuilder();
        for (byte b : byteList) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
