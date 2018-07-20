package com.onedsol.tools.jpdfmake;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.table.Width;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hermeslm on 4/13/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NestedText extends Item {

    private List<Item> text = new ArrayList<>();
    private List<String> style = new ArrayList<>();
    private Integer fontSize;
    private Boolean bold = false;
    private Boolean italics = false;
    private Width width;

    public NestedText text(List<Item> text) {

        this.text = text;
        return this;
    }

    public NestedText style(List<String> style) {

        this.style = style;
        return this;
    }

    public NestedText fontSize(Integer fontSize) {

        this.fontSize = fontSize;
        return this;
    }

    public NestedText bold(Boolean bold) {

        this.bold = bold;
        return this;
    }

    public NestedText italics(Boolean italics) {

        this.italics = italics;
        return this;
    }

    public NestedText width(Width width) {

        this.width = width;
        return this;
    }

    public List<Item> getText() {
        return text;
    }

    public void setText(List<Item> text) {
        this.text = text;
    }

    public void setStyle(List<String> style) {
        this.style = style;
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

    public Width getWidth() {
        return width;
    }

    public void setWidth(Width width) {
        this.width = width;
    }

    @Override
    public String toString() {

        String widthString = width != null ? ", width=" + width : "";

        return "{" +
                "text=" + text +
                ", style=" + style +
                ", fontSize=" + fontSize +
                ", bold=" + bold +
                ", italics=" + italics +
                widthString +
                '}';
    }

}
