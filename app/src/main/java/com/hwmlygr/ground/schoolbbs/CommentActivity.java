package com.hwmlygr.ground.schoolbbs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends Activity {

    private List<Comment> commnetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        commnetList=new ArrayList<>();
        Comment comment1=new Comment("nan",1,"khasopdfhopasdhfpoas","2323");
        commnetList.add(comment1);
        CommentAdapter adapter=new
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
            return view;
        }
    }
}
