package com.onedsol.tools.jpdfmake.canvas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.Item;

import java.util.List;

/**
 * Created by hermeslm on 4/13/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Canvas extends Item {

    private List<Item> canvas;

    private Boolean[] border;

    public Canvas(List<Item> canvasElements) {
        super();
        this.canvas = canvasElements;
    }

    public Canvas(List<Item> canvas, Boolean[] border) {
        super();
        this.canvas = canvas;
        this.border = border;
    }

    public List<Item> getCanvas() {
        return canvas;
    }

    public void setCanvas(List<Item> canvas) {
        this.canvas = canvas;
    }


    public Boolean[] getBorder() {
        return border;
    }

    public void setBorder(Boolean[] border) {
        this.border = border;
    }
}
