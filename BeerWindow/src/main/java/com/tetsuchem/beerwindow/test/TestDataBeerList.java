package com.tetsuchem.beerwindow.test;

import com.tetsuchem.beerwindow.model.BeerInfo;
import com.tetsuchem.beerwindow.model.Shop;

import java.util.ArrayList;

/**
 * Created by matsuo on 14/02/17.
 */
public class TestDataBeerList {

    Shop shop;

    public void setBeerList() {
        shop = Shop.getInstance();
        ArrayList<BeerInfo> beerInfos = new ArrayList<BeerInfo>();
        BeerInfo beerInfo = new BeerInfo();
        beerInfo.setImgUrl("");
        beerInfo.setStyle("ペールエール");
        beerInfo.setName("東京ペールエール");
        beerInfo.setBrewer("醸造所:クラフトビールブリューイング");
        beerInfo.setArea("(東京)");
        beerInfo.setContext("すごくおいしいよ");
        beerInfo.setAbv("AVB 5.0%");
        beerInfos.add(beerInfo);
        beerInfos.add(beerInfo);
        beerInfos.add(beerInfo);
        beerInfos.add(beerInfo);
        beerInfos.add(beerInfo);
        shop.setBeerInfos(beerInfos);
    }

    public BeerInfo getBeerInfo() {
        BeerInfo beerInfo = new BeerInfo();
        beerInfo.setImgUrl("");
        beerInfo.setStyle("ペールエール");
        beerInfo.setName("東京ペールエール");
        beerInfo.setBrewer("クラフトビールブリューイング");
        beerInfo.setArea("(東京)");
        //beerInfo.setContext("すごくおいしいよ" + "\n" + "1"+ "\n" + "1"+ "\n" + "1"+ "\n" + "1");
        beerInfo.setContext("すごくおいしいよ。本当にヤバ目のビール。");
        beerInfo.setAbv("AVB 5.0%");
        return beerInfo;
    }

}
