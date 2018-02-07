package com.onedsol.tools.jpdfmake.yo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item {
    protected ArrayList<String> style = new ArrayList<>();
    protected Object width;

    public ArrayList<String> getStyle() {
        return style;
    }

    public void setStyle(ArrayList<String> style) {
        this.style = style;
    }

    public void addStyle(String style) {
        this.style.add(style);
    }

    public Object getWidth() {
        return width;
    }

    public void setWidth(Object width) {
        this.width = width;
    }
}
