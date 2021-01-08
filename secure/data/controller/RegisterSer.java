package com.secure.data.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.secure.data.bean.Bean;
import com.secure.data.dao.SecurityDAO;

public class RegisterSer extends HttpServlet {

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Bean b = new Bean();
		b.setUname(request.getParameter("name"));
		b.setPassword(request.getParameter("password"));
		b.setMobile(request.getParameter("mobile"));
		b.setEmail(request.getParameter("email"));
		b.setDob(request.getParameter("date"));
		b.setUtype(request.getParameter("user"));
		b.setAddress(request.getParameter("address"));
		try{
		int i = new SecurityDAO().reg(b);
		if(i!=0)
		{
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp?status=Successfully Signed Up");
			rd.include(request, response);
		}
		else{
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp?status=Not Successfull");
			rd.include(request, response);
		}
		}catch
		(Exception e) {
			e.printStackTrace();
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp?status=Some Internal Error");
			rd.include(request, response);
		}		
	}
 }