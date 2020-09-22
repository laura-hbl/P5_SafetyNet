package com.safetynet.Alerts;

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
}
