package com.onedsol.tools.jpdfmake;

/**
 * Created by hermeslm on 4/13/17.
 */
public class DefaultStyle {

    private Integer columnGap;

    public DefaultStyle() {
    }

    public DefaultStyle(Integer columnGap) {
        this.columnGap = columnGap;
    }

    public Integer getColumnGap() {
        return columnGap;
    }

    public void setColumnGap(Integer columnGap) {
        this.columnGap = columnGap;
    }

    @Override
    public String toString() {
        return "DefaultStyle{" +
            "columnGap=" + columnGap +
            '}';
    }
}
