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

public class DataDisseminatorChangeKeySer extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession ses = request.getSession();
		Bean b = new Bean();
		b.setUid(Integer.parseInt(request.getParameter("uid")));
		b.setFid(Integer.parseInt(request.getParameter("fid")));
		b.setPublickey(request.getParameter("key"));
		try{
		int i = new SecurityDAO().datadissminatorUpdateDataOwnerKey(b);
		if(i!=0)
		{
			ses.setAttribute("Success", "Successfully Updated");
		}
		else
		{
			ses.setAttribute("Fail", "Fail to Update");
		}
		}catch (Exception e) {
			e.printStackTrace();
			ses.setAttribute("error", "Some Internal Error");
		}
		RequestDispatcher rd  =request.getRequestDispatcher("dDViewDataOwnerFiles.jsp");
		rd.include(request, response);
	}

}
