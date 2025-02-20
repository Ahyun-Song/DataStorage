package com.datastorage.config;

public class ConfigTest {
	public static void main(String[] args) {
		System.out.println("PLC IP: " + Config.get("plc.ip"));
		System.out.println("PLC Port: " + Config.getInt("plc.port"));
		System.out.println("Database URL: " + Config.get("db.url"));
		System.out.println("Database User: " + Config.get("db.user"));
	}
}
