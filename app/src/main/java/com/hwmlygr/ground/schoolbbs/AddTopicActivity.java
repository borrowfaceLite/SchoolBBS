package com.hwmlygr.ground.schoolbbs;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hwmlygr.ground.schoolbbs.bean.TopicInfo;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTopicActivity extends AppCompatActivity {

    private boolean isEdit=false;
    private EditText topicTitle,topicContent;
    private ImageView add,edit;
    private Spinner spinner;
    private String topicTitleStr,topicContentStr;
    private int topicId;
    private TextView title;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);

        Intent intent=getIntent();
        if (intent.getBooleanExtra("isEdit",false)){
            isEdit=true;
        }
        topicId=intent.getIntExtra("topicId",-1);

        topicContent=findViewById(R.id.topic_content);
        topicTitle=findViewById(R.id.topic_title);
        title=findViewById(R.id.title);
        spinner=findViewById(R.id.category_list);
        add=findViewById(R.id.add);
        edit=findViewById(R.id.edit);
        db=new DBHelper(getApplicationContext());
        db.open();
        if (isEdit){
            title.setText("修改帖子");
            add.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            getData(topicId);
        }
    }

    private void getData(int topicId) {
        if (topicId!=-1){
            Cursor cursor=db.query(DBHelper.TOPIC_INFO,null,
                    DBHelper.TOPIC_ID+"=?",new String[]{String.valueOf(topicId)});
            Log.d("topicCount", "getData: "+cursor.getCount());
            if (cursor.moveToFirst()){
                do{
                    topicContent.setText(cursor.getString(cursor.getColumnIndex(DBHelper.TOPIC_CONTENT)));
                    topicTitle.setText(cursor.getString(cursor.getColumnIndex(DBHelper.TOPIC_NAME)));
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
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

    public void editTopic(View view){
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
        builder.setMessage(isEdit?"确认修改么":"确认发布么");//提示内容
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
                contentValues.put(DBHelper.TOPIC_USER,SPUtils.getString(AddTopicActivity.this,"userName",""));
                if (isEdit){
                    if (db.update(DBHelper.TOPIC_INFO,contentValues,DBHelper.TOPIC_ID+"=?",new String[]{String.valueOf(topicId)})>0){
                        Toast.makeText(AddTopicActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else {
                    if (db.insert(DBHelper.TOPIC_INFO,contentValues)>0){
                        int topicIdNew=0;
                        Toast.makeText(AddTopicActivity.this, "帖子已发布", Toast.LENGTH_SHORT).show();
                        Cursor cursor=db.query(DBHelper.TOPIC_INFO,new String[]{DBHelper.TOPIC_ID},null,null,"TopicId",null,DBHelper.TOPIC_ID+" desc");
                        if (cursor.moveToFirst()){
                            topicIdNew=cursor.getInt(cursor.getColumnIndex(DBHelper.TOPIC_ID));
                        }
                        db.close();
                        TopicInfo topicInfo=new TopicInfo();
                        topicInfo.setTopicCategory(topicCategory);
                        topicInfo.setTopicContent(topicContentStr);
                        topicInfo.setTopicName(topicTitleStr);
                        topicInfo.setTopicId(topicIdNew);
                        topicInfo.setTopicUploadTime(uploadTime);
                        Intent intent=new Intent();
                        intent.putExtra("topicInfo",topicInfo);
                        setResult(RESULT_OK,intent);
                        finish();
                    }else {
                        Toast.makeText(AddTopicActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
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
