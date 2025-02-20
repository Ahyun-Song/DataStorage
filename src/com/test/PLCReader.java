package com.test;

import net.wimpi.modbus.facade.ModbusTCPMaster;
import net.wimpi.modbus.procimg.InputRegister;

public class PLCReader {
	public static void main(String[] args) {
		String plcIp = "192.168.0.200"; // PLC의 IP 주소 (변경해야 함)
		int plcPort = 502; // Modbus 기본 포트
		int registerAddress = 0; // 읽어올 레지스터 주소 (PLC 설정에 따라 변경)

		try {
			// Modbus TCP 마스터 생성
			ModbusTCPMaster master = new ModbusTCPMaster(plcIp, plcPort);
			master.connect();

			// PLC에서 데이터 읽기
			InputRegister[] registers = master.readInputRegisters(registerAddress, 1);
			int sensorData = registers[0].toShort(); // 읽은 값

			System.out.println("PLC 데이터: " + sensorData);

			master.disconnect();
		} catch (Exception e) {
			System.out.println("PLC 데이터 읽기 실패");
			e.printStackTrace();
		}
	}
}
