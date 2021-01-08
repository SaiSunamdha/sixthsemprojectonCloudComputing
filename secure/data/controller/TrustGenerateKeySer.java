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

public class TrustGenerateKeySer extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try{
		ArrayList<Bean> al = new ViewDAO().trustViewDataUserKeyRequest();
		if(al!=null)
		{
			HttpSession ses = request.getSession();
			ses.setAttribute("list", al);
			RequestDispatcher rd = request.getRequestDispatcher("tsecretkeypermission.jsp");
			rd.include(request, response);
		}
		else
		{
			RequestDispatcher rd = request.getRequestDispatcher("tfileshare1.jsp?status=No Files To Display");
			rd.include(request, response);
		}
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd = request.getRequestDispatcher("tfileshare1.jsp?status=Some Internal Error");
			rd.include(request, response);
		}
	}
}