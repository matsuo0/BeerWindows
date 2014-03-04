package com.tetsuchem.beerwindow;

// http://qiita.com/gabu/items/673288c3a5b39f89aa92

import android.app.Activity;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.tetsuchem.beerwindow.util.TwitterUtils;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterOAuthActivity extends FragmentActivity {

    private final static String TAG = TwitterOAuthActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_oauth);

        if (savedInstanceState == null) {
            PlaceholderFragment placeholderFragment = new PlaceholderFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment(), TAG).commit();
            //            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        //super.onNewIntent(intent);
        final String fragmentClassName = PlaceholderFragment.class.getName();
        android.support.v4.app.Fragment fragment  = getSupportFragmentManager().findFragmentByTag(TAG);
        ((PlaceholderFragment)fragment).onNewIntent(intent);

        //PlaceholderFragment placeholderFragment = (PlaceholderFragment)getFragmentManager().findFragmentByTag(fragmentClassName);TAG
        //placeholderFragment.onNewIntent(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.twitter_oauth, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final String REQUEST_TOKEN = "request_token";
        private String mCallbackURL;
        private Twitter mTwitter;
        private RequestToken mRequestToken;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_twitter_oauth, container, false);
            mCallbackURL = getString(R.string.twitter_callback_url);
            mTwitter = TwitterUtils.getTwitterInstance(getActivity());

            rootView.findViewById(R.id.action_start_oauth).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startAuthorize();
                }
            });
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        //    mRequestToken = (RequestToken) savedInstanceState.getSerializable(REQUEST_TOKEN);
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
        //    outState.putSerializable(REQUEST_TOKEN, mRequestToken);
        }

        /**
         * OAuth認証（厳密には認可）を開始します。
         *
         */
        private void startAuthorize() {
            AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    try {
                        mRequestToken = mTwitter.getOAuthRequestToken(mCallbackURL);
                        return mRequestToken.getAuthorizationURL();
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, mRequestToken.getAuthorizationURL());
                    return null;
                }

                @Override
                protected void onPostExecute(String url) {
                    if (url != null) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    } else {
                        // 失敗。。。
                    }
                }
            };
            task.execute();
        }

        /**
         * インテントを捕捉する
         * @param intent アクティビティで捕捉したインテントがセットされます
         */
        public void onNewIntent(Intent intent) {
            if (intent == null
                    || intent.getData() == null
                    || !intent.getData().toString().startsWith(mCallbackURL)) {
                return;
            }
            String verifier = intent.getData().getQueryParameter("oauth_verifier");

            AsyncTask<String, Void, AccessToken> task = new AsyncTask<String, Void, AccessToken>() {
                @Override
                protected AccessToken doInBackground(String... params) {
                    try {
                        return mTwitter.getOAuthAccessToken(mRequestToken, params[0]);
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(AccessToken accessToken) {
                    if (accessToken != null) {
                        // 認証成功！
                        showToast("認証成功！");
                        successOAuth(accessToken);
                    } else {
                        // 認証失敗。。。
                        showToast("認証失敗。。。");
                    }
                }
            };
            task.execute(verifier);
        }


        private void successOAuth(AccessToken accessToken) {
            TwitterUtils.storeAccessToken(getActivity(), accessToken);
            Intent intent = new Intent(getActivity(), BeerWindowActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

        private void showToast(String text) {
            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
        }

    }

}
