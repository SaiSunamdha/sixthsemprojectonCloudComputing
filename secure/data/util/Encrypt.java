package com.secure.data.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.secure.data.bean.Bean;

public class Encrypt extends Dbcon {
	static Connection con;
	Cipher ecipher;
	
	public static int   encryptDecrypt(String key, int chieperMode,Bean b) throws Exception, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IOException
	{
		con=getConnection();
		int i = 0;
		DESKeySpec deskey = new DESKeySpec(key.getBytes());
		SecretKeyFactory se = SecretKeyFactory.getInstance("DES");
		SecretKey secretkey  = se.generateSecret(deskey);
		Cipher chiper =Cipher.getInstance("DES/ECB/PKCS5Padding");
		if(chieperMode == Cipher.ENCRYPT_MODE)
		{
			chiper.init(Cipher.ENCRYPT_MODE,secretkey,SecureRandom.getInstance("SHA1PRNG"));
			PreparedStatement ps = con.prepareStatement("insert into ufiles(userid,UPLODEDBY,filename,ufile,content,status,fid,policy,publickey) values(?,?,?,?,?,'sent to trust',(select nvl(max(fid),0)+1 from ufiles),?,?)");
			ps.setInt(1, b.getUid());
			ps.setString(2, b.getUname());
			ps.setString(3, b.getFname());
			ps.setBytes(4, b.getImage());
			ps.setString(5, b.getContent());
			ps.setString(6, b.getPolicy());
			ps.setString(7, b.getPublickey());
			i=ps.executeUpdate();
		}
		else if(chieperMode == Cipher.DECRYPT_MODE)
		{
			chiper.init(Cipher.DECRYPT_MODE,secretkey,SecureRandom.getInstance("SHA1PRNG"));
			//CipherOutputStream cos  = new CipherOutputStream(fo, chiper);
			//write(fi, cos);
		}
		return chieperMode;
	}
	
	public int  dataCoownerSendRequestToCSP(Bean b,String key,int chieperMode)throws Exception
	{
		con=getConnection();
		DESKeySpec deskey = new DESKeySpec(key.getBytes());
		SecretKeyFactory se = SecretKeyFactory.getInstance("DES");
		SecretKey secretkey  = se.generateSecret(deskey);
		Cipher chiper =Cipher.getInstance("DES/ECB/PKCS5Padding");
		int fid = 0;
		int i = 0;
		String reqid=null;
		String status=null;
		PreparedStatement ps1 = con.prepareStatement("select fid,REQUESTEDID,STATUS  from COOWNERFILES where REQUESTEDID=? and fid=?");
		ps1.setInt(1, b.getUid());
		ps1.setInt(2, b.getFid());
		ResultSet rs = ps1.executeQuery();
		while(rs.next())
		{
			fid = rs.getInt(1);
			reqid =  rs.getString(2);
			status =  rs.getString(3);
		}
		if(fid!=0 && reqid!=null)
		{
			return 0;
		}
		else{
		if(chieperMode == Cipher.ENCRYPT_MODE)
		{
			chiper.init(Cipher.ENCRYPT_MODE,secretkey,SecureRandom.getInstance("SHA1PRNG"));
		PreparedStatement ps =con.prepareStatement("insert into COOWNERFILES(DOWNERID,DOWNERNAME,fid,fname,content,ACCESSPOLICY,REQUESTEDBY,REQUESTEDID,STATUS)values(?,?,?,?,?,?,?,?,'waiting at csp')");
		ps.setString(1, b.getAddress());
		ps.setString(2, b.getUtype());
		ps.setInt(3, b.getFid());
		ps.setString(4, b.getFname());
		ps.setString(5, b.getContent());
		ps.setString(6, b.getPolicy());
		ps.setString(7, b.getUname());
		ps.setInt(8, b.getUid());
		i=ps.executeUpdate();
		}
		else if(chieperMode == Cipher.DECRYPT_MODE)
		{
			chiper.init(Cipher.DECRYPT_MODE,secretkey,SecureRandom.getInstance("SHA1PRNG"));
			//CipherOutputStream cos  = new CipherOutputStream(fo, chiper);
			//write(fi, cos);
		}
		}
		return chieperMode;
	}
}