package com.onedsol.tools.jpdfmake.table;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by hermeslm on 4/14/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableLayout {

    //    private Integer hLineWidth;
    //    private Integer vLineWidth;
    private String hLineColor;
    private String vLineColor;
    private Boolean defaultBorder;

    public TableLayout(Boolean defaultBorder) {
        this.defaultBorder = defaultBorder;
    }

    public TableLayout(String hLineColor, String vLineColor) {
        this.hLineColor = hLineColor;
        this.vLineColor = vLineColor;
    }

    public String gethLineColor() {
        return hLineColor;
    }

    public void sethLineColor(String hLineColor) {
        this.hLineColor = hLineColor;
    }

    public String getvLineColor() {
        return vLineColor;
    }

    public void setvLineColor(String vLineColor) {
        this.vLineColor = vLineColor;
    }

    public Boolean getDefaultBorder() {
        return defaultBorder;
    }

    public void setDefaultBorder(Boolean defaultBorder) {
        this.defaultBorder = defaultBorder;
    }
}
