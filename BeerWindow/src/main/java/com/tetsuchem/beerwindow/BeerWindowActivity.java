package com.tetsuchem.beerwindow;


import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import com.tetsuchem.beerwindow.fragment.BeerInfoListFragment;
import com.tetsuchem.beerwindow.fragment.TwitterFragment;
import java.util.Locale;

public class BeerWindowActivity extends FragmentActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    FragmentActivity mFragmentActivity;

    private boolean pagerMoved = false;
    private static final long ANIM_VIEWPAGER_DELAY = 2000;

    SharedPreferences sharedPreferences;
    private int    scrollTime;

    private Handler h = new Handler();
    private Runnable animateViewPager = new Runnable() {
        public void run() {
            if (!pagerMoved) {
                if (mViewPager != null){
                    if (mViewPager.getCurrentItem() == mViewPager.getChildCount()){
                        mViewPager.setCurrentItem(0, true);
                    } else {
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1, true);
                    }
                } else {
                    mViewPager = (ViewPager)findViewById(R.id.viewPager);
                    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                    mViewPager.setAdapter(mSectionsPagerAdapter);
                }
                //h.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
                h.postDelayed(animateViewPager, scrollTime);

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ステータスバーを消す//
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_beer_window);

        // Keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        scrollTime = Integer.valueOf(sharedPreferences.getString("scroll_ad_frequency", "5000"));

        mFragmentActivity = this;

        //Color Scheme Designer
        //http://colorschemedesigner.com/

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frameShopLogo, new ShopLogoFragment())
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frameTwitter, TwitterFragment.newInstance("", ""))
                    //.add(R.id.frameTwitter, new TwitterFragment("", ""))
                    .commit();
            getFragmentManager().beginTransaction()
                    .add(R.id.frameBeerInfo, new BeerInfoListFragment())
                    .commit();

            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            mViewPager = (ViewPager) findViewById(R.id.viewPager);
            mViewPager.setAdapter(mSectionsPagerAdapter);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (h != null){
            h.removeCallbacks(animateViewPager);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        h.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ShopLogoの表示
    public static class ShopLogoFragment extends Fragment {

        public ShopLogoFragment(){

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_shoplogo, container, false);
            return rootView;
        }

    }

    /*
    // Twitterの表示
    public static class TwitterFragment extends Fragment {

        public TwitterFragment(){

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

    }
    */

    // BeerInfoの表示
    public static class BeerInfoFragment extends Fragment {

        public BeerInfoFragment(){

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_beer_info_list, container, false);
            return rootView;
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    //return getString("Selection No1")toUpperCase(l);
                    return "Selection No.1";
                case 1:
                    //return getString(R.string.title_section2).toUpperCase(l);
                    return "Selection No.2";
                case 2:
                    //return getString(R.string.title_section3).toUpperCase(l);
                    return "Selection No.3";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }


}
