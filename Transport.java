package fcommon;

import model.FetchedDocument;

public interface Transport {
    public FetchedDocument fetch(String url) throws TransportException;
    public void init();
    public void clear();
    public boolean pauseRequired();
}
