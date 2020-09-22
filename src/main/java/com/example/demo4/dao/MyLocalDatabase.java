package com.example.demo4.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.demo4.model.User;

//we need to extend the class to SQLiteOpenHelper
//SQLiteOpenHelper is responsible to perform all the backend schema (table creation/deletion, database creation etc.)
public class MyLocalDatabase extends SQLiteOpenHelper {
    //making an object of child class (MyLocalDatabase) helps us to access data of parent class (SQLiteOpenHelper)
    private static final String DATABASE_NAME = "demoDatabase";
    private static final String COL_NAME = "name";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASS = "password";
    private static final String COL_MOBILE = "mobile";
    private static final String COL_STATE = "state";

    private static final String TABLE_NAME = "user";
    private static final int DATABASE_VERSION = 1;
    //constructor
    //Context used to achieve "loose coupling" so that all components (Activity/BroadcastReceiver/ContentProvider/Services) can use it

    public MyLocalDatabase(Context context){
        //super constructor takes a data to the parent class
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }
    //SQLiteOpenHelper provides two methods (1)onCreate--(table creation) (2)onUpdate--(table update)
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    //table creation code here
        sqLiteDatabase.execSQL(" create table " + TABLE_NAME + " (" + COL_NAME + " varchar(30), " + COL_EMAIL + " varchar(35) primary key, " + COL_PASS + " varchar(15), " + COL_MOBILE + " long(10), " + COL_STATE + " varchar(20));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    //table upgrade code here
    //dropping the older table and creating the new table
    sqLiteDatabase.execSQL(" drop table if exists " + TABLE_NAME + "");
    onCreate(sqLiteDatabase);
    }

    //insert method
    //instead of passing each parameter indiv. we passed the class containing them (User model class)
    public long insert(User user){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();      //to get the object of SQLiteDatabase
        //ContentValues help us to map the correct storage location for each user given value
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, user.getName());
        cv.put(COL_EMAIL, user.getEmail());
        cv.put(COL_PASS, user.getPass());
        cv.put(COL_MOBILE,user.getMobile());
        cv.put(COL_STATE, user.getState());
        //if value successfully stored, it will return a long i.e, 1 (defined for insert method in SQLiteDatabase.class)
        return sqLiteDatabase.insert(TABLE_NAME,null, cv);
    }

    public boolean login(String email, String pass){
        Cursor cursor = getReadableDatabase().query(TABLE_NAME,null,"" + COL_EMAIL + "=? and " + COL_PASS + "=?",new String[]{email,pass},null, null,null);     //this is the same as creating an obj of SQLiteDatabase as on line 48
        // .moveTONext method returns true if the query is matched to the existing data else returns a false
        return cursor.moveToNext();
    }

    //method to fetch dat to Profile fragment
    public User getUserByEmail(String email){
        User user = null;
        Cursor cursor = getReadableDatabase().query(TABLE_NAME,null,"" + COL_EMAIL + "=?",new String[]{email},null,null,null,null);

        if(cursor.moveToNext()){
            user = new User();
            user.setName(cursor.getString(0));
            user.setEmail(cursor.getString(1));
            user.setPass(cursor.getString(2));
            user.setMobile(cursor.getLong(3));
            user.setState(cursor.getString(4));
        }

        return user;
    }

    public int updateUser(User user){
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, user.getName());
        cv.put(COL_EMAIL, user.getEmail());
        cv.put(COL_PASS, user.getPass());
        cv.put(COL_MOBILE,user.getMobile());
        cv.put(COL_STATE, user.getState());

        return getWritableDatabase().update(TABLE_NAME,cv,""+COL_EMAIL+"=?",new String[]{user.getEmail()});

    }

    public long deleteAccount(String email){
        return getWritableDatabase().delete(TABLE_NAME,"" +COL_EMAIL+ "=?", new String[]{email});
    }

}
