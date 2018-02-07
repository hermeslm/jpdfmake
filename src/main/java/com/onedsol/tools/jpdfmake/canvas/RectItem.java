package com.onedsol.tools.jpdfmake.canvas;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by hermeslm on 5/3/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RectItem extends CanvasElement {

    private CanvasType type = CanvasType.rect;

    private int x;

    private int y;

    private int w;

    private int h;

    private int r;

    private Dash dash;

    private String lineColor;

    private String color;

    //private int lineWidth: 10,

    public RectItem(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public RectItem(int x, int y, int w, int h, int r) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.r = r;
    }

    public RectItem(int x, int y, int w, int h, String color) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
    }

    public CanvasType getType() {
        return type;
    }

    public void setType(CanvasType type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public Dash getDash() {
        return dash;
    }

    public void setDash(Dash dash) {
        this.dash = dash;
    }

    public String getLineColor() {
        return lineColor;
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
