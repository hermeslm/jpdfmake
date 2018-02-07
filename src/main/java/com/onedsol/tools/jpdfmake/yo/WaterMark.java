package com.onedsol.tools.jpdfmake.yo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WaterMark extends TextItem{
    Float opacity;
}
