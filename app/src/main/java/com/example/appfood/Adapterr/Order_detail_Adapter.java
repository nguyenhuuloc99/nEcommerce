package com.example.appfood.Adapterr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.Model.Item;
import com.example.appfood.R;
import com.example.appfood.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Order_detail_Adapter extends RecyclerView.Adapter<Order_detail_Adapter.ViewHolder>{
    List<Item>itemList;
    public Order_detail_Adapter(List<Item> itemList) {
        this.itemList = itemList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item=itemList.get(position);
        holder.txt_oder_detail_count.setText("Số lương "+item.getQuantity()+"");
        holder.txt_oder_name_detail.setText(item.getName());
        String avatar= Utils.url+"image/"+item.getAvatar();
        Picasso.get().load(avatar).into(holder.img_order_detail);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_order_detail;
        TextView txt_oder_name_detail,txt_oder_detail_count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_order_detail=itemView.findViewById(R.id.img_order_detail);
            txt_oder_detail_count=itemView.findViewById(R.id.txt_oder_detail_count);
            txt_oder_name_detail=itemView.findViewById(R.id.txt_oder_name_detail);
        }
    }
}
