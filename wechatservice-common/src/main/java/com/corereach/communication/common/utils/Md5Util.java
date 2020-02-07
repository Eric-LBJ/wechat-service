package com.corereach.communication.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

/**
 * @author ga.zhang
 */
public class Md5Util {

	/**
	 * @Description: 对字符串进行md5加密 
	 */
	public static String getMd5Str(String strValue) {
		MessageDigest md5;
		String newStr = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			newStr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return newStr;
	}

//	public static void main(String[] args) {
//		try {
//			String md5 = getMd5Str("imooc");
//			System.out.println(md5);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
