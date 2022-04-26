package com.example.appfood.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.Adapterr.ProductAdapter;
import com.example.appfood.Model.Category;
import com.example.appfood.Model.Product;
import com.example.appfood.Model.Product_Res;
import com.example.appfood.R;
import com.example.appfood.Service.RetrofitClient;
import com.example.appfood.Service.ServiceApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    ServiceApi serviceApi;
    Bundle bundle;
    Category category;
    TextView no_filter,hightolow,lowtohigh;
    int id_category;
    List<Product>list;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        iniit();
        bundle=getIntent().getExtras();
        if (bundle!=null)
        {
            category= (Category) bundle.getSerializable("category");
            id_category=category.getId();
        }
      /*  Intent intent=getIntent();
        if (intent!=null)
        {
            id_category=intent.getIntExtra("id_category",-1);
        }*/

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        compositeDisposable.add(serviceApi.getProducts(id_category)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                product_res -> {
                    if (product_res.isSuccess())
                    {
                        list=product_res.getResult();
                        productAdapter=new ProductAdapter(list,getApplicationContext());
                        recyclerView.setAdapter(productAdapter);
                    }
                }
        ));

        //filter
        no_filter.setBackgroundResource(R.drawable.filter_bg);
        no_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setdata(0);
                no_filter.setBackgroundResource(R.drawable.filter_bg);
                hightolow.setBackgroundResource(0);
                lowtohigh.setBackgroundResource(0);
            }
        });
        hightolow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setdata(1);
                no_filter.setBackgroundResource(0);
                hightolow.setBackgroundResource(R.drawable.filter_bg);
                lowtohigh.setBackgroundResource(0);
            }
        });
        lowtohigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setdata(2);
                no_filter.setBackgroundResource(0);
                hightolow.setBackgroundResource(0);
                lowtohigh.setBackgroundResource(R.drawable.filter_bg);
            }
        });
    }

    private void setdata(int i) {
      if (i==0)
      {

          compositeDisposable.add(serviceApi.getProducts(category.getId())
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
                  product_res -> {
                      if (product_res.isSuccess())
                      {
                          list=product_res.getResult();
                          productAdapter=new ProductAdapter(list,getApplicationContext());
                          recyclerView.setAdapter(productAdapter);
                      }
                  }
          ));

      }else  if (i==1)
      {
          compositeDisposable.add(serviceApi.high_to_low(category.getId())
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
                  product_res -> {
                      list=product_res.getResult();
                      if (product_res.isSuccess())
                      {
                          productAdapter=new ProductAdapter(list,getApplicationContext());
                          recyclerView.setAdapter(productAdapter);
                      }
                  }
          ));



      }else if (i==2)
      {
          compositeDisposable.add(serviceApi.low_to_high(category.getId())
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
                  product_res -> {
                      if (product_res.isSuccess())
                      {
                          list=product_res.getResult();
                          productAdapter=new ProductAdapter(list,getApplicationContext());
                          recyclerView.setAdapter(productAdapter);
                      }
                  }
          ));

      }
    }

    private void iniit() {
        toolbar=findViewById(R.id.toolbar_product);
        recyclerView=findViewById(R.id.re_product);
        serviceApi= RetrofitClient.getIntance().create(ServiceApi.class);
        setSupportActionBar(toolbar);
       // toolbar.setTitle(category.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        list=new ArrayList<>();
        no_filter=findViewById(R.id.no_filter);
        hightolow=findViewById(R.id.hightolow);
        lowtohigh=findViewById(R.id.lowtohigh);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}