package com.app.storemanager.login;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LoginUtils {
    public static boolean isAccountActive(LocalDate accountValidTill){
        return ChronoUnit.DAYS.between( LocalDate.now(),accountValidTill) > 0;
    }
}
