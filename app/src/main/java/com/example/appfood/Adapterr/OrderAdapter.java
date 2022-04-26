package com.example.appfood.Adapterr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.Model.Order;
import com.example.appfood.R;


import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    List<Order>orderList;
    Context context;

    public OrderAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_order,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order=orderList.get(position);
        holder.txt_order_count.setText("Đơn hàng  :");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(holder.re_detail.getContext(), LinearLayoutManager.VERTICAL,false);
        Order_detail_Adapter order_detail_adapter=new Order_detail_Adapter(orderList.get(position).getItem());
        holder.re_detail.setHasFixedSize(true);
        holder.re_detail.setLayoutManager(linearLayoutManager);
        holder.re_detail.setAdapter(order_detail_adapter);
    }

    @Override
    public int getItemCount() {
        if (orderList==null)
        {
            return 0;
        }
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_order_count;
        RecyclerView re_detail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_order_count=itemView.findViewById(R.id.txt_order_count);
            re_detail=itemView.findViewById(R.id.re_detail);
        }
    }
}
