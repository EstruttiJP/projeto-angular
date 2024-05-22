package me.register.springboot.dto;

import javax.validation.constraints.NotEmpty;

public class AccountDTO {
    @NotEmpty(message = "this field model cannot be null or empty")
    protected String name;
    @NotEmpty(message = "this field model cannot be null or empty")
    protected String email;
    @NotEmpty(message = "this field model cannot be null or empty")
    protected String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
