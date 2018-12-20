package com.example.alex.shopping1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.shopping1.HttpThread.ImageHttpThread;

import java.util.List;

/**
 * Created by 15587 on 2018/12/18.
 */
public class cartAdapter extends ArrayAdapter{
    DBHelper db = new DBHelper(getContext(), "num", null, 1);
    private int resourceID;
    private List list;

    public cartAdapter(Context context,int resource,List<Product> list){
        super(context,resource,list);
        this.resourceID=resource;
        this.list=list;
    }
    public View getView(final int position,View convertView,ViewGroup parent) {
        final Product product = (Product) getItem(position);
        View view;
        final P2 p2;
        final Double price=Double.valueOf(product.getPrice());

         if (convertView==null){
             view= LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
             p2=new P2();
             p2.titleView=(TextView)view.findViewById(R.id.title);
             p2.priceView=(TextView)view.findViewById(R.id.price);
             p2.image=(ImageView)view.findViewById(R.id.proImg);
             p2.number=(TextView)view.findViewById(R.id.number);
             p2.add_number=(TextView)view.findViewById(R.id.add_number);
             p2.sub_number=(TextView)view.findViewById(R.id.sub_number);
             p2.b=(RelativeLayout)view.findViewById(R.id.asd);
             view.setTag(p2);
         }else {
             view=convertView;
             p2=(P2)view.getTag();
         }
        p2.titleView.setText(product.getTitle());
        p2.priceView.setText(String.valueOf(product.getNum()*price));
        ImageHttpThread imageHttpThread=new ImageHttpThread(product.getImage());
        imageHttpThread.start();
        try {
            imageHttpThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        p2.image.setImageBitmap(imageHttpThread.getResult());
        p2.number.setText(String.valueOf(product.getNum()));
        p2.add_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.valueOf(p2.number.getText().toString());
                p2.number.setText(String.valueOf(++number));
                db.add(db, product);
                p2.priceView.setText(String.valueOf(number * price));
            }
        });
        p2.sub_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.valueOf(p2.number.getText().toString());
                if (number!=1){
                    p2.number.setText(String.valueOf(--number));
                    db.sub(db, product);
                    p2.priceView.setText(String.valueOf(number* price) );
                }else {
                    Toast.makeText(getContext(),"no",Toast.LENGTH_LONG).show();
                }
            }
        });
        p2.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("delete");
                builder.setMessage("delete? yes or no");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.del(db, product);
                        list.remove(position);
                        notifyDataSetChanged();
                    }
                });
               builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                   }
               });
                builder.show();
            }
        });
        return view;
    }
    class P2{
        TextView titleView;
        TextView priceView;
        ImageView image;
        TextView number;
        TextView add_number;
        TextView sub_number;
        RelativeLayout b;
    }
}
