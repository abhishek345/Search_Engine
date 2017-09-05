

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import inter.MySearcher;
import inter.PageRank;
import searchresult.SearchResult;
import webdfs.CrawlData;
import inter.PageRank;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class serveit
 */
@WebServlet("/serveit")
public class serveit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public serveit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String query= request.getParameter("query");
		SearchResult src[]=null;
		HashMap<String,String> m= new HashMap<String,String>();
		MySearcher myc;
		
		 
		if (request.getParameter("lucky")!=null)
		{	
		  CrawlData crawler= new CrawlData("C:\\Users\\MANojUTD\\Crawl_data\\crawl-1354672016697");
		  crawler.init();
		  PageRank pagerank = new PageRank(crawler);
		  pagerank.setAlpha(0.99);
		  pagerank.setEpsilon(0.000001);
		  try {
			pagerank.build();
		  } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  } 
		   myc = new MySearcher("C:\\Users\\MANojUTD\\Crawl_data\\crawl-1354672016697\\lucene-index");
		   src=myc.search(query, 50, pagerank);
		   
		   
		}
		
		 else if(request.getParameter("bad")!=null)
		 {
			
			myc = new MySearcher("C:\\Users\\MANojUTD\\Crawl_data\\crawl-1354672016697\\lucene-index");
			src=myc.search(query, 50);
		 }
		
		 if(src.length==0)
		 {
			 String addres="int.html";
			 RequestDispatcher dispatcher = request.getRequestDispatcher(addres);	
		     dispatcher.forward(request, response);
		 }
		 else
		 {

			 for(int i=0;i<10;i++){
				 m.put(src[i].getUrl(), src[i].getTitle());
			 }
		 String address="outputFile.jsp";
		 
		  
	       // request.setAttribute("map",om);
			
		     RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		     session.setAttribute("m", m);  	
		     dispatcher.forward(request, response); 
			
		response.getWriter().println("<html>");
		response.getWriter().println("<head>");
		response.getWriter().println("<title>I am the best coder in the world!!!</title>");
		response.getWriter().println("<body>");
		response.getWriter().println("<table align = 'center' background=#CCCCCC style='color : #FFFFFF'>");
        
	     for(int i=0;i<10;i++){
             
             response.getWriter().println("<tr> <td width=300>");
             response.getWriter().println("<b> <a href=\""+src[i].getUrl()+"\">"+src[i].getTitle()+"</a> </b>");
             response.getWriter().println("</td></tr>");
	     }
	     response.getWriter().println("</table>");
    
		
	
		response.getWriter().println("</body>");
		response.getWriter().println("</html>");
		// response.getWriter().println("<body background = images/music_light.gif>");
		// response.getWriter().println("<H1 style= 'color : #FFFFFF' align='center'>Your Search Results are :</H1>");
        // response.getWriter().println("<img src='images/home.gif' onclick='javascript:history.go(-1)'>");
        // response.getWriter().println("</h1>");
/*
         for(int i=0;i<10;i++){
             response.getWriter().println("URL>>"+src[i].getUrl());
             response.getWriter().println("<table align = 'center' background=#CCCCCC style='color : #FFFFFF'>");
             response.getWriter().println("<tr> <td width=300>");
             response.getWriter().println("<b> <a href=\""+src[i].getUrl()+"\">"+src[i].getTitle()+"</a> </b>");
             response.getWriter().println("</td></tr>");
             response.getWriter().println("</table>");
         }
         response.getWriter().println("</body>");
         request.getRequestDispatcher("/response.jsp").include(request, response);*/
		 }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
