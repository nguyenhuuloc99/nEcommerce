package com.example.appfood.Adapterr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.Activity.CartActivity;
import com.example.appfood.Activity.MainActivity;
import com.example.appfood.Model.Cart;
import com.example.appfood.R;

import com.example.appfood.Utils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    List<Cart>cartList;
    Context context;

    public CartAdapter(List<Cart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart=cartList.get(position);
        holder.txt_name_cart.setText(cart.getName());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.gia.setText(decimalFormat.format(cart.getPrice())+"");
        String avatar= Utils.url+"image/"+cartList.get(position).getAvatar();
        Picasso.get().load(avatar).into(holder.img_cart);
        holder.txt_count_cart.setText(cart.getCount()+"");
        int count= Integer.parseInt(holder.txt_count_cart.getText().toString());
        if (count>=10)
        {
            holder.btn_add.setVisibility(View.INVISIBLE);
            holder.btn_remove.setVisibility(View.VISIBLE);
        }else if (count<=1)
        {
            holder.btn_add.setVisibility(View.VISIBLE);
            holder.btn_remove.setVisibility(View.INVISIBLE);
        }else if (count>=1){
            holder.btn_add.setVisibility(View.VISIBLE);
            holder.btn_remove.setVisibility(View.VISIBLE);
        }
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmn=Integer.parseInt(holder.txt_count_cart.getText().toString())+1;
                int slht= MainActivity.cartList.get(position).getCount();
                long giaht=MainActivity.cartList.get(position).getPrice();
                MainActivity.cartList.get(position).setCount(slmn);
                long giamn=(giaht*slmn)/slht;
                MainActivity.cartList.get(position).setPrice(giamn);
                DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                holder.gia.setText(decimalFormat.format(giamn)+" Đ");
                CartActivity.money();
                if (slmn>9)
                {
                    holder.btn_add.setVisibility(View.INVISIBLE);
                    holder.btn_remove.setVisibility(View.VISIBLE);
                    holder.txt_count_cart.setText(slmn+"");
                }else {
                    holder.btn_add.setVisibility(View.VISIBLE);
                    holder.btn_remove.setVisibility(View.VISIBLE);
                    holder.txt_count_cart.setText(slmn+"");
                }
            }
        });
        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmn=Integer.parseInt(holder.txt_count_cart.getText().toString())-1;
                int slht= MainActivity.cartList.get(position).getCount();
                long giaht=MainActivity.cartList.get(position).getPrice();
                MainActivity.cartList.get(position).setCount(slmn);
                long giamn=(giaht*slmn)/slht;
                MainActivity.cartList.get(position).setPrice(giamn);
                DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                holder.gia.setText(decimalFormat.format(giamn)+" Đ");
                CartActivity.money();
                if (slmn<2)
                {
                    holder.btn_add.setVisibility(View.VISIBLE);
                    holder.btn_remove.setVisibility(View.INVISIBLE);
                    holder.txt_count_cart.setText(slmn+"");
                }else {
                    holder.btn_add.setVisibility(View.VISIBLE);
                    holder.btn_remove.setVisibility(View.VISIBLE);
                    holder.txt_count_cart.setText(slmn+"");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView gia,txt_count_cart,txt_name_cart;
        ImageView img_cart,btn_add,btn_remove;
        //CheckBox checkbox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //checkbox=itemView.findViewById(R.id.checkbox);
            gia=itemView.findViewById(com.example.appfood.R.id.gia);
            txt_count_cart=itemView.findViewById(R.id.txt_count_cart);
            btn_add=itemView.findViewById(R.id.btn_add);
            btn_remove=itemView.findViewById(R.id.btn_remove);
            img_cart=itemView.findViewById(R.id.img_cart);
            txt_name_cart=itemView.findViewById(R.id.txt_name_cart);
        }
    }
}
