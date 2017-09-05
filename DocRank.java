/**
 * 
 */
package inter;

import linkandrank.DocRankMatrixBuilder;
import linkandrank.PageRankMatrixH;
import linkandrank.Rank;


public class DocRank extends Rank {

    DocRankMatrixBuilder docRankBuilder;
        
    public DocRank(String luceneIndexDir, int termsToKeep) {
        docRankBuilder = new DocRankMatrixBuilder(luceneIndexDir);
        docRankBuilder.setTermsToKeep(termsToKeep);
        docRankBuilder.run();
    }
    
    @Override
	public PageRankMatrixH getH() {
        return docRankBuilder.getH();
    }
}
