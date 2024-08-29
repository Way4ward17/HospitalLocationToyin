package com.toyin.locatehospital.models;


import java.util.ArrayList;

public class DetailResult {
    String status;

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    String error_message;
    DetailSinglePlace result;

    public DetailResult() {
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DetailSinglePlace getResult() {
        return result;
    }

    public void setResult(DetailSinglePlace result) {
        this.result = result;
    }

    public void setId(String id) {
    }

    public void setName(String name) {
    }

    public void setIcon(String icon) {
    }

    public void setPlaceId(String placeId) {
    }

    public void setVicinity(String vicinity) {
    }

    public void setRating(float rating) {
    }

    public void setPhone(String phone) {
    }

    public void setWeekday(ArrayList<String> weekday) {
    }

    public void setUrl(String url) {
    }

    public void setWebsite(String website) {

    }
}
