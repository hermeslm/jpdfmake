package com.onedsol.tools.jpdfmake.yo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.Element;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UlTextItem extends ListTextItem {
    List<Item> ul = new ArrayList<>();

    public List<Item> getUl() {
        return ul;
    }

    public void setUl(List<Item> ul) {
        this.ul = ul;
    }

    public void addItem(Item ulTextItem) {
        ul.add(ulTextItem);
    }
}
