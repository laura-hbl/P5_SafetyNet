package com.safetynet.Alerts;

import com.safetynet.Alerts.data.StoredData;
import com.safetynet.Alerts.util.DataReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SafetyNetApplication {

	@Value("${dataFile}")
	private String dataFilePath;

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetApplication.class, args);
	}

	@Bean
	public StoredData readData() throws Exception {
		return DataReader.readFile(dataFilePath);
	}

}
