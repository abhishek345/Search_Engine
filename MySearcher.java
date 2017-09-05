package inter;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;

import linkandrank.Rank;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import searchresult.SearchResult;

public class MySearcher {

	/**
	 * An arbitrary small value
	 */
	public static final double EPSILON = 0.0001;

	private static final String PRETTY_LINE = 
		"_______________________________________________________________________";

	private String indexDir;

	
	private boolean verbose = true;


	// The Mysearcher constructor takes input parameter as lucene-indexed directory
	public MySearcher(String indexDir) {
		this.indexDir = indexDir;
	}
	
	@SuppressWarnings({ "deprecation", "null" })
	public SearchResult[] search(String query, int numberOfMatches,String old) {
    System.out.println("inside"+query);

		LinkedHashMap<String, SearchResult> results = new LinkedHashMap<String, SearchResult>();
		IndexSearcher is = null;


		/*try {
			File file = new File(indexDir);
			// First create an object for indexsearcher with lucene-indexed directory as input argument 
			is = new IndexSearcher(FSDirectory.open(file));
			//System.out.println(FSDirectory.getDirectory(indexDir));
		//	System.out.println("INdexed Directory is : " + FSDirectory.getDirectory(indexDir));
		} catch (IOException ioX) {
			System.out.println("ERROR: "+ioX.getMessage());
		}*/

		//QueryParser qp = new QueryParser(Version.LUCENE_36,"content", new StandardAnalyzer(Version.LUCENE_36));
		
		BooleanQuery booleanQuery = new BooleanQuery(); //for boolean retrieval
        //the following lines of code processes the query for boolean retrieval
        String[] queryBits = query.split(" ");
        Query queries[] = new Query[queryBits.length];
        for (int i = 0; i < queryBits.length; i++) {
            queryBits[i] = queryBits[i].toLowerCase();
            queryBits[i] = queryBits[i].trim();
            if (queryBits[i].equals("")) {
                continue;
            }
        }
        for (int i = 0; i < queryBits.length; i++) {
            queries[i] = new TermQuery(new Term("title", queryBits[i]));
            booleanQuery.add(queries[i], BooleanClause.Occur.MUST);
        }
        
        try {
        	Analyzer alyzr;
        	alyzr = new StandardAnalyzer(Version.LUCENE_36);
            // Lucene vector model retrieval from title of pages
            Query qNew = new QueryParser(Version.LUCENE_36, "title", alyzr)
                    .parse(query);
            // Lucene vector model retrieval from content of pageso
            Query q1New = new QueryParser(Version.LUCENE_36, "content", alyzr)
                    .parse(query);
            int hitsPerPage = 10; //10 results were retrieved from each type of retrieval
            File Index = new File(indexDir); //path to index.
            if (!Index.exists()) {
                System.out.println("The specified index path does not exist: " 
                        + Index);
            }
            Directory indexDir = FSDirectory.open(Index); //read index directory.
            IndexReader indexReader = IndexReader.open(indexDir); //read index.
            //open an index searcher index which does the retrieval process.
            IndexSearcher searcher = new IndexSearcher(indexReader);
            //create collectors to collect retrieved page information.
            //three collectors for each type of retrieval.
            TopScoreDocCollector collector = TopScoreDocCollector.create
                    (hitsPerPage, true);
            TopScoreDocCollector collector1 = TopScoreDocCollector.create
                    (hitsPerPage, true);
            TopScoreDocCollector collector2 = TopScoreDocCollector.create
                    (hitsPerPage, true);
            //Search for pages and retrieve in collectors.
            searcher.search(booleanQuery, collector);
            searcher.search(qNew, collector1);
            searcher.search(q1New, collector2);
            //Store the retrieved documents' info.
            ScoreDoc[] hits = collector.topDocs().scoreDocs;
            ScoreDoc[] hits1 = collector1.topDocs().scoreDocs;
            ScoreDoc[] hits2 = collector2.topDocs().scoreDocs;
            
            //add page title and url to output hashmap variable.
            for (int i = 0; i < hits.length; ++i) {
            	int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                SearchResult sr = new SearchResult(String.valueOf(docId),d.get("doctype"),d.get("title"),d.get("url"),(double)hits[i].score);
                if (results.get(d.get("title")) == null) {
                    results.put(d.get("title"), sr);
                }
            }
            //add page title and url to output hashmap variable.
            for (int i = 0; i < hits1.length; ++i) {
                int docId = hits1[i].doc;
                Document d = searcher.doc(docId);
                SearchResult sr = new SearchResult(String.valueOf(docId),d.get("doctype"),d.get("title"),d.get("url"),(double)hits1[i].score);
                if (results.get(d.get("title")) == null) {
                    results.put(d.get("title"), sr);
                }
            }
            //add page title and url to output hashmap variable.
            for (int i = 0; i < hits2.length; ++i) {
                int docId = hits2[i].doc;
                Document d = searcher.doc(docId);
                SearchResult sr = new SearchResult(String.valueOf(docId),d.get("doctype"),d.get("title"),d.get("url"),(double)hits2[i].score);
                if (results.get(d.get("title")) == null) {
                    results.put(d.get("title"), sr);
                }
            }
            //close the searcher instance.
            searcher.close();
            SearchResult[] docResults = new SearchResult[results.keySet().size()];
            int i = 0;
            for(String s:results.keySet()){
            	docResults[i] = results.get(s);
            	i++;
            }
		/*Query q = null;
		try {

			System.out.println("Query received for parsing is : " + query);
			q = qp.parse(query); // Now here, we are converting the Normal query to lucene query
			System.out.println("printing q "+q);
		} catch (ParseException pX) {
			System.out.println("ERROR: "+pX.getMessage());
		}
		Hits hits = null;

		try {
			hits = is.search(q);
			BooleanQuery 
			is.search(query, results);
			int n = hits.length();
			
			docResults = new SearchResult[n]; // INitally we allocated the size as only 1 which is default
											 // but now lets increase the size of this based on user pref

			for (int i = 0; i < n; i++) {

				docResults[i] = new SearchResult(hits.doc(i).get("docid"),
						hits.doc(i).get("doctype"),
						hits.doc(i).get("title"),
						hits.doc(i).get("url"),
						hits.score(i));
			}

			is.close();

		} catch (IOException ioX) {
			System.out.println("ERROR: "+ioX.getMessage());
		}*/

		String header = "Search results using Lucene index scores:";
		boolean showTitle = true;
		printResults(header, "Query: " + query, docResults, showTitle);
		return docResults;
        }
        catch(Exception e){
        	e.printStackTrace();
        }
		return null;
	}

	@SuppressWarnings({ "deprecation", "null" })
	public SearchResult[] search(String query, int numberOfMatches) {
    System.out.println("inside"+query);

		LinkedHashMap<String, SearchResult> results = new LinkedHashMap<String, SearchResult>();
		IndexSearcher is = null;


		/*try {
			File file = new File(indexDir);
			// First create an object for indexsearcher with lucene-indexed directory as input argument 
			is = new IndexSearcher(FSDirectory.open(file));
			//System.out.println(FSDirectory.getDirectory(indexDir));
		//	System.out.println("INdexed Directory is : " + FSDirectory.getDirectory(indexDir));
		} catch (IOException ioX) {
			System.out.println("ERROR: "+ioX.getMessage());
		}*/

		//QueryParser qp = new QueryParser(Version.LUCENE_36,"content", new StandardAnalyzer(Version.LUCENE_36));
		
		BooleanQuery booleanQuery = new BooleanQuery(); //for boolean retrieval
        //the following lines of code processes the query for boolean retrieval
        String[] queryBits = query.split(" ");
        Query queries[] = new Query[queryBits.length];
        for (int i = 0; i < queryBits.length; i++) {
            queryBits[i] = queryBits[i].toLowerCase();
            queryBits[i] = queryBits[i].trim();
            if (queryBits[i].equals("")) {
                continue;
            }
        }
        for (int i = 0; i < queryBits.length; i++) {
            queries[i] = new TermQuery(new Term("title", queryBits[i]));
            booleanQuery.add(queries[i], BooleanClause.Occur.MUST);
        }
        
        try {
        	Analyzer alyzr;
        	alyzr = new StandardAnalyzer(Version.LUCENE_36);
            // Lucene vector model retrieval from title of pages
            Query qNew = new QueryParser(Version.LUCENE_36, "title", alyzr)
                    .parse(query);
            // Lucene vector model retrieval from content of pageso
            Query q1New = new QueryParser(Version.LUCENE_36, "content", alyzr)
                    .parse(query);
            int hitsPerPage = 10; //10 results were retrieved from each type of retrieval
            File Index = new File(indexDir); //path to index.
            if (!Index.exists()) {
                System.out.println("The specified index path does not exist: " 
                        + Index);
            }
            Directory indexDir = FSDirectory.open(Index); //read index directory.
            IndexReader indexReader = IndexReader.open(indexDir); //read index.
            //open an index searcher index which does the retrieval process.
            IndexSearcher searcher = new IndexSearcher(indexReader);
            //create collectors to collect retrieved page information.
            //three collectors for each type of retrieval.
            TopScoreDocCollector collector = TopScoreDocCollector.create
                    (hitsPerPage, true);
            TopScoreDocCollector collector1 = TopScoreDocCollector.create
                    (hitsPerPage, true);
            TopScoreDocCollector collector2 = TopScoreDocCollector.create
                    (hitsPerPage, true);
            //Search for pages and retrieve in collectors.
            searcher.search(booleanQuery, collector);
            searcher.search(qNew, collector1);
            searcher.search(q1New, collector2);
            //Store the retrieved documents' info.
            ScoreDoc[] hits = collector.topDocs().scoreDocs;
            ScoreDoc[] hits1 = collector1.topDocs().scoreDocs;
            ScoreDoc[] hits2 = collector2.topDocs().scoreDocs;
            
            //add page title and url to output hashmap variable.
            for (int i = 0; i < hits.length; ++i) {
            	int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                SearchResult sr = new SearchResult(String.valueOf(docId),d.get("doctype"),d.get("title"),d.get("url"),(double)hits[i].score);
                if (results.get(d.get("title")) == null) {
                    results.put(d.get("title"), sr);
                }
            }
            //add page title and url to output hashmap variable.
            for (int i = 0; i < hits1.length; ++i) {
                int docId = hits1[i].doc;
                Document d = searcher.doc(docId);
                SearchResult sr = new SearchResult(String.valueOf(docId),d.get("doctype"),d.get("title"),d.get("url"),(double)hits1[i].score);
                if (results.get(d.get("title")) == null) {
                    results.put(d.get("title"), sr);
                }
            }
            //add page title and url to output hashmap variable.
            for (int i = 0; i < hits2.length; ++i) {
                int docId = hits2[i].doc;
                Document d = searcher.doc(docId);
                SearchResult sr = new SearchResult(String.valueOf(docId),d.get("doctype"),d.get("title"),d.get("url"),(double)hits2[i].score);
                if (results.get(d.get("title")) == null) {
                    results.put(d.get("title"), sr);
                }
            }
            //close the searcher instance.
            searcher.close();
            SearchResult[] docResults = new SearchResult[results.keySet().size()];
            int i = 0;
            for(String s:results.keySet()){
            	docResults[i] = results.get(s);
            	i++;
            }
		/*Query q = null;
		try {

			System.out.println("Query received for parsing is : " + query);
			q = qp.parse(query); // Now here, we are converting the Normal query to lucene query
			System.out.println("printing q "+q);
		} catch (ParseException pX) {
			System.out.println("ERROR: "+pX.getMessage());
		}
		Hits hits = null;

		try {
			hits = is.search(q);
			BooleanQuery 
			is.search(query, results);
			int n = hits.length();
			
			docResults = new SearchResult[n]; // INitally we allocated the size as only 1 which is default
											 // but now lets increase the size of this based on user pref

			for (int i = 0; i < n; i++) {

				docResults[i] = new SearchResult(hits.doc(i).get("docid"),
						hits.doc(i).get("doctype"),
						hits.doc(i).get("title"),
						hits.doc(i).get("url"),
						hits.score(i));
			}

			is.close();

		} catch (IOException ioX) {
			System.out.println("ERROR: "+ioX.getMessage());
		}*/

		String header = "Search results using Lucene index scores:";
		boolean showTitle = true;
		printResults(header, "Query: " + query, docResults, showTitle);
		return docResults;
        }
        catch(Exception e){
        	e.printStackTrace();
        }
		return null;
	}

	/**
	 * A method that combines the score of an index based search 
	 * and the score of the PageRank algorithm to achieve better 
	 * relevance results.
	 */
	public SearchResult[] search(String query, int numberOfMatches, Rank pR) {

		SearchResult[] docResults = search(query, numberOfMatches); // We are invoking the lucene indexing normal form of search

		String url;

		int n = pR.getH().getSize(); // This indicates the toal size of page ran vector
 
		double m = 1 - (double) 1/n; // Calculating the scaling factor for efficient relevance

		// actualNumberOfMatches <= numberOfMatches
		int actualNumberOfMatches = docResults.length; // obtained from the lucene index scoring

		for (int i = 0; i < actualNumberOfMatches; i++) {

			url = docResults[i].getUrl();

			double hScore = docResults[i].getScore() * Math.pow(pR.getPageRank(url),m);

			// Update the score of the results
			docResults[i].setScore(hScore); 
		}

		// sort results by score
		SearchResult.sortByScore(docResults);

		String header = "Search results using combined Lucene scores and page rank scores:";
		boolean showTitle = false;
		printResults(header, "Query: " + query, docResults, showTitle);

		return docResults;
	}


	// END OF IMPLEMENTATION - LUCENCE INDEXING + PAGE RANKING LOGIC ;-)
	
	/**
	 * A method that combines the score of an index based search 
	 * and the score of the PageRank algorithm to achieve better 
	 * relevance results, while personalizing the result set based on
	 * past user clicks on the same or similar queries.
	 * 
	 * NOTE: You would typically refactor all these search methods 
	 *       in order to consider it production quality code. Here,
	 *       we repeat the code of the previous method, so that it 
	 *       is easier to read.
	 *       
	 * @param userID identifies the person who issues the query
	 * @param query is the whole query
	 * @param numberOfMatches defines the maximim number of desired matches 
	 * @param pR the PageRank vector
	 * @return the result set
	 */
	/*public SearchResult[] search(UserQuery uQuery, int numberOfMatches, Rank pR) {

		SearchResult[] docResults = search(uQuery.getQuery(), numberOfMatches);

		String url;

		int docN = docResults.length;

		if (docN > 0) {

			int loop = (docN<numberOfMatches) ? docN : numberOfMatches;

			for (int i = 0; i < loop; i++) {

				url = docResults[i].getUrl();

				UserClick uClick = new UserClick(uQuery,url);

				/**
				 * TODO: 2.6 -- Weighing the scores to meet your needs (Book Section 2.4.2)
				 * 
				 *       At this point, we have three scores of relevance. The relevance score that
				 *       is based on the index search, the PageRank score, and the score that is based
				 *       on the user's prior selections. There is no golden formula for everybody.
				 *       Below we are selecting a formula that we think would make sense for most people.
				 *       
				 *       Feel free to change the formula, experiment with different weighting factors,
				 *       to find out the choices that are most appropriate for your own site.
				 * 
				 */				
/*				double indexScore     = docResults[i].getScore();

				double pageRankScore  = pR.getPageRank(url);

				
				double userClickScore = 0;

				// Create the final score
				double hScore;

				if (userClickScore == 0) {

					hScore = indexScore * pageRankScore * EPSILON;

				} else {

					hScore = indexScore * pageRankScore * userClickScore;
				}

				// Update the score of the results
				docResults[i].setScore(hScore);
			}
		}		

		// Sort array of results
		SearchResult.sortByScore(docResults);

		String header = "Search results using combined Lucene scores, " + 
		"page rank scores and user clicks:";
		String query = "Query: user=" + uQuery.getUid() + ", query text=" + uQuery.getQuery();
		boolean showTitle = false;
		printResults(header, query, docResults, showTitle);
		
		return docResults;
	}*/

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	private void printResults(String header, String query, SearchResult[] values, boolean showDocTitle) {

		if( verbose ) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);

			boolean printEntrySeparator = false;
			if( showDocTitle ) { // multiple lines per entry
				printEntrySeparator = true;
			}

			pw.print("\n");
			pw.println(header); // PRINT : "Search results using lucene index scores / blah blah blah" 
			
			if( query != null ) {
				pw.println(query); // PRINT : Query given by the user .
			}
			pw.print("\n");
			for(int i = 0, n = values.length; i < n; i++) { 
				if( showDocTitle ) {
					pw.printf("Document Title: %s\n", values[i].getTitle());
				}
				pw.printf("Document URL: %-46s  -->  Relevance Score: %.15f\n", 
						values[i].getUrl(), values[i].getScore());
				if( printEntrySeparator ) {   
					pw.printf(PRETTY_LINE); 
					pw.printf("\n");
				}
			}
			if( !printEntrySeparator ) { 
				pw.print(PRETTY_LINE);
			}

			System.out.println(sw.toString());
		}
	}

}
