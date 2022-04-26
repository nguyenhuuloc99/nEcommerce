package com.example.appfood.Adapterr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.Activity.ProductActivity;
import com.example.appfood.Model.Category;
import com.example.appfood.R;

import com.example.appfood.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    List<Category>list;
    Context context;

    public CategoryAdapter(List<Category> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_category,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Category category=list.get(position);
            //tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        if (category.getAvatar().contains("https"))
        {
            Picasso.get().load(category.getAvatar()).into(holder.image_Category);
        }else
        {
            String avatar= Utils.url+"image/"+category.getAvatar();
            Picasso.get().load(avatar).into(holder.image_Category);
        }

        holder.txt_category.setText(category.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ProductActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("category",category);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image_Category;
        TextView txt_category;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_Category=itemView.findViewById(R.id.image_Category);
            txt_category=itemView.findViewById(R.id.txt_category);
        }
    }
}
