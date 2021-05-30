/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubd.account;

import java.io.Serializable;

/**
 *
 * @author CND
 */
public class AccountErr implements Serializable{

    private String usernameErr, passwordErr, rePasswordErr, fullnameErr, emailErr, phoneErr, photoErr;

    public AccountErr() {
    }

    public String getUsernameErr() {
        return usernameErr;
    }

    public void setUsernameErr(String usernameErr) {
        this.usernameErr = usernameErr;
    }

    public String getPasswordErr() {
        return passwordErr;
    }

    public void setPasswordErr(String passwordErr) {
        this.passwordErr = passwordErr;
    }

    public String getRePasswordErr() {
        return rePasswordErr;
    }

    public void setRePasswordErr(String rePasswordErr) {
        this.rePasswordErr = rePasswordErr;
    }

    public String getFullnameErr() {
        return fullnameErr;
    }

    public void setFullnameErr(String fullnameErr) {
        this.fullnameErr = fullnameErr;
    }

    public String getEmailErr() {
        return emailErr;
    }

    public void setEmailErr(String emailErr) {
        this.emailErr = emailErr;
    }

    public String getPhoneErr() {
        return phoneErr;
    }

    public void setPhoneErr(String phoneErr) {
        this.phoneErr = phoneErr;
    }

    public String getPhotoErr() {
        return photoErr;
    }

    public void setPhotoErr(String photoErr) {
        this.photoErr = photoErr;
    }
    
    
}
