package com.datastorage;

import java.sql.*;

public class DatabaseManager {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/paintflow";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "12345";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	}

	public static void saveDryData(Connection conn, Timestamp time, double temperature, double humidity) throws SQLException {
		String sql = "INSERT INTO dry (time, temperature, humidity) VALUES (?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setTimestamp(1, time);
			pstmt.setDouble(2, temperature);
			pstmt.setDouble(3, humidity);
			pstmt.executeUpdate();
		}
	}

	public static void savePaintData(Connection conn, Timestamp time, double paintAmount, double pressure) throws SQLException {
		String sql = "INSERT INTO paint (time, paintamount, pressure) VALUES (?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setTimestamp(1, time);
			pstmt.setDouble(2, paintAmount);
			pstmt.setDouble(3, pressure);
			pstmt.executeUpdate();
		}
	}

	public static void saveElectroDepositionData(Connection conn, Timestamp time, double waterLevel, double viscosity, double ph, double current, double voltage) throws SQLException {
		String sql = "INSERT INTO electroDeposition (time, waterlevel, viscosity, ph, current, voltage) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setTimestamp(1, time);
			pstmt.setDouble(2, waterLevel);
			pstmt.setDouble(3, viscosity);
			pstmt.setDouble(4, ph);
			pstmt.setDouble(5, current);
			pstmt.setDouble(6, voltage);
			pstmt.executeUpdate();
		}
	}
}
