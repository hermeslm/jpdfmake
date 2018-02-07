package com.onedsol.tools.jpdfmake;

/**
 * Created by hermeslm on 4/14/17.
 */
public class LayoutElement {

//    private Integer hLineWidth;
//    private Integer vLineWidth;
    private String hLineColor;
    private String vLineColor;

    public LayoutElement(String hLineColor, String vLineColor) {
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
}
