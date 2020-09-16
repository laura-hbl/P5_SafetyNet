package com.safetynet.Alerts;

import com.safetynet.Alerts.data.DataStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Launch SafetyNet Application.
 *
 * @author Laura Habdul
 */
@SpringBootApplication
public class SafetyNetApplication {

	/**
	 * SafetyNetApplication logger.
	 */
	private static final Logger LOGGER = LogManager.getLogger(SafetyNetApplication.class);

	/**
	 * Starts SafetyNet application.
	 *
	 * @param args no argument
	 */
	public static void main(final String[] args) {
		SpringApplication.run(SafetyNetApplication.class, args);
	}

     /**
	 * Loads application data by calling DataStore.loadData method.
	 */
	public void loadSafetyNetData() throws Exception {
		LOGGER.debug("Read Data File");
		DataStore init = new DataStore();
		init.loadData();
	}
}
