package com.onedsol.tools.jpdfmake;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by hermeslm on 4/13/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageItem extends Element {

    private String image;

    private Integer width;

    private Integer height;

    //TODO add fit: [100, 300] to the library.

    private String pageBreak;

    public ImageItem() {
    }

    public ImageItem(String image, Integer width, Integer height) {
        this.image = image;
        this.width = width;
        this.height = height;
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

    public String getPageBreak() {
        return pageBreak;
    }

    public void setPageBreak(String pageBreak) {
        this.pageBreak = pageBreak;
    }

    @Override
    public String toString() {
        return "ImageItem{" +
            "image='" + image + '\'' +
            ", width=" + width +
            ", height=" + height +
            ", pageBreak='" + pageBreak + '\'' +
            '}';
    }
}
