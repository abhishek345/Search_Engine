package webdfs;

import java.net.URL;

public class URLNormalizer {
    public URLNormalizer() {
        // empty
    }
    
    public String normalizeUrl(String url) {
        String normalizedUrl = url;
        if( url.startsWith("file://") ) {
            normalizedUrl = normalizeFileUrl(url);
        }
        return normalizedUrl;
    }
  
    
    private String normalizeFileUrl(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            return url.toExternalForm();
        } 
        catch (Exception e) {
            throw new RuntimeException("URL Normalization error: ", e);
        }
    }
}
