package com.secure.data.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.Request;

import com.secure.data.bean.Bean;
import com.secure.data.dao.SecurityDAO;

public class CSPAcceptDATAOwnerReqSer extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Bean b = new Bean();
		b.setFid(Integer.parseInt(request.getParameter("fid")));
		b.setUid(Integer.parseInt(request.getParameter("uid")));
		try{
		int i = new SecurityDAO().CSPAcceptDATACOOwnerRequest(b);
		if(i!=0)
		{
			request.setAttribute("Accept", "Successfully Accepted");
		}
		else
		{
			request.setAttribute("fail", "Not Accepted");
		}
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Some Internal Error");
		}
		RequestDispatcher rd  =request.getRequestDispatcher("CSPAcceptRequestfrom.jsp");
		rd.include(request, response);
	}
}