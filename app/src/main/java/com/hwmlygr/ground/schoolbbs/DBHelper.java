package com.hwmlygr.ground.schoolbbs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yt on 2018/6/11.
 */

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase mHelper;

    public static final String USER_ID = "UserId";
    public static final String USER_NAME = "UserName";
    public static final String PASSWORD = "password";
    public static final String USER_INFO = "UserInfo";
    private final String CREATE_USER_INFO = "create table "+ USER_INFO +"(" +
            USER_ID+" integer primary key autoincrement," +
            USER_NAME+" text," +
            PASSWORD+" text)";
    public DBHelper(Context context) {
        super(context, "Database", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        建表语句
        sqLiteDatabase.execSQL(CREATE_USER_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public long insert(String table,ContentValues values){
        return mHelper.insert(table,null,values);
    }
    public long delete(String table,String whereCalues,String[] whereArgs){
        return mHelper.delete(table,whereCalues,whereArgs);
    }
    public long update(String table,ContentValues values,String whereCalues,String[] whereArgs){
        return mHelper.update(table,values,whereCalues,whereArgs);
    }
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        return mHelper.query(table,columns,selection,selectionArgs,groupBy,having,orderBy);
    }
    public Cursor query(String table,String[] columns,String selection,String[] selectionArgs){
        return mHelper.query(table,columns,selection,selectionArgs,null,null,null);
    }
    public void open(){
        if (mHelper == null) {
            mHelper = this.getWritableDatabase();
        }else if(mHelper != null && !mHelper.isOpen()){
            mHelper = this.getWritableDatabase();
        }
    }
    public void close(){
        if(mHelper != null && mHelper.isOpen()){
            mHelper.close();
        }
    }

}
