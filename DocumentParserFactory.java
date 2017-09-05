package commonparser;

import htmlparser.HTMLDocumentParser;
import wordparser.MSWordDocumentParser;

public class DocumentParserFactory {
    
    private static DocumentParserFactory instance = new DocumentParserFactory();
    
    public static DocumentParserFactory getInstance() {
        return instance;
    }
    
    private DocumentParserFactory() {
        // empty
    }
    
    /**
     * Returns an instance of the <code>DocumentParser</code> based 
     * on the document type.
     * 
     * @param type document type.
     * @return
     * @throws DocumentParserException
     */
    public DocumentParser getDocumentParser(String type) 
        throws DocumentParserException {
    	
        if( "text/html".equalsIgnoreCase(type) ) {
        	System.out.println("doc type"+type);
            return new HTMLDocumentParser();
        }
        else if( "application/msword".equalsIgnoreCase(type) ) {
        	System.out.println("doc type"+type);
            return new MSWordDocumentParser();
        }
        else {
            throw new DocumentParserException("Unsupported document type: '"+ type + "'.");
            
        }
    }
}
