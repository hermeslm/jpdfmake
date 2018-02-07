package com.onedsol.tools.jpdfmake.yo.table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.yo.Item;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableItem extends Item {
    String color;
    Item table;

    public TableItem() {
        table = new TableBody();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Item getTable() {
        return table;
    }

    public void setTable(Item table) {
        this.table = table;
    }
}
