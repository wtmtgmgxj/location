package com.wdf.location;

import com.wdf.location.httpclient.initializer.HttpClientInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@ComponentScan({ "com.wdf" })
@EnableAutoConfiguration
@SpringBootApplication
@Component
public class Application extends HttpClientInitializer {

	@Autowired
	private Environment env;

	public static void main(String[] args) throws Exception {

		SpringApplication.run(Application.class, args);

		addShutdownHooks();

	}

	private static void addShutdownHooks() {

		// Runtime.getRuntime().addShutdownHook(new
		// Thread(ThreadPoolManager::shutdownExecutors));

	}

	@Override
	public String providePropertyValue(String propertyName) {
		return env.getProperty(propertyName);
	}

}
