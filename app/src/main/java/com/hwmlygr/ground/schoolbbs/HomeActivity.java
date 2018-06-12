package com.hwmlygr.ground.schoolbbs;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hwmlygr.ground.schoolbbs.bean.TopicInfo;
import com.viewpagerindicator.TabPageIndicator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        mDbHelper.open();
        mTitle = new String[]{"学习","吐槽","美食","游戏"};
        mTopicListView = new ArrayList<>(4);
        for (int i = 0;i < 4; i++){
            String topic = mTitle[i];
            Cursor cursor = queryData(topic);
            if(cursor != null && cursor.getCount() == 0){
//                主题没数据，先插入假数据
                insertOriginalData(topic);
                cursor.close();
                cursor = queryData(topic);
            }
            ArrayList<TopicInfo> topicList = processCursor(cursor);

            ListView listView = new ListView(getApplicationContext());
            final TopicListAdapter adapter = new TopicListAdapter(topicList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(adapter.getItemViewType(position)!=1){
//                        判断是否是最后一个条目
                        TopicInfo item = adapter.getItem(position);
                        TopicActivity.actionStart(getApplicationContext(),item.getTopicName(),item.getTopicId());
                    }
                }
            });
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

    private ArrayList<TopicInfo> processCursor(Cursor cursor) {
        ArrayList<TopicInfo> list = new ArrayList<>();
        if(cursor == null && cursor.getCount() == 0) return list;
        while (cursor.moveToNext()) {
            TopicInfo info = new TopicInfo();
            info.setTopicName(cursor.getString(cursor.getColumnIndex(DBHelper.TOPIC_NAME)));
            info.setTopicContent(cursor.getString(cursor.getColumnIndex(DBHelper.TOPIC_CONTENT)));
            info.setTopicCategory(cursor.getString(cursor.getColumnIndex(DBHelper.TOPIC_CATEGORY)));
            info.setTopicId(cursor.getInt(cursor.getColumnIndex(DBHelper.TOPIC_ID)));
            info.setTopicUploadTime(cursor.getLong(cursor.getColumnIndex(DBHelper.TOPIC_UPLOADTIME)));
            list.add(info);
        }
        return list;
    }

    private void insertOriginalData(String topic) {
        for (int i = 0;i < 8;i++){
            ContentValues values = new ContentValues();
            String title = "";
            String content = "";
            for (int j = 0; j < 20;j++){
                title += topic+i;
                content += topic+"内容"+i;
            }
            values.put(DBHelper.TOPIC_NAME,title);
            values.put(DBHelper.TOPIC_CATEGORY,topic);
            values.put(DBHelper.TOPIC_UPLOADTIME,System.currentTimeMillis()+i*1000);
            values.put(DBHelper.TOPIC_CONTENT,content);
            mDbHelper.insert(DBHelper.TOPIC_INFO,values);
        }
    }

    private Cursor queryData(String topic) {
        return mDbHelper.query(DBHelper.TOPIC_INFO,null, DBHelper.TOPIC_CATEGORY+"=?",new String[]{topic});
    }
    private class TopicListAdapter extends BaseAdapter{
        private static final int LAST = 1;
        private static final int NORMAL = 0;
        private ArrayList<TopicInfo> topicList;

        public TopicListAdapter(ArrayList<TopicInfo> topicList) {
            this.topicList = topicList;
        }

        @Override
        public int getCount() {
            return topicList.size()+1;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            if(position < getCount()-1) return NORMAL;
            else return LAST;
        }

        @Override
        public TopicInfo getItem(int position) {
            if (getItemViewType(position) == NORMAL) {
                return topicList.get(position);
            }else{
                return null;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (getItemViewType(position) == NORMAL){
                ViewHolder viewHolder = null;
                if(convertView == null){
                    convertView = View.inflate(getApplicationContext(), R.layout.item_topic, null);
                    viewHolder = new ViewHolder();
                    viewHolder.tv_title = convertView.findViewById(R.id.tv_title);
                    viewHolder.tv_content = convertView.findViewById(R.id.tv_content);
                    viewHolder.tv_time = convertView.findViewById(R.id.tv_time);
                    convertView.setTag(viewHolder);
                }else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                TopicInfo item = getItem(position);
                viewHolder.tv_title.setText(item.getTopicName());
                viewHolder.tv_content.setText(item.getTopicContent());
                long time = item.getTopicUploadTime();

                DateFormat simpleFormat = SimpleDateFormat.getDateTimeInstance();
                viewHolder.tv_time.setText(simpleFormat.format(new Date(time)));
            }else{
                convertView = View.inflate(getApplicationContext(),R.layout.item_topic_last,null);
            }
            return convertView;
        }
        private class ViewHolder{
            public TextView tv_title;
            public TextView tv_content;
            public TextView tv_time;
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
