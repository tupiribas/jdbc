package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Scanner;

import db.DB;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		System.out.println("Enter the department identifier to delete: ");
		int id = sc.nextInt();
		
		try {
			conn = DB.getConnection();
			
			String sql = "DELETE FROM department WHERE Id = ?";
			stmt = conn.prepareStatement(sql);
			
			System.out.println("You are deleting the " + id + " department, do you want to do this?");
			String resp = sc.next().toUpperCase();
			
			verification(id, resp, stmt);	
			printDepartment(rs, stmt);
		} 
		catch (SQLException e) {
			System.out.println("ERRO cod.:304 >>> " + e.getMessage());
		}
		finally {
			DB.closeConnection(conn, stmt, rs);
		}
		sc.close();
	}

	private static void printDepartment(ResultSet rs, PreparedStatement stmt) {
		try {
			rs = stmt.executeQuery("SELECT * FROM department");
			
			while (rs.next()) {
				System.out.println(rs.getInt("Id") + " - " + rs.getString("Name"));
			}
		} 
		catch (SQLException e) {
			System.out.println("ERRO cod.:306 >>> " + e.getMessage());
		}
	}

	private static void verification(int id, String resp, PreparedStatement stmt) {
		try {
			
			if (resp.equals("S")) {
				stmt.setInt(1, id);
				int rowsAffected = stmt.executeUpdate();
				System.out.println("Done! Rows affected: " + rowsAffected);
			}
			
		} 
		catch (SQLException e) {
			System.out.println("ERRO cod.:305 >>> " + e.getMessage());
		}
	}
	
}
