package com.example.appfood.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.Adapterr.FavouriteAdapter;
import com.example.appfood.DataLocal.Dao_User;
import com.example.appfood.Model.Product_Res;
import com.example.appfood.R;
import com.example.appfood.Service.RetrofitClient;
import com.example.appfood.Service.ServiceApi;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_favourite extends Fragment {
    ServiceApi serviceApi;
    FavouriteAdapter favouriteAdapter;
    RecyclerView re_favourite;
    Dao_User dao_user;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    public Fragment_favourite() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_favourite, container, false);
       re_favourite=view.findViewById(R.id.re_favourite);
       re_favourite.setHasFixedSize(true);
       re_favourite.setLayoutManager(new LinearLayoutManager(getContext()));
       serviceApi= RetrofitClient.getIntance().create(ServiceApi.class);
        dao_user=new Dao_User(getContext());
        fetchdata(dao_user.readData().getId());
        return view;
    }

    private void fetchdata(int id_user) {
        compositeDisposable.add(serviceApi.fetch_product_save(id_user)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                product_res -> {
                    if (product_res.isSuccess())
                    {
                        favouriteAdapter=new FavouriteAdapter(product_res.getResult(),getContext());
                        re_favourite.setAdapter(favouriteAdapter);
                    }
                }
        ));

    }

}

