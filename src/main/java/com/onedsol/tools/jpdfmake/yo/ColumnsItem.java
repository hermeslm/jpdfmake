package com.onedsol.tools.jpdfmake.yo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.Element;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnsItem extends Item {
    ArrayList<Object> columns = new ArrayList<>();

    public ArrayList<Object> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<Object> columns) {
        this.columns = columns;
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
}
