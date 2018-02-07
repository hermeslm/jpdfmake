package com.onedsol.tools.jpdfmake.yo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnItem extends TextItem {
    public ColumnItem() {
    }

    public ColumnItem(String text) {
        super(text);
    }

    public ColumnItem(String text, int width) {
        super(text);
        setWidth(width);
    }
}
