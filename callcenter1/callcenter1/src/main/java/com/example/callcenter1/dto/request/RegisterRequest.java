package com.example.callcenter1.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "İsim boş olamaz")
    private String operatorName;

    @NotBlank(message = "Soyisim boş olamaz")
    private String operatorSurname;

    @NotBlank(message = "Telefon numarası boş olamaz")
    @Size(min = 10, max = 15, message = "Telefon numarası 10-15 karakter olmalı")
    private String operatorPhoneNumber;

    @NotBlank(message = "Email boş olamaz")
    @Email(message = "Geçerli bir email giriniz")
    private String operatorEmail;

    @NotBlank(message = "Şifre boş olamaz")
    private String operatorPassword;
}



