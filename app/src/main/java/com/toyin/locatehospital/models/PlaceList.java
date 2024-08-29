package com.toyin.locatehospital.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;



@Parcel
public class PlaceList {

    @SerializedName("next_page_token")
    String nextPageToken;

    @SerializedName("results")
    public ArrayList<SinglePlace> places;

    @SerializedName("error_message")
    public String error_message;

    public PlaceList(String nextPageToken, ArrayList<SinglePlace> places) {
        this.nextPageToken = nextPageToken;
        this.places = places;
    }

    public PlaceList(){}

}
