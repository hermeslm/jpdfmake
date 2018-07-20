package com.onedsol.tools.jpdfmake;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hermeslm on 4/13/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Column extends Item {

    private String alignment;
    private ArrayList<Object> columns = new ArrayList<>();
    private ArrayList<String> style = new ArrayList<>();
    private Integer columnGap;

    public Column(ArrayList<Object> columns) {

        this.columns = columns;
    }

    public Column() {

    }

    public void addColumn(List item) {
        columns.add(item);
    }

    public void addColumn(Object item) {
        columns.add(item);
    }

    public void addColumn(Item item) {
        columns.add(item);
    }

    public Column alignment(String alignment) {

        this.alignment = alignment;
        return this;
    }

    public Column columns(ArrayList<Object> columns) {

        this.columns = columns;
        return this;
    }

    public Column style(ArrayList<String> style) {

        this.style = style;
        return this;
    }

    public Column columnGap(Integer columnGap) {

        this.columnGap = columnGap;
        return this;
    }

    public List<String> getStyle() {
        return style;
    }

    public void setStyle(ArrayList<String> style) {
        this.style = style;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public List<Object> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<Object> columns) {
        this.columns = columns;
    }

    public Integer getColumnGap() {
        return columnGap;
    }

    public void setColumnGap(Integer columnGap) {
        this.columnGap = columnGap;
    }

    @Override
    public String toString() {
        return "Column{" +
                "alignment='" + alignment + '\'' +
                ", columns=" + columns +
                ", style=" + style +
                ", columnGap=" + columnGap +
                '}';
    }

}
