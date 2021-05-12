package com.dialer1998.vps.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Profiledata extends SQLiteOpenHelper
{  public static final  String Table_name="profile" ;
    public static final  String  Database_name= "profiledata1";
    public static final  String col_1="id" ;
    public static final  String col_2="Name" ;
    public static final  String col_3="email";




    public Profiledata(@Nullable Context context) {
        super ( context, Database_name, null, 1 );
        SQLiteDatabase db1=this.getWritableDatabase ();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + Table_name +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT ,Department TEXT, contact TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL ( "DROP TABLE IF EXISTS " +Table_name);
        onCreate ( db );
    }
    public boolean insertData(String Name,String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2,Name);
        contentValues.put(col_3,email);


        long result = db.insert(Table_name,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+Table_name,null);
        return res;
    }
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Table_name, "ID = ?",new String[] {id});
    }
}
