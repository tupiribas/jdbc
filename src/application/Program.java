package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

import db.DB;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			System.out.print("Name: ");
			String name = sc.nextLine();
			System.out.print("Email: ");
			String email = sc.nextLine();
			System.out.print("BirthDate: ");
			Date date = new java.sql.Date(sdf.parse(sc.nextLine()).getTime());
			System.out.print("Base salary: ");
			double baseSalary = sc.nextDouble();
			System.out.print("Department number: ");
			int departmentId = sc.nextInt();
			
			conn = DB.getConnection();

			String sql = "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES (?, ?, ?, ?, ?)";

			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setDate(3, date);
			stmt.setDouble(4, baseSalary);
			stmt.setInt(5, departmentId);

			int rows = stmt.executeUpdate();
			
			verification(name, rows, stmt);
			
			rs = stmt.executeQuery("SELECT * FROM seller");
			
			while (rs.next()) {
				System.out.println(rs.getString("Id") + " - " + rs.getString("Name") + " - " + rs.getString("Email")
						+ " - " + rs.getDate("BirthDate") + " - " + rs.getDouble("BaseSalary") + " - "
						+ rs.getInt("DepartmentId"));
			}
		} 
		catch (SQLException e) {
			System.out.println("ERROR SQL cod.:304>>> " + e.getMessage());
		} 
		catch (ParseException e) {
			System.out.println("ERROR cod.:305>>> " + e.getMessage());
		}
		finally {
			DB.closeConnection(conn, stmt, rs);
		}
		sc.close();

	}

	private static void verification(String name, int rows, PreparedStatement stmt) {
		try {
			if (rows > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				while (rs.next()) {
					int id = rs.getInt(1);
					System.out.println("\n" + name + ", been successfully added! ID=" + id);
				}
			}
			else {
				System.out.println("No rows affected");
			}
		}
		catch (SQLException e) {
			System.out.println("ERROR SQL cod.:306>>> " + e.getMessage());
		}
	}
}
