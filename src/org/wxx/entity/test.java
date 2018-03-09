package org.wxx.entity;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.wxx.lucene.BasicSearch;
import org.wxx.lucene.SpatialSearch;

public class test {

	public static void main(String[] args) throws ParseException, IOException, SQLException {
	String keyword = "Jia Zhang Service";
	BasicSearch q = new BasicSearch();
	System.out.println(q.LuceneQueryAuthor(keyword,10,20));
	SpatialSearch s = new SpatialSearch();
	String yearfrom="2003";
	String yearto="2014";
	System.out.println(s.LuceneQueryTitle(keyword,yearfrom,yearto,10,20));
	}

}
