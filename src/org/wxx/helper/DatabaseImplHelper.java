package org.wxx.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseImplHelper {
	MysqlHelper helper = null;
	Statement stmt = null;
	ResultSet rs = null;
	private void setStatement() {
		helper = new MysqlHelper();
		stmt = helper.getStatement();
	}
	public ResultSet getTitleAuthor() {
		setStatement();
		String sql = "Select * from info";  
		try {
			rs = stmt.executeQuery(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}  
		return rs;
	}
	
	public ResultSet getSpatial(String year, String month,String year1, String month1) {
		setStatement();
		String poly="'Polygon(("+year+" "+month+",\n" + 
				"                "+year+" "+month1+",\n" + 
				"                "+year1+" "+month1+" ,\n"+ 
				"                 "+year1+" "+month+",\n" + 
				"                 "+year+" "+month+"))'";
		String sql = "Select AsText(g),author,title from geom WHERE MBRContains(GeomFromText("+poly+"),g)";  
		try {
			rs = stmt.executeQuery(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}  
		return rs;
	}
	
}
