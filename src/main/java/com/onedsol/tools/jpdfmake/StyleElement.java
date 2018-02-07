package com.onedsol.tools.jpdfmake;

/**
 * Created by hermeslm on 4/13/17.
 */
public class StyleElement {

    private String name;

    private Integer fontSize = null;

    private Boolean bold = false;

    private Boolean italics = false;

    private String alignment = null;

    private int margin[] = {0, 0, 0, 0};

    private String font;

    public StyleElement() {
    }

    public StyleElement(Integer fontSize, Boolean bold, int[] margin) {
        this.fontSize = fontSize;
        this.bold = bold;
        this.margin = margin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {

        String align = alignment == null ? "null" : "'" + alignment + "'";

        return name + ": {" +
            "  fontSize=" + fontSize +
            ", bold=" + bold +
            ", italics=" + italics +
            ", alignment=" + align +
            '}';
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
}
