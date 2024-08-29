package com.toyin.locatehospital.models;

import org.parceler.Parcel;

import java.util.ArrayList;


@Parcel
public class ElementsArray {
    ArrayList<DistanceDuration> elements;

    public ElementsArray() {
    }

    public ArrayList<DistanceDuration> getElements() {
        return elements;
    }

    public void setElements(ArrayList<DistanceDuration> elements) {
        this.elements = elements;
    }
}
