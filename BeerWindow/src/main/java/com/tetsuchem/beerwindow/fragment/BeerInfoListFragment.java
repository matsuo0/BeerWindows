package com.tetsuchem.beerwindow.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tetsuchem.beerwindow.R;
import com.tetsuchem.beerwindow.model.BeerInfo;
import com.tetsuchem.beerwindow.model.Shop;
import com.tetsuchem.beerwindow.test.TestDataBeerList;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BeerInfoListFragment extends Fragment {

    private final static String TAG = BeerInfoListFragment.class.getName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "Beer";
    private static final String ARG_PARAM2 = "Info";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    private ListView lvBeerInfos;
    BeerInfosAdapter adapter;

    List<BeerInfo> beerInfoList = new ArrayList<BeerInfo>();

    Timer timer = null;
    Timer reloadTimer = null;
    Handler handler;

    int iAdapterCount;

    private void scrollMyListViewToBottom() {
        lvBeerInfos.post(new Runnable() {
            @Override
            public void run() {

                if (iAdapterCount == adapter.getCount()){
                    iAdapterCount = 0;
                }
                lvBeerInfos.setSelection(iAdapterCount);

                iAdapterCount = iAdapterCount + 1;
            }
        });
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BeerInfoList.
     */
    // TODO: Rename and change types and number of parameters
    public static BeerInfoListFragment newInstance(String param1, String param2) {
        BeerInfoListFragment fragment = new BeerInfoListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public BeerInfoListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beer_info_list, container, false);
        lvBeerInfos = (ListView)view.findViewById(R.id.lv_beer_infos);

        initializeUI();
        return view;
    }

    public void initializeUI(){

        TestDataBeerList testDataBeerList = new TestDataBeerList();
        beerInfoList.clear();
        beerInfoList.add(testDataBeerList.getBeerInfo());
        beerInfoList.add(testDataBeerList.getBeerInfo());
        beerInfoList.add(testDataBeerList.getBeerInfo());
        beerInfoList.add(testDataBeerList.getBeerInfo());
        beerInfoList.add(testDataBeerList.getBeerInfo());
        beerInfoList.add(testDataBeerList.getBeerInfo());
        beerInfoList.add(testDataBeerList.getBeerInfo());
        adapter = new BeerInfosAdapter(getActivity().getApplicationContext(), beerInfoList);
        lvBeerInfos.setAdapter(adapter);


        iAdapterCount = 0;
        reloadTimer = new Timer(true);
        reloadTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                scrollMyListViewToBottom();
            }
        }, 5000, 5000);
    }

    public class BeerInfosAdapter extends ArrayAdapter<BeerInfo>{
        Context mContext;
        private LayoutInflater mInflater;

        public BeerInfosAdapter(Context context, List<BeerInfo> objects) {
            super(context, 0, objects);
            this.mContext = context;
            mInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater
                        .inflate(R.layout.beer_item, null);
                holder = new ViewHolder();

                // Icon
                holder.vIcon = (ImageView) convertView.findViewById(R.id.ivIcon);

                // Style
                holder.vStyle = (TextView) convertView.findViewById(R.id.tvStyle);
                //holder.vStyle.setTextColor(getResources().getColor(android.R.color.black));

                // 醸造所名
                holder.vBrewery = (TextView) convertView.findViewById(R.id.tvBrewery);
                //holder.vBrewery.setTextColor(getResources().getColor(android.R.color.black));

                // ビール名
                holder.vName = (TextView) convertView.findViewById(R.id.tvName);
                //holder.vName.setTextColor(getResources().getColor(android.R.color.black));

                // 説明
                holder.vContent = (TextView) convertView.findViewById(R.id.tvContent);
                //holder.vContent.setTextColor(getResources().getColor(android.R.color.black));

                // アルコール度数
                holder.vABV = (TextView) convertView.findViewById(R.id.tvABV);
                //holder.vABV.setTextColor(getResources().getColor(android.R.color.black));

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final BeerInfo beerInfo = this.getItem(position);
            if (beerInfo != null) {

                //holder.vIcon.setImageDrawable();
                holder.vStyle.setText(beerInfo.getStyle());
                holder.vBrewery.setText(beerInfo.getBrewer());
                holder.vName.setText(beerInfo.getName());
                holder.vContent.setText(beerInfo.getContext());
                holder.vABV.setText(beerInfo.getAbv());

            }
            return convertView;
        }


        class ViewHolder {
            ImageView vIcon;
            TextView vStyle;
            TextView vBrewery;
            TextView vName;
            TextView vContent;
            TextView vABV;
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    //public void onButtonPressed(Uri uri) {
       //
       // if (mListener != null) {
       //   mListener.onFragmentInteraction(uri);
       // }
    //}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
           // mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

/*
*   *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
*/

}
