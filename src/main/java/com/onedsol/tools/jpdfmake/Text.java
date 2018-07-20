package com.onedsol.tools.jpdfmake;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.enums.Alignment;
import com.onedsol.tools.jpdfmake.enums.Color;
import com.onedsol.tools.jpdfmake.enums.PageBreak;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Text extends Item {

    protected Object color;
    protected String text;
    protected Integer fontSize;
    protected PageBreak pageBreak;
    protected Alignment alignment;
    protected Boolean italics;
    protected Boolean bold;

    protected ArrayList<String> style = new ArrayList<>();
    protected Object width;

    private Integer margin[];
//    private Boolean preserveLeadingSpaces;

    public Text() {
    }

    public Text(String text) {
        this.text = text;
    }

    public Text(String text, int fontSize) {
        this.text = text;
        this.fontSize = fontSize;
    }

    public Text color(Color color) {

        this.color = color;
        return this;
    }

    public Text color(Object color) {

        this.color = color;
        return this;
    }

    public Text text(String text) {

        this.text = text;
        return this;
    }

    public Text fontSize(Integer fontSize) {

        this.fontSize = fontSize;
        return this;
    }

    public Text pageBreak(PageBreak pageBreak) {

        this.pageBreak = pageBreak;
        return this;
    }

    public Text alignment(Alignment alignment) {

        this.alignment = alignment;
        return this;
    }

    public Text italics(Boolean italics) {

        this.italics = italics;
        return this;
    }

    public Text bold(Boolean bold) {

        this.bold = bold;
        return this;
    }

    public Text margin(Integer[] margin) {

        this.margin = margin;
        return this;
    }

    public Object getColor() {
        return color;
    }

    public void setColor(Object color) {
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

    public Integer[] getMargin() {
        return margin;
    }

    public void setMargin(Integer[] margin) {
        this.margin = margin;
    }

    public Text style(ArrayList<String> style) {

        this.style = style;
        return this;
    }

    public Text width(Object width) {

        this.width = width;
        return this;
    }

    public ArrayList<String> getStyle() {
        return style;
    }

    public void setStyle(ArrayList<String> style) {
        this.style = style;
    }

    public void addStyle(String style) {
        this.style.add(style);
    }

    public Object getWidth() {
        return width;
    }

    public void setWidth(Object width) {
        this.width = width;
    }
}
