package com.onedsol.tools.jpdfmake.canvas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.Item;

/**
 * Created by hermeslm on 5/11/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dash extends Item {

    private Integer length;

    public Dash(Integer length) {
        this.length = length;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
