package com.onedsol.tools.jpdfmake.yo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.Alignment;
import com.onedsol.tools.jpdfmake.PageBreak;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TextItem extends Item {
    String color;
    String text;
    Integer fontSize;
    PageBreak pageBreak;
    Alignment alignment;
    Boolean italics;
    Boolean bold;

    public TextItem() {
    }

    public TextItem(String text) {
        this.text = text;
    }

    public TextItem(String text, int fontSize) {
        this.text = text;
        this.fontSize = fontSize;
    }

    public TextItem color(String color) {
        this.color = color;

        return this;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public PageBreak getPageBreak() {
        return pageBreak;
    }

    public void setPageBreak(PageBreak pageBreak) {
        this.pageBreak = pageBreak;
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
}
