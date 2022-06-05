package org.maktab.OnlineServicesAndRepairsPhase2.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder {
    private static PasswordEncoder encoder;
    static {
        encoder = new BCryptPasswordEncoder();
    }
    public static String hashPassword(String password) {
        return encoder.encode(password);
    }
    public static Boolean matches(String oldPassword, String newPassword){
        return encoder.matches(oldPassword, newPassword);
    }
}
