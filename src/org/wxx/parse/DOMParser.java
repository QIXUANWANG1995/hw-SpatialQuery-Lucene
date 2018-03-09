package org.wxx.parse;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMParser {
	public static void main(String[] arg) throws ParserConfigurationException, SAXException, IOException,
			InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
       // System.out.println("wxx");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		//System.out.println(Class);
		Document document = builder.parse("SOCpapers.xml");
		System.out.println();
		// link to mySQL
		String url = "jdbc:mysql://localhost:3306/info";
		String username = "root";
		String password = "wxjy19950414";
		Connection sqlConn = null;

		Class.forName("com.mysql.jdbc.Driver");
		sqlConn = DriverManager.getConnection(url, username, password);
		PreparedStatement stmt;

		NodeList nodeList = document.getDocumentElement().getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node Article = nodeList.item(i);
			if (Article instanceof Element) {
				String mdate = ((Element) Article).getAttribute("mdate");
				String key = ((Element) Article).getAttribute("key");
				String authorWithSpace = "";
				String authorWithoutSpace="";
				
				if (((Element) Article).getElementsByTagName("author").getLength() != 0) {
					NodeList author1 = ((Element) Article).getElementsByTagName("author");
					authorWithSpace= new String(author1.item(0).getFirstChild().getNodeValue().getBytes("iso8859-1"), "utf-8");
					for (int j = 1; j < author1.getLength(); j++) {
						authorWithSpace += "," + new String(author1.item(j).getFirstChild().getNodeValue().getBytes("iso8859-1"),
								"utf-8");
					}
				}
				String s;
				if (((Element) Article).getElementsByTagName("author").getLength() != 0) {
					NodeList author2 = ((Element) Article).getElementsByTagName("author");
					//authorWithoutSpace= new String(author2.item(0).getFirstChild().getNodeValue().getBytes("iso8859-1"), "utf-8");
					
					for (int j = 0; j < author2.getLength(); j++) {
						//System.out.println(author2.item(j).getFirstChild().getNodeValue());
						//authorWithoutSpace+= "," + new String(author2.item(j).getFirstChild().getNodeValue().replaceAll("  ", "").getBytes("iso8859-1"),
						//		"utf-8");
						s=author2.item(j).getFirstChild().getNodeValue().replaceAll(" ", "");
//						System.out.println(s);
						authorWithoutSpace+=new String(s)+" ";
//						System.out.println(authorWithoutSpace);
					}
				}

				Node title = ((Element) Article).getElementsByTagName("title").item(0);
				String t1 = title.getFirstChild().getNodeValue();
				String t = new String(t1.getBytes("ISO-8859-1"), "ISO-8859-1");

				String p = "";
				if (((Element) Article).getElementsByTagName("pages").getLength() != 0) {
					Node pages = ((Element) Article).getElementsByTagName("pages").item(0);
					p = pages.getFirstChild().getNodeValue();
				}

				Node year = ((Element) Article).getElementsByTagName("year").item(0);
				String y = year.getFirstChild().getNodeValue();

				String v = "";
				if (((Element) Article).getElementsByTagName("volume").getLength() != 0) {
					Node volume = ((Element) Article).getElementsByTagName("volume").item(0);
					v = volume.getFirstChild().getNodeValue();
				}

				String j = "";
				if (((Element) Article).getElementsByTagName("journal").getLength() != 0) {
					Node journal = ((Element) Article).getElementsByTagName("journal").item(0);
					String jj = journal.getFirstChild().getNodeValue();
					j = new String(jj.getBytes("iso8859-1"), "utf-8");
					// System.out.println(j);
				}

				String n = "";
				if (((Element) Article).getElementsByTagName("number").getLength() != 0) {
					Node number = ((Element) Article).getElementsByTagName("number").item(0);
					n = number.getFirstChild().getNodeValue();
					// System.out.println(n);
				}
				
				String b = "";
				if (((Element) Article).getElementsByTagName("booktitle").getLength() != 0) {
					Node booktitle = ((Element) Article).getElementsByTagName("booktitle").item(0);
					b = booktitle.getFirstChild().getNodeValue();
					// System.out.println(b);
				}
				
				String c = "";
				if (((Element) Article).getElementsByTagName("crossref").getLength() != 0) {
					Node crossref = ((Element) Article).getElementsByTagName("crossref").item(0);
					c = crossref.getFirstChild().getNodeValue();
					// System.out.println(c);
				}

				String sql = "replace into info values(?,?,?,?,?,?,?,?,?,?,?,?)";
				stmt = sqlConn.prepareStatement(sql);
				stmt.setString(1, key);
				stmt.setString(2, mdate);
				stmt.setString(3, authorWithSpace);
				stmt.setString(4, t);
				stmt.setString(5, p);
				stmt.setString(6, y);
				stmt.setString(7, v);
				stmt.setString(8, j);
				stmt.setString(9, n);
				stmt.setString(10, b);
				stmt.setString(11, c);
				stmt.setString(12, authorWithoutSpace);
				stmt.executeUpdate();
				
				String sql3="REPLACE INTO geom VALUES (GeomFromText('POINT("+y+" 1)'),?,?)";
				
				String sql2="replace into geom values(?,?,?)";
				stmt = sqlConn.prepareStatement(sql3);
			   //System.out.println("GeomFromText('POINT(1 1)')");
				//stmt.setString(1,"GeomFromText('POINT(1 1)'))";
				stmt.setString(1, authorWithSpace);
				stmt.setString(2, t);
				stmt.executeUpdate();
				//System.out.println("DOM 140");
			}
		}
	}
}
