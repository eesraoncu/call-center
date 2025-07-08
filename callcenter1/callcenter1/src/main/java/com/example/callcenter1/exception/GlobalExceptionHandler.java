package com.example.callcenter1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Uygulama genelinde fırlatılan özel ve genel istisnaları yakalayan global exception handler sınıfıdır.
 * Her bir exception türü için uygun HTTP cevabı ve mesajı döner.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Kaynak bulunamadığında fırlatılan istisnaları yakalar.
     * @param ex ResourceNotFoundException
     * @return 404 NOT FOUND ve hata mesajı
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Doğrulama hatalarını yakalar.
     * @param ex ValidationException
     * @return 400 BAD REQUEST ve hata mesajı
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Uygulamanın iş kurallarına özel hataları yakalar.
     * @param ex CustomException
     * @return 400 BAD REQUEST ve hata mesajı
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Diğer tüm beklenmeyen istisnaları yakalar.
     * @param ex Exception
     * @return 500 INTERNAL SERVER ERROR ve genel hata mesajı
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("Beklenmeyen bir hata oluştu: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
