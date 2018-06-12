package com.hwmlygr.ground.schoolbbs;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private EditText edt_user_name;
    private EditText edt_password;
    private EditText edt_repassword;
    private Button btn_register;
    private DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initData();
        initView();

    }

    private void initData() {
        mDbHelper = new DBHelper(getApplicationContext());
        mDbHelper.open();
    }

    private void initView() {
        edt_user_name = findViewById(R.id.edt_user_name);
        edt_password = findViewById(R.id.edt_password);
        edt_repassword = findViewById(R.id.edt_repassword);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                register();
                break;
        }
    }

    private void register() {
        String userName = edt_user_name.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        String repassword = edt_repassword.getText().toString().trim();
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Cursor cursor = mDbHelper.query(DBHelper.USER_INFO, null, DBHelper.USER_NAME + "=?", new String[]{userName});
//        判断存在
        if(cursor != null && cursor.getCount() != 0 && cursor.moveToNext()){
            String dbUserName = cursor.getString(cursor.getColumnIndex(DBHelper.USER_NAME));
            if (dbUserName.equals(userName)) {
                Toast.makeText(getApplicationContext(),"用户已存在",Toast.LENGTH_SHORT).show();
                return;
            }
        }
//        注册用户
        if(!password.equals(repassword)){
            Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
        }
        ContentValues values = new ContentValues();
        values.put(DBHelper.USER_NAME,userName);
        values.put(DBHelper.PASSWORD,password);
        if (mDbHelper.insert(DBHelper.USER_INFO, values)>0) {
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
