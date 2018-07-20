package com.onedsol.tools.jpdfmake;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onedsol.tools.jpdfmake.enums.PageBreak;

/**
 * Created by hermeslm on 4/13/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Image extends Item {

    private String image;
    private Integer width;
    private Integer height;

    //Taken from table cell if we use image in tables.
    private Integer[] margin;
    private Boolean[] border;
    private String fillColor;
    private Integer colSpan;
    private Integer rowSpan;


    //fit format: [100, 300] to the library.
    private Integer[] fit;
    private PageBreak pageBreak;

    public Image() {
    }

    public Image(String image, Integer width, Integer height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public Image image(String image) {

        this.image = image;
        return this;
    }

    public Image width(Integer width) {

        this.width = width;
        return this;
    }

    public Image height(Integer height) {

        this.height = height;
        return this;
    }

    public Image fit(Integer[] fit) {

        this.fit = fit;
        return this;
    }

    public Image pageBreak(PageBreak pageBreak) {

        this.pageBreak = pageBreak;
        return this;
    }

    public Image fillColor(String fillColor) {

        this.fillColor = fillColor;
        return this;
    }

    public Integer getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(Integer rowSpan) {
        this.rowSpan = rowSpan;
    }

    public Integer[] getMargin() {
        return margin;
    }

    public void setMargin(Integer[] margin) {
        this.margin = margin;
    }

    public Boolean[] getBorder() {
        return border;
    }

    public void setBorder(Boolean[] border) {
        this.border = border;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public Integer getColSpan() {
        return colSpan;
    }

    public void setColSpan(Integer colSpan) {
        this.colSpan = colSpan;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public PageBreak getPageBreak() {
        return pageBreak;
    }

    public void setPageBreak(PageBreak pageBreak) {
        this.pageBreak = pageBreak;
    }

    public Integer[] getFit() {
        return fit;
    }

    public void setFit(Integer[] fit) {
        this.fit = fit;
    }

    @Override
    public String toString() {
        return "Image{" +
                "image='" + image + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", pageBreak='" + pageBreak + '\'' +
                '}';
    }
}
