package com.example.callcenter1.exception;

/**
 * İstenen bir kaynak (örneğin müşteri, operatör, çağrı kaydı) bulunamadığında fırlatılan özel istisna sınıfı.
 * Genellikle 404 NOT FOUND HTTP cevabı ile birlikte kullanılır.
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Hata mesajı ile yeni bir ResourceNotFoundException oluşturur.
     * @param message Hata açıklaması
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
