package com.wdf.location.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesContext {

	private static final Logger logger = LoggerFactory.getLogger(PropertiesContext.class);

	private static Properties MESSAGE_PROPERTIES;

	private static PropertiesContext ourInstance = new PropertiesContext();

	private PropertiesContext() {
		String profile = System.getProperty("spring.profiles.active");
		MESSAGE_PROPERTIES = loadPropertyFile("message.properties");
	}

	public static PropertiesContext getInstance() {
		return ourInstance;
	}

	public String getResponseMessage(String key) {

		if (StringUtils.isEmpty(key)) {
			return "500";
		}
		return MESSAGE_PROPERTIES.getProperty(key);
	}

	private Properties loadAllPropertyFiles(String fileName, String profile) {
		Properties properties = new Properties();
		try {
			InputStream globalStream = getFileStream(fileName + ".properties");
			if (globalStream != null) {
				properties.load(globalStream);
			}
			InputStream profileStream = getFileStream(fileName + "-" + profile + ".properties");
			if (profileStream != null) {
				properties.load(profileStream);
			}
		}
		catch (Throwable t) {
			logger.error("Error in reading {} properties :", fileName, t);
		}
		return properties;
	}

	private Properties loadPropertyFile(String fileName) {
		Properties properties = new Properties();
		try {
			InputStream in = getFileStream(fileName);
			if (in != null) {
				properties.load(in);
			}
		}
		catch (Throwable t) {
			logger.error("Error in reading properties {} : {}", fileName, t);
		}
		return properties;
	}

	private InputStream getFileStream(String fileName) {
		return PropertiesContext.class.getClassLoader().getResourceAsStream(fileName);
	}

}
