package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;

public class Program {

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			
			conn = DB.getConnection();
			
			stmt = conn.createStatement();
			String sql = "select * from department";
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				System.out.println(rs.getInt("Id") + " - " + rs.getString("Name"));
			}
			
		} 
		catch (SQLException e) {
			System.out.println("DATA PRINTING ERROR >>> " + e.getMessage());
		} 
		finally {
			DB.closeConnection(conn, stmt, rs); 
		}
	}
}
