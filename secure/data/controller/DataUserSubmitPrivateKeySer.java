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
import com.secure.data.dao.SecurityDAO;
import com.secure.data.dao.ViewDAO;

public class DataUserSubmitPrivateKeySer extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession ses = request.getSession();
		Bean b = new Bean();
		b.setUid((Integer)ses.getAttribute("userid"));
		b.setFid(Integer.parseInt(request.getParameter("fid")));
		b.setPublickey(request.getParameter("key"));
		try{
		ArrayList<Bean> al = new ViewDAO().dataUserSubmitPrivateKey(b);
		System.out.println("ArrayList------>"+al);
		if(al!=null)
		{
			HttpSession session = request.getSession();
			session.setAttribute("list", al);
			RequestDispatcher rd = request.getRequestDispatcher("dataUserViewPrivateInformation.jsp");
			rd.include(request, response);
		}
		else
		{
			RequestDispatcher rd = request.getRequestDispatcher("dataUserViewPrivateInformation.jsp?status=No Files to display");
			rd.include(request, response);
		}
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd = request.getRequestDispatcher("dataUserViewPrivateInformation.jsp?status=Some Internal Error");
			rd.include(request, response);
		}
	}
}