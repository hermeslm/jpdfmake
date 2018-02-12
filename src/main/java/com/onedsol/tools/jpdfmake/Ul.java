package com.onedsol.tools.jpdfmake;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ul extends List {

    java.util.List ul = new ArrayList<>();

    public Ul(java.util.List ul) {

        this.ul = ul;
    }

    public Ul ul(java.util.List ul) {

        this.ul = ul;
        return this;
    }

    public void addItem(Item ulTextItem) {
        ul.add(ulTextItem);
    }

    public java.util.List getUl() {
        return ul;
    }

    public void setUl(java.util.List ul) {
        this.ul = ul;
    }

}
