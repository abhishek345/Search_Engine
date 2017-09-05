package inter;

import lucene.LuceneIndexBuilder;
import webdfs.CrawlData;
import webdfs.CrawlDataProcessor;
import utils.FileUtils;

import java.io.File;

public class LuceneIndexer {
	
	private String baseDir;
	
	private String luceneIndexDir;
	
	private PageRank pr;
	
	public LuceneIndexer(String dir) {
		
		baseDir = dir;
		luceneIndexDir = baseDir+System.getProperty("file.separator")+"lucene-index";
	}
	
    public LuceneIndexer(String dir,PageRank pr) {
		
		baseDir = dir;
		this.pr=pr;
		luceneIndexDir = baseDir+System.getProperty("file.separator")+"lucene-index";
	}
	
    public void run() {
    
        // load existing data
        CrawlData crawlData = new CrawlData(baseDir);
        crawlData.init(); 
        
        File luceneIndexRootDir = new File(getLuceneDir());
        
        // Delete the index directory, if it exists
        FileUtils.deleteDir(luceneIndexRootDir);
        luceneIndexRootDir.mkdirs();
        
        CrawlDataProcessor luceneIndexBuilder = new LuceneIndexBuilder(luceneIndexRootDir, crawlData);
        
        System.out.print("Starting the indexing ... ");
        
        luceneIndexBuilder.run();

        System.out.println("Indexing completed! \n");        
    }
    
    public String getLuceneDir() {
    	
    	return luceneIndexDir;
    }
    
}
