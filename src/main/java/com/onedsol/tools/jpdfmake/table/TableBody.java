package com.onedsol.tools.jpdfmake.table;

import com.onedsol.tools.jpdfmake.Element;

import java.util.List;

/**
 * Created by hermeslm on 4/14/17.
 */
public class TableBody {

    private Integer headerRows = 1;

    private List<Object> widths;

    private List<List<Element>> body;

    public List<Object> getWidths() {
        return widths;
    }

    public void setWidths(List<Object> widths) {
        this.widths = widths;
    }

    public List<List<Element>> getBody() {
        return body;
    }

    public void setBody(List<List<Element>> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "TableBody{" +
            "headerRows=" + headerRows +
            ", widths=" + widths +
            ", body=" + body +
            '}';
    }

    public Integer getHeaderRows() {
        return headerRows;
    }

    public void setHeaderRows(Integer headerRows) {
        this.headerRows = headerRows;
    }
}
