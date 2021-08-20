package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Scanner;

import db.DB;
import db.DbException;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		System.out.println("Enter the department identifier: ");
		int id = sc.nextInt();
		System.out.println("Enter the salary amount you want to add: ");
		Double salaryAdd = sc.nextDouble();

		try {
			conn = DB.getConnection();
			
			conn.setAutoCommit(false);
			
			String sql = "UPDATE seller SET BaseSalary = BaseSalary + ? WHERE DepartmentId = ?";
			stmt = conn.prepareStatement(sql);
			
			stmt.setDouble(1, salaryAdd);
			stmt.setInt(2, id);
			
//			Possible Exception!
//			int x = 1;
//			if (x < 2) {
//				throw new SQLException("Fake error!");
//			}
			
			System.out.println("You will make an addition to the ID=" + id 
					+ " employee's R$" + String.format("%.2f", salaryAdd) 
					+ ", do you want to continue? [Y/N]:");
			String resp = sc.next().toUpperCase();
			verification(id, resp, stmt, conn);
			printDepartment(rs, stmt);
		} 
		catch (SQLException e) {
			try {
				conn.rollback();
				throw new DbException("ERRO cod.:308 >>> Transaction rolled back! Caused by: " + e.getMessage());
			} 
			catch (SQLException e1) {
				throw new DbException("ERRO cod.:309 >>> Erro trying to rollback! Caused by: " + e1.getMessage());
			}
		} 
		finally {
			DB.closeConnection(conn, stmt, rs);
		}
		sc.close();
	}

	private static void printDepartment(ResultSet rs, Statement stmt) {
		try {
			rs = stmt.executeQuery("SELECT * FROM seller");

			while (rs.next()) {
				System.out.println(rs.getInt("Id") 
						+ " - " + rs.getString("Name") 
						+ " - " + rs.getDouble("BaseSalary"));
			}
		} 
		catch (SQLException e) {
			System.out.println("ERRO cod.:306 >>> " + e.getMessage());
		}
	}
	
	private static void verification(int id, String resp, PreparedStatement stmt, Connection conn) {
		try {
			if (resp.equals("Y")) {
				stmt.executeUpdate();
				stmt.setInt(1, id);
				int rowsAffected = stmt.executeUpdate();
				conn.commit();
				System.out.println("Done! Rows affected: " + rowsAffected);
			}
		} 
		catch (SQLException e) {
			System.out.println("ERRO cod.:305 >>> " + e.getMessage());
		}
	}

}
