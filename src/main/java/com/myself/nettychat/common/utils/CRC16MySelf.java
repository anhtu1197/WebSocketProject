package com.myself.nettychat.common.utils;

import java.math.BigInteger;


public class CRC16MySelf {

    /**
     *
     * @param ChannelId
     * @param type
     * @param data
     * @return
     */
    public static String getAllString(String ChannelId,String type,String data){
        String test = ChannelId + type + data;
        String crcString = getCRC(test.getBytes());
        String result = Const.HEAD + test + crcString + Const.TAIL;
        return result;
    }

    /**
     *
     *
     * @param bytes
     * @return {@link String}
     * @since 1.0
     */
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

    /**
     *
     *
     * @return float
     * @since 1.0
     */
    private float parseHex2Float(String hexStr) {
        BigInteger bigInteger = new BigInteger(hexStr, 16);
        return Float.intBitsToFloat(bigInteger.intValue());
    }

    /**
     *
     *
     * @return String
     * @since 1.0
     */
    private String parseFloat2Hex(float data) {
        return Integer.toHexString(Float.floatToIntBits(data));
    }

}
