package com.onedsol.tools.jpdfmake;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by hermeslm on 4/13/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExtendedTextItem extends Element {

    protected String text;

    protected List<String> style;

    protected Integer fontSize;

    protected Boolean bold = false;

    protected Boolean italics = false;

    protected Alignment alignment;

    protected String color;

    protected Object width;

    protected int margin[];

    protected PageBreak pageBreak;

    protected Boolean preserveLeadingSpaces;

    public ExtendedTextItem() {
    }

    public ExtendedTextItem(String text) {
        super();
        this.text = text;
    }

    public ExtendedTextItem(String text, Alignment alignment) {
        super();
        this.text = text;
        this.alignment = alignment;
    }

    public ExtendedTextItem(String text, Integer fontSize) {
        super();
        this.text = text;
        this.fontSize = fontSize;
    }

    public ExtendedTextItem(String text, String color) {
        super();
        this.text = text;
        this.color = color;
    }

    public ExtendedTextItem(String text, String color, Alignment alignment) {
        super();
        this.text = text;
        this.color = color;
        this.alignment = alignment;
    }

    public ExtendedTextItem(String text, Integer fontSize, Alignment alignment) {
        super();
        this.text = text;
        this.fontSize = fontSize;
        this.alignment = alignment;
    }

    public ExtendedTextItem(String text, String color, Integer fontSize) {
        super();
        this.text = text;
        this.color = color;
        this.fontSize = fontSize;
    }

    public ExtendedTextItem(String text, int width, int fontSize, Alignment alignment) {
        super();
        this.text = text;
        this.width = width;
        this.fontSize = fontSize;
        this.alignment = alignment;
    }

    public ExtendedTextItem(String text, List<String> styles, Alignment alignment, String color) {
        super();
        this.text = text;
        this.style = styles;
        this.alignment = alignment;
        this.color = color;
    }

    public ExtendedTextItem(String text, int fontSize, boolean bold, Alignment alignment) {
        super();
        this.text = text;
        this.fontSize = fontSize;
        this.bold = bold;
        this.alignment = alignment;
    }

    public ExtendedTextItem(String text, int fontSize, boolean bold, Alignment alignment, String color) {
        super();
        this.text = text;
        this.fontSize = fontSize;
        this.bold = bold;
        this.alignment = alignment;
        this.color = color;
    }

    public ExtendedTextItem(String text, int fontSize, Alignment alignment, int[] margin) {
        super();
        this.text = text;
        this.fontSize = fontSize;
        this.alignment = alignment;
        this.margin = margin;
    }

    public ExtendedTextItem(String text, int fontSize, int[] margin) {
        super();
        this.text = text;
        this.fontSize = fontSize;
        this.margin = margin;
    }

    public ExtendedTextItem(String text, String color, Integer fontSize, Alignment alignment) {
        super();
        this.text = text;
        this.fontSize = fontSize;
        this.alignment = alignment;
        this.color = color;
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

    public int[] getMargin() {
        return margin;
    }

    public void setMargin(int[] margin) {
        this.margin = margin;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
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

    public Object getWidth() {
        return width;
    }

    public void setWidth(Object width) {
        this.width = width;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getPreserveLeadingSpaces() {
        return preserveLeadingSpaces;
    }

    public void setPreserveLeadingSpaces(Boolean preserveLeadingSpaces) {
        this.preserveLeadingSpaces = preserveLeadingSpaces;
    }

    @Override
    public String toString() {
        return "ExtendedTextItem{" +
            "text='" + text + '\'' +
            ", style=" + style +
            ", fontSize=" + fontSize +
            ", bold=" + bold +
            ", italics=" + italics +
            ", color='" + color + '\'' +
            ", width=" + width +
            '}';
    }

}
