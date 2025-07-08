package com.example.callcenter1.validation;

/**
 * Telefon numaralarının geçerliliğini kontrol etmek için yardımcı sınıf.
 * Şu anki kontrol, 10 haneli (ör: 5XXXXXXXXX) Türk GSM numaraları içindir.
 */
public class PhoneNumberValidator {
    /**
     * Verilen telefon numarasının geçerli olup olmadığını kontrol eder.
     * @param phoneNumber Kontrol edilecek telefon numarası
     * @return Geçerli ise true, değilse false
     */
    public static boolean isValid(String phoneNumber) {
        // Örnek: 10 haneli rakam kontrolü
        return phoneNumber != null && phoneNumber.matches("^\\d{10}$");
    }
}
