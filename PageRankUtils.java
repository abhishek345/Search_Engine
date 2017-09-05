package inter;

import webdfs.CrawlData;

public class PageRankUtils {
	private  static PageRank[]  pageRank = new PageRank[4]; 
	public  static PageRank getInstance(CrawlData crawler,String type){
		int i = 0;
		if(type.equals("Travel")){
			i=1;
		}else if(type.equals("Photography")){
			i=2;
		}else if(type.equals("All")){
			i=3;
		}
		System.out.println("inside pageRank util");
		synchronized(PageRankUtils.class){
			if (pageRank[i] == null) {
				pageRank[i] = new PageRank(crawler);
				pageRank[i].setAlpha(0.99);
				pageRank[i].setEpsilon(0.000001);
				try {
					pageRank[i].build();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		return pageRank[i];
		}
	}

}
