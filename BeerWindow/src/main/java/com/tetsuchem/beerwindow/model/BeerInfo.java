package com.tetsuchem.beerwindow.model;

/**
 * Created by matsuo on 14/02/17.
 */
public class BeerInfo {

    private String imgUrl;
    private String style;
    private String name;
    private String brewer;
    private String area;
    private String abv;
    private String context;

    public int getImageResouce() {
        return imageResouce;
    }

    public void setImageResouce(int imageResouce) {
        this.imageResouce = imageResouce;
    }

    // getImageResouce
    private int imageResouce;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getBrewer() {
        return brewer;
    }

    public void setBrewer(String brewer) {
        this.brewer = brewer;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
