package com.onedsol.tools.jpdfmake.yo.table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.yo.Item;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableBody extends Item{
    ArrayList<String> widths = new ArrayList<>();
    Integer headerRows;
    ArrayList<ArrayList<Item>> body = new ArrayList<>();

    public ArrayList<String> getWidths() {
        return widths;
    }

    public void setWidths(ArrayList<String> widths) {
        this.widths = widths;
    }

    public Integer getHeaderRows() {
        return headerRows;
    }

    public void setHeaderRows(Integer headerRows) {
        this.headerRows = headerRows;
    }

    public ArrayList<ArrayList<Item>> getBody() {
        return body;
    }

    public void setBody(ArrayList<ArrayList<Item>> body) {
        this.body = body;
    }
}
