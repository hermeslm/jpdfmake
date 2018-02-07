package com.onedsol.tools.jpdfmake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hermeslm on 4/13/17.
 */
public class Pdf {

    private List<ContentElement> content = new ArrayList<>();

    private List<StyleElement> styles = new ArrayList<>();

    private DefaultStyle defaultStyle;

    private PageOrientation pageOrientation = PageOrientation.portrait;

    public Pdf() {
    }

    public Pdf(List<ContentElement> content) {
        this.content = content;
    }

    public List<ContentElement> getContent() {
        return content;
    }

    public void addContentElement(ContentElement ...contentElement) {
        for (ContentElement element: contentElement){
            content.add(element);
        }
    }

    public void setContent(List<ContentElement> content) {
        this.content = content;
    }

    public List<StyleElement> getStyles() {
        return styles;
    }

    public void setStyles(List<StyleElement> styles) {
        this.styles = styles;
    }

    public DefaultStyle getDefaultStyle() {
        return defaultStyle;
    }

    public void setDefaultStyle(DefaultStyle defaultStyle) {
        this.defaultStyle = defaultStyle;
    }

    @Override
    public String toString() {
        return "Pdf{" +
            "content=" + content +
            ", styles=" + styles +
            ", defaultStyle=" + defaultStyle +
            '}';
    }

    public PageOrientation getPageOrientation() {
        return pageOrientation;
    }

    public void setPageOrientation(PageOrientation pageOrientation) {
        this.pageOrientation = pageOrientation;
    }
}
