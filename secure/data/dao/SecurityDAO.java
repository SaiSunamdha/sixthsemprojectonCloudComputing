package com.secure.data.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.secure.data.bean.Bean;
import com.secure.data.util.Dbcon;
import com.secure.data.util.MsgEncrypt;

public class SecurityDAO extends Dbcon {
	Connection con;
	public int reg(Bean b)throws Exception
	{
		con=getConnection();
		int i=0;
		PreparedStatement ps = con.prepareStatement("insert into userdetails(userid,username,password,email,mobile,dod,utype,address)values((select nvl(max(userid),0)+1 from userdetails),?,?,?,?,?,?,?)");
		ps.setString(1, b.getUname());
		ps.setString(2, b.getPassword());
		ps.setString(3, b.getEmail());
		ps.setString(4, b.getMobile());
		String d = b.getDob();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date dd =new Date(sd.parse(d).getTime());
		ps.setDate(5,dd);
		ps.setString(6, b.getUtype());
		ps.setString(7, b.getAddress());
		i=ps.executeUpdate();
		return i;
	}
	public ArrayList<Bean> login(Bean b)throws Exception
	{
		con=getConnection();
		ArrayList<Bean> al = new ArrayList<Bean>();
		PreparedStatement ps = con.prepareStatement("select userid,email,username,utype from userdetails where email=? and password=?");
		ps.setString(1, b.getEmail());
		ps.setString(2, b.getPassword());
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
		Bean bb = new Bean();
		bb.setUid(rs.getInt(1));
		bb.setEmail(rs.getString(2));
		bb.setUname(rs.getString(3));
		bb.setUtype(rs.getString(4));
		al.add(bb);
	}
		return al;
	}
	public int tAcceptFile(int b)throws Exception
	{
		con=getConnection();
		PreparedStatement ps = con.prepareStatement("update ufiles set status='Accepted by Trust' where fid=? and STATUS='sent to trust'");
		ps.setInt(1, b);
		int i =  ps.executeUpdate();
		return i;
	}
	public int dataUserPrivateFileRequest(Bean b)throws Exception
	{
		con=getConnection();
		int uid=0;
		int fid=0;
		PreparedStatement ps1 = con.prepareStatement("select USERID,FID from FILEREQUEST where USERID=?");
		ps1.setInt(1, b.getUid());
		ResultSet rs=ps1.executeQuery();
		while(rs.next())
		{
			uid = rs.getInt(1);
			fid = rs.getInt(2);
		}
		if(uid!=0 && fid!=0)
		{
			return 0;
		}
		int i=0;
		PreparedStatement ps = con.prepareStatement("insert into FILEREQUEST(userid,REQUESTEDBY,FID,FNAME,KEY)values(?,?,?,?,'Not Generated')");
		ps.setInt(1, b.getUid());
		ps.setString(2, b.getUname());
		ps.setInt(3, b.getFid());
		ps.setString(4, b.getFname());
		i=ps.executeUpdate();
		return i;
	}	
	public int dataCoOwnerRequest(Bean b)throws Exception
	{
		con=getConnection();
		int i=0;
		int ownerid =0;
		int coownerid =0;
		PreparedStatement ps = con.prepareStatement("select OWNERID,COOWNERID from APPENDEDUSERS where OWNERID=? and COOWNERID=?");
		ps.setInt(1, b.getFid());
		ps.setInt(2, b.getUid());
		ResultSet rs = ps.executeQuery();
		System.out.println("ResultSet===>"+rs);
		while(rs.next())
		{
			ownerid =rs.getInt(1);
			coownerid = rs.getInt(2);
		}
		if(ownerid==0 && coownerid==0)
		{
			PreparedStatement ps1 = con.prepareStatement("insert into APPENDEDUSERS(OWNERID,COOWNERID,OWNERNAME,COOWNERNAME,STATUS) values(?,?,?,?,'Requested')");
			ps1.setInt(1, b.getFid());
			ps1.setInt(2, b.getUid());
			ps1.setString(3, b.getFname());
			ps1.setString(4, b.getUname());
			i=ps1.executeUpdate();
			System.out.println("Value of i===>"+i);
		}
		else
		{
			return 0;
		}
		return i;
	}
	public int dataOwnerAcceptDataCoOwner(Bean b)throws Exception
	{
		con=getConnection();
		int  uid = b.getUid();
		System.out.println("Uid===DAO===>"+uid);
		PreparedStatement ps = con.prepareStatement("update APPENDEDUSERS set STATUS='Accept' where OWNERID=? and COOWNERID=?");
		ps.setInt(1, b.getFid());
		ps.setInt(2, uid);
		int i =  ps.executeUpdate();
		return i;
	}
	public int CSPAcceptDATACOOwnerRequest(Bean b)throws Exception
	{
		con=getConnection();
		PreparedStatement ps = con.prepareStatement("update COOWNERFILES set STATUS='Accepted' where FID=? and REQUESTEDID=?");
		ps.setInt(1, b.getFid());
		ps.setInt(2, b.getUid());
		int i =  ps.executeUpdate();
		return i;
	}
	public int trustUpdateUserKey(Bean b)throws Exception
	{
		con=getConnection();
		PreparedStatement ps = con.prepareStatement("update FILEREQUEST set KEY=? where FID=? and userid=?");
		ps.setString(1, b.getPublickey());
		ps.setInt(2, b.getFid());
		ps.setInt(3, b.getUid());
		int i =  ps.executeUpdate();
		return i;
	}
	public int datadissminatorUpdateDataOwnerKey(Bean b)throws Exception
	{
		con=getConnection();
		PreparedStatement ps = con.prepareStatement("update UFILES set PUBLICKEY=? where FID=? and userid=?");
		ps.setString(1, b.getPublickey());
		ps.setInt(2, b.getFid());
		ps.setInt(3, b.getUid());
		int i =  ps.executeUpdate();
		return i;
	}
}