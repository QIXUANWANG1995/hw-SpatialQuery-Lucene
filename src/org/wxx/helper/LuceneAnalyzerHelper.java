package org.wxx.helper;

import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class LuceneAnalyzerHelper {
	private IndexWriterConfig config;
	private IndexWriter w;
	Directory index = new RAMDirectory();

	public LuceneAnalyzerHelper() {
		StandardAnalyzer analyzer = new StandardAnalyzer();
		config = new IndexWriterConfig(analyzer);
		try {
			w = new IndexWriter(index, config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
   
	public void addDocAuthor(IndexWriter w, String author, String title) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("author", author, Field.Store.YES));
		doc.add(new StringField("title", title, Field.Store.YES));
		w.addDocument(doc);
	}
	public void addDocTitle(IndexWriter w, String title) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("title", title, Field.Store.YES));
		w.addDocument(doc);
	}
	 public Directory getIndex() {
	    	return index;
	}
	public IndexWriter getIndexWriter() {
			return w;
	}
}
