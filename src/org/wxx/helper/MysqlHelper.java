package org.wxx.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlHelper {
	private Statement stmt = null;
	private String url = "jdbc:mysql://localhost:3306/info";
	private String username = "root";
	private String password = "wxjy19950414";
	private Connection sqlConn = null;

	public MysqlHelper() { 
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				sqlConn = (Connection) DriverManager.getConnection(url, username, password);
			     stmt = sqlConn.createStatement();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}   
     }
	
	 public Statement getStatement() {
 	    return stmt;
     }
	 
	 public Connection getConnection() {
		 return sqlConn;
	 }
}
