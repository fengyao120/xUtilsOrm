package com.lidroid.xutils.sample.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.sample.R;

import butterknife.ButterKnife;

/**
 * Author: wyouflf
 * Date: 13-9-14
 * Time: 下午3:35
 */
public class HttpFragment extends Fragment {

    //private HttpHandler handler;

    private Context mAppContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.http_fragment, container, false);
        ButterKnife.bind(this, view);

        mAppContext = inflater.getContext().getApplicationContext();
        return view;
    }

}
