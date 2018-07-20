package com.onedsol.tools.jpdfmake;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ol extends List {

    private Long start;
    private Boolean reversed;
    private java.util.List ol = new ArrayList<>();

    public Ol(java.util.List ol) {
        this.ol = ol;
    }

    public Ol start(Long start) {

        this.start = start;
        return this;
    }

    public Ol reversed(Boolean reversed) {

        this.reversed = reversed;
        return this;
    }

    public Ol ol(java.util.List ol) {

        this.ol = ol;
        return this;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Boolean getReversed() {
        return reversed;
    }

    public void setReversed(Boolean reversed) {
        this.reversed = reversed;
    }

    public java.util.List getOl() {
        return ol;
    }

    public void setOl(java.util.List ol) {
        this.ol = ol;
    }
}
