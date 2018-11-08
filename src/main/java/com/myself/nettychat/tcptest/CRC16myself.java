package com.myself.nettychat.tcptest;

import java.math.BigInteger;


public class CRC16myself {

    public static void main(String[] args) {
        String test = "F5690137563CC8syyyyyyyyyyyyyyyyynnnnnnn";
        System.out.println("：" + test);
        String crcString = getCRC(test.getBytes());
        System.out.println("str：" + crcString);
        int crc = getCRCInt(test.getBytes());
        System.out.println("hex：" + crc);
        CRC16myself myself = new CRC16myself();
        float crc16 = myself.parseHex2Float(crcString);
        System.out.println("10：" + crc16);
        String crc16String = myself.parseFloat2Hex(crc16);
        System.out.println("：" + crc16String);
        System.out.println("：" + "gz" + test + crcString + "xr");
    }


    public static String getCRC(byte[] bytes) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;
        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        return Integer.toHexString(CRC);
    }


    public static Integer getCRCInt(byte[] bytes) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;
        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        return CRC;
    }


    private float parseHex2Float(String hexStr) {
        BigInteger bigInteger = new BigInteger(hexStr, 16);
        return Float.intBitsToFloat(bigInteger.intValue());
    }


    private String parseFloat2Hex(float data) {
        return Integer.toHexString(Float.floatToIntBits(data));
    }

}
