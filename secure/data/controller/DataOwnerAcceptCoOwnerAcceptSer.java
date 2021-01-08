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

public class DataOwnerAcceptCoOwnerAcceptSer extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession ses = request.getSession();
		Bean b = new Bean();
		int s=  Integer.parseInt(request.getParameter("uid"));
		System.out.println("Uid========>"+s);
		b.setUid(s);
		b.setFid((Integer)ses.getAttribute("userid"));
		try{
		int i = new SecurityDAO().dataOwnerAcceptDataCoOwner(b);
		if(i!=0)
		{
			RequestDispatcher rd = request.getRequestDispatcher("dCo-OwnerRequest.jsp?status=Accepted");
			rd.include(request, response);
		}
		else
		{
			RequestDispatcher rd = request.getRequestDispatcher("dCo-OwnerRequest.jsp?status=Not Accepted");
			rd.include(request, response);
		}
		}catch (Exception e) {
			e.printStackTrace();	
			RequestDispatcher rd = request.getRequestDispatcher("dCo-OwnerRequest.jsp?status=Some Internal Error");
			rd.include(request, response);
		}
	}
}


