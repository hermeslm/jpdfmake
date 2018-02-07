package com.onedsol.tools.jpdfmake;

import java.util.List;

/**
 * Created by hermeslm on 4/13/17.
 */
public class NestedTextItem extends Element {

    private List<Element> text;
    private List<String> style;
    private Integer fontSize;
    private Boolean bold = false;
    private Boolean italics = false;
    private Width width;

    public List<Element> getText() {
        return text;
    }

    public void setText(List<Element> text) {
        this.text = text;
    }

    public List<String> getStyle() {
        return style;
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

    public Width getWidth() {
        return width;
    }

    public void setWidth(Width width) {
        this.width = width;
    }
}
