package com.example.appfood.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.appfood.Adapterr.OrderAdapter;
import com.example.appfood.Adapterr.ViewPagerAdapter;
import com.example.appfood.DataLocal.Dao_User;
import com.example.appfood.Model.Order_Res;
import com.example.appfood.R;
import com.example.appfood.Service.RetrofitClient;
import com.example.appfood.Service.ServiceApi;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_history extends Fragment {

    ViewPagerAdapter viewPagerAdapter;
    TabLayout tablayout;
    ViewPager viewpager2;
    public Fragment_history() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_history, container, false);
        tablayout=view.findViewById(R.id.tablayout);
        viewpager2=view.findViewById(R.id.viewpager2);
        viewPagerAdapter=new ViewPagerAdapter(getChildFragmentManager());
        viewpager2.setAdapter(viewPagerAdapter);
        tablayout.setupWithViewPager(viewpager2);
        return view;
    }
}