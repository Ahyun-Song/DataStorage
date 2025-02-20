package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLTest {
	public static void main(String[] args) {
		// MySQL 연결 정보
		String url = "jdbc:mysql://localhost:3306/paintflow";
		String user = "root";
		String password = "12345";
		try {
			// MySQL에 연결 시도
			Connection conn = DriverManager.getConnection(url, user, password);
			if (conn != null) {
				System.out.println("MySQL 연결 성공");
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("MySQL 연결 실패");
			e.printStackTrace();
		}
	}
}
