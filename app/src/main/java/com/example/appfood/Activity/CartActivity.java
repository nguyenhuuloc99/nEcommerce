package com.example.appfood.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.appfood.Adapterr.CartAdapter;
import com.example.appfood.R;
import com.google.gson.Gson;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {
        RecyclerView re_cart;
        CartAdapter cartAdapter;
        Toolbar toolbar_cart;
     static TextView total_money;
       static long totalmoney;
    TextView txt7;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.appfood.R.layout.activity_cart);
        iniit();
        money();
        if (MainActivity.cartList.size()==0) {
            total_money.setVisibility(View.GONE);
            txt7.setVisibility(View.VISIBLE);
        }else
        {
            total_money.setVisibility(View.VISIBLE);
            txt7.setVisibility(View.GONE);
        }
        re_cart.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter=new CartAdapter(MainActivity.cartList,getApplicationContext());
        re_cart.setAdapter(cartAdapter);

        total_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CartActivity.this,ConfirmActivity.class);
                intent.putExtra("total",totalmoney);
                startActivity(intent);
            }
        });
    }

    public static  void money() {
        totalmoney=0;
        for(int i=0;i<MainActivity.cartList.size();i++)
        {
            totalmoney=totalmoney+MainActivity.cartList.get(i).getPrice();
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        total_money.setText(decimalFormat.format(totalmoney)+" VND Thanh toán");
    }

    private void iniit() {
        txt7=findViewById(R.id.txt7);
        re_cart=findViewById(R.id.re_cart);
        toolbar_cart=findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbar_cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_cart.setTitle("Thanh toán");
        toolbar_cart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        total_money=findViewById(R.id.total_money);
    }
}