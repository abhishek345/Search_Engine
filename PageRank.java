package inter;

import linkandrank.PageRankMatrixBuilder;
import linkandrank.PageRankMatrixH;
import linkandrank.Rank;
import webdfs.CrawlData;

public class PageRank extends Rank {
    
	PageRankMatrixBuilder pageRankBuilder;
	
	public PageRank(CrawlData crawlData) {
        pageRankBuilder = new PageRankMatrixBuilder(crawlData);
        pageRankBuilder.run();     
	}
	
	@Override
	public PageRankMatrixH getH() {
		return pageRankBuilder.getH();
	}
	
}
