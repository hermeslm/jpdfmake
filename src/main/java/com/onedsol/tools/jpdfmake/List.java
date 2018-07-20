package com.onedsol.tools.jpdfmake;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class List extends Item {

    String type;
    String markerColor;
    String color;
    String separator;

    public List type(String type) {

        this.type = type;
        return this;
    }

    public List markerColor(String markerColor) {

        this.markerColor = markerColor;
        return this;
    }

    public List color(String color) {

        this.color = color;
        return this;
    }

    public List separator(String separator) {

        this.separator = separator;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMarkerColor() {
        return markerColor;
    }

    public void setMarkerColor(String markerColor) {
        this.markerColor = markerColor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

}
