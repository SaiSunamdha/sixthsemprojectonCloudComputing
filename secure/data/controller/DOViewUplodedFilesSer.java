package com.secure.data.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.secure.data.bean.Bean;
import com.secure.data.dao.ViewDAO;

public class DOViewUplodedFilesSer extends HttpServlet {

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession ses = request.getSession();
		int uid = (Integer)ses.getAttribute("userid");
		try{
		ArrayList<Bean> al = new ViewDAO().dataOwnerViewFile(uid);
		if(al.isEmpty())
		{
			RequestDispatcher rd = request.getRequestDispatcher("dViewUplodedFiles.jsp?status=No Files to Display");
			rd.include(request, response);
		}
		else
		{
			HttpSession session = request.getSession();
			session.setAttribute("list", al);
			RequestDispatcher rd = request.getRequestDispatcher("dViewUplodedFiles.jsp");
			rd.include(request, response);
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
		 
	}

}
