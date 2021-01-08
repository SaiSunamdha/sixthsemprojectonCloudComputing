package com.secure.data.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.crypto.Cipher;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.secure.data.bean.Bean;
import com.secure.data.dao.SecurityDAO;
import com.secure.data.util.ContentEncrypt;
import com.secure.data.util.Encrypt;

public class DUploadFileSer extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession ses = request.getSession();
		int uid = (Integer)ses.getAttribute("userid");
		String uploadedby  = (String)ses.getAttribute("username");
		String cont=null;
		try{
			Bean b = new Bean();
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			List li = fileUpload.parseRequest(request);
			FileItem fname=(FileItem) li.get(0);
			b.setFname(fname.getString());
			 FileItem image = (FileItem)li.get(1);
			 byte[] imagefile =  image.get();
			 b.setImage(imagefile);
			 FileItem privacy = (FileItem)li.get(2);
			 b.setPolicy(privacy.getString());
			FileItem content = (FileItem)li.get(3);
			String  conver =  content.getString();
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
			b.setUid(uid);
			b.setUname(uploadedby);
			String key="4567891231";
			b.setPublickey(key);
			int chieperMode=Cipher.ENCRYPT_MODE;
			
			Encrypt cc = new Encrypt();
			int i = cc.encryptDecrypt(key, chieperMode, b);
			if(i!=0)
			{
				RequestDispatcher rd = request.getRequestDispatcher("dupload.jsp?status=Successfully Uploded");
				rd.include(request, response);
			}
			else
			{
				RequestDispatcher rd = request.getRequestDispatcher("dupload.jsp?status=Not Successfull");
				rd.include(request, response);
			}
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd = request.getRequestDispatcher("dupload.jsp?status=Some Internal Error");
			rd.include(request, response);
		}
	}
}