package com.example.appfood.Adapterr;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.appfood.Activity.ProductActivity;
import com.example.appfood.Model.Banner;
import com.example.appfood.R;
import com.example.appfood.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BannerAdapter extends PagerAdapter {
    List<Banner>bannerList;
    Context context;

    public BannerAdapter(List<Banner> bannerList, Context context) {
        this.bannerList = bannerList;
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_banner,null);
        ImageView img_banner=view.findViewById(R.id.img_banner);
        String avatar= Utils.url+"image/"+bannerList.get(position).getImage();
        Picasso.get().load(avatar).into(img_banner);
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ProductActivity.class);
                intent.putExtra("id_category",bannerList.get(position).getCategoryId());
                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return bannerList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
       return  view==object;
    }
}
