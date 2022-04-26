package com.example.appfood.Adapterr;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.Activity.Product_Detail_Activity;
import com.example.appfood.DataLocal.Dao_User;
import com.example.appfood.Model.Product;
import com.example.appfood.Model.Product_Res;
import com.example.appfood.R;
import com.example.appfood.Service.RetrofitClient;
import com.example.appfood.Service.ServiceApi;

import com.example.appfood.Utils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.Viewholder> {
    List<Product>productList;
    Context context;
    Dao_User dao_user;
    CompositeDisposable compositeDisposable;
    ServiceApi serviceApi= RetrofitClient.getIntance().create(ServiceApi.class);

    public FavouriteAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
        compositeDisposable=new CompositeDisposable();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite,parent,false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Product product=productList.get(position);
        dao_user=new Dao_User(context);
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        String avatar= Utils.url+"image/"+product.getAvatar();
        Picasso.get().load(avatar).into(holder.img_product_fv);
        holder.txt_name_product_fv.setText(product.getName());
        holder.txt_price_product_fv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        double khuyenmai=(Integer.parseInt(product.getPrice())*(product.getSale())/100);
        double price_sale=Integer.parseInt(product.getPrice())-khuyenmai;
        double price=Double.parseDouble(product.getPrice());
        holder.txt_price_product_fv.setText(decimalFormat.format(price)+" VNĐ");
        holder.txt_sale_product_fv.setText(product.getSale()+"%");

        holder.txt_price_sale_product_fv.setText(decimalFormat.format(price_sale)+" VNĐ");
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
        holder.img_delete_fv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Bạn có chắc chắn xóa không?");
                builder.setIcon(R.drawable.ic_baseline_delete_24);
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete_save(dao_user.readData().getId(),product.getId());
                        productList.remove(product);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Hủy",null);
               AlertDialog alertDialog=builder.create();
               alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder
    {
        ImageView img_product_fv,img_delete_fv;
        TextView txt_name_product_fv,txt_price_product_fv,txt_sale_product_fv,txt_price_sale_product_fv;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            img_product_fv=itemView.findViewById(R.id.img_product_fv);
            img_delete_fv=itemView.findViewById(R.id.img_delete_fv);
            txt_name_product_fv=itemView.findViewById(R.id.txt_name_product_fv);
            txt_price_product_fv=itemView.findViewById(R.id.txt_price_product_fv);
            txt_sale_product_fv=itemView.findViewById(R.id.txt_sale_product_fv);
            txt_price_sale_product_fv=itemView.findViewById(R.id.txt_price_sale_product_fv);
        }
    }
    private  void delete_save(int id_user,int id_product)
    {
        compositeDisposable.add(serviceApi.delete_save(id_user,id_product)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(

        ));

    }

}
