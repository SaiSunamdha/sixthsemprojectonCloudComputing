package com.secure.data.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.secure.data.dao.SecurityDAO;

public class TfileAccept extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int b = (Integer.parseInt(request.getParameter("fid")));
		try{
		int a = new SecurityDAO().tAcceptFile(b);
		System.out.println("Value A======>"+a);
		if(a!=0)
		{
			RequestDispatcher rd = request.getRequestDispatcher("tfileshare1.jsp?status=Acceped Successfully");
			rd.include(request, response);
		}
		else
		{
			RequestDispatcher rd = request.getRequestDispatcher("tfileshare1.jsp?status=Faild To Accept");
			rd.include(request, response);
		}
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd = request.getRequestDispatcher("tfileshare1.jsp?status=Some Internal Error");
			rd.include(request, response);
		}
	}
}