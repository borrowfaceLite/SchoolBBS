package com.hwmlygr.ground.schoolbbs;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private String[] mTitle;
    private ArrayList<ListView> mTopicListView;
    private Toolbar tb_home;
    private ViewPager vp_home;
    private TabPageIndicator tpi_indicator;
    private DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initData();
        initView();
    }

    private void initData() {
        mDbHelper = new DBHelper(getApplicationContext());
        mTitle = new String[]{"学习","吐槽","美食","游戏"};
        mTopicListView = new ArrayList<>(4);
        for (int i = 0;i < 4; i++){
            ListView listView = new ListView(getApplicationContext());
            listView.setAdapter(new TopicListAdapter(mTitle[i]));
            mTopicListView.add(listView);
        }
    }

    private void initView() {
        tb_home = findViewById(R.id.tb_home);
        vp_home = findViewById(R.id.vp_home);
        tpi_indicator = findViewById(R.id.tpi_indicator);

        vp_home.setAdapter(new IndicatorAdapter());
        tpi_indicator.setViewPager(vp_home);
    }
    private class TopicListAdapter extends BaseAdapter{
        String topic;
        public TopicListAdapter(String topic) {
            this.topic = topic;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return topic;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(getApplicationContext());
            textView.setText(topic);
            textView.setTextColor(Color.BLACK);
            return textView;
        }
    }
    private class IndicatorAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mTopicListView.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public ListView instantiateItem(ViewGroup container, int position) {
            ListView listview = mTopicListView.get(position);
            container.addView(listview);
            return listview;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }
    }
}
