package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import net.wimpi.modbus.facade.ModbusTCPMaster;
import net.wimpi.modbus.procimg.InputRegister;

public class PLCDataSaver {
	public static void main(String[] args) {
		String plcIp = "192.168.0.200"; // PLC IP 주소
		int plcPort = 502; // Modbus 포트
		int registerAddress = 0; // 읽을 레지스터 주소

		String dbUrl = "jdbc:mysql://localhost:3306/paintflow";
		String dbUser = "root";
		String dbPassword = "12345";

		try {
			// Modbus TCP 마스터 생성
			ModbusTCPMaster master = new ModbusTCPMaster(plcIp, plcPort);
			master.connect();

			// PLC에서 데이터 읽기
			InputRegister[] registers = master.readInputRegisters(registerAddress, 1);
			int sensorData = registers[0].toShort(); // 읽은 데이터

			// MySQL 연결
			Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

			// 데이터 저장 SQL 실행
			String sql = "INSERT INTO plc_data (sensor_value, timestamp) VALUES (?, NOW())";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sensorData);
			pstmt.executeUpdate();

			System.out.println("PLC 데이터 저장 완료: " + sensorData);

			// 연결 종료
			pstmt.close();
			conn.close();
			master.disconnect();
		} catch (Exception e) {
			System.out.println("데이터 저장 실패");
			e.printStackTrace();
		}
	}
}
