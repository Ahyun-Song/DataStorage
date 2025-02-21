package com.datastorage;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import net.wimpi.modbus.facade.ModbusTCPMaster;
import net.wimpi.modbus.procimg.InputRegister;

public class PLCDataLogger {
	public static void main(String[] args) {
		String plcIp = "192.168.10.200";
		int plcPort = 502;
		int startAddress = 0;
		int numRegisters = 10;

		while (true) {  // 주기적으로 실행
			try {
				ModbusTCPMaster master = new ModbusTCPMaster(plcIp, plcPort);
				master.connect();
				System.out.println("✅ PLC 연결 성공!");

				// PLC에서 데이터 읽기
				InputRegister[] registers = master.readInputRegisters(startAddress, numRegisters);

				double temperature = registers[0].toShort();
				double humidity = registers[1].toShort();
				double waterLevel = registers[2].toShort();
				double viscosity = registers[3].toShort();
				double pH = registers[4].toShort() / 100.0; // pH는 x100 스케일링
				double voltage = registers[5].toShort();
				double current = registers[6].toShort();
				double paintPressure = registers[7].toShort() / 100.0;
				double paintFlow = registers[8].toShort();

				// MySQL에 데이터 저장
				try (Connection conn = DatabaseManager.getConnection()) {
					Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());

					DatabaseManager.saveDryData(conn, currentTime, temperature, humidity);
					DatabaseManager.savePaintData(conn, currentTime, paintFlow, paintPressure);
					DatabaseManager.saveElectroDepositionData(conn, currentTime, waterLevel, viscosity, pH, current, voltage);

					System.out.println("✅ PLC 데이터 저장 완료");
				}

				master.disconnect();
				Thread.sleep(5000); // 5초마다 데이터 저장
			} catch (Exception e) {
				System.out.println("❌ PLC 통신 오류: " + e.getMessage());
			}
		}
	}
}
