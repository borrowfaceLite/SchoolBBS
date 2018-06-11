package com.hwmlygr.ground.schoolbbs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

public class AddCommentActivity extends AppCompatActivity {

    EditText commentText;
    StringBuilder reply=new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        Intent intent=getIntent();
        if(intent!=null){
            reply.append("回复@"+intent.getStringExtra("username")+ ":\n\n");
        }
        commentText=findViewById(R.id.comment_text);
    }

    public void back(View view){
        finish();
    }

    public void add(View view){

    }
}
