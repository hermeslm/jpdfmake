package com.onedsol.tools.jpdfmake.canvas;

import com.onedsol.tools.jpdfmake.Element;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by hermeslm on 4/13/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Canvas extends Element {

    private List<CanvasElement> canvas;

    private Boolean[] border;

    public Canvas(List<CanvasElement> canvasElements) {
        super();
        this.canvas = canvasElements;
    }

    public Canvas(List<CanvasElement> canvas, Boolean[] border) {
        super();
        this.canvas = canvas;
        this.border = border;
    }

    public List<CanvasElement> getCanvas() {
        return canvas;
    }

    public void setCanvas(List<CanvasElement> canvas) {
        this.canvas = canvas;
    }


    public Boolean[] getBorder() {
        return border;
    }

    public void setBorder(Boolean[] border) {
        this.border = border;
    }
}
