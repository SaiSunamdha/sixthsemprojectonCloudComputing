package com.secure.data.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DataEncrypt {
	private static SecretKeySpec skey;
	   private static byte[] bkey;
	   
	public static void setkey(String key)
	   {
		
		System.out.println("=========Key of AES==========");
		   
		   MessageDigest msg= null;
		   try{
		   bkey = key.getBytes("UTF-8");
		   msg = MessageDigest.getInstance("SHA-512");
		   bkey = msg.digest(bkey);
		   bkey =  Arrays.copyOf(bkey, 16);
		   skey = new SecretKeySpec(bkey, "AES");
		   }catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		   }catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	   }
	   public String encrypt(String strEncrypt,String secretkey)
	   {
		   try{
			   setkey(secretkey); 
		   Cipher ci = Cipher.getInstance("AES/ECB/PKCS5Padding");
		   ci.init(Cipher.ENCRYPT_MODE, skey);
		   return
		   Base64.getEncoder().encodeToString(ci.doFinal(strEncrypt.getBytes("UTF-8")));
		   }catch (Exception e) {
			   System.out.println("Encrypt Completed===>"+e.toString());
		}
		   return null;
	   }
	   
	 public String  descrypt(String strDecrypt,String secretkey){
		 try{
			 setkey(secretkey);
		 Cipher ci = Cipher.getInstance("AES/ECB/PKCS5Padding");
		   ci.init(Cipher.DECRYPT_MODE, skey);
		   return new String(ci.doFinal(Base64.getDecoder().decode(strDecrypt)));
		 }catch (Exception e) {
			 System.out.println("Error While Decryption Process====>"+e.toString());
		}
		 return null;
	 }
}
