package com.example.appfood.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.appfood.Adapterr.BannerAdapter;
import com.example.appfood.Adapterr.CategoryAdapter;
import com.example.appfood.Adapterr.ProductCmAdapter;
import com.example.appfood.Adapterr.ProductSugAdapter;
import com.example.appfood.Model.Product_Sub_Res;
import com.example.appfood.R;
import com.example.appfood.Service.RetrofitClient;
import com.example.appfood.Service.ServiceApi;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Fragment_Home extends Fragment {
    BannerAdapter bannerAdapter;
    RecyclerView re_category,re_sub;
    ViewPager viewPager;
    ServiceApi dataservice;
    CategoryAdapter categoryAdapter;
    ProductCmAdapter productCmAdapter;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    public Fragment_Home() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View view=inflater.inflate(R.layout.fragment__home, container, false);
     viewPager=view.findViewById(R.id.viewpager);
        re_sub=view.findViewById(R.id.re_sub);
        re_category=view.findViewById(R.id.re_category);
        re_category.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        dataservice= RetrofitClient.getIntance().create(ServiceApi.class);
        compositeDisposable.add(dataservice.get_banner()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                banner_res -> {
                    if (banner_res.getSuccess())
                    {
                        bannerAdapter=new BannerAdapter(banner_res.getResult(),getContext());
                        viewPager.setAdapter(bannerAdapter);
                    }

                }
        ));
       compositeDisposable.add(dataservice.get_category()
       .subscribeOn(Schedulers.io())
       .observeOn(AndroidSchedulers.mainThread())
       .subscribe(
               category_res -> {
                   if (category_res.getSuccess())
                   {
                       categoryAdapter=new CategoryAdapter(category_res.getResult(),getContext());
                       re_category.setAdapter(categoryAdapter);
                   }
               }
       ));
       re_sub.setHasFixedSize(true);
       re_sub.setLayoutManager(new LinearLayoutManager(getContext()));
       compositeDisposable.add(dataservice.product_sub()
       .subscribeOn(Schedulers.io())
       .observeOn(AndroidSchedulers.mainThread())
       .subscribe(
         product_sub_res1 -> {
             if (product_sub_res1.getSuccess())
             {
                    productCmAdapter=new ProductCmAdapter(product_sub_res1.getResult(),getContext());
                    re_sub.setAdapter(productCmAdapter);
             }

         },throwable ->
               {
                   Log.d("a",throwable.getMessage());
               }
       ));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}