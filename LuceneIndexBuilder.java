package lucene;


import java.io.File;
import java.io.IOException;
import java.util.List;

import model.ProcessedDocument;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import webdfs.CrawlData;
import webdfs.CrawlDataProcessor;
import db.ProcessedDocsDB;

public class LuceneIndexBuilder implements CrawlDataProcessor {

    private File indexDir;
    private CrawlData crawlData;
    
    public LuceneIndexBuilder(File indexDir, CrawlData crawlData) {

    	this.indexDir = indexDir;
    	this.crawlData = crawlData;
        
    	try {
    		
        	 Directory idr = FSDirectory.open(indexDir); //read index directory.
			IndexWriter indexWriter = new IndexWriter(idr,	new StandardAnalyzer(Version.LUCENE_36), true,MaxFieldLength.UNLIMITED);
			indexWriter.close();
		} catch (IOException ioX) {
			throw new RuntimeException("Error while creating lucene index: ", ioX);
		}
    }
    
    private void buildLuceneIndex(String groupId, ProcessedDocsDB parsedDocsService) {
        try {
            List<String> docIdList = parsedDocsService.getDocumentIds(groupId);
            
            Directory idr = FSDirectory.open(indexDir); //read index directory.
			IndexWriter indexWriter = new IndexWriter(idr,	new StandardAnalyzer(Version.LUCENE_36), false,MaxFieldLength.UNLIMITED);
            for(String docId : docIdList) {
                indexDocument(indexWriter, parsedDocsService.loadDocument(docId));
            }
            indexWriter.close();
        }
        catch(IOException ioX) {
            throw new RuntimeException("Error while creating lucene index: ", ioX);
        }
    }
    
    private void indexDocument(IndexWriter iw, ProcessedDocument parsedDoc) throws IOException {
        
    	org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
        doc.add(new Field("content", 
        		          parsedDoc.getText(), 
        		          Field.Store.NO, 
        		          Field.Index.ANALYZED,
        		          Field.TermVector.YES));
        
        doc.add(new Field("url", 
        		          parsedDoc.getDocumentURL(), 
        		          Field.Store.YES, 
        		          Field.Index.NO));
        
        doc.add(new Field("docid", 
        		          parsedDoc.getDocumentId(), 
        		          Field.Store.YES, 
        		          Field.Index.NO));
        
        doc.add(new Field("title", 
        		          parsedDoc.getDocumentTitle(), 
        		          Field.Store.YES,Field.Index.ANALYZED, 
        		          Field.TermVector.YES));

        doc.add(new Field("doctype", 
                          parsedDoc.getDocumentType(), 
                          Field.Store.YES, 
                          Field.Index.NO));
        
/**
 * TODO: 2.2 -- The effect of boosting (Book Section 2.1.2) 
 *  
 *      Uncomment the lines below to demonstrate the effect of boosting
 */     
//		 if ( parsedDoc.getDocumentId().equals("g1-d13")) {
//		    	doc.setBoost(2);
//		 }
        
        iw.addDocument(doc);
    }

    public void run() {
        List<String> allGroups = crawlData.getProcessedDocsDB().getAllGroupIds();
        for(String groupId : allGroups) {
            buildLuceneIndex(groupId, crawlData.getProcessedDocsDB());
        }
    }
}
