package com.example.appfood.Activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appfood.Model.Cart;
import com.example.appfood.Model.Product;
import com.example.appfood.R;

import com.example.appfood.Utils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Product_Detail_Activity extends AppCompatActivity {
    TextView txt_name_detail;
    ImageView img_detail;
    TextView txt_price_detail,txt_price_sale_detail,txt_des_detail,txt_save,txt_add_to_cart;
    Toolbar toolbar_detail;
    Bundle bundle;
    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        iniit();
        bundle=getIntent().getExtras();
       product= (Product) bundle.get("product");
        String avatar= Utils.url+"image/"+product.getAvatar();
        Picasso.get().load(avatar).into(img_detail);
        txt_name_detail.setText(product.getName());
        txt_des_detail.setText(product.getDescription());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txt_price_detail.setText(decimalFormat.format(Double.parseDouble(product.getPrice()))+" VND");
        txt_price_detail.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        double khuyenmai=(Integer.parseInt(product.getPrice())*(product.getSale())/100);
        double price_sale=Integer.parseInt(product.getPrice())-khuyenmai;
        txt_price_sale_detail.setText(decimalFormat.format(price_sale)+" VND");
        txt_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Product_Detail_Activity.this,CartActivity.class);
                startActivity(intent);
                long price= Long.parseLong(product.getPrice());
                if(MainActivity.cartList.size()>0)
                {
                    boolean exists=false;
                    for(int i=0;i<MainActivity.cartList.size();i++)
                    {
                        if (MainActivity.cartList.get(i).getId()==product.getId())
                        {

                            MainActivity.cartList.get(i).setCount(1+MainActivity.cartList.get(i).getCount());
                            long price2=price*MainActivity.cartList.get(i).getCount();
                            MainActivity.cartList.get(i).setPrice(price2);
                            if (MainActivity.cartList.get(i).getCount()>=10)
                            {
                                MainActivity.cartList.get(i).setCount(10);
                            }
                            exists=true;
                        }
                    }
                    if (exists==false)
                    {
                        MainActivity.cartList.add(new Cart(product.getId(),product.getName(),price,product.getAvatar(),1));
                    }
                }else
                {
                    MainActivity.cartList.add(new Cart(product.getId(),product.getName(),price,product.getAvatar(),1));
                }

            }
        });
    }

    private void iniit() {
        toolbar_detail=findViewById(R.id.toolbar_detail);
        txt_name_detail=findViewById(R.id.txt_name_detail);
        img_detail=findViewById(R.id.img_detail);
        txt_price_detail=findViewById(R.id.txt_price_detail);
        txt_price_sale_detail=findViewById(R.id.txt_price_sale_detail);
        txt_des_detail=findViewById(R.id.txt_des_detail);
        txt_add_to_cart=findViewById(R.id.txt_add_to_cart);
        txt_save=findViewById(R.id.txt_save);
        setSupportActionBar(toolbar_detail);
       // toolbar_detail.setTitle(p.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_detail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}