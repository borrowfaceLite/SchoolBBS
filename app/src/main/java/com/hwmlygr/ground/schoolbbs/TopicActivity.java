package com.hwmlygr.ground.schoolbbs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class TopicActivity extends Activity {

    private TextView tv_content;
    private String topicName;
    private int topicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        //获取上一个activity传来的数据
        Intent intent=getIntent();
        if (intent!=null){
            topicName=intent.getStringExtra("topicName");
            topicId=intent.getIntExtra("topicId",-1);
            Log.d("topicId1", "onCreate: "+topicId);
        }

        tv_content=findViewById(R.id.content);
        StringBuffer string1=new StringBuffer("阿斯蒂芬hiuasjdhfiouashdfopasdhfpoasdhfpoashdfouashgfiuoasgdfiougasd");
        for(int i=0;i<4;++i){
            string1.append(string1);
        }
        tv_content.setText(string1);
    }

    public void comment(View view){
        Intent intent=new Intent(TopicActivity.this,CommentActivity.class);
        intent.putExtra("topicName",topicName);
        intent.putExtra("topicId",topicId);
        startActivity(intent);
    }


    public void back(View view){
        finish();
    }

    /**
     **方便上一个程序员知道启动这个activity需要什么数据。
     * @param context 上下文
     * @param topicName 帖子名字
     * @param topicId 帖子id
     */
    public static void actionStart(Context context, String topicName, int topicId){
        Intent intent=new Intent(context,TopicActivity.class);
        intent.putExtra("topicName",topicName);
        intent.putExtra("topicId",topicId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
