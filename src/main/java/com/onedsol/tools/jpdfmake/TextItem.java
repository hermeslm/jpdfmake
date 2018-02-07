package com.onedsol.tools.jpdfmake;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by hermeslm on 4/13/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TextItem extends Element {

    private String text;

    private PageBreak pageBreak;

    public TextItem() {
    }

    public TextItem(String text) {
        this.text = text;
    }

    public TextItem(String text, PageBreak pageBreak) {
        this.text = text;
        this.pageBreak = pageBreak;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "'" + text + "'";
    }

    public PageBreak getPageBreak() {
        return pageBreak;
    }

    public void setPageBreak(PageBreak pageBreak) {
        this.pageBreak = pageBreak;
    }
}
