package com.util;

import java.io.File;

import org.ini4j.ConfigParser;
import org.ini4j.ConfigParser.InterpolationException;
import org.ini4j.ConfigParser.NoOptionException;
import org.ini4j.ConfigParser.NoSectionException;

public class PropertyConfig {
	public static ConfigParser config;
	static {
		try {
			System.out.println("initializing PropertyConfig");
			config = new ConfigParser();
			String config_path = new File(PropertyConfig.class.getResource("").getFile()).getParentFile().getParent()
					+ "/config.ini";

			System.out.println("config_path = " + config_path);
			config.read(config_path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static public String get(String section, String option) {
		try {
			return config.get(section, option);
		} catch (NoSectionException | NoOptionException | InterpolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(config);
	}

}
