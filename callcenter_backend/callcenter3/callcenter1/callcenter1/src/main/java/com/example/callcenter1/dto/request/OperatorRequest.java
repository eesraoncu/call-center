package com.example.callcenter1.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class OperatorRequest {
    @NotBlank(message = "Ad boş olamaz")
    @Size(max = 30, message = "Ad en fazla 30 karakter olabilir")
    private String operatorName;

    @NotBlank(message = "Soyad boş olamaz")
    @Size(max = 30, message = "Soyad en fazla 30 karakter olabilir")
    private String operatorSurname;

    @NotBlank(message = "Email boş olamaz")
    @Email(message = "Geçerli bir email adresi giriniz")
    private String operatorEmail;

    @NotBlank(message = "Telefon numarası boş olamaz")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Geçerli bir telefon numarası giriniz")
    private String operatorPhoneNumber;

    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 6, message = "Şifre en az 6 karakter olmalıdır")
    private String operatorPassword;
}