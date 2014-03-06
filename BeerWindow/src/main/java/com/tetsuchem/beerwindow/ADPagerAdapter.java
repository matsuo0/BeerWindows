package com.tetsuchem.beerwindow;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import com.tetsuchem.beerwindow.fragment.ADFragment;
import java.util.ArrayList;

/**
 * Created by matsuo on 14/02/13.
 */
public class ADPagerAdapter extends FragmentStatePagerAdapter {

    private final static String TAG = ADPagerAdapter.class.getSimpleName();
    private ArrayList<String> mStrings;

    /*

     */


    public ADPagerAdapter(Fragment fragment)
    {
        super(fragment.getChildFragmentManager());
        mStrings = new ArrayList<String>();
        mStrings.add("No.1 Fragment");
        mStrings.add("No.2 Fragment");
        mStrings.add("No.3 Fragment");
        mStrings.add("No.4 Fragment");
/*
        mShops = new ArrayList<Shop>();
        prefs = PreferenceManager
                .getDefaultSharedPreferences(fragment.getActivity());
        mShops.clear();
        for (Shop shop : Manager.getInstance().getShops()){
            if (prefs.getInt(CommonUtilities.DELETE_SHOP + shop.getId(), 0) == 0){
                mShops.add(shop);
            }
        }
*/
    }

    @Override
    public Fragment getItem(int position) {

        Log.d(TAG, "Fragment getItem()--Start");
        Bundle bundle = new Bundle();
        Log.d(TAG, "position : " + String.valueOf(position));

        Fragment fragment = null;

        new ADFragment();
        fragment = new ADFragment(mStrings.get(position));

        return fragment;

        /*
        bundle.putString("SHOP_ID", mShops.get(position).getId());
        MainFragment frag = new MainFragment();
        frag.setArguments(bundle);
        Log.d(TAG, "Fragment getItem()--End");
        */
        //return null;
    }

    @Override
    public int getCount() {
        return mStrings.size();
    }
}
