package com.onedsol.tools.jpdfmake.yo.table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.TextItem;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableCellItem extends TextItem {
    Integer colSpan;
    Integer rowSpan;
    Boolean[] border = new Boolean[4];
    String fillColor;
    Integer[] margin = new Integer[4];
}
