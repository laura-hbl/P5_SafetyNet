package com.safetynet.Alerts.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class AgeCalculator {

    private static final Logger LOGGER = LogManager.getLogger(AgeCalculator.class);

    public int getAge(LocalDate birthDate) {

        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();

        if (birthDate.isAfter(currentDate)) {
            LOGGER.error("BirthDate provided is incorrect:" + birthDate);
            throw new IllegalArgumentException("Invalid: birthDate is in the future");
        } else if (age == 0) {
            LOGGER.debug("Baby with less than one year");
            age++;
        }

        return age;
    }
}
