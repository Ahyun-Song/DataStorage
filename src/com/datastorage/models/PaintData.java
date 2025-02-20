package com.datastorage.models;

public class PaintData {
	private double paintAmount;
	private double pressure;

	public PaintData(double paintAmount, double pressure) {
		this.paintAmount = paintAmount;
		this.pressure = pressure;
	}

	public double getPaintAmount() { return paintAmount; }
	public double getPressure() { return pressure; }
}
