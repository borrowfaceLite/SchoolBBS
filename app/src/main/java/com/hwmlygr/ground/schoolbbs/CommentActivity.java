package com.hwmlygr.ground.schoolbbs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends Activity {

    private String topicName;
    private int topicId;
    private List<Comment> commnetList;
    private ListView commentListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        //获取帖子名帖子id；
        Intent intent=getIntent();
        if (intent!=null){
            topicId=intent.getIntExtra("topicId",-1);
            topicName=intent.getStringExtra("topicName");
        }

        commentListView=findViewById(R.id.comment_list);
        commnetList=new ArrayList<>();
        Comment comment1=new Comment("borrowface",1,"水贴怪","2018/4/12/11:00");
        commnetList.add(comment1);
        Comment comment2=new Comment("borrowface",1,"垃圾","2018/4/12/12:00");
        commnetList.add(comment2);
        CommentAdapter adapter=new CommentAdapter();
        commentListView.setAdapter(adapter);
    }

    public void addComment(View view){
        Intent intent=new Intent(CommentActivity.this,AddCommentActivity.class);
        intent.putExtra("topicId",topicId);
        startActivity(intent);
    }

    public void back(View view){
        finish();
    }

    private class CommentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return commnetList.size();
        }

        @Override
        public Object getItem(int position) {
            return commnetList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            View view1= LayoutInflater.from(getApplicationContext()).inflate(R.layout.commentitem,null);
            TextView username=view1.findViewById(R.id.username);
            TextView content=view1.findViewById(R.id.content);
            TextView time=view1.findViewById(R.id.time);
            username.setText(commnetList.get(position).getUserName());
            content.setText(commnetList.get(position).getContent());
            time.setText(commnetList.get(position).getTime());
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(CommentActivity.this,AddCommentActivity.class);
                    intent.putExtra("username","hello");
                    startActivity(intent);
                }
            });
            return view1;
        }
    }
}
