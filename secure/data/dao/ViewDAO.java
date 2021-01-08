package com.secure.data.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.secure.data.bean.Bean;
import com.secure.data.util.ContentEncrypt;
import com.secure.data.util.Dbcon;
import com.secure.data.util.MsgEncrypt;
import com.sun.org.apache.regexp.internal.recompile;

public class ViewDAO extends Dbcon {
	Connection con;
public ArrayList<Bean> dataOwnerViewProfile(int userid)throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select userid,username,email,mobile,dod,address from userdetails where userid=?");
	ps.setInt(1, userid);
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean b = new Bean();
		b.setUid(rs.getInt(1));
		b.setUname(rs.getString(2));
		b.setEmail(rs.getString(3));
		b.setMobile(rs.getString(4));
		Date  d = rs.getDate(5);
		SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
		b.setDob(sd.format(d));
		b.setAddress(rs.getString(6));
		al.add(b);
	}
	return al;
}
public ArrayList<Bean> dataOwnerViewFile(int s)throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	MsgEncrypt ms = new MsgEncrypt("passPhrase");
	PreparedStatement ps = con.prepareStatement("select filename,ufile,content,status from ufiles where userid=?");
	ps.setInt(1,s);
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean b = new Bean();
		b.setFname(rs.getString(1));
		b.setImage(rs.getBytes(2));
		System.out.println("aaaa===="+rs.getString(3));
		String  content = ms.decrypt(rs.getString(3));
		if(content!=null)
		{
			System.out.println("Content Decrypted---->"+content);
		}
		b.setContent(content);
		b.setUtype(rs.getString(4));
		al.add(b);
	}
	return al;
}
public ArrayList<Bean> trustViewOwnerFileShare()throws Exception
{
		con=getConnection();
		System.out.println("Connection--DAO----->"+con);
		ArrayList<Bean> al = new ArrayList<Bean>();
		PreparedStatement ps = con.prepareStatement("select USERID,UPLODEDBY,FILENAME,UFILE,CONTENT,FID from ufiles where STATUS='sent to trust'");
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			Bean b = new Bean();
			System.out.println("Bean-----DAO---->"+b);
			b.setUid(rs.getInt(1));
			b.setUname(rs.getString(2));
			b.setFname(rs.getString(3));
			b.setImage(rs.getBytes(4));
			b.setContent(rs.getString(5));
			b.setFid(rs.getInt(6));
			al.add(b);
		}
		return al;
}
public ArrayList<Bean> trustViewPublicKeys()throws Exception
{
		con=getConnection();
		System.out.println("Connection--DAO----->"+con);
		ArrayList<Bean> al = new ArrayList<Bean>();
		PreparedStatement ps = con.prepareStatement("select USERID,UPLODEDBY,FILENAME,UFILE,CONTENT,FID,PUBLICKEY from ufiles");
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			Bean b = new Bean();
			System.out.println("Bean-----DAO---->"+b);
			b.setUid(rs.getInt(1));
			b.setUname(rs.getString(2));
			b.setFname(rs.getString(3));
			b.setImage(rs.getBytes(4));
			b.setContent(rs.getString(5));
			b.setFid(rs.getInt(6));
			b.setPublickey(rs.getString(7));
			al.add(b);
		}
		return al;
}
public ArrayList<Bean> tViewFileRequestFromDUser()throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select USERID,REQUESTEDBY,FID,FNAME from filerequest where key='Not Generated'");
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean b = new Bean();
		b.setUid(rs.getInt(1));
		b.setUname(rs.getString(2));
		b.setFid(rs.getInt(3));
		b.setFname(rs.getString(4));
		al.add(b);
	}
	return al;
}
public ArrayList<Bean> dataUserProfile(int userid)throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select userid,username,email,mobile,dod,address from userdetails where userid=?");
	ps.setInt(1, userid);
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean b = new Bean();
		b.setUid(rs.getInt(1));
		b.setUname(rs.getString(2));
		b.setEmail(rs.getString(3));
		b.setMobile(rs.getString(4));
		Date  d = rs.getDate(5);
		SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
		b.setDob(sd.format(d));
		b.setAddress(rs.getString(6));
		al.add(b);
	}
	return al;
}

public ArrayList<Bean> dataUserViewPublicFiles()throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select FID,userid,UPLODEDBY,FILENAME from ufiles where status='Accepted by Trust' and policy='public'");
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean b = new Bean();
		b.setFid(rs.getInt(1));
		int userid = rs.getInt(2);
		System.out.println("Userid======DAO===>"+userid);
		b.setUid(userid);
		b.setUname(rs.getString(3));
		b.setFname(rs.getString(4));
		al.add(b);
	}
	return al;
}
public ArrayList<Bean> dataUserViewPrivateFiles()throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select FID,userid,UPLODEDBY,FILENAME from ufiles where status='Accepted by Trust' and policy='private'");
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean b = new Bean();
		b.setFid(rs.getInt(1));
		int userid = rs.getInt(2);
		System.out.println("Userid======DAO===>"+userid);
		b.setUid(userid);
		b.setUname(rs.getString(3));
		b.setFname(rs.getString(4));
		al.add(b);
	}
	return al;
}

public ArrayList<Bean> dataUserViewPublicKeys()throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select FID,PUBLICKEY from ufiles where status='Accepted by Trust' and policy='public'");
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean b = new Bean();
		b.setFid(rs.getInt(1));
		b.setPublickey(rs.getString(2));
		al.add(b);
	}
	return al;
}
public ArrayList<Bean> dUserViewPublicFiles(Bean b)throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	MsgEncrypt ms = new MsgEncrypt("passPhrase");
	PreparedStatement ps = con.prepareStatement("select FID,FILENAME,UFILE,CONTENT from ufiles where fid=? and PUBLICKEY=?");
	ps.setInt(1, b.getFid());
	ps.setString(2, b.getPublickey());
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		b.setFid(rs.getInt(1));
		b.setFname(rs.getString(2));
		b.setImage(rs.getBytes(3));
		String  content = ms.decrypt(rs.getString(4));
		if(content!=null)
		{
			System.out.println("Content Decrypted---->"+content);
		}
		b.setContent(content);
		al.add(b);
	}
	return al;
}
public ArrayList<Bean> dataCoownerViewDataUsers()throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select u.USERID,u.USERNAME,u.EMAIL,u.MOBILE,u.ADDRESS from userdetails u where u.utype='dataowner'");
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean b = new Bean();
		b.setUid(rs.getInt(1));
		b.setUname(rs.getString(2));
		b.setEmail(rs.getString(3));
		b.setMobile(rs.getString(4));
		b.setAddress(rs.getString(5));
		al.add(b);
		}
	return al;
}
public ArrayList<Bean> dataCoownerViewDataOwners(int coowner)throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select OWNERID,ownername,status from appendedusers where coownerid=?");
	ps.setInt(1, coowner);
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean b = new Bean();
		b.setUid(rs.getInt(1));
		b.setUname(rs.getString(2));
		b.setUtype(rs.getString(3));
		al.add(b);
		}
	return al;
}
public ArrayList<Bean> dataCoownerViewDataOwnersFiles(int coowner)throws Exception
{
	con=getConnection();
	MsgEncrypt content = new MsgEncrypt("passPhrase");
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select u.userid,u.uplodedby,u.filename,u.ufile,u.content,u.fid,u.policy from ufiles u,appendedusers a where a.OWNERID=u.USERID and a.COOWNERID=? and a.status='Accept'");
	ps.setInt(1, coowner);
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean b = new Bean();
		b.setUid(rs.getInt(1));
		b.setUname(rs.getString(2));
		b.setFname(rs.getString(3));
		b.setImage(rs.getBytes(4));
		b.setContent(content.decrypt(rs.getString(5)));
		b.setFid(rs.getInt(6));
		b.setPolicy(rs.getString(7));
		al.add(b);
		}
	return al;
}
public ArrayList<Bean> dataOwnerViewDataCoOwnersRequest(int owner)throws Exception
{
	con=getConnection();
	MsgEncrypt content = new MsgEncrypt("passPhrase");
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select u.userid,u.username,u.email,u.mobile from userdetails u,APPENDEDUSERS a where a.COOWNERID=u.USERID and a.OWNERID=? and a.status='Requested' and u.UTYPE='datacoowner'");
	ps.setInt(1, owner);
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean b = new Bean();
		b.setUid(rs.getInt(1));
		b.setUname(rs.getString(2));
		b.setEmail(rs.getString(3));
		b.setMobile(rs.getString(4));
		al.add(b);
		}
	return al;
}

public ArrayList<Bean> cspAcceptRequestFromCoOwner()throws Exception
{
	con=getConnection();
	MsgEncrypt content = new MsgEncrypt("passPhrase");
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select u.userid,u.uplodedby,u.filename,u.ufile,u.content,u.fid,u.policy from ufiles u,appendedusers a where a.OWNERID=u.USERID and a.COOWNERID=? and a.status='Accept'");
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean b = new Bean();
		b.setUid(rs.getInt(1));
		b.setUname(rs.getString(2));
		b.setFname(rs.getString(3));
		b.setImage(rs.getBytes(4));
		b.setContent(content.decrypt(rs.getString(5)));
		b.setFid(rs.getInt(6));
		b.setPolicy(rs.getString(7));
		al.add(b);
		}
	return al;
}
public ArrayList<Bean> dataUserViewCSPFiles(int coowner)throws Exception
{
	con=getConnection();
	MsgEncrypt content = new MsgEncrypt("passPhrase");
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select co.DOWNERID,co.DOWNERNAME,co.FID,co.FNAME,co.CONTENT,co.ACCESSPOLICY,co.STATUS,uf.ufile from COOWNERFILES co,UFILES uf where co.REQUESTEDID=? and uf.fid=co.fid");
	ps.setInt(1, coowner);
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean b = new Bean();
		b.setUid(rs.getInt(1));
		b.setUname(rs.getString(2));
		b.setFid(rs.getInt(3));
		b.setFname(rs.getString(4));
		b.setContent(content.decrypt(rs.getString(5)));
		b.setPolicy(rs.getString(6));
		b.setUtype(rs.getString(7));
		b.setImage(rs.getBytes(8));
		al.add(b);
		}
	return al;
}

public ArrayList<Bean> dataOwnerViewCoOwners(int uid)throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select COOWNERID,COOWNERNAME from APPENDEDUSERS where OWNERID=?");
	ps.setInt(1, uid);
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean b = new Bean();
		b.setUid(rs.getInt(1));
		b.setUname(rs.getString(2));
		al.add(b);
		}
	return al;
}
public ArrayList<Bean> cloudVeiwDataCoOwnerRequest()throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select co.DOWNERID,co.DOWNERNAME,co.FID,co.FNAME,co.CONTENT,co.ACCESSPOLICY,co.REQUESTEDBY,co.REQUESTEDID from COOWNERFILES co where STATUS='waiting at csp'");
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean b = new Bean();
		b.setUid(rs.getInt(1));
		b.setUname(rs.getString(2));
		b.setFid(rs.getInt(3));
		b.setFname(rs.getString(4));
		b.setContent(rs.getString(5));
		b.setPolicy(rs.getString(6));
		b.setUtype(rs.getString(7));
		b.setAddress(rs.getString(8));
		al.add(b);
		}
	return al;
}
public ArrayList<Bean> cloudVeiwDataCoOwnerAcceptedFiles()throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select co.DOWNERID,co.DOWNERNAME,co.FID,co.FNAME,co.CONTENT,co.ACCESSPOLICY,co.REQUESTEDBY,co.REQUESTEDID from COOWNERFILES co where STATUS='Accepted'");
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean b = new Bean();
		b.setUid(rs.getInt(1));
		b.setUname(rs.getString(2));
		b.setFid(rs.getInt(3));
		b.setFname(rs.getString(4));
		b.setContent(rs.getString(5));
		b.setPolicy(rs.getString(6));
		b.setUtype(rs.getString(7));
		b.setAddress(rs.getString(8));
		al.add(b);
		}
	return al;
}

public ArrayList<Bean>  trustViewDataUserKeyRequest()throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select userid,requestedby,fid,fname from FILEREQUEST where key='Not Generated'");
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean b = new Bean();
		b.setUid(rs.getInt(1));
		b.setUname(rs.getString(2));
		b.setFid(rs.getInt(3));
		b.setFname(rs.getString(4));
		al.add(b);
	}
	return al;
}
public ArrayList<Bean>  trustViewDataUserKeyAccepted()throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select userid,requestedby,fid,fname, key from FILEREQUEST where not key='Not Generated'");
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean b = new Bean();
		b.setUid(rs.getInt(1));
		b.setUname(rs.getString(2));
		b.setFid(rs.getInt(3));
		b.setFname(rs.getString(4));
		b.setPublickey(rs.getString(5));
		al.add(b);
	}
	return al;
}
public ArrayList<Bean> dataUserViewPrivateKeys(int b)throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select fid,key from FILEREQUEST where USERID=?");
	ps.setInt(1, b);
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean be = new Bean();
		be.setFid(rs.getInt(1));
		be.setPublickey(rs.getString(2));
		al.add(be);
	}
	return al;
}

public ArrayList<Bean> dataUserViewPrivateFiles(int b)throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select fid,fname from FILEREQUEST where userid=? and not key='Not Generated'");
	ps.setInt(1, b);
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean be = new Bean();
		be.setFid(rs.getInt(1));
		be.setFname(rs.getString(2));
		al.add(be);
	}
	return al;
}
public ArrayList<Bean> dataUserSubmitPrivateKey(Bean b)throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	MsgEncrypt msg = new MsgEncrypt("passPhrase");
	PreparedStatement ps = con.prepareStatement("select u.USERID,u.UPLODEDBY,u.FID,u.FILENAME,u.UFILE, u.CONTENT from UFILES u,FILEREQUEST f where u.POLICY='private' and u.status='Accepted by Trust' and f.userid=? and f.FID=u.fid and f.key=?");
	ps.setInt(1, b.getUid());
	ps.setString(2, b.getPublickey());
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean be = new Bean();
		be.setUid(rs.getInt(1));
		be.setUname(rs.getString(2));
		be.setFid(rs.getInt(3));
		be.setFname(rs.getString(4));
		be.setImage(rs.getBytes(5));
		be.setContent(msg.decrypt(rs.getString(6)));
		al.add(be);
	}
	return al;
}
public ArrayList<Bean> dataDisseminatorViewDataOwnerFiles()throws Exception
{
	con=getConnection();
	ArrayList<Bean> al = new ArrayList<Bean>();
	PreparedStatement ps = con.prepareStatement("select * from UFILES where status='Accepted by Trust'");
	ResultSet rs = ps.executeQuery();
	while(rs.next())
	{
		Bean be = new Bean();
		be.setUid(rs.getInt(1));
		be.setUname(rs.getString(2));
		be.setFname(rs.getString(3));
		be.setImage(rs.getBytes(4));
		be.setContent(rs.getString(5));
		be.setAddress(rs.getString(6));
		be.setFid(rs.getInt(7));
		be.setPolicy(rs.getString(8));
		be.setPublickey(rs.getString(9));
		al.add(be);
	}
	return al;
}

}