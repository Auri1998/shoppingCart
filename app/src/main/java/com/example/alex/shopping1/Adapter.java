package com.example.alex.shopping1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.shopping1.HttpThread.ImageHttpThread;

import java.util.List;

/**
 * Created by 15587 on 2018/12/17.
 */
public class Adapter  extends ArrayAdapter{
    private int resourceID;

    public Adapter(Context context,int resource,List<Product> list){
        super(context,resource,list);
        this.resourceID=resource;
    }
    public View getView(int position,View convertView,ViewGroup parent){
        final Product product = (Product) getItem(position);
        View view;
        P1 p1=new P1();
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
            p1.titleView=(TextView)view.findViewById(R.id.title);
            p1.priceView=(TextView)view.findViewById(R.id.price);
            p1.image=(ImageView)view.findViewById(R.id.proImg);
            view.setTag(p1);
        }else {
            view=convertView;
            p1=(P1)view.getTag();
        }
        p1.titleView.setText(product.getTitle());
        p1.priceView.setText(product.getPrice());
        ImageHttpThread imageHttpThread=new ImageHttpThread(product.getImage());
        imageHttpThread.start();
        //
        try {
            imageHttpThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        p1.image.setImageBitmap(imageHttpThread.getResult());
        p1.addButton=(Button)view.findViewById(R.id.add_shop);
        p1.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db=new DBHelper(getContext(),"num",null,1);
                db.add(db,product);
            }
        });
        return view;
    }
    class P1{
        TextView titleView;
        TextView priceView;
        ImageView image;
        Button addButton;
    }
}
