package com.example.alex.shopping1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15587 on 2018/12/18.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper( Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
     db.execSQL("create table num(product integer,number integer,title varchar(50),img varchar(20),price double)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void add(DBHelper db,Product product){
        Cursor cursor=db.getReadableDatabase().query("num",null,"product=?",new String[]{String.valueOf(product.getId())},null,null,null);
        if(cursor.moveToFirst()){
            String sql="update num set number = number+1 where product=?";
            db.getWritableDatabase().execSQL(sql,new Object[]{product.getId()});
        }else{
            String sql="insert into num values(?,?,?,?,?)";
            db.getWritableDatabase().execSQL(sql,new Object[]{product.getId(),1,product.getTitle(),product.getImage(),Double.valueOf(product.getPrice().substring(product.getPrice().indexOf("￥")+1))});
        }
        cursor.close();
    }
    public List<Product> output(DBHelper db){
        final List<Product> list=new ArrayList<>();
        Cursor cursor=db.getReadableDatabase().rawQuery("select * from num",null);
        while (cursor.moveToNext()){
            Product product=new Product();
            product.setId(cursor.getInt(0));
            product.setNum(cursor.getInt(1));
            product.setTitle(cursor.getString(2));
            product.setImage(cursor.getString(3));
            product.setPrice(String.valueOf(cursor.getDouble(4)));
            list.add(product);
        }
        return list;
    }
    public void sub(DBHelper db,Product product){
        String sql="update num set number = number-1 where product=?";
        db.getWritableDatabase().execSQL(sql,new Object[]{product.getId()});
    }

    public void del(DBHelper db,Product product){
        db.getWritableDatabase().execSQL("delete from num where product=?",new String[]{String.valueOf(product.getId())});
    }
}
