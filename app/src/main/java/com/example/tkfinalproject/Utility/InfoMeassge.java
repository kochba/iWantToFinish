package com.example.tkfinalproject.Utility;

public class InfoMeassge {
    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrentPhone() {
        return currentPhone;
    }

    public String getStauts() {
        return stauts;
    }

    public String getMethod() {
        return Method;
    }

    String name;
    String amount;
    String currentPhone;
    String stauts;
    String Method;
    public InfoMeassge(String name, String amount, String currentPhone, String stauts, String method) {
        this.name = name;
        this.amount = amount;
        this.currentPhone = currentPhone;
        this.stauts = stauts;
        Method = method;
    }


}
