package com.safetynet.Alerts;

import com.safetynet.Alerts.data.StoredData;
import com.safetynet.Alerts.util.DataReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SafetyNetApplication {

	private static final Logger LOGGER = LogManager.getLogger(SafetyNetApplication.class);

	@Value("${dataFile}")
	private String dataFilePath;

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetApplication.class, args);
	}

	@Bean
	public StoredData readData() throws Exception {
		LOGGER.debug("Read Data File");
		return DataReader.readFile(dataFilePath);
	}

}
