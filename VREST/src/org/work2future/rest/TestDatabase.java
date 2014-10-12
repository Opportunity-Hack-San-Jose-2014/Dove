package org.work2future.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestDatabase {
	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Hello world!!");
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gearworks","root","asdfasdf");
		
		PreparedStatement stmt = conn.prepareStatement("select * from seller_topic_preference");
		ResultSet result = stmt.executeQuery();
		
		while(result.next()){
			System.out.println(result.getString(1));
			System.out.println(result.getString(2));
		}
	}
}
