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
		
		System.out.print("Enter the value you want to add: ");
		double value = sc.nextDouble();
		System.out.print("Enter the person's id: ");
		int id = sc.nextInt();
		
		try {
			conn = DB.getConnection();
			
			String sql = "UPDATE seller SET BaseSalary = BaseSalary + ? "
					+ "WHERE "
					+ "(Id = ?)";
			
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, value);
			stmt.setInt(2, id);
			
			stmt.executeUpdate();
			
			rs = stmt.executeQuery("SELECT * FROM seller WHERE (Id = " + id + ")");
			
			
			while (rs.next()) {
				System.out.println(rs.getString("Name") + " - " + rs.getDouble("BaseSalary"));
			}
		} 
		catch (SQLException e) {
			System.out.println("ERROR cod.:304 >>>" + e.getMessage());
		}
		finally {
			DB.closeConnection(conn, stmt, rs);
		}
		sc.close();
	}


	
	
}
