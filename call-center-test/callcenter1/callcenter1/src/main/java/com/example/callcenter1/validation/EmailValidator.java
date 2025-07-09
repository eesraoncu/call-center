package com.example.callcenter1.validation;

import java.util.regex.Pattern;

/**
 * E-posta adreslerinin geçerliliğini kontrol etmek için yardımcı sınıf.
 * Temel bir regex ile e-posta formatı doğrulaması yapar.
 */
public class EmailValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    /**
     * Verilen e-posta adresinin geçerli olup olmadığını kontrol eder.
     * @param email Kontrol edilecek e-posta adresi
     * @return Geçerli ise true, değilse false
     */
    public static boolean isValid(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
}
