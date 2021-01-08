package com.secure.data.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.jms.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.secure.data.bean.Bean;
import com.secure.data.dao.ViewDAO;

public class DataUserSubmitPublicKeySer extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession ses = request.getSession();
		ses.getAttribute("userid");
		Bean b = new Bean();
		int fid = (Integer.parseInt(request.getParameter("fid")));
		String key = request.getParameter("key");
		b.setFid(fid);
		b.setPublickey(key);
		try{
		ArrayList<Bean> al = new ViewDAO().dUserViewPublicFiles(b);
		if(al.isEmpty())
		{
			
			RequestDispatcher rd = request.getRequestDispatcher("dUPblicFiles.jsp?status=Key Not Matched");
			rd.include(request, response);
		}
		else
		{
			HttpSession session =request.getSession();
			session.setAttribute("list", al);
			RequestDispatcher rd = request.getRequestDispatcher("dataUserDownloadPublicFile.jsp?");
			rd.include(request, response);
		}
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd = request.getRequestDispatcher("dUPblicFiles.jsp?status=Some Internal Error");
			rd.include(request, response);
		}
}
}