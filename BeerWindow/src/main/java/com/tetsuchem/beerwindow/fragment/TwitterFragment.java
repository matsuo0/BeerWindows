package com.tetsuchem.beerwindow.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.tetsuchem.beerwindow.R;
import com.tetsuchem.beerwindow.TwitterOAuthActivity;
import com.tetsuchem.beerwindow.util.SwipeDetector;
import com.tetsuchem.beerwindow.util.TwitterUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterFragment extends Fragment {

    private static final String TAG = TwitterFragment.class.getName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    static final private String hashTaga = "swallows";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView lvTweet;

    TweetAdapter adapter;
    TweetAdapter adapterShow;

    private Twitter twitter;
    private int count;
    private int position;

    Timer timer = null;
    Timer reloadTimer = null;
    Handler handler;

    SharedPreferences sharedPreferences;
    private String hashTag;
    private int    refreshTime;
    private int    scrollTime;

    Preference preference;

    //sharedPreferences = getPreferences(MODE_PRIVATE);
    //sharedPreferences.getInt(,);
    //sharedPreferences.getInt(,);
    //sharedPreferences.getString(,);

    //scroll_ad_frequency
    //twitter_hash
    //sync_twitter_frequency
    //scroll_twitter_frequency

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TwitterFragment newInstance(String param1, String param2) {
        TwitterFragment fragment = new TwitterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public TwitterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        hashTag = sharedPreferences.getString("twitter_hash", "swallows");
        refreshTime = Integer.valueOf(sharedPreferences.getString("sync_twitter_frequency", "50000"));
        scrollTime = Integer.valueOf(sharedPreferences.getString("scroll_twitter_frequency", "5000"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_twitter, container, false);
        lvTweet = (ListView)view.findViewById(R.id.lvTwitter);
        initializeUI();
        return view;
    }

    public void initializeUI(){

        if (!TwitterUtils.hasAccessToken(getActivity())) {
            Intent intent = new Intent(getActivity(), TwitterOAuthActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            adapter = new TweetAdapter(getActivity(), 0);
            adapterShow = new TweetAdapter(getActivity(), 0);
            lvTweet.setAdapter(adapterShow);
            twitter = TwitterUtils.getTwitterInstance(getActivity());
            reloadTimeLine();
        }

        final Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (adapterShow.getCount() > 0){
                    adapterShow.clear();
                    position = position + 1;
                    if (position == count){
                        position = 0;
                    }
                    adapterShow.add(adapter.getItem(position));
                }
                adapterShow.notifyDataSetChanged();
            }
        });

        final Animation animation_in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in);
        animation_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (adapterShow.getCount() > 0){
                    adapterShow.clear();
                    position = position + 1;
                    if (position == count){
                        position = 0;
                    }
                    adapterShow.add(adapter.getItem(position));
                }
                adapterShow.notifyDataSetChanged();

            }
        });

        final SwipeDetector swipeDetector = new SwipeDetector();
        lvTweet.setOnTouchListener(swipeDetector);
        lvTweet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (swipeDetector.swipeDetected()){
                    switch (swipeDetector.getAction()){
                        case LR:
                            view.startAnimation(animation);
                            break;
                        default :
                            break;
                    }
                }
            }
        });

        timer = new Timer(true);
        handler = new Handler(); // (1)
        timer.schedule( new TimerTask(){
            @Override
            public void run() {
                // mHandlerを通じてUI Threadへ処理をキューイング
                handler.post( new Runnable() {
                    public void run() {
                        lvTweet.startAnimation(animation_in);
                    }
                });
            }
        }, scrollTime, scrollTime);

        reloadTimer = new Timer(true);
        reloadTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                reloadTimeLine();
            }
        }, refreshTime, refreshTime);
    }

    private class TweetAdapter extends ArrayAdapter<Status> {

        private LayoutInflater mInflater;

        public TweetAdapter(Context context, int textViewResourceId){
            super(context, textViewResourceId);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public TweetAdapter(Context context, int textViewResourceId, List<Status> objects){
            super(context, textViewResourceId, objects);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater
                        .inflate(R.layout.tweet_item, null);
                holder = new ViewHolder();

                // Icon
                holder.vIcon = (SmartImageView) convertView.findViewById(R.id.ivIcon);

                // 名前
                holder.vName = (TextView) convertView.findViewById(R.id.tvName);
                //holder.vName.setTextColor(getResources().getColor(android.R.color.black));

                // ID
                holder.vID = (TextView) convertView.findViewById(R.id.tvID);
                //holder.vID.setTextColor(getResources().getColor(android.R.color.black));

                // 書き込み日付
                holder.vDateTime = (TextView) convertView.findViewById(R.id.tvDateTime);
                //holder.vDateTime.setTextColor(getResources().getColor(android.R.color.black));

                // Tweet
                holder.vTweet = (TextView) convertView.findViewById(R.id.tvTweet);
                //holder.vTweet.setTextColor(getResources().getColor(android.R.color.black));

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //final BeerInfo beerInfo = this.getItem(position);

            Status item = getItem(position);
            if (item != null) {
                holder.vIcon.setImageUrl(item.getUser().getProfileImageURL());
                holder.vName.setText(item.getUser().getName());
                //holder.vID.setText("@" + item.getUser().getScreenName() + " : " + String.valueOf(item.getId()));
                holder.vID.setText("@" + item.getUser().getScreenName());
                holder.vDateTime.setText(String.valueOf(item.getCreatedAt()));
                holder.vTweet.setText(item.getText());
            }
            return convertView;
        }


        class ViewHolder {
            SmartImageView vIcon;
            TextView vName;
            TextView vID;
            TextView vDateTime;
            TextView vTweet;
        }

    }


    private void reloadTimeLine() {
        AsyncTask<Void, Void, List<Status>> task = new AsyncTask<Void, Void, List<Status>>() {
            @Override
            protected List<twitter4j.Status> doInBackground(Void... params) {
                Log.d(TAG, "doInBackground()--Start");
                try {
                    Query query = new Query();
                    //1度のリクエストで取得するTweetの数(100が最大)
                    query.setCount(30);
                    query.setLang("ja");
                    query.setQuery("#" + hashTag);
                    //query.setQuery(hashTaga);
                    QueryResult result = twitter.search(query);
                    return result.getTweets();
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<twitter4j.Status> result) {
                Log.d(TAG, "onPostExecute()--Start");
                if (result != null) {
                    adapter.clear();
                    for (twitter4j.Status status : result) {
                        HashtagEntity[] hashtagEntity = status.getHashtagEntities();
                        for (HashtagEntity hashTags : hashtagEntity){
                            if (hashTags.getText().equals(hashTag)){
                                adapter.add(status);
                                Log.d(TAG, status.getCreatedAt().toLocaleString() + "-" + status.getText());
                            }
                        }
                       // Log.d(TAG, status.getCreatedAt().toLocaleString() + "-" + status.getText());
                    }

                    adapterShow.clear(); // Adapterに登録するのは１つのみ
                    if (adapter.getCount() > 0) {
                        adapterShow.add(adapter.getItem(0));
                    }
                    count = adapter.getCount();
                    position = 0;

                    lvTweet.setSelection(0);
                } else {

                }
                Log.d(TAG, "onPostExecute()--End");
            }
        };
        task.execute();
    }



}
