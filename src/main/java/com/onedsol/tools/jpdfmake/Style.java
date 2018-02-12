package com.onedsol.tools.jpdfmake;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Arrays;

/**
 * Created by hermeslm on 4/13/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Style {

    private Integer fontSize = null;
    private Boolean bold = false;
    private Boolean italics = false;
    private String alignment = null;
    private int margin[];
    private String font;

    public Style() {
    }

    public Style(Integer fontSize, Boolean bold, int[] margin) {
        this.fontSize = fontSize;
        this.bold = bold;
        this.margin = new int[]{0, 0, 0, 0};
        this.margin = margin;
    }

    public Style fontSize(Integer fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public Style bold(Boolean bold) {

        this.bold = bold;
        return this;
    }

    public Style italics(Boolean italics) {

        this.italics = italics;
        return this;
    }

    public Style alignment(String alignment) {

        this.alignment = alignment;
        return this;
    }

    public Style margin(int[] margin) {

        this.margin = margin;
        return this;
    }

    public Style font(String font) {

        this.font = font;
        return this;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public Boolean getBold() {
        return bold;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    public Boolean getItalics() {
        return italics;
    }

    public void setItalics(Boolean italics) {
        this.italics = italics;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public int[] getMargin() {
        return margin;
    }

    public void setMargin(int[] margin) {
        this.margin = margin;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    @Override
    public String toString() {

        return "Style{" +
                "fontSize=" + fontSize +
                ", bold=" + bold +
                ", italics=" + italics +
                ", alignment='" + alignment + '\'' +
                ", margin=" + Arrays.toString(margin) +
                ", font='" + font + '\'' +
                '}';
    }
}
