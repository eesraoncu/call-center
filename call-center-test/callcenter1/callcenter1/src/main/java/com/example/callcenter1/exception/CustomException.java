package com.example.callcenter1.exception;

/**
 * Uygulamanın iş kurallarına özel hata durumlarında fırlatılmak üzere kullanılan genel amaçlı exception sınıfıdır.
 * Örneğin, bir müşteri kaydı sırasında iş kuralı ihlali olduğunda bu exception fırlatılabilir.
 */
public class CustomException extends RuntimeException {
    /**
     * Hata mesajı ile yeni bir CustomException oluşturur.
     * @param message Hata açıklaması
     */
    public CustomException(String message) {
        super(message);
    }
} 