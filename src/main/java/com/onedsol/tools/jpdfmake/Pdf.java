package com.onedsol.tools.jpdfmake;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.enums.PageOrientation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pdf {

    private WaterMark waterMark;
    private List<Object> content = new ArrayList<>();
    private HashMap<String, Object> styles = new HashMap<>();
    private DefaultStyle defaultStyle;
    private PageOrientation pageOrientation = PageOrientation.portrait;

    public Pdf() {
    }

    public Pdf waterMark(WaterMark waterMark) {

        this.waterMark = waterMark;
        return this;
    }

    public Pdf content(List<Object> content) {

        this.content = content;
        return this;
    }

    public Pdf styles(HashMap<String, Object> styles) {

        this.styles = styles;
        return this;
    }

    public Pdf defaultStyle(DefaultStyle defaultStyle) {

        this.defaultStyle = defaultStyle;
        return this;
    }

    public Pdf pageOrientation(PageOrientation pageOrientation) {

        this.pageOrientation = pageOrientation;
        return this;
    }

    public void addItem(Object item) {
        content.add(item);
    }

    public void addItem(Item item) {
        content.add(item);
    }

    public List<Object> getContent() {
        return content;
    }

    public void setContent(List<Object> content) {
        this.content = content;
    }

    public WaterMark getWaterMark() {
        return waterMark;
    }

    public void setWaterMark(WaterMark waterMark) {
        this.waterMark = waterMark;
    }

    public DefaultStyle getDefaultStyle() {
        return defaultStyle;
    }

    public void setDefaultStyle(DefaultStyle defaultStyle) {
        this.defaultStyle = defaultStyle;
    }

    public PageOrientation getPageOrientation() {
        return pageOrientation;
    }

    public void setPageOrientation(PageOrientation pageOrientation) {
        this.pageOrientation = pageOrientation;
    }

    public HashMap<String, Object> getStyles() {
        return styles;
    }

    public void setStyles(HashMap<String, Object> styles) {
        this.styles = styles;
    }
}
