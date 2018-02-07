package com.onedsol.tools.jpdfmake.table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.Alignment;
import com.onedsol.tools.jpdfmake.ExtendedTextItem;

/**
 * Created by hermeslm on 4/13/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableItem extends ExtendedTextItem {

    private int[] margin;

    private Boolean[] border;

    private String fillColor;

    private Integer colSpan;

    private Integer rowSpan;

    public TableItem(String text) {
        super(text);
    }

    public TableItem(String text, int colSpan) {
        super(text);
        this.colSpan = colSpan;
    }
    public TableItem(String text, Integer colSpan, Integer fontSize, boolean bold) {
        super(text);
        this.colSpan = colSpan;
        this.fontSize = fontSize;
        this.bold = bold;
    }

    public TableItem(String text, String fillColor) {
        super(text);
        this.fillColor = fillColor;
    }

    public TableItem(String text, String fillColor, String color) {
        super(text, color);
        this.fillColor = fillColor;
    }

    public TableItem(String text, Alignment alignment) {
        super(text, alignment);
    }

    public TableItem(String text, Alignment alignment, int rowSpan) {

        super(text, alignment);
        this.rowSpan = rowSpan;
    }

    public TableItem(String text, Integer fontSize) {
        super(text, fontSize);
    }

    public TableItem(String text, String fillColor, Integer fontSize) {
        super(text, fontSize);
        this.fillColor = fillColor;
    }

    public TableItem(String text, String fillColor, String color, Integer fontSize) {
        super(text, color, fontSize);
        this.fillColor = fillColor;
    }

    public TableItem(String text, String fillColor, String color, Alignment alignment) {
        super(text, color, alignment);
        this.fillColor = fillColor;
    }

    public TableItem(String text, String fillColor, String color, Integer fontSize, Alignment alignment) {
        super(text, color, fontSize, alignment);
        this.fillColor = fillColor;
    }

    public TableItem(String text, String fillColor, Integer fontSize, Alignment alignment) {
        super(text, fontSize, alignment);
        this.fillColor = fillColor;
    }

    public TableItem(String text, Integer fontSize, Alignment alignment) {
        super(text, fontSize, alignment);
    }

    public TableItem(String text, Integer fontSize, Alignment alignment, int rowSpan) {
        super(text, fontSize, alignment);
        this.rowSpan = rowSpan;
    }

    public TableItem(String text, String color, Integer fontSize, Alignment alignment, int[] margin,
                     Boolean[] border, String fillColor) {
        super(text, color, fontSize, alignment);
        this.margin = margin;
        this.border = border;
        this.fillColor = fillColor;
    }

    public TableItem(String text, String color, Integer fontSize, Alignment alignment, int[] margin,
                     Boolean[] border, String fillColor, int colSpan) {
        super(text, color, fontSize, alignment);
        this.margin = margin;
        this.border = border;
        this.fillColor = fillColor;
        this.colSpan = colSpan;
    }

    public TableItem(String text, String fillColor, String color, Integer fontSize, int colSpan) {

        super(text, color, fontSize);
        this.fillColor = fillColor;
        this.colSpan = colSpan;
    }

    public TableItem(String text, String fillColor, String color, Integer fontSize, Boolean[] border, int colSpan) {

        super(text, color, fontSize);
        this.border = border;
        this.fillColor = fillColor;
        this.colSpan = colSpan;
    }

    public TableItem(String text, Integer fontSize, Alignment alignment,
                     Boolean[] border) {

        super(text, fontSize, alignment);
        this.border = border;
    }

    public TableItem(String text, String fillColor, String color, Integer fontSize, Alignment alignment,
                     Boolean[] border) {

        super(text, color, fontSize, alignment);
        this.border = border;
        this.fillColor = fillColor;
    }

    public TableItem(String text, String fillColor, String color, Integer fontSize, Alignment alignment,
                     Boolean[] border, int colSpan) {

        super(text, color, fontSize, alignment);
        this.border = border;
        this.fillColor = fillColor;
        this.colSpan = colSpan;
    }

    public Integer getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(Integer rowSpan) {
        this.rowSpan = rowSpan;
    }

    public Object getWidth() {
        return width;
    }

    public void setWidth(Object width) {
        this.width = width;
    }

    public int[] getMargin() {
        return margin;
    }

    public void setMargin(int[] margin) {
        this.margin = margin;
    }

    public Boolean[] getBorder() {
        return border;
    }

    public void setBorder(Boolean[] border) {
        this.border = border;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public Integer getColSpan() {
        return colSpan;
    }

    public void setColSpan(Integer colSpan) {
        this.colSpan = colSpan;
    }
}
