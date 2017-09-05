package webdfs;


public class URLFilter {

    private boolean allowFileUrls = true;
    private boolean allowHttpUrls = true;
    
    public URLFilter() {
        // empty
    }
    
 
    public boolean accept(String url) {
        boolean acceptUrl = false;
        if( allowFileUrls && url.startsWith("file:") ) { 
            acceptUrl = true;
        }
        else if( allowHttpUrls && url.startsWith("http:") ) {
            acceptUrl = true;
            if(url.endsWith("xml")){
            	acceptUrl = false;
            }else if(url.contains("s.teoma.com"))
            	acceptUrl = false;
            else if(url.contains("store.fifa.com")||url.contains("oldungvar")||url.contains("ajkids.com"))
            	acceptUrl = false;
        }
        else {
            acceptUrl = false;
            System.out.println("DEBUG: Filtered url: '" + url + "'");
        }
        
        return acceptUrl;
    }
    
    public void setAllowFileUrls(boolean flag) {
        this.allowFileUrls = flag;
    }
    
    public void setAllowHttpUrls(boolean flag) {
        this.allowHttpUrls = flag;
    }
}
