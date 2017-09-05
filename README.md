# Search_Engine
Search Engine to display results of input query and compare them with the results of Google and Bing

Search Engine is based on the topic of Animals which crawls more than 100,000 web pages using crawler 4j. It also uses Lucene search engine library. It also performs incremental indexing of crawled web pages and for the link analysis of the Web graph that was retrieved by crawling.  Two relevance models (e.g. vector space relevance model) as well as relevance models based on PageRanking and HITS (and their combinations with the vector-based relevance models) were created. It displays the relevance results based for query expansion.

When a query is entered, the search engine presenst in separate frames of the web page the following: 
-Results of your relevance models against the query
-Results of the search engine relevance against the query when clustering has been incorporated
-Results of the search engine against the query when query expansion has been enabled
-Results of Google against the query-Results of Bing against the query.

Different clustering methods (flat clustering and agglomerative clustering methods) are performed. 

Also, various query expansion algorithms are implemented to retrieve relevant output results
