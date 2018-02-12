package com.onedsol.tools.jpdfmake.table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.Item;

import java.util.List;

/**
 * Created by hermeslm on 4/13/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableBody {

    private Integer[] heights;
    private Integer headerRows;
    private Object widths;
    private List<List<Item>> body;

    public TableBody heights(Integer[] heights) {

        this.heights = heights;
        return this;
    }

    public TableBody headerRows(Integer headerRows) {

        this.headerRows = headerRows;
        return this;
    }

    public TableBody widths(Object widths) {

        this.widths = widths;
        return this;
    }

    public TableBody body(List<List<Item>> body) {

        this.body = body;
        return this;
    }

    public Object getWidths() {
        return widths;
    }

    public void setWidths(Object widths) {
        this.widths = widths;
    }

    public List<List<Item>> getBody() {
        return body;
    }

    public void setBody(List<List<Item>> body) {
        this.body = body;
    }

    public Integer[] getHeights() {
        return heights;
    }

    public void setHeights(Integer[] heights) {
        this.heights = heights;
    }

    public Integer getHeaderRows() {
        return headerRows;
    }

    public void setHeaderRows(Integer headerRows) {
        this.headerRows = headerRows;
    }
}
