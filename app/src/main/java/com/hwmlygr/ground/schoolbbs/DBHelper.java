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

    //userinfo
    public static final String USER_ID = "UserId";
    public static final String USER_NAME = "UserName";
    public static final String PASSWORD = "password";
    public static final String USER_INFO = "UserInfo";

    //topicinfo
    public static final String TOPIC_NAME="TopicName";
    public static final String TOPIC_CATEGORY="TopicCategory";
    public static final String TOPIC_CONTENT="TopicContent";
    public static final String TOPIC_ID="TopicId";
    public static final String TOPIC_INFO="TopicInfo";
    public static final String TOPIC_UPLOADTIME="TopicUploadTime";

    //commentinfo
    public static final String COMMENT_CONNTENT="CommentContent";
    public static final String COMMENT_USER="CommentUser";
    public static final String COMMENT_ID="CommentID";
    public static final String COMMENT_TOPICID="CommentTopicId";
    public static final String COMMENT_UPLOADTIME="CommentUploadTime";
    public static final String COMMENT_INFO="CommentInfo";

    private final String CREATE_USER_INFO = "create table "+ USER_INFO +"(" +
            USER_ID+" integer primary key autoincrement," +
            USER_NAME+" text," +
            PASSWORD+" text)";

    private final String CREATE_TOPIC_INFO = "create table "+TOPIC_INFO+"("+
            TOPIC_ID+" integer primary key autoincrement, "+
            TOPIC_NAME+" text, "+
            TOPIC_CATEGORY+" text, "+
            TOPIC_UPLOADTIME+" text, "+
            TOPIC_CONTENT+" text)";

    private final String CREATE_COMMENT_INFO="create table "+COMMENT_INFO+"("+
            COMMENT_ID+" integer primary key autoincrement, "+
            COMMENT_USER+" text, "+
            COMMENT_CONNTENT+" text, "+
            COMMENT_TOPICID+" integer, "+
            COMMENT_UPLOADTIME+" text)";

    public DBHelper(Context context) {
        super(context, "Database", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        建表语句
        sqLiteDatabase.execSQL(CREATE_USER_INFO);
        sqLiteDatabase.execSQL(CREATE_TOPIC_INFO);
        sqLiteDatabase.execSQL(CREATE_COMMENT_INFO);
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
