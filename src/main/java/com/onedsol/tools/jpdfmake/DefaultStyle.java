package com.onedsol.tools.jpdfmake;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.enums.Alignment;
import com.onedsol.tools.jpdfmake.enums.Color;

/**
 * Created by hermeslm on 4/13/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultStyle {

    private Integer columnGap;
    private Color color;
    private Integer fontSize;
    private Alignment alignment;
    private Boolean italics;
    private Boolean bold;

    public DefaultStyle(Integer columnGap) {
        this.columnGap = columnGap;
    }

    public DefaultStyle() {

    }

    public DefaultStyle color(Color color) {

        this.color = color;
        return this;
    }

    public DefaultStyle fontSize(Integer fontSize) {

        this.fontSize = fontSize;
        return this;
    }

    public DefaultStyle alignment(Alignment alignment) {

        this.alignment = alignment;
        return this;
    }

    public DefaultStyle italics(Boolean italics) {

        this.italics = italics;
        return this;
    }

    public DefaultStyle bold(Boolean bold) {

        this.bold = bold;
        return this;
    }

    public Integer getColumnGap() {
        return columnGap;
    }

    public void setColumnGap(Integer columnGap) {
        this.columnGap = columnGap;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public Boolean getItalics() {
        return italics;
    }

    public void setItalics(Boolean italics) {
        this.italics = italics;
    }

    public Boolean getBold() {
        return bold;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    @Override
    public String toString() {
        return "DefaultStyle{" +
                "columnGap=" + columnGap +
                '}';
    }
}
