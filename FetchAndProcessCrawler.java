package inter;



import java.util.ArrayList;
import java.util.List;

import webdfs.BasicWebCrawler;
import webdfs.CrawlData;
import webdfs.URLFilter;
import webdfs.URLNormalizer;

public class FetchAndProcessCrawler {

	public static final int DEFAULT_MAX_DEPTH = 5;
	public static final int DEFAULT_MAX_DOCS = 1500;
	
	//INSTANCE VARIABLES
	// A reference to the crawled data
	CrawlData crawlData;
	
	// The location dwhere we will store the fetched data
	String rootDir;

	// total number of iterations
    int maxDepth = DEFAULT_MAX_DEPTH;
    
    // max number of pages that will be fetched within every crawl/iteration.
    int maxDocs = DEFAULT_MAX_DOCS; 
        
    List<String> seedUrls;
    
    URLFilter urlFilter;

    public FetchAndProcessCrawler(String dir, int maxDepth, int maxDocs) {
    	
    	rootDir = dir;
    	
    	// @balaji - The root directory is where the crawled data is stored after processing.
    	if ( rootDir == null || rootDir.trim().length() == 0) {
    		
    		String prefix = System.getProperty("iweb2.home");
    		if (prefix == null) {
    			prefix = "..";
    		}

    		rootDir = System.getProperty("iweb2.home")+System.getProperty("file.separator")+"data";
    	}
    	
    	rootDir = rootDir+System.getProperty("file.separator")+"crawl-" + System.currentTimeMillis();
    	
    	this.maxDepth = maxDepth;
    	
    	this.maxDocs = maxDocs;
    	
    	this.seedUrls = new ArrayList<String>();
    	
    	/* default url filter configuration */
    	this.urlFilter = new URLFilter();
    	urlFilter.setAllowFileUrls(true); // @balaji - Crawler is made to crawl both FTP / HTTP urls
    	urlFilter.setAllowHttpUrls(true);
    }
    
    
    
    public void run() {
    	        
    	crawlData = new CrawlData(rootDir); // basically creates all the directories under crawl_timestamp folder

    	System.out.println("CRAWLER NOW WRITES TO THIS DIRECTORY: "+rootDir);
    	
        BasicWebCrawler webCrawler = new BasicWebCrawler(crawlData); // initialises the crawl data
        
        webCrawler.addSeedUrls(getSeedUrls()); //@balaji - to add all the URLs that are initially started as seeds

        webCrawler.setURLFilter(urlFilter); // @balaji - This is basically to know whether crawler needs to search html files or also file protocols
        
    	long t0 = System.currentTimeMillis(); // Fetched the current time 

        /* run crawl */
    	System.out.println("Maximum Depth : " + maxDepth);
    	System.out.println("Maximum Documents to be fetched in each iteration : " + maxDocs);
    	
    	webCrawler.fetchAndProcess(maxDepth, maxDocs);

        System.out.println("Timer (s): [Crawler processed data] --> " + 
                (System.currentTimeMillis()-t0)*0.001); // The total amount of time consumed by the crawler to run
    	
    }
    
    public List<String> getSeedUrls() { 

    	return seedUrls;
    }

    public void addUrl(String val) {
    	URLNormalizer urlNormalizer = new URLNormalizer();
    	seedUrls.add(urlNormalizer.normalizeUrl(val));
    }

    public void setAllUrls() {
    	
    //addUrl("http://www.dmoz.org/Arts/");
    addUrl("http://www.dmoz.org/Sports/Soccer");
    //addUrl("http://www.dmoz.org/Business/");
    //addUrl("http://www.dmoz.org/Recreation/");  
    	
     //addUrl("http://www.dmoz.org/search?q=java");
     //addUrl("http://www.dmoz.org/search?q=.NET");
     //addUrl("http://www.dmoz.org/search?q=asp.net");
     //addUrl("http://www.dmoz.org/search?q=EJB");  
    // addUrl("http://www.dmoz.org/search?q=soccer");
     //addUrl("http://www.dmoz.org/search?q=servlets");
    }
    

    public void setUrlFilter(URLFilter urlFilter) {
        this.urlFilter = urlFilter;
    }
    
    private void setFilesOnlyUrlFilter() {
        // * configure url filter to accept only file:urls 
        URLFilter urlFilter = new URLFilter();
        urlFilter.setAllowFileUrls(true);
        urlFilter.setAllowHttpUrls(false);
        setUrlFilter(urlFilter);
    }
    
   
    
  
    
	/**
	 * @return the rootDir
	 */
	public String getRootDir() {
		return rootDir;
	}

	/**
	 * @param rootDir the rootDir to set
	 */
	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}

	/**
	 * @return the maxNumberOfCrawls
	 */
	public int getMaxNumberOfCrawls() {
		return maxDepth;
	}

	/**
	 * @param maxNumberOfCrawls the maxNumberOfCrawls to set
	 */
	public void setMaxNumberOfCrawls(int maxNumberOfCrawls) {
		this.maxDepth = maxNumberOfCrawls;
	}

	/**
	 * @return the maxNumberOfDocsPerCrawl
	 */
	public int getMaxNumberOfDocsPerCrawl() {
		return maxDocs;
	}

	/**
	 * @param maxNumberOfDocsPerCrawl the maxNumberOfDocsPerCrawl to set
	 */
	public void setMaxNumberOfDocsPerCrawl(int maxNumberOfDocsPerCrawl) {
		this.maxDocs = maxNumberOfDocsPerCrawl;
	}

	/**
	 * @return the crawlData
	 */
	public CrawlData getCrawlData() {
		return crawlData;
	}
    
}
