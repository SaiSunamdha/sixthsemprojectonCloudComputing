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

public class LoginSer extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int userid=0;
		String username=null;
		String email=null;
		String utype=null;
		Bean b =new Bean();
		b.setEmail(request.getParameter("email"));
		b.setPassword(request.getParameter("password"));
		try{
		ArrayList<Bean> al = new SecurityDAO().login(b);
		for(Bean bb:al)
		{
			userid = bb.getUid();
			username=  bb.getUname();
			email = bb.getEmail();
			utype = bb.getUtype();
		}
		if(al.isEmpty())
		{
			RequestDispatcher rd= request.getRequestDispatcher("Login.jsp?status=Invalid Email or Password");
			rd.include(request, response);
		}
		else if(utype.equals("trust"))
		{
			HttpSession ses = request.getSession();
			ses.setAttribute("userid", userid);
			ses.setAttribute("username", username);
			ses.setAttribute("email", email);
			RequestDispatcher rd= request.getRequestDispatcher("trustHome.jsp?status=Welcome "+username);
			rd.include(request, response);
		}
		else if (utype.equals("csp")) {
			HttpSession ses = request.getSession();
			ses.setAttribute("userid", userid);
			ses.setAttribute("username", username);
			ses.setAttribute("email", email);
			RequestDispatcher rd= request.getRequestDispatcher("cspHome.jsp?status=Welcome "+username);
			rd.include(request, response);
		}
		else if (utype.equals("disseinator")) {
			HttpSession ses = request.getSession();
			ses.setAttribute("userid", userid);
			ses.setAttribute("username", username);
			ses.setAttribute("email", email);
			RequestDispatcher rd= request.getRequestDispatcher("dataDisseminatorHome.jsp?status=Welcome "+username);
			rd.include(request, response);
		}
		else if(utype.equals("dataowner"))
		{
			HttpSession ses = request.getSession();
			ses.setAttribute("userid", userid);
			ses.setAttribute("username", username);
			ses.setAttribute("email", email);
			RequestDispatcher rd= request.getRequestDispatcher("dataownerHome.jsp?status=Welcome "+username);
			rd.include(request, response);
		}
		else if(utype.equals("datauser"))
		{
			HttpSession ses = request.getSession();
			ses.setAttribute("userid", userid);
			ses.setAttribute("username", username);
			ses.setAttribute("email", email);
			RequestDispatcher rd= request.getRequestDispatcher("dataUserHome.jsp?status=Welcome "+username);
			rd.include(request, response);
		}
		else if(utype.equals("datacoowner"))
		{
			HttpSession ses = request.getSession();
			ses.setAttribute("userid", userid);
			ses.setAttribute("username", username);
			ses.setAttribute("email", email);
			RequestDispatcher rd= request.getRequestDispatcher("dataCo-ownerHome.jsp?status=Welcome "+username);
			rd.include(request, response);
		}
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher rd= request.getRequestDispatcher("Login.jsp?status=Some Internal Error");
			rd.include(request, response);
		}
	}
}