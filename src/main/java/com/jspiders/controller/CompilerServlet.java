package com.jspiders.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspiders.logics.compiler.CompileSourceInMemory;
import com.oracle.jrockit.jfr.RequestDelegate;

public class CompilerServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String codes = req.getParameter("codes");
		System.out.println(codes);
		ArrayList<String> output = (ArrayList<String>)CompileSourceInMemory.compile(codes);
		
		RequestDispatcher rd = req.getRequestDispatcher("/results");
		req.setAttribute("output", output);
		rd.forward(req, resp);
		
		/*resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html>"
				+ "<body>"
				+ "<h3>"
				+ output
				+ "</h3>"
				+ "</body>"
				+ "</html>");*/
	}
}
