package com.onedsol.tools.jpdfmake.yo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.Element;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListTextItem extends Item {
    String type;
    String markerColor;
    String color;
    String separator;
}
