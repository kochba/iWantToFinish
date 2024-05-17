package com.example.tkfinalproject.Utility;

import java.io.Serializable;

public class Phone implements Serializable {
    int code;
    String amount;
    String currentPhone;
    String stauts;

    public Phone(int code, String amount, String currentPhone,String stauts) {
        this.code = code;
        this.amount = amount;
        this.currentPhone = currentPhone;
        this.stauts = stauts;
    }

    public String getCurrentPhone() {
        return currentPhone;
    }

    public void setCurrentPhone(String currentPhone) {
        this.currentPhone = currentPhone;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts;
    }
}
