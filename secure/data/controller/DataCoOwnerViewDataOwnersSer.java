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

public class DataCoOwnerViewDataOwnersSer extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Bean b = new Bean();
		HttpSession ses = request.getSession();
		b.setUid((Integer)ses.getAttribute("userid"));
		b.setUname((String)ses.getAttribute("username"));
		b.setFid(Integer.parseInt(request.getParameter("uid")));
		b.setFname(request.getParameter("uname"));
		try{
		int i = new SecurityDAO().dataCoOwnerRequest(b);
		System.out.println("I value=====>"+i);
		if(i!=0)
		{
			RequestDispatcher rd = request.getRequestDispatcher("dCoViewDataOwners.jsp?status=SuccessFully Requested");
			rd.include(request, response);
		}
		else
		{
			RequestDispatcher rd = request.getRequestDispatcher("dCoViewDataOwners.jsp?status=Request Already Sent");
			rd.include(request, response);
		}
		}catch (Exception e) {
			RequestDispatcher rd = request.getRequestDispatcher("dCoViewDataOwners.jsp?status=Some Internal Error");
			rd.include(request, response);	
		}
	}
}