package com.example.appfood.DataLocal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appfood.Model.User;


public class Dao_User {
    Dbhelper dbhelper;

    public Dao_User(Context context) {
      dbhelper=new Dbhelper(context);
    }

    public boolean inset(User user)
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("id",user.getId());
        contentValues.put("full_name",user.getFull_name());
        contentValues.put("email",user.getEmail());
        contentValues.put("password",user.getPassword());
        contentValues.put("avatar",user.getAvatar());
        long index=db.insert("user",null,contentValues);
        return index>0;
    }
    public boolean inset(String full_name,String email,String password)
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("full_name",full_name);
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("avatar","default");
        long index=db.insert("user",null,contentValues);
        return index>0;
    }
    public  User readData()
    {
        User user=null;
        SQLiteDatabase db=dbhelper.getReadableDatabase();
            String sql="select *from user";
         Cursor cursor= db.rawQuery(sql,null);
         cursor.moveToFirst();
         while (!cursor.isAfterLast())
         {
             int id=cursor.getInt(0);
              String fullname=cursor.getString(1);
             String email=cursor.getString(2);
             String password=cursor.getString(3);
             String avatar=cursor.getString(4);
          user =new User(id,fullname,email,password,avatar);
             cursor.moveToNext();
         }
        return user;
    }
    public void delete()
    {
        String sql="delete from user";
        SQLiteDatabase sqLiteDatabase=dbhelper.getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
    }
    public void update(int id,String avatar,String fullname)
    {
        SQLiteDatabase sqLiteDatabase=dbhelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("full_name",fullname);
        contentValues.put("avatar",avatar);
        sqLiteDatabase.update("user",contentValues,"id=?",new String[]{id+""});
    }
}
