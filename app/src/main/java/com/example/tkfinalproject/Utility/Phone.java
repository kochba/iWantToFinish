package com.example.tkfinalproject.Utility;

import java.io.Serializable;

/**
 * Phone is a class that represents a phone with its code, amount, current phone details, and status.
 */
public class Phone implements Serializable {
    int code;
    String amount;
    String currentPhone;
    String stauts;

    /**
     * Constructs a new Phone with the specified code, amount, current phone details, and status.
     *
     * @param code the code of the phone.
     * @param amount the amount associated with the phone.
     * @param currentPhone the current phone details.
     * @param stauts the status of the phone.
     */
    public Phone(int code, String amount, String currentPhone, String stauts) {
        this.code = code;
        this.amount = amount;
        this.currentPhone = currentPhone;
        this.stauts = stauts;
    }

    /**
     * Retrieves the current phone details.
     *
     * @return the current phone details.
     */
    public String getCurrentPhone() {
        return currentPhone;
    }

    /**
     * Sets the current phone details.
     *
     * @param currentPhone the current phone details to set.
     */
    public void setCurrentPhone(String currentPhone) {
        this.currentPhone = currentPhone;
    }

    /**
     * Retrieves the code of the phone.
     *
     * @return the code of the phone.
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets the code of the phone.
     *
     * @param code the code to set.
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Retrieves the amount associated with the phone.
     *
     * @return the amount associated with the phone.
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Sets the amount associated with the phone.
     *
     * @param amount the amount to set.
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Retrieves the status of the phone.
     *
     * @return the status of the phone.
     */
    public String getStauts() {
        return stauts;
    }

    /**
     * Sets the status of the phone.
     *
     * @param stauts the status to set.
     */
    public void setStauts(String stauts) {
        this.stauts = stauts;
    }
}