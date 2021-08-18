package application;

import java.sql.Connection;

import db.DB;

public class Program {

	public static void main(String[] args) {
		Connection conn = DB.getConnection();
		
		if (conn != null) {
			System.out.println("Success!");
		}
		
		DB.closeConnection(); 
	}
}
