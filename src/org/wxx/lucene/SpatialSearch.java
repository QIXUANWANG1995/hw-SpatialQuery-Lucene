
package org.wxx.lucene;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.wxx.helper.DatabaseImplHelper;
import org.wxx.helper.LuceneAnalyzerHelper;

public class SpatialSearch {
	DatabaseImplHelper dbiHelper = new DatabaseImplHelper();
	LuceneAnalyzerHelper laHelper = new LuceneAnalyzerHelper();
	ResultSet rs = null;
	IndexWriter w = null;
	StandardAnalyzer analyzer = new StandardAnalyzer();
	Directory index = laHelper.getIndex();
	public SpatialSearch() {
//		rs = dbiHelper.getTitleAuthor();
		w = laHelper.getIndexWriter();
	}
	
	public JSONArray LuceneQueryTitle(String keyWords, String yearfrom, String yearto, 
			int numResultsToSkip,int numResultsToReturn)
			throws ParseException, IOException, SQLException {
		rs = dbiHelper.getSpatial(yearfrom, "0", yearto, "12");
		try {
            while (rs.next()) {  
               laHelper.addDocAuthor(w, rs.getString(2), rs.getString(3)) ;
            } 
			w.close();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 	
		w.close();
		
		try {
            while (rs.next()) {  
               laHelper.addDocTitle(w, rs.getString(3)) ;
            } 
			w.close();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 	
		w.close();
		
		System.out.println("Spatial Query from "+yearfrom +" to "+yearto);
		Query q = new QueryParser("author", analyzer).parse(keyWords);
        Query q2 = new QueryParser("title", analyzer).parse(keyWords);
		
        BooleanQuery.Builder qBuilder = new BooleanQuery.Builder();
        qBuilder.add(q, BooleanClause.Occur.SHOULD).add(q2, BooleanClause.Occur.SHOULD);
		int hitsPerPage = 1000;
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopDocs docs = searcher.search(qBuilder.build(),1000);
		ScoreDoc[] hits = docs.scoreDocs;
		System.out.println(qBuilder.build().toString());
	
		System.out.println("Found " + hits.length + " hits.");			
		JSONArray obj = new JSONArray();
		if(numResultsToSkip > hits.length) {
			System.out.println("no enough items to skip!");
			return null;
		}
		else if(numResultsToSkip + numResultsToReturn > hits.length) {
			System.out.println("no enough items to show!");
		}
		queryInfo info;
		for (int i = numResultsToSkip; i < numResultsToReturn; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			info = new queryInfo(i+1,d.get("author"),d.get("title"));
			obj.put(info.ret());
		}
		reader.close();
		return obj;
	}
}
