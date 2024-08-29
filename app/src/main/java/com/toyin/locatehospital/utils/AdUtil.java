package com.toyin.locatehospital.utils;

import android.content.Context;
import android.content.res.Resources;

import com.toyin.locatehospital.R;


public class AdUtil {
    static String admobAppId;
    //final static String testId;
    //final static String testId = "ca-app-pub-3940256099942544~3347511713";
    //AdView mAdView;

    public static void initAds(Context ctx){
        admobAppId = ctx.getString(R.string.admob_app_id);
        //MobileAds.initialize(ctx);
    }

//    public static void loadAds(AdView mAdView){
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//    }
}
