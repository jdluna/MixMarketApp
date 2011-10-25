package com.mambu.xbrl.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Downloads the XML file
 * @author edanilkis
 *
 */
public class DownloadXMLServlet  extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public static String XML_PARAM = "xml";
	
	/**
	 * Takes a HTML table and filename and generates an Excel file out of it
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getCharacterEncoding() == null) {
			req.setCharacterEncoding("UTF-8");
		}
		resp.setCharacterEncoding("UTF-8");

		String xmlContents = new String(req.getParameter(XML_PARAM).getBytes("UTF-8"));
		
		// set correct headers and filename
		resp.setContentType("text/xml");
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + "Mambu XBRL Export" + ".xml\"");
	
		// output
		OutputStream out = resp.getOutputStream();
		
		PrintStream stream = new PrintStream(out);
		stream.write(xmlContents.getBytes());
		stream.flush();
		stream.close();
		
		// write data
		out.close();

	}
}
