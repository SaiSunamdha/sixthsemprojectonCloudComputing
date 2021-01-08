package com.secure.data.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jms.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.secure.data.bean.Bean;
import com.secure.data.dao.SecurityDAO;

public class TrustAcceptSecretKey extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Bean b = new Bean();
		b.setUid(Integer.parseInt(request.getParameter("uid")));
		b.setFid(Integer.parseInt(request.getParameter("fid")));
		b.setPublickey(request.getParameter("key"));
		try{
		int i = new SecurityDAO().trustUpdateUserKey(b);
		if(i!=0)
		{
			request.setAttribute("seccess", "Successfully Updated");
		}
		else
		{
			request.setAttribute("fail", "Not Updated");
		}
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Some Internal Error");
		}
		RequestDispatcher rd = request.getRequestDispatcher("tsecretkeypermission.jsp");
		rd.include(request,response);
	}
}