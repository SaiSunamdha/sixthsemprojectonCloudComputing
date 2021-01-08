package com.secure.data.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.secure.data.bean.Bean;
import com.secure.data.dao.SecurityDAO;

public class DataUserRequestFile extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Bean b = new Bean();
		HttpSession session= request.getSession();
		b.setUid((Integer)session.getAttribute("userid"));
		b.setUname((String)session.getAttribute("username"));
		b.setFid(Integer.parseInt(request.getParameter("fid")));
		b.setFname(request.getParameter("fname"));
		try{
		int i = new SecurityDAO().dataUserPrivateFileRequest(b);
		if(i!=0)
		{
			RequestDispatcher rd = request.getRequestDispatcher("dUPrivateFiles.jsp?status=Successfully Requested");
			rd.include(request, response);
		}
		else if(i==0)
		{
			RequestDispatcher rd = request.getRequestDispatcher("dUPrivateFiles.jsp?status=You are already requested");
			rd.include(request, response);
		}
		else
		{
			RequestDispatcher rd = request.getRequestDispatcher("dUPrivateFiles.jsp?status=Not Succesfull");
			rd.include(request, response);
		}
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd = request.getRequestDispatcher("dUPrivateFiles.jsp?status=Some Internal Error");
			rd.include(request, response);
		}
	}
}