package com.example.appfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.appfood.Adapterr.Search_Adapter;
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

public class SearchActivity extends AppCompatActivity {
    EditText edt_search;
    RecyclerView re_search;
    ServiceApi serviceApi;
    List<Product>list;
    Search_Adapter search_adapter;
    Toolbar toolbar_search;
    ImageView img_back;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        iniit();
        re_search.setLayoutManager(new LinearLayoutManager(this));
        re_search.setHasFixedSize(true);
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals(""))
                {
                    list.clear();
                    search_adapter=new Search_Adapter(list,getApplicationContext());
                    re_search.setAdapter(search_adapter);
                }
                fetchData(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void fetchData(String toString) {
        list.clear();
        compositeDisposable.add(serviceApi.search(toString)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                product_res -> {
                    if (product_res.isSuccess())
                    {
                        list=product_res.getResult();
                        search_adapter=new Search_Adapter(list,getApplicationContext());
                        re_search.setAdapter(search_adapter);
                    }

                }
        ));

    }

    private void iniit() {

        toolbar_search=findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar_search);
        img_back=findViewById(R.id.img_back);
        serviceApi= RetrofitClient.getIntance().create(ServiceApi.class);
        list=new ArrayList<>();
        edt_search=findViewById(R.id.edt_search);
        re_search=findViewById(R.id.re_search);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}