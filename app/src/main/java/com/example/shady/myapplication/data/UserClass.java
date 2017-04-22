package com.example.shady.myapplication.data;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

/**
 * Created by ShaDy on 3/24/2017.
 */

@IgnoreExtraProperties
public class UserClass {

    public String userId;
    public String username;
    public String email;
    public String password;
    public String phone;
    public HashMap<String, MedicInformation> medicines;

    public UserClass() {
        // Default constructor required for calls to DataSnapshot.getValue(UserClass.class)
    }

    public UserClass(String userId, String username, String email, String password, String phone) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;

    }

    public HashMap<String, MedicInformation> getMedicines() {
        return medicines;
    }

    public void setMedicines(HashMap<String, MedicInformation> medicines) {
        this.medicines = medicines;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



}