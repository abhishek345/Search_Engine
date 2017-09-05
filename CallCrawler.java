import inter.*;
import searchresult.*;
import webdfs.CrawlData;
//import course.ir.project.IntegratorEngine.LuceneIndexer;
//import course.ir.project.IntegratorEngine.MySearcher;
//import course.ir.project.IntegratorEngine.PageRank;


public class CallCrawler {
	public static void main(String args[]) throws Exception{
		System.out.println("Hello world");
		/*FetchAndProcessCrawler crawler = new FetchAndProcessCrawler("C:\\Users\\MANojUTD\\Crawl_data", 10,1800);
		crawler.setAllUrls(); // basically this function is used to set the seeds 
		// THe ideal value for fetching the docs is : 5,1500
		crawler.setAllUrls();
		crawler.run();  
		System.out.println("Crawling Completed Succefully");     
		*/
		CrawlData crawler= new CrawlData("C:\\Users\\MANojUTD\\Crawl_data\\crawl-1387016549785");
		CrawlData crawler1= new CrawlData("C:\\Users\\MANojUTD\\Crawl_data\\crawl-1387022804166");
		CrawlData crawler2 = new CrawlData("C:\\Users\\MANojUTD\\Crawl_data\\crawl-1387019596776");
		CrawlData crawler3 = new CrawlData("C:\\Users\\MANojUTD\\Crawl_data\\crawl-1387011779926");
		crawler.init();
		crawler1.init();
		crawler2.init();
		crawler3.init();
		
		/*//System.out.println("crawler.getcrawldata() : " + crawler.getCrawlData());
	 	PageRank pagerank = new PageRank(crawler);
		//PageRank pagerank = new PageRank(crawler.getCrawlData());
		pagerank.setAlpha(0.99);
		pagerank.setEpsilon(0.000001);
		pagerank.build();
		PageRank pagerank1 = new PageRank(crawler1);
		//PageRank pagerank = new PageRank(crawler.getCrawlData());
		pagerank.setAlpha(0.99);
		pagerank.setEpsilon(0.000001);
		pagerank.build();
		
		PageRank pagerank2 = new PageRank(crawler2);
		//PageRank pagerank = new PageRank(crawler.getCrawlData());
		pagerank.setAlpha(0.99);
		pagerank.setEpsilon(0.000001);
		pagerank.build();
		
		PageRank pagerank3 = new PageRank(crawler3);
		//PageRank pagerank = new PageRank(crawler.getCrawlData());
		pagerank.setAlpha(0.99);
		pagerank.setEpsilon(0.000001);
		pagerank.build();
		
		

		System.out.println("Completed Page rank \n Running Lucene Indexer now");*/

		LuceneIndexer luceneindexer1 = new LuceneIndexer("C:\\Users\\MANojUTD\\Crawl_data\\crawl-1387016549785",PageRankUtils.getInstance(crawler,"Soccer"));
		LuceneIndexer luceneindexer2 = new LuceneIndexer("C:\\Users\\MANojUTD\\Crawl_data\\crawl-1387022804166",PageRankUtils.getInstance(crawler1,"Travel"));
		LuceneIndexer luceneindexer3 = new LuceneIndexer("C:\\Users\\MANojUTD\\Crawl_data\\crawl-1387019596776",PageRankUtils.getInstance(crawler2,"Photography"));
		LuceneIndexer luceneindexer4 = new LuceneIndexer("C:\\Users\\MANojUTD\\Crawl_data\\crawl-1387011779926",PageRankUtils.getInstance(crawler3,"All"));
		luceneindexer1.run();
		luceneindexer2.run();
		luceneindexer3.run();
		luceneindexer4.run();
		System.out.println("Indexing Complete"); 
		

/*		MySearcher oracle = new MySearcher("C:\\Users\\MANojUTD\\Crawl_data\\crawl-1386982958691\\lucene-index");
		SearchResult sr[]= oracle.search("manchester united",10,PageRankUtils.getInstance(crawler));
		for (int i=0;i<sr.length;i++)
			 System.out.println("url"+sr[i].getUrl());*/
		//System.out.println("RESULTS FOR programming WITH OUT PAGE RANK **************************");
		//oracle.search("hadoop",3);
		//System.out.println("RESULTS FOR TRAVEL WITH PAGE RANK **************************");
		//oracle.search("travel",3,pagerank);  */
	}
}
