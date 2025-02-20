package com.datastorage;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PLCDataLogger {
	public static void main(String[] args) {
		try {
			ModbusConnector modbus = new ModbusConnector();
			Connection conn = DatabaseManager.getConnection();

			Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());

			// 건조 공정 데이터 읽기
			double temperature = modbus.readRegister(1);
			double humidity = modbus.readRegister(2);
			DatabaseManager.saveDryData(conn, currentTime, temperature, humidity);

			// 페인트 공정 데이터 읽기
			double paintAmount = modbus.readRegister(3);
			double pressure = modbus.readRegister(4);
			DatabaseManager.savePaintData(conn, currentTime, paintAmount, pressure);

			// 전착 공정 데이터 읽기
			double waterLevel = modbus.readRegister(5);
			double viscosity = modbus.readRegister(6);
			double ph = modbus.readRegister(7);
			double current = modbus.readRegister(8);
			double voltage = modbus.readRegister(9);
			DatabaseManager.saveElectroDepositionData(conn, currentTime, waterLevel, viscosity, ph, current, voltage);

			conn.close();
			System.out.println("데이터 저장 완료");
		} catch (Exception e) {
			System.out.println("데이터 저장 실패");
			e.printStackTrace();
		}
	}
}
