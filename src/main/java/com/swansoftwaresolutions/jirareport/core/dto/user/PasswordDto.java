package com.swansoftwaresolutions.jirareport.core.dto.user;

import com.swansoftwaresolutions.jirareport.web.validation.PasswordsEqualConstraint;

import javax.validation.constraints.NotNull;

/**
 * @author Vladimir Martynyuk
 */
@PasswordsEqualConstraint(message = "Passwords do not match", field = "newPasswordAgain")
public class PasswordDto {

    @NotNull
    private String newPassword;

    @NotNull
    private String newPasswordAgain;


    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordAgain() {
        return newPasswordAgain;
    }

    public void setNewPasswordAgain(String newPasswordAgain) {
        this.newPasswordAgain = newPasswordAgain;
    }
}
