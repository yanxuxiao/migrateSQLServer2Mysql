package com.kingshine.util;

import java.io.File;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Global {
	public static String KEY = "king!@>)" ;
	/**
	 *字符串是否为空
	 * @param text
	 * @return
	 */
	public static boolean isEmpty(String text){
		try {
			if(null==text||"".equals(text.trim())){
				return true ;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean isNotEmpty(String text){
		try {
			if(null!=text&&!"".equals(text.trim())){
				return true ;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 3DES解密
	 * 
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String Decrypt(String message, String key) throws Exception{
		byte[] bytesrc = convertHexString(message);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte);
	}

	/**
	 * 3DES加密
	 * 
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String Encrypt(String message, String key)  throws Exception{
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		return toHexString(cipher.doFinal(message.getBytes("UTF-8")))
				.toUpperCase();
	}
	/**
	 * Byte数组转字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}
		return hexString.toString();
	}
	/**
	 * 字符串转Byte数组
	 * 
	 * @param ss
	 * @return
	 */
	private static byte[] convertHexString(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}
		return digest;
	}
	/**
	 * 获取配置
	 * @return
	 */
	public static String[] commonGetProperties(String fileName){
		try {
			File file = new File(fileName) ;
			if(!file.exists()){
				return null ;
			}
			List<String> result = FileUtil.readList(file) ;
			String[] items = null ;
			if(result.size()>0){
				items = result.get(0).split(",") ;
			}
			return items ;
		} catch (Exception e) {
			e.printStackTrace();
			return null ;
		}
	}
}
