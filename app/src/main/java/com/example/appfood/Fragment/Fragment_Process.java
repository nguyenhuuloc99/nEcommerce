package com.example.appfood.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appfood.Adapterr.OrderAdapter;
import com.example.appfood.DataLocal.Dao_User;
import com.example.appfood.Model.Order_Res;
import com.example.appfood.R;
import com.example.appfood.Service.RetrofitClient;
import com.example.appfood.Service.ServiceApi;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Process extends Fragment {
    OrderAdapter orderAdapter;
    ServiceApi serviceApi;
    Dao_User dao_user;
        RecyclerView re_process;
        CompositeDisposable compositeDisposable=new CompositeDisposable();
    public Fragment_Process() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment__process, container, false);
        re_process=view.findViewById(R.id.re_process);
        re_process.setLayoutManager(new LinearLayoutManager(getContext()));
        re_process.setHasFixedSize(true);
        dao_user=new Dao_User(getContext());
        serviceApi= RetrofitClient.getIntance().create(ServiceApi.class);
        compositeDisposable.add(serviceApi.get_order(dao_user.readData().getId(),1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        order_res -> {
                            if (order_res.getSuccess())
                            {
                                orderAdapter=new OrderAdapter(order_res.getResult(),getContext());
                                re_process.setAdapter(orderAdapter);
                            }
                        },throwable -> {
                            Log.d("throwable",throwable.getMessage());
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