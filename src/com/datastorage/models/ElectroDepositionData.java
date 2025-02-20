package com.datastorage.models;

public class ElectroDepositionData {
	private double waterLevel, viscosity, ph, current, voltage;

	public ElectroDepositionData(double waterLevel, double viscosity, double ph, double current, double voltage) {
		this.waterLevel = waterLevel;
		this.viscosity = viscosity;
		this.ph = ph;
		this.current = current;
		this.voltage = voltage;
	}

	public double getWaterLevel() { return waterLevel; }
	public double getViscosity() { return viscosity; }
	public double getPh() { return ph; }
	public double getCurrent() { return current; }
	public double getVoltage() { return voltage; }
}
