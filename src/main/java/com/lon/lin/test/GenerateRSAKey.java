package com.lon.lin.test;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class GenerateRSAKey {

    private static final String algorithm = "RSA";
    private static final String publicKeyPath = "public.key";
    private static final String privateKeyPath = "private.key";
    private static final String charset = "UTF-8";

    private static final String MD5 = "MD5";
    private static final String SHA1 = "SHA1";

    private static final String AES = "AES/ECB/PKCS5Padding";

    public static void main(String[] args) throws Exception {

        generateKeyToFile(algorithm, publicKeyPath, privateKeyPath);
        // 读取秘钥key
        PrivateKey privateKey = getPrivateKey(privateKeyPath, algorithm);
        System.out.println(Base64.encode(privateKey.getEncoded()));
        // 读取公钥key
        PublicKey publicKey = getPublicKey(publicKeyPath, algorithm);
        System.out.println(Base64.encode(publicKey.getEncoded()));

        //RSA加密解密，公钥加密私钥解密；私钥加密公钥解密
        String input = "三一";
        String encryptRSA = encryptRSA(algorithm, privateKey, input);
        System.out.println("RSA加密密文：" + encryptRSA);

        String decryptRSA = decryptRSA(algorithm, publicKey, encryptRSA);
        System.out.println("RSA解密原文：" + decryptRSA);

        String msgDigestBySHA1 = getDigest(input, SHA1);
        System.out.println("消息摘要sha1:" + msgDigestBySHA1);

        String msgDigestByMD5 = getDigest(input, MD5);
        System.out.println("消息摘要MD5:" + msgDigestByMD5);

        String aesKey = "1234567812345678";
        String encryptAES = encryptAES(input, aesKey, AES);
        System.out.println("AES加密密文:" + encryptAES);
        String decryptAES = decryptAES(encryptAES, aesKey, AES);
        System.out.println("AES解密原文:" + decryptAES);
    }

    /**
     * 获取数字摘要
     * @param input 原文
     * @param algorithm 算法
     * @return
     * @throws Exception
     */
    private static String getDigest(String input, String algorithm) throws Exception {
        // 创建消息摘要对象
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        // 执行消息摘要算法
        byte[] digest1 = digest.digest(input.getBytes());

        return toHex(digest1);
    }

    private static String toHex(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        // 对密文进行迭代
        for (byte b : digest) {
            // 把密文转换成16进制
            String s = Integer.toHexString(b & 0xff);
            // 判断如果密文的长度是1，需要在高位进行补0
            if (s.length() == 1){
                s = "0"+s;
            }
            sb.append(s);
//            System.out.print(s);
        }
        // 使用base64进行转码
        return sb.toString();
//        System.out.println(sb.toString());
    }

    /**
     * 解密数据
     *
     * @param algorithm      : 算法
     * @param encrypted      : 密文
     * @param publicKey            : 密钥
     * @return : 原文
     * @throws Exception
     */
    public static String decryptRSA(String algorithm,Key publicKey,String encrypted) throws Exception{
        // 创建加密对象
        Cipher cipher = Cipher.getInstance(algorithm);
        // 私钥解密
        cipher.init(Cipher.DECRYPT_MODE,publicKey);
        // 使用base64进行转码
        byte[] decode = Base64.decode(encrypted);


        // 使用私钥进行解密
        byte[] bytes1 = cipher.doFinal(decode);
        return new String(bytes1);
    }


    /**
     * 使用密钥加密数据
     *
     * @param algorithm      : 算法
     * @param input          : 原文
     * @param privateKey            : 密钥
     * @return : 密文
     * @throws Exception
     */
    public static String encryptRSA(String algorithm, Key privateKey, String input) throws Exception{
        // 创建加密对象
        Cipher cipher = Cipher.getInstance(algorithm);
        // 对加密进行初始化
        // 第一个参数：加密的模式
        // 第二个参数：你想使用公钥加密还是私钥加密
        // 我想使用私钥进行加密
        cipher.init(Cipher.ENCRYPT_MODE,privateKey);
        // 使用私钥进行加密
        byte[] bytes = cipher.doFinal(input.getBytes());
        return Base64.encode(bytes);
    }

    /**
     * 读取公钥
     * @param publicPath 公钥路径
     * @param algorithm  算法
     * @return
     */
    public static PublicKey getPublicKey(String publicPath, String algorithm) throws Exception{
        String publicKeyString = FileUtils.readFileToString(new File(publicPath), charset);
        // 创建key的工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 创建公钥规则
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKeyString));
        return keyFactory.generatePublic(keySpec);
    }

    /**
     *  读取私钥
     * @param priPath 私钥的路径
     * @param algorithm 算法
     * @return 返回私钥的key对象
     */
    public static PrivateKey getPrivateKey(String priPath, String algorithm) throws Exception{
        String privateKeyString = FileUtils.readFileToString(new File(priPath), charset);
        // 创建key的工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 创建私钥key的规则
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString));
        // 返回私钥对象
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 保存公钥和私钥，把公钥和私钥保存到根目录
     * @param algorithm 算法
     * @param pubPath 公钥路径
     * @param priPath 私钥路径
     */
    private static void generateKeyToFile(String algorithm, String pubPath, String priPath) throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 生成私钥
        PrivateKey privateKey = keyPair.getPrivate();
        // 生成公钥
        PublicKey publicKey = keyPair.getPublic();
        // 获取私钥的字节数组
        byte[] privateKeyEncoded = privateKey.getEncoded();
        // 获取公钥字节数组
        byte[] publicKeyEncoded = publicKey.getEncoded();
        // 使用base64进行编码
        String privateEncodeString = Base64.encode(privateKeyEncoded);
        String publicEncodeString = Base64.encode(publicKeyEncoded);
        System.out.println("\nprivateEncodeString:\n" + privateEncodeString);
        System.out.println("\npublicEncodeString:\n" + publicEncodeString);
        // 把公钥和私钥保存到根目录
        System.out.println("\n秘钥存储路径:" + new File(pubPath).getAbsolutePath() + "\n");
        FileUtils.writeStringToFile(new File(pubPath), publicEncodeString, charset);
        FileUtils.writeStringToFile(new File(priPath), privateEncodeString, charset);

    }

    /**
     * 解密
     * @param encryptDES  密文
     * @param key         密钥
     * @param transformation 加密算法
     * @return
     */
    private static String decryptAES(String encryptDES, String key, String transformation) throws Exception{
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        //Cipher.DECRYPT_MODE:表示解密
        // 解密规则
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
        // 解密，传入密文
        byte[] bytes = cipher.doFinal(Base64.decode(encryptDES));

        return new String(bytes);
    }

    /**
     * 使用DES加密数据
     *
     * @param input          : 原文
     * @param key            : 密钥(DES,密钥的长度必须是8个字节)
     * @param transformation : 获取Cipher对象的算法
     * @return : 密文
     * @throws Exception
     */
    private static String encryptAES(String input, String key, String transformation) throws Exception {
        // 获取加密对象
        Cipher cipher = Cipher.getInstance(transformation);
        // 创建加密规则
        // 第一个参数key的字节
        // 第二个参数表示加密算法
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), "AES");
        // ENCRYPT_MODE：加密模式
        // DECRYPT_MODE: 解密模式
        // 初始化加密模式和算法
        cipher.init(Cipher.ENCRYPT_MODE,sks);
        // 加密
        byte[] bytes = cipher.doFinal(input.getBytes());

        // 输出加密后的数据
        String encode = Base64.encode(bytes);

        return encode;
    }

}
