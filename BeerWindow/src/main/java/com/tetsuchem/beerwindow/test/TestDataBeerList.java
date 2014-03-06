package com.tetsuchem.beerwindow.test;

import android.content.Context;

import com.tetsuchem.beerwindow.R;
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
        beerInfo.setContext("本当にヤバ目のビール。");
        beerInfo.setAbv("AVB 5.0%");
        return beerInfo;
    }

    public BeerInfo getBeerInfo_Lager() {
        BeerInfo beerInfo = new BeerInfo();
        beerInfo.setImageResouce(R.drawable.beer_lager);
        beerInfo.setImgUrl("");
        beerInfo.setStyle("ライト・ラガー");
        beerInfo.setName("アメリカンラガー");
        beerInfo.setBrewer("アメリカンブリュワリー");
        beerInfo.setArea("(アメリカ)");
        //beerInfo.setContext("すごくおいしいよ" + "\n" + "1"+ "\n" + "1"+ "\n" + "1"+ "\n" + "1");
        beerInfo.setContext("非常にさわやかで、渇きを癒す。");
        beerInfo.setAbv("AVB 4.2%");
        return beerInfo;
    }

    public BeerInfo getBeerInfo_Ale() {
        BeerInfo beerInfo = new BeerInfo();
        beerInfo.setImageResouce(R.drawable.beer_paleale);

        beerInfo.setImgUrl("");
        beerInfo.setStyle("アイリッシュ・エール");
        beerInfo.setName("エール！エール！エール！");
        beerInfo.setBrewer("北の国醸造所");
        beerInfo.setArea("(日本)");
        //beerInfo.setContext("すごくおいしいよ" + "\n" + "1"+ "\n" + "1"+ "\n" + "1"+ "\n" + "1");
        beerInfo.setContext("クリーンなモルト感でドライなフィニッシュ");
        beerInfo.setAbv("AVB 2.8%");
        return beerInfo;
    }

    public BeerInfo getBeerInfo_BlownAle() {
        BeerInfo beerInfo = new BeerInfo();
        beerInfo.setImageResouce(R.drawable.beer_brown);
        beerInfo.setImgUrl("");
        beerInfo.setStyle("マイルド");
        beerInfo.setName("ブラウンエール2014");
        beerInfo.setBrewer("南の島ブリュー");
        beerInfo.setArea("(日本)");
        beerInfo.setContext("軽いフレバー、モルトに強調されたビール");
        beerInfo.setAbv("AVB 4.3%");
        return beerInfo;
    }

    public BeerInfo getBeerInfo_Porter() {
        BeerInfo beerInfo = new BeerInfo();
        beerInfo.setImageResouce(R.drawable.beer_porter);
        beerInfo.setImgUrl("");
        beerInfo.setStyle("ポーター");
        beerInfo.setName("運搬人スペシャル");
        beerInfo.setBrewer("島国PUNK醸造所");
        beerInfo.setArea("(日本)");
        beerInfo.setContext("しっかりとしたモルト感のあるダーク・エールで、複雑な風味豊かなローストの特徴を有する");
        beerInfo.setAbv("AVB 4.3%");
        return beerInfo;
    }

}
