package com.autoservice.dto;

import javax.validation.constraints.NotBlank;

public class UserRegistrationDto {
    @NotBlank private String fullName;
    @NotBlank private String birthDate; // временно строка
    @NotBlank private String login;
    @NotBlank private String password;

    // конструктор по умолчанию
    public UserRegistrationDto() {}

    // геттеры и сеттеры
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}