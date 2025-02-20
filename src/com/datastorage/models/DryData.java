package com.datastorage.models;

public class DryData {
	private double temperature;
	private double humidity;

	public DryData(double temperature, double humidity) {
		this.temperature = temperature;
		this.humidity = humidity;
	}

	public double getTemperature() { return temperature; }
	public double getHumidity() { return humidity; }
}
