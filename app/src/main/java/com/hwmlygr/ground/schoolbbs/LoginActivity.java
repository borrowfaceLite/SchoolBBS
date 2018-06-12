package com.hwmlygr.ground.schoolbbs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText edt_account;
    private EditText edt_password;
    private Button btn_login;
    private Button btn_registe;
    private DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
        initView();
    }

    private void initData() {
        mDbHelper = new DBHelper(getApplicationContext());
        mDbHelper.open();
    }

    private void initView() {
        edt_account = findViewById(R.id.edt_account);
        edt_password = findViewById(R.id.edt_password);
        btn_login = findViewById(R.id.btn_login);
        btn_registe = findViewById(R.id.btn_registe);
        btn_login.setOnClickListener(this);
        btn_registe.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_registe:
                register();
                break;
        }
    }

    private void register() {
//        注册按钮
        startActivity(new Intent(this,RegisterActivity.class));
    }

    private void login() {
//        登录按钮
        String userName = edt_account.getText().toString().trim();
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
//        核对密码
        Cursor cursor = mDbHelper.query(DBHelper.USER_INFO, null, DBHelper.USER_NAME + "=?", new String[]{userName}, null, null, null);
        if(cursor != null && cursor.getCount() != 0 && cursor.moveToNext()){
            String password = cursor.getString(cursor.getColumnIndex(DBHelper.PASSWORD));
            String userId = cursor.getString(cursor.getColumnIndex(DBHelper.USER_ID));
            cursor.close();
            String edtPassword = edt_password.getText().toString().trim();
            if(password.equals(edtPassword)){
//                密码正确切换主界面
                SPUtils.put(getApplicationContext(), SPUtils.USER_NAME,userName);
                SPUtils.put(getApplicationContext(), SPUtils.USER_ID,userId);
                startActivity(new Intent(this,HomeActivity.class));
                finish();
            }else {
                Toast.makeText(this, "账户名或密码不正确", Toast.LENGTH_SHORT).show();
            }
        }else{
//            用户不存在
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("用户不存在，是否需要注册");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.create().show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }
}
