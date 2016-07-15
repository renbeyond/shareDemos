package com.example.demo.peng.retrofitwithokhttp.pictureworks;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密解密
 *
 * @author bauer_bao
 */
public class AESKeyHelper {

    /**
     * aes加密字节数组到文件
     *
     * @param sourceData        源数据
     * @param encryptedFilePath 目标文件
     * @param aesKey            16字节密码
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public static void encrypt(byte[] sourceData, String encryptedFilePath, String aesKey) throws Exception {
        // Here you read the cleartext.
//        FileInputStream fis = new FileInputStream(sourceFilePath);
        // This stream write the encrypted text. This stream will be wrapped by another stream.
        FileOutputStream fos = new FileOutputStream(encryptedFilePath);

        // Length is 16 byte
        // Careful when taking user input!!! http://stackoverflow.com/a/3452620/1188357
        SecretKeySpec sks = new SecretKeySpec(getRawKey(aesKey.getBytes()), "AES");
        // Create cipher
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sks);
        // Wrap the output stream
        CipherOutputStream cos = new CipherOutputStream(fos, cipher);
        // Write bytes
        cos.write(sourceData);
//        int b;
//        byte[] d = new byte[8];
//        
//        while((b = fis.read(d)) != -1) {
//            cos.write(d, 0, b);
//        }
        // Flush and close streams.
        cos.flush();
        cos.close();
//        fis.close();
    }

    /**
     * aes解密文件到字节数组
     *
     * @param sourceFilePath 目标文件
     * @param aesKey         key
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public static byte[] decrypt(String sourceFilePath, String aesKey) throws Exception {
        FileInputStream fis = new FileInputStream(sourceFilePath);

        ByteArrayOutputStream outs = new ByteArrayOutputStream(1000);
        SecretKeySpec sks = new SecretKeySpec(getRawKey(aesKey.getBytes()), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, sks);
        CipherInputStream cis = new CipherInputStream(fis, cipher);
        int b;
        byte[] d = new byte[8];
        while ((b = cis.read(d)) != -1) {
            outs.write(d, 0, b);
        }
        outs.flush();
        outs.close();
        cis.close();
        return outs.toByteArray();
    }

    /**
     * 加密文件到新的文件
     *
     * @param sourceFilePath    原始文件
     * @param encryptedFilePath 加密后文件
     * @param aesKey            key
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public static void encrypt(String sourceFilePath, String encryptedFilePath, String aesKey) throws Exception {
        // Here you read the cleartext.
        FileInputStream fis = new FileInputStream(sourceFilePath);
        // This stream write the encrypted text. This stream will be wrapped by another stream.
        FileOutputStream fos = new FileOutputStream(encryptedFilePath);

        // Length is 16 byte
        // Careful when taking user input!!! http://stackoverflow.com/a/3452620/1188357
        SecretKeySpec sks = new SecretKeySpec(getRawKey(aesKey.getBytes()), "AES");
        // Create cipher
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sks);
        // Wrap the output stream
        CipherOutputStream cos = new CipherOutputStream(fos, cipher);
        // Write bytes
        int b;
        byte[] d = new byte[8];
        while ((b = fis.read(d)) != -1) {
            cos.write(d, 0, b);
        }
        // Flush and close streams.
        cos.flush();
        cos.close();
        fis.close();
    }

    /**
     * aes解密文件到文件
     *
     * @param sourceFilePath    原始文件
     * @param decryptedFilePath 解密后文件
     * @param aesKey            key
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public static void decrypt(String sourceFilePath, String decryptedFilePath, String aesKey) throws Exception {
        FileInputStream fis = new FileInputStream(sourceFilePath);

        FileOutputStream fos = new FileOutputStream(decryptedFilePath);
        SecretKeySpec sks = new SecretKeySpec(getRawKey(aesKey.getBytes()), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, sks);
        CipherInputStream cis = new CipherInputStream(fis, cipher);
        int b;
        byte[] d = new byte[8];
        while ((b = cis.read(d)) != -1) {
            fos.write(d, 0, b);
        }
        fos.flush();
        fos.close();
        cis.close();
    }

    /**
     * 根据种子生成密钥
     *
     * @param seed
     * @return
     * @throws Exception
     */
    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
//      SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        sr.setSeed(seed);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    /**
     * 结合密钥生成加密后的密文
     *
     * @param raw   key
     * @param input 输入数据
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] raw, byte[] input) throws Exception {
        // 根据上一步生成的密匙指定一个密匙
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        // Cipher cipher = Cipher.getInstance("AES");
        // 加密算法，加密模式和填充方式三部分或指定加密算
        Cipher cipher = Cipher.getInstance("AES");
        // 初始化模式为加密模式，并指定密匙
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        return cipher.doFinal(input);
    }

    /**
     * 根据密钥解密已经加密的数据
     *
     * @param raw       key
     * @param encrypted 加密数据
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        return cipher.doFinal(encrypted);
    }

    /**
     * 加密字符串
     *
     * @param source 原始文字
     * @param seed   seed
     * @return
     */
    public static String encryptString(String source, String seed) {
        byte[] result = null;
        try {
            byte[] rawKey = getRawKey(seed.getBytes());
            result = encrypt(rawKey, source.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toHex(result);

    }

    /**
     * 解密字符串
     *
     * @param encrypted 加密的字符串
     * @param seed      seed
     * @return
     */
    public static String decryptString(String encrypted, String seed) {
        if (encrypted == null)
            return null;
        byte[] result = null;
        try {
            byte[] rawKey = getRawKey(seed.getBytes());
            byte[] enc = toByte(encrypted);
            result = decrypt(rawKey, enc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != result) {
            return new String(result);
        }
        return "";
    }

    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null || buf.length <= 0)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (byte aBuf : buf) {
            appendHex(result, aBuf);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        final String HEX = "0123456789ABCDEF";
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}