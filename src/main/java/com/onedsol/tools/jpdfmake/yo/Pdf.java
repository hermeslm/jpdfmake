package com.onedsol.tools.jpdfmake.yo;

import com.onedsol.tools.jpdfmake.PageOrientation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pdf {
    private WaterMark waterMark;
    private List<Object> content = new ArrayList<>();
    private HashMap<String, Object> defaultStyle = new HashMap<>();

    private PageOrientation pageOrientation = PageOrientation.portrait;

    public Pdf() {
        setDefaultStyleFontSize(8);
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

    public void setDefaultStyleFontSize(Object object) {
        defaultStyle.put("fontSize", object);
    }

    public HashMap<String, Object> getDefaultStyle() {
        return defaultStyle;
    }

    public void setDefaultStyle(HashMap<String, Object> defaultStyle) {
        this.defaultStyle = defaultStyle;
    }

    public PageOrientation getPageOrientation() {
        return pageOrientation;
    }

    public void setPageOrientation(PageOrientation pageOrientation) {
        this.pageOrientation = pageOrientation;
    }
}
