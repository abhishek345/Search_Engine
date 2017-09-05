package htmlparser;

import commonparser.DocumentParserException;

public class HTMLDocumentParserException extends DocumentParserException {

    private static final long serialVersionUID = -7886897172434304778L;

    public HTMLDocumentParserException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public HTMLDocumentParserException(String msg) {
        super(msg);
    }
}
