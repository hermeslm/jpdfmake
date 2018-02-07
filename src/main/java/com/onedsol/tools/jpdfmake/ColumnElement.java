package com.onedsol.tools.jpdfmake;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hermeslm on 4/13/17.
 */
public class ColumnElement extends Element {

    private String alignment;

    private List<Element> columns = new ArrayList<>();
    private List<String> style;
    private Integer columnGap;

    public ColumnElement() {
    }

    public ColumnElement(Integer columnGap) {
        this.columnGap = columnGap;
    }

    public ColumnElement(String alignment, List<Element> columns, List<String> style, Integer columnGap) {
        this.alignment = alignment;
        this.columns = columns;
        this.style = style;
        this.columnGap = columnGap;
    }

    public List<String> getStyle() {
        return style;
    }

    public void setStyle(List<String> style) {
        this.style = style;
    }

    @Override
    public String toString() {
        return "ColumnElement{" +
            "alignment='" + alignment + '\'' +
            ", columns=" + columns +
            ", style=" + style +
            ", columnGap=" + columnGap +
            '}';
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public List<Element> getColumns() {
        return columns;
    }

    public void setColumns(List<Element> columns) {
        this.columns = columns;
    }

    public Integer getColumnGap() {
        return columnGap;
    }

    public void setColumnGap(Integer columnGap) {
        this.columnGap = columnGap;
    }
}
