package com.hwmlygr.ground.schoolbbs;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yt on 2018/6/11.
 */

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase mHelper;

    public DBHelper(Context context) {
        super(context, "Database", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        建表语句

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insert(String table,ContentValues values){
        mHelper.insert(table,null,values);
    }
    public void delete(String table,String whereCalues,String[] whereArgs){
        mHelper.delete(table,whereCalues,whereArgs);
    }
    public void update(String table,ContentValues values,String whereCalues,String[] whereArgs){
        mHelper.update(table,values,whereCalues,whereArgs);
    }
    public void query(String table,String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy){
        mHelper.query(table,columns,selection,selectionArgs,groupBy,having,orderBy);
    }
    public void query(String table,String[] columns,String selection,String[] selectionArgs){
        mHelper.query(table,columns,selection,selectionArgs,null,null,null);
    }
    public void open(){
        if (mHelper != null && !mHelper.isOpen()) {
            mHelper = this.getWritableDatabase();
        }
    }
    public void close(){
        if(mHelper != null && mHelper.isOpen()){
            mHelper.close();
        }
    }

}
