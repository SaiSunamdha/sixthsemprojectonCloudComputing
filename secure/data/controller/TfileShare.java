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

public class TfileShare extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession  ses = request.getSession();
		String s = (String)ses.getAttribute("username");
		try{
		ArrayList<Bean> al = new ViewDAO().trustViewOwnerFileShare();
		System.out.println("ArrayList------>"+al);
		if(al.isEmpty())
		{
			RequestDispatcher rd = request.getRequestDispatcher("tfileshare1.jsp?status=No Files to Display");
			rd.include(request, response);
		}
		else
		{
			HttpSession session = request.getSession();
			session.setAttribute("list", al);
			RequestDispatcher rd = request.getRequestDispatcher("tfileshare.jsp");
			rd.include(request, response);
		}
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd = request.getRequestDispatcher("tfileshare1.jsp?status=Some Internal Error");
			rd.include(request, response);
		}
	}

}
