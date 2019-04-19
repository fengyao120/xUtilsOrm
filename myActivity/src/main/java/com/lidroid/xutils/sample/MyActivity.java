package com.lidroid.xutils.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.lidroid.xutils.sample.fragment.DbFragment;
import com.lidroid.xutils.sample.fragment.HttpFragment;
import com.lidroid.xutils.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyActivity extends FragmentActivity {

    @BindView(R.id.tabhost) FragmentTabHost mTabHost;

    private Class fragmentArray[] = {
            HttpFragment.class,
            DbFragment.class};
    private int iconArray[] = {
            R.drawable.icon_http,
            R.drawable.icon_database};
    private String titleArray[] = {
            "Http",
            "db"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);

        LogUtils.customTagPrefix = "xUtilsSample"; // 方便调试时过滤 adb logcat 输出
        LogUtils.allowI = false; //关闭 LogUtils.i(...) 的 adb log 输出

        setupTabView();
    }

    private void setupTabView() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null);

        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(titleArray[i]).setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_item);
        }

    }

    private View getTabItemView(int index) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.tab_bottom_nav, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
        imageView.setImageResource(iconArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.tv_icon);
        textView.setText(titleArray[index]);

        return view;
    }
}
