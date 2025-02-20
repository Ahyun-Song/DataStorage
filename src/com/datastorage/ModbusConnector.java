package com.datastorage;

import net.wimpi.modbus.facade.ModbusTCPMaster;
import net.wimpi.modbus.procimg.InputRegister;
import java.util.Properties;
import java.io.InputStream;

public class ModbusConnector {
	private String plcIp;
	private int plcPort;
	private ModbusTCPMaster master;

	public ModbusConnector() throws Exception {
		Properties config = new Properties();
		InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
		config.load(input);

		this.plcIp = config.getProperty("plc.ip");
		this.plcPort = Integer.parseInt(config.getProperty("plc.port"));
		master = new ModbusTCPMaster(plcIp, plcPort);
		master.connect();
	}

	public double readRegister(int registerAddress) throws Exception {
		InputRegister[] registers = master.readInputRegisters(registerAddress, 1);
		return registers[0].toShort();
	}

	public void close() throws Exception {
		master.disconnect();
	}
}
