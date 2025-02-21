package com.datastorage;

import java.sql.*;

public class DatabaseManager {
	// ✅ MySQL 연결 정보
	private static final String DB_URL = "jdbc:mysql://localhost:3306/paintflow";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "12345";

	// MySQL 연결 생성
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	}

	// 건조 공정 (Dry) 데이터 저장
	public static void saveDryData(Connection conn, Timestamp time, double temperature, double humidity) throws SQLException {
		String sql = "INSERT INTO dry (time, temperature, humidity) VALUES (?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setTimestamp(1, time);
			pstmt.setDouble(2, temperature);
			pstmt.setDouble(3, humidity);
			pstmt.executeUpdate();
		}
	}

	// 페인트 공정 (Paint) 데이터 저장
	public static void savePaintData(Connection conn, Timestamp time, double paintAmount, double pressure) throws SQLException {
		String sql = "INSERT INTO paint (time, paintamount, pressure) VALUES (?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setTimestamp(1, time);
			pstmt.setDouble(2, paintAmount);
			pstmt.setDouble(3, pressure);
			pstmt.executeUpdate();
		}
	}

	// 전착 공정 (Electrodeposition) 데이터 저장
	public static void saveElectroDepositionData(Connection conn, Timestamp time, double waterLevel, double viscosity, double pH, double current, double voltage) throws SQLException {
		String sql = "INSERT INTO electrodeposition (time, waterlevel, viscosity, ph, current, voltage) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setTimestamp(1, time);
			pstmt.setDouble(2, waterLevel);
			pstmt.setDouble(3, viscosity);
			pstmt.setDouble(4, pH);
			pstmt.setDouble(5, current);
			pstmt.setDouble(6, voltage);
			pstmt.executeUpdate();
		}
	}

	// 알람 (Alarm) 데이터 저장
	public static void saveAlarmData(Connection conn, Timestamp time, String sensor, double data, String message) throws SQLException {
		String sql = "INSERT INTO alarm (time, sensor, data, message) VALUES (?, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setTimestamp(1, time);
			pstmt.setString(2, sensor);
			pstmt.setDouble(3, data);
			pstmt.setString(4, message);
			pstmt.executeUpdate();
		}
	}

	// PLC 데이터 통합 저장 (전체 공정)
	public static void saveData(Connection conn, Timestamp time, double temp, double hum, double level,
								double visc, double pH, double volt, double curr, double press, double flow) throws SQLException {
		saveDryData(conn, time, temp, hum);		 // 건조 공정 저장
		savePaintData(conn, time, flow, press);	 // 페인트 공정 저장
		saveElectroDepositionData(conn, time, level, visc, pH, curr, volt); // 전착 공정 저장
	}
}
