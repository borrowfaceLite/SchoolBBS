package com.hwmlygr.ground.schoolbbs;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTopicActivity extends AppCompatActivity {

    private EditText topicTitle,topicContent;
    private Spinner spinner;
    private String topicTitleStr,topicContentStr;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);
        topicContent=findViewById(R.id.topic_content);
        topicTitle=findViewById(R.id.topic_title);
        spinner=findViewById(R.id.category_list);
        db=new DBHelper(getApplicationContext());
        db.open();
    }

    public void  back(View view){
        finish();
    }

    public void add(View view){
        topicTitleStr=topicTitle.getText().toString().trim();
        topicContentStr=topicContent.getText().toString().trim();
        if (topicContentStr.equals("")||topicTitleStr.equals("")){
            Toast.makeText(AddTopicActivity.this,"请输入完整内容",Toast.LENGTH_SHORT).show();
            return;
        }
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
                String topicCategory=spinner.getSelectedItem().toString();
                String uploadTime= getTime();
                Log.d("addTopic", "onClick: "+uploadTime+topicCategory);
                ContentValues contentValues=new ContentValues();
                contentValues.put(DBHelper.TOPIC_CONTENT,topicContentStr);
                contentValues.put(DBHelper.TOPIC_CATEGORY,topicCategory);
                contentValues.put(DBHelper.TOPIC_UPLOADTIME,uploadTime);
                contentValues.put(DBHelper.TOPIC_NAME,topicTitleStr);
                if (db.insert(DBHelper.TOPIC_INFO,contentValues)>0){
                    Toast.makeText(AddTopicActivity.this, "帖子已发布", Toast.LENGTH_SHORT).show();
                    db.close();
                    finish();
                }else {
                    Toast.makeText(AddTopicActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
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
