package org.wxx.lucene;

public class queryInfo {
 int rank = 0;
 String author = "";
 String title = "";
 
 public queryInfo(int r, String a, String t) {
	 rank = r;
	 author = a;
	 title = t;
 }
 
 public String ret() {
	 return "{\"rank\":"+ rank + ",\"author\":\"" + author + "\",\"title\":\"" + title + "\"}";
 }
}
