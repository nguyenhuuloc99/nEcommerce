package com.example.appfood.Adapterr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.Model.Banner;
import com.example.appfood.Model.Product_Sub_Res;
import com.example.appfood.Model.Result;
import com.example.appfood.R;
import com.example.appfood.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductCmAdapter extends RecyclerView.Adapter<ProductCmAdapter.ViewHolder>{
    List<Result>product_sub_resList;
    Context context;

    public ProductCmAdapter(List<Result> product_sub_resList, Context context) {
        this.product_sub_resList = product_sub_resList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_sug,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result product_sub_res=product_sub_resList.get(position);
        String avatar= Utils.url+"image/"+product_sub_res.getImage();
        Picasso.get().load(avatar).into(holder.img_baner_sub);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(holder.re_product_sub.getContext(),LinearLayoutManager.HORIZONTAL,false);
        ProductSugAdapter productSugAdapter=new ProductSugAdapter(product_sub_res.getProduct(),context);
        holder.re_product_sub.setHasFixedSize(true);
        holder.re_product_sub.setLayoutManager(linearLayoutManager);
        holder.re_product_sub.setAdapter(productSugAdapter);
    }

    @Override
    public int getItemCount() {
        return product_sub_resList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_baner_sub;
        RecyclerView re_product_sub;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            re_product_sub=itemView.findViewById(R.id.re_product_sub);
            img_baner_sub=itemView.findViewById(R.id.img_baner_sub);
        }
    }
}
