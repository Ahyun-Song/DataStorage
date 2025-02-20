package com.datastorage.config;

import java.io.InputStream;
import java.util.Properties;

public class Config {
	private static Properties properties = new Properties();

	static {
		try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties")) {
			if (input == null) {
				throw new RuntimeException("config.properties 파일을 찾을 수 없습니다.");
			}
			properties.load(input);
		} catch (Exception e) {
			throw new RuntimeException("환경설정 파일 로드 실패", e);
		}
	}

	public static String get(String key) {
		return properties.getProperty(key);
	}

	public static int getInt(String key) {
		return Integer.parseInt(properties.getProperty(key));
	}
}
