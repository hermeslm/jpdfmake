package com.onedsol.tools.jpdfmake.yo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.Element;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OlTextItem extends ListTextItem {
    Long start;
    Boolean reversed;
    List<Element> ol = new ArrayList<>();
}
