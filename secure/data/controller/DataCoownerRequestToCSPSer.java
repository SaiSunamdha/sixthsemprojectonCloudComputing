package com.secure.data.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.crypto.Cipher;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.secure.data.bean.Bean;
import com.secure.data.util.ContentEncrypt;
import com.secure.data.util.Encrypt;

public class DataCoownerRequestToCSPSer extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String key="1234567891";
		String cont=null;
		int chieperMode=Cipher.ENCRYPT_MODE;
		HttpSession ses = request.getSession();
		Bean b = new Bean();
		b.setUid((Integer)ses.getAttribute("userid"));
		b.setUname((String)ses.getAttribute("username"));
		b.setAddress(request.getParameter("uid"));
		b.setUtype(request.getParameter("uname"));
		int fid = Integer.parseInt(request.getParameter("fid"));
		System.out.println("Fid---->"+fid);
		b.setFid(fid);
		b.setFname(request.getParameter("fname"));
		String conver = request.getParameter("content");
		b.setPolicy(request.getParameter("policy"));
		ContentEncrypt c = new ContentEncrypt(conver);
		if(conver!=null)
		{
			cont =  c.encrypt(conver);
			if(cont!=null)
			{
				System.out.println("Content Encrypted");
			}
			else
			{
				System.out.println("Not Encrypted");
			}
		}
		b.setContent(cont);
		try{
		int i = new Encrypt().dataCoownerSendRequestToCSP(b, key, chieperMode);
		if(i!=0)
		{
			RequestDispatcher rd = request.getRequestDispatcher("dCoAppendedDataOwnerFiles.jsp?status=Request Sent Successfull");
			rd.include(request, response);
		}
		else
		{
			RequestDispatcher rd = request.getRequestDispatcher("dCoAppendedDataOwnerFiles.jsp?status=Your Request Already Sent");
			rd.include(request, response);
		}
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd = request.getRequestDispatcher("dCoAppendedDataOwnerFiles.jsp?status=Some Internal Error");
			rd.include(request, response);
		}
	}
}