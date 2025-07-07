package com.example.callcenter1.exception;

/**
 * Giriş veya iş kurallarına uygun olmayan verilerle karşılaşıldığında fırlatılan özel doğrulama istisnası.
 * Örneğin, e-posta veya telefon numarası formatı hatalıysa bu exception kullanılabilir.
 */
public class ValidationException extends RuntimeException {
    /**
     * Hata mesajı ile yeni bir ValidationException oluşturur.
     * @param message Hata açıklaması
     */
    public ValidationException(String message) {
        super(message);
    }
}
