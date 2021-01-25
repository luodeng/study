package com.roden.study.java.security;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.Security;

public class Des2 {
    public static final String secretKey="D6726F45E612850D800FF171875312FD";
    /**
     * DES
     *
     * @param arrBTmp
     * @return
     * @throws Exception
     */
    private static Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];// 创建一个空的8位字节数组（默认值为0）
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) { // 将原始字节数组转换为8位
            arrB[i] = arrBTmp[i];
        }
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");// 生成密钥
        return key;
    }

    /**
     * DES
     *
     * @param strIn
     * @return
     * @throws Exception
     */
    private static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes("UTF-8");
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * DES
     *
     * @param arrB
     * @return
     * @throws Exception
     */
    private static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }
    /**
     * DES方法 0为加密,1为解密
     *
     * @param deskey 密钥
     * @param str 待加密字符串
     * @param type
     *            0为加密,1为解密
     * @return DES Str
     */
    public static String getDES(String str, int type) {
        if (str==null || str.equals("")) {
            return "";
        }
        Cipher encryptCipher = null;
        Cipher decryptCipher = null;
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        try {
            Key key = getKey(secretKey.getBytes("UTF-8"));
            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
//			if (type == 0) { // 0为加密
//			return byteArr2HexStr(encryptCipher.doFinal(str.getBytes("UTF-8")));
//		} else {
//			return new String(decryptCipher.doFinal(hexStr2ByteArr(str)));
//		}
            if (type == 0) { // 0为加密
                return byteArr2HexStr(encryptCipher.doFinal(str.getBytes("UTF-8")));
            } else {
                return new String(decryptCipher.doFinal(hexStr2ByteArr(str)),"UTF-8");
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {

    }
}
