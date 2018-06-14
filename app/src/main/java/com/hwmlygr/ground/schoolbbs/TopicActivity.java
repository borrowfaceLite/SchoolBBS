package com.hwmlygr.ground.schoolbbs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class TopicActivity extends Activity {

    private TextView tv_content,topicTitle,tv_comment,topic_Edit;
    private String topicName,topicContent,topicUser;
    private int topicId,commentCount;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        db=new DBHelper(getApplicationContext());
        db.open();

        //获取上一个activity传来的数据
        Intent intent=getIntent();
        if (intent!=null){
            topicName=intent.getStringExtra("topicName");
            Log.d("topicname", "onCreate: "+topicName);
            topicId=intent.getIntExtra("topicId",-1);
            Log.d("topicId1", "onCreate: "+topicId);
        }


        //数据库拿数据
        getData(topicId);

        tv_content=findViewById(R.id.content);
        topicTitle=findViewById(R.id.topic_title);
        tv_comment=findViewById(R.id.comment_count);
        topic_Edit=findViewById(R.id.topic_edit);
        if (topicUser!=null){
            if (topicUser.equals(SPUtils.getString(TopicActivity.this,"userName",""))){
                topic_Edit.setVisibility(View.VISIBLE);
            }
        }

//        StringBuffer string1=new StringBuffer("阿斯蒂芬hiuasjdhfiouashdfopasdhfpoasdhfpoashdfouashgfiuoasgdfiougasd");
//        for(int i=0;i<4;++i){
//            string1.append(string1);
//        }
        tv_content.setText(topicContent);
        topicTitle.setText(topicName);
        tv_comment.setText("共有"+commentCount+"条评论，点击查看或回复");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getData(topicId);
        tv_comment.setText("共有"+commentCount+"条评论，点击查看或回复");
        tv_content.setText(topicContent);
        topicTitle.setText(topicName);
    }

    private void getData(int topicId) {
        Cursor cursor=db.query(DBHelper.TOPIC_INFO,null,
                DBHelper.TOPIC_ID+"=?",new String[]{String.valueOf(topicId)});
        Log.d("topicCount", "getData: "+cursor.getCount());
        if (cursor.moveToFirst()){
            do{
                topicContent=cursor.getString(cursor.getColumnIndex(DBHelper.TOPIC_CONTENT));
                topicUser=cursor.getString(cursor.getColumnIndex(DBHelper.TOPIC_USER));
                Log.d("", "getData: "+topicContent);
            }while (cursor.moveToNext());
        }
        cursor=db.query(DBHelper.COMMENT_INFO,null,"CommentTopicId = ?",new String[]{String.valueOf(topicId)});
        commentCount=cursor.getCount();
        Log.d("topicComment", "getData: "+topicId);
        cursor.close();
    }

    public void comment(View view){
        Intent intent=new Intent(TopicActivity.this,CommentActivity.class);
        intent.putExtra("topicName",topicName);
        intent.putExtra("topicId",topicId);
        startActivity(intent);
    }

    public void edit(View view){
        Intent intent=new Intent(TopicActivity.this,AddTopicActivity.class);
        intent.putExtra("isEdit",true);
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
