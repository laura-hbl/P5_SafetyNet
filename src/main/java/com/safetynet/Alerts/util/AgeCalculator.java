package com.safetynet.Alerts.util;

import java.time.LocalDate;
import java.time.Period;

public class AgeCalculator {

    public int getAge(LocalDate birthDate) {

        LocalDate currentDate = LocalDate.now();

        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        }

        return 0;
    }
}
