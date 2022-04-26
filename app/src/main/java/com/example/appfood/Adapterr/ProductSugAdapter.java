package com.example.appfood.Adapterr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.Activity.Product_Detail_Activity;
import com.example.appfood.Model.Product;
import com.example.appfood.R;
import com.example.appfood.Utils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ProductSugAdapter extends RecyclerView.Adapter<ProductSugAdapter.ViewHolder>{
    List<Product>productList;
    Context context;

    public ProductSugAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_product_sug,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product=productList.get(position);
        String avatar= Utils.url+"image/"+product.getAvatar();
        Picasso.get().load(avatar).into(holder.img_sug);
        double khuyenmai=(Integer.parseInt(product.getPrice())*(product.getSale())/100);
        double price_sale=Integer.parseInt(product.getPrice())-khuyenmai;
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        if (product.getSale()==0)
        {
            holder.txt_price_sug.setVisibility(View.INVISIBLE);
            holder.txt_price_sale_sug.setText(decimalFormat.format(price_sale)+" VNĐ");
        }else
        {
            holder.txt_price_sug.setVisibility(View.VISIBLE);
            holder.txt_price_sug.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txt_price_sale_sug.setText(decimalFormat.format(price_sale)+" VNĐ");
            double price=Double.parseDouble(product.getPrice());
            holder.txt_price_sug.setText(decimalFormat.format(price)+" VNĐ");

    }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Product_Detail_Activity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("product",product);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.txt_name_sug.setText(product.getName());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_sug;
        TextView txt_name_sug,txt_price_sale_sug,txt_price_sug;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_sug=itemView.findViewById(R.id.img_sub);
            txt_name_sug=itemView.findViewById(R.id.txt_name_sub);
            txt_price_sale_sug=itemView.findViewById(R.id.txt_price_sale_sug);
            txt_price_sug=itemView.findViewById(R.id.txt_price_sug);
        }
    }
}
