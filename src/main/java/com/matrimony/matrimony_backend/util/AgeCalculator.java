package com.matrimony.matrimony_backend.util;

import java.time.LocalDate;
import java.time.Period;

public class AgeCalculator {
    public static int calculateAge(LocalDate dob) {
        if (dob == null) return 0;
        return Period.between(dob, LocalDate.now()).getYears();
    }
}
