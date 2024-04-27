package ru.alliedar.pokaznoi.web.dto.auth;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import ru.alliedar.pokaznoi.web.dto.validation.OnCreate;
import ru.alliedar.pokaznoi.web.dto.validation.OnPasswordUpdate;


public class UserChangePasswordDto {
    private String email;
    private String oldPassword;

    @NotBlank(message = "Password must not be null!", groups = {OnCreate.class, OnPasswordUpdate.class})
    @Length(message = "Password must be at least 8 symbols long!", min = 8,
            groups = {OnCreate.class, OnPasswordUpdate.class})
    @Length(message = "Password must be less than 255 symbols long!", max = 255,
            groups = {OnCreate.class, OnPasswordUpdate.class})
    private String newPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
