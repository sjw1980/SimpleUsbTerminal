package de.kai_morich.simple_usb_terminal;

public class Device {
    public int Number;
    public int Count;
    public int TotalUsage;
    public int Count2;

    public void setByte(byte[] inputData) {
        if (inputData.length == Define.FrameType1Length) {
            this.Number = Utility.getU32(inputData, 2, 3);
            this.Count = Utility.getU32(inputData, 5, 2);
            this.TotalUsage = Utility.getU32(inputData, 7, 3);
            this.Count2 = Utility.getU32(inputData, 10, 3);
        } else if (inputData.length == Define.FrameType2Length) {
            this.Number = Utility.getU32(inputData, 1, 2);
            this.Count = Utility.getU32(inputData, 3, 3);
            this.TotalUsage = Utility.getU32(inputData, 6, 4);
            this.Count2 = Utility.getU32(inputData, 10, 4);
        }
    }

    public byte[] getByte(int typeLength) {
        byte[] returnValue;

        if (typeLength == Define.FrameType1Length) {
            returnValue = new byte[Define.FrameType1Length];

            returnValue[0] = Define.Start1stByte;
            returnValue[1] = Define.Start2ndByte;

            Utility.setU32(this.Number, 2, 3, returnValue);
            Utility.setU32(this.Count, 5, 2, returnValue);
            Utility.setU32(this.TotalUsage, 7, 3, returnValue);
            Utility.setU32(this.Count2, 10, 3, returnValue);

            returnValue[13] = Define.EndByte;
        } else if (typeLength == Define.FrameType2Length) {
            returnValue = new byte[Define.FrameType2Length];

            returnValue[0] = Define.Start1stByte;

            Utility.setU32(this.Number, 1, 2, returnValue);
            Utility.setU32(this.Count, 3, 3, returnValue);
            Utility.setU32(this.TotalUsage, 6, 4, returnValue);
            Utility.setU32(this.Count2, 10, 4, returnValue);

            returnValue[14] = Define.EndByte;
        } else {
            returnValue = new byte[0];
        }

        return returnValue;
    }
}
