package com.tetsuchem.beerwindow.model;

import java.util.ArrayList;

/**
 * Created by matsuo on 14/02/17.
 */
public class Shop {

    private static Shop instance = null;

    private String name;
    private ArrayList<AD> ads;
    private ArrayList<BeerInfo> beerInfos;

    protected Shop() {

    }

    public static Shop getInstance(){
        if (instance == null){
            instance = new Shop();
        }
        return instance;
    }

    public ArrayList<AD> getAds() {
        return ads;
    }

    public void setAds(ArrayList<AD> ads) {
        this.ads = ads;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ArrayList<BeerInfo> getBeerInfos() {
        return beerInfos;
    }

    public void setBeerInfos(ArrayList<BeerInfo> beerInfos) {
        this.beerInfos = beerInfos;
    }
}
