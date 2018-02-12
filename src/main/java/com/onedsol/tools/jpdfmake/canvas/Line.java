package com.onedsol.tools.jpdfmake.canvas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.Item;
import com.onedsol.tools.jpdfmake.enums.CanvasType;

/**
 * Created by hermeslm on 5/3/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Line extends Item {

    private CanvasType type = CanvasType.line;

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int lineWidth;

    public Line(int x1, int y1, int x2, int y2, int lineWidth) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.lineWidth = lineWidth;
    }

    public CanvasType getType() {
        return type;
    }

    public void setType(CanvasType type) {
        this.type = type;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }
}
