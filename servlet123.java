package click;


import inter.MySearcher;

import java.io.IOException;
import java.io.PrintWriter;
import searchresult.*;
import inter.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class servlet123
 */
@WebServlet("/servlet123")
public class servlet123 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servlet123() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 response.setContentType("text/html");
		 PrintWriter out = response.getWriter();
		 String username= request.getParameter("searchKey");
		 System.out.println("inside serv "+username);
		 MySearcher m = new MySearcher("C:\\Users\\MANojUTD\\Crawl_data\\crawl-1354473094008\\lucene-index");
		   SearchResult sr[]= m.search(username,10);
		   for(int i=0;i<10;i++)
		   {
			   System.out.println("prinitng in servlet"+sr[i].getUrl());
		   }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
