package com.onedsol.tools.jpdfmake.table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.Text;

/**
 * Created by hermeslm on 4/13/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableCell extends Text {

    private Integer[] margin;
    private Boolean[] border;
    private String fillColor;
    private Integer colSpan;
    private Integer rowSpan;

    public TableCell(String text) {
        super(text);
    }

    public TableCell(String text, Integer fontSize) {

        super(text, fontSize);
    }

    public TableCell(String text, String fillColor, Integer fontSize) {

        super(text, fontSize);
        this.fillColor = fillColor;
    }

    public TableCell fillColor(String fillColor) {

        this.fillColor = fillColor;
        return this;
    }

    public Integer getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(Integer rowSpan) {
        this.rowSpan = rowSpan;
    }

    public Integer[] getMargin() {
        return margin;
    }

    public void setMargin(Integer[] margin) {
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
