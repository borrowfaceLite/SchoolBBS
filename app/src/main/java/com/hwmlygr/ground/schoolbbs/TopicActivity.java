package com.hwmlygr.ground.schoolbbs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TopicActivity extends Activity {

    TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        tv_content=findViewById(R.id.content);
        StringBuffer string1=new StringBuffer("阿斯蒂芬hiuasjdhfiouashdfopasdhfpoasdhfpoashdfouashgfiuoasgdfiougasd");
        for(int i=0;i<4;++i){
            string1.append(string1);
        }
        tv_content.setText(string1);
    }

    public void comment(View view){
        Intent intent=new Intent(TopicActivity.this,CommentActivity.class);
        startActivity(intent);
    }
}
