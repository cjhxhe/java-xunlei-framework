package com.xunlei.framework.common.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 加密工具类型
 */
public class EncryptUtils {

    private static final Logger LOG = LoggerFactory.getLogger(EncryptUtils.class);

    /**
     * 自定义加密、解密规则
     */
    private static Map<String, Object> simEncryptMap = MapUtils.gmap("9", "0", "z", "a", "Z", "A");
    private static Map<String, Object> simDecryptMap = MapUtils.gmap("0", "9", "a", "z", "A", "Z");

    /**
     * md5加密字符串
     *
     * @param input
     * @return
     */
    public static String md5(String input) {
        if (StringUtils.isBlank(input)) {
            return null;
        }
        try {
            return EncryptUtils.md5(input.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * md5加密字符串
     *
     * @param input
     * @return
     */
    public static String md5(byte[] input) {
        byte[] digesta;
        try {
            MessageDigest alga = MessageDigest.getInstance("MD5");
            alga.update(input);
            digesta = alga.digest();
            return ByteUtils.byteToHexStr(digesta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * SHA1加密字符串
     *
     * @param str
     * @return
     */
    public static String encryptBySHA1(String str) {
        try {
            //指定sha1算法
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            //获取字节数组
            byte[] messageDigest = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString().toLowerCase();

        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    /**
     * AES加密字符串
     *
     * @param content  需要被加密的字符串
     * @param password 加密需要的密码
     * @return 密文
     */
    public static String encryptByAES(String content, String password) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return ByteUtils.byteToHexStr(cipher.doFinal(byteContent)).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密AES加密过的字符串
     *
     * @param content  AES加密过过的内容
     * @param password 加密时的密码
     * @return 明文
     */
    public static String decryptByAES(String content, String password) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(ByteUtils.hexStrToByte(content));
            return new String(result, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES加密(ECB模式)
     *
     * @param content   原始内容
     * @param secretKey 密钥(不足16位补0，超出16位截取)
     * @return
     */
    public static String encryptByAESWithECB(String content, String secretKey) {
        try {
            Cipher cipher = EncryptUtils.getAESECBCipher(content, secretKey, Cipher.ENCRYPT_MODE);
            return Base64.encodeBase64String(cipher.doFinal(content.getBytes("utf-8")));
        } catch (Exception e) {
            LOG.error("AES加密失败，原因：", e);
        }
        return null;
    }

    /**
     * AES解密(ECB模式)
     *
     * @param content   Base64编码后的字符串
     * @param secretKey 密钥(不足16位补0，超出16位截取)
     * @return
     */
    public static String decryptByAESWithECB(String content, String secretKey) {
        try {
            Cipher cipher = EncryptUtils.getAESECBCipher(content, secretKey, Cipher.DECRYPT_MODE);
            return new String(cipher.doFinal(Base64.decodeBase64(content)));
        } catch (Exception e) {
            LOG.error("AES解密失败，原因：", e);
        }
        return null;
    }

    /**
     * 获取AES密码对象(ECB模式)
     *
     * @param content
     * @param secretKey
     * @param mode
     * @return
     * @throws Exception
     */
    private static Cipher getAESECBCipher(String content, String secretKey, int mode) throws Exception {
        if (StringUtils.isEmpty(content) || StringUtils.isEmpty(secretKey)) {
            return null;
        }
        // 密钥不足16位补0，超出16位截取
        byte[] keyBytes = Arrays.copyOf(secretKey.getBytes("ASCII"), 16);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        // 算法/模式/补码方式
        // 补码方式，ios和js需使用Pkcs7方式
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(mode, keySpec);
        return cipher;
    }

    /**
     * Mysql AES加密
     * <p>
     * 对应Mysql 128位AES加密, 对应函数：HEX(AES_ENCRYPT('content', 'password'))
     *
     * @param content
     * @param password
     * @return
     */
    public static String encryptByMysqlAES(String content, String password) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        try {
            byte[] keyBytes = Arrays.copyOf(password.getBytes("ASCII"), 16);
            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cleartext = content.getBytes("UTF-8");
            byte[] cipherTextBytes = cipher.doFinal(cleartext);
            return ByteUtils.byteToHexStr(cipherTextBytes).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mysql AES解密
     * <p>
     * 对应Mysql 128位AES解密，对应函数：AES_DECRYPT(UNHEX('content'), 'password')
     *
     * @param content
     * @param password
     * @return
     */
    public static String decryptByMysqlAES(String content, String password) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        try {
            byte[] keyBytes = Arrays.copyOf(password.getBytes("ASCII"), 16);
            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] cleartext = ByteUtils.hexStrToByte(content);
            byte[] cipherTextBytes = cipher.doFinal(cleartext);
            return new String(cipherTextBytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Hmacsha256加密字符串
     *
     * @param content 需要加密的内容
     * @param key     密钥
     * @return
     */
    public static byte[] encryptByHmacsha256(String content, String key) {
        return EncryptUtils.encryptByHmacsha(content, key, "HmacSHA256");
    }

    /**
     * Hmacsha1加密字符串
     *
     * @param content 需要加密的内容
     * @param key     密钥
     * @return
     */
    public static byte[] encryptByHmacsha1(String content, String key) {
        return EncryptUtils.encryptByHmacsha(content, key, "HmacSHA1");
    }

    private static byte[] encryptByHmacsha(String content, String key, String algorithm) {
        try {
            byte[] byteKey = key.getBytes("UTF-8");
            Mac hmac = Mac.getInstance(algorithm);
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, algorithm);
            hmac.init(keySpec);
            return hmac.doFinal(content.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 自定义加密
     *
     * @param content
     * @param password
     * @return
     */
    public static String encryptBySIM(String content, String password) {
        return EncryptUtils.generateBySIM(content, password, true);
    }

    /**
     * 自定义解密
     *
     * @param content
     * @param password
     * @return
     */
    public static String decryptBySIM(String content, String password) {
        return EncryptUtils.generateBySIM(content, password, false);
    }

    private static String generateBySIM(String content, String password, boolean isEncrypt) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        StringBuilder sb = new StringBuilder(content);
        int length = content.length();
        List<Integer> indexs = EncryptUtils.getReplaceIndexs(password);
        for (Integer index : indexs) {
            if (index >= length) {
                continue;
            }
            EncryptUtils.replaceAtIndex(sb, index, isEncrypt);
        }
        return sb.toString();
    }

    private static void replaceAtIndex(StringBuilder sb, Integer index, boolean isEncrypt) {
        char ch = sb.charAt(index);
        Object str;
        if (isEncrypt) {
            str = simEncryptMap.get(ch + "");
        } else {
            str = simDecryptMap.get(ch + "");
        }
        if (str == null) {
            str = (char) ((int) ch + (isEncrypt ? 1 : -1));
        }
        sb.replace(index, index + 1, str.toString());
    }

    private static List<Integer> getReplaceIndexs(String password) {
        List<Integer> indexs = new ArrayList<>();
        String[] values = StringUtils.split(password, "|");
        for (String value : values) {
            indexs.add(new Integer(value));
        }
        return indexs;
    }
}
