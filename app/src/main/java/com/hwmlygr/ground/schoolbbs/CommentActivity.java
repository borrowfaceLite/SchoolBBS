package com.hwmlygr.ground.schoolbbs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hwmlygr.ground.schoolbbs.bean.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends Activity {

    private String topicName;
    private int topicId;
    private List<Comment> commnetList;
    private ListView commentListView;
    private TextView title;
    private DBHelper db;
    private CommentAdapter adapter=new CommentAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        db=new DBHelper(getApplicationContext());
        db.open();


        //获取帖子名帖子id；
        Intent intent=getIntent();
        if (intent!=null){
            topicId=intent.getIntExtra("topicId",-1);
            topicName=intent.getStringExtra("topicName");
            Log.d("topicId2", "onCreate: "+topicId);
            Log.d("topicName", "onCreate: "+topicName);
        }

        title=findViewById(R.id.comment_title);
        commentListView=findViewById(R.id.comment_list);
        title.setText(topicName.toString());
        commnetList=new ArrayList<>();
        getData(topicId);
//        Comment comment1=new Comment("borrowface",1,"水贴怪","2018/4/12/11:00");
//        commnetList.add(comment1);
//        Comment comment2=new Comment("borrowface",1,"垃圾","2018/4/12/12:00");
//        commnetList.add(comment2);
        commentListView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        commnetList.clear();
        getData(topicId);
        adapter.notifyDataSetChanged();
    }

    private void getData(int topicId) {
      Cursor cursor=db.query(DBHelper.COMMENT_INFO,null,"CommentTopicId = ?",new String[]{String.valueOf(topicId)});
      if (cursor.moveToFirst()){
          do {
              String username=cursor.getString(cursor.getColumnIndex(DBHelper.COMMENT_USER));
              String uploadTime=cursor.getString(cursor.getColumnIndex(DBHelper.COMMENT_UPLOADTIME));
              String content=cursor.getString(cursor.getColumnIndex(DBHelper.COMMENT_CONNTENT));
              Comment comment=new Comment(username,0,content,uploadTime);
              commnetList.add(comment);
          }while (cursor.moveToNext());
      }
      cursor.close();
    }

    public void addComment(View view){
        Intent intent=new Intent(CommentActivity.this,AddCommentActivity.class);
        intent.putExtra("topicId",topicId);
        intent.putExtra("topicName",topicName);
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
        public View getView(final int position, View view, ViewGroup viewGroup) {
            View view1= LayoutInflater.from(getApplicationContext()).inflate(R.layout.commentitem,null);
            TextView username=view1.findViewById(R.id.username);
            TextView content=view1.findViewById(R.id.content);
            TextView time=view1.findViewById(R.id.time);
            TextView delete=view1.findViewById(R.id.delete);
            LinearLayout touch=view1.findViewById(R.id.touch_layout);
            username.setText(commnetList.get(position).getUserName());
            content.setText(commnetList.get(position).getContent());
            time.setText(commnetList.get(position).getTime());
            touch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(CommentActivity.this,AddCommentActivity.class);
                    intent.putExtra("username",commnetList.get(position).getUserName());
                    intent.putExtra("topicName",topicName);
                    intent.putExtra("topicId",topicId);
                    startActivity(intent);
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(CommentActivity.this);
                    builder.setIcon(R.drawable.ic_alert1_name);
                    builder.setTitle("警告");
                    builder.setMessage("确认删除么");//提示内容
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String username=commnetList.get(position).getUserName();
                            String uploadTime=commnetList.get(position).getTime();
                            long count=db.delete(DBHelper.COMMENT_INFO,DBHelper.COMMENT_USER+"=? and "+DBHelper.COMMENT_TOPICID+"=? and "+DBHelper.COMMENT_UPLOADTIME+"=?"
                            ,new String[]{username,String.valueOf(topicId),uploadTime});
                            if (count>0) {
                                commnetList.remove(position);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(CommentActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(CommentActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    if (commnetList.get(position).getUserName().equals(SPUtils.getString(CommentActivity.this,"userName",null))){
                        AlertDialog dialog=builder.create();
                        dialog.show();
                    }else{
                        Toast.makeText(CommentActivity.this,"你不是该条评论用户，不能删除",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });
            return view1;
        }
    }
}
