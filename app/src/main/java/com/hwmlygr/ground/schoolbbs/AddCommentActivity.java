package com.hwmlygr.ground.schoolbbs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCommentActivity extends AppCompatActivity {

    private DBHelper db;
    private EditText commentText;
    private StringBuilder reply=new StringBuilder("");
    private String replyHead,commmentUser;
    private int topicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        Intent intent=getIntent();
        if(intent.getStringExtra("username")!=null){
            replyHead="回复@"+intent.getStringExtra("username")+ ":\n\n";
            reply.append(replyHead);
        }
        topicId=intent.getIntExtra("topicId",-1);
        Log.d("topicId3", "onCreate: "+topicId);
        commmentUser=SPUtils.getString(this,"userName",null);

        db=new DBHelper(getApplicationContext());
        db.open();
        commentText=findViewById(R.id.comment_text);
        commentText.setText(reply);
        commentText.setSelection(reply.length());
    }

    public void back(View view){
        finish();
    }

    public void add(View view){

        if (commentText.getText().toString().equals("")||commentText.getText().toString().equals(replyHead)){
            Toast.makeText(AddCommentActivity.this,"请输入内容",Toast.LENGTH_SHORT).show();
            return;
        }
        reply.append(commentText.getText().toString().replace(replyHead.toString(),"").trim());
        Log.d("addcomment", reply.toString());
        showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_alert1_name);
        builder.setTitle("警告");
        builder.setMessage("确认发布么");//提示内容
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (commmentUser!=null&&topicId!=-1){
                    String uploadTime= getTime();
                    ContentValues contentValues=new ContentValues();
                    contentValues.put(DBHelper.COMMENT_TOPICID,topicId);
                    contentValues.put(DBHelper.COMMENT_CONNTENT,reply.toString());
                    contentValues.put(DBHelper.COMMENT_USER,commmentUser);
                    contentValues.put(DBHelper.COMMENT_UPLOADTIME,uploadTime);
                    if (db.insert(DBHelper.COMMENT_INFO,contentValues)>0){
                        Toast.makeText(AddCommentActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                        db.close();
                        finish();
                    }else {
                        Toast.makeText(AddCommentActivity.this, "回复失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else{
                    Toast.makeText(AddCommentActivity.this, "回复失败", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String getTime() {
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=sdf.format(date);
        return time;
    }

}
