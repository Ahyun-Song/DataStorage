package com.datastorage;

import net.wimpi.modbus.facade.ModbusTCPMaster;
import net.wimpi.modbus.procimg.InputRegister;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PLCDataReader {
	public static void main(String[] args) {
		// PLC 정보 설정
		String plcIp = "192.168.10.200";  // PLC IP 주소
		int plcPort = 502;				// Modbus TCP 포트
		int startAddress = 0;			 // 시작 레지스터 주소
		int numRegisters = 10;			// 읽을 레지스터 개수

		try {
			// Modbus Master 객체 생성
			ModbusTCPMaster master = new ModbusTCPMaster(plcIp, plcPort);
			master.connect();
			System.out.println("PLC 연결 성공");

			// PLC에서 레지스터 값 읽기
			InputRegister[] registers = master.readInputRegisters(startAddress, numRegisters);

			// 읽은 데이터 변환
			double temperature = registers[0].toShort();
			double humidity = registers[1].toShort();
			double waterLevel = registers[2].toShort();
			double viscosity = registers[3].toShort();
			double pH = registers[4].toShort() / 100.0; // pH는 x100 스케일링
			double voltage = registers[5].toShort();
			double current = registers[6].toShort();
			double paintPressure = registers[7].toShort() / 100.0; // bar 단위 변환
			double paintFlow = registers[8].toShort();

			// MySQL에 데이터 저장
			try (Connection conn = DatabaseManager.getConnection()) {
				Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
				DatabaseManager.saveData(conn, currentTime, temperature, humidity, waterLevel,
										 viscosity, pH, voltage, current, paintPressure, paintFlow);
				System.out.println("PLC 데이터 저장 완료");
			}

			// 연결 종료
			master.disconnect();
		} catch (Exception e) {
			System.out.println("PLC 통신 오류: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
