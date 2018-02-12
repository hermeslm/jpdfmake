package com.onedsol.tools.jpdfmake;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WaterMark extends Text {

    private Float opacity;


    public WaterMark(Float opacity) {
        this.opacity = opacity;
    }

    public WaterMark opacity(Float opacity) {

        this.opacity = opacity;
        return this;
    }

    public Float getOpacity() {
        return opacity;
    }

    public void setOpacity(Float opacity) {
        this.opacity = opacity;
    }
}
