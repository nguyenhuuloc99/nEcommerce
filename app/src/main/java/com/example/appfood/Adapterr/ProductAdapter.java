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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Viewholder>{
    List<Product>list;
    Context context;
    ServiceApi serviceApi;
    int dem=0;
    Dao_User dao_user;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    public ProductAdapter(List<Product> list, Context context) {
        this.list = list;
        this.context = context;

        serviceApi= RetrofitClient.getIntance().create(ServiceApi.class);
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_product,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
            Product product=list.get(position);
            dao_user=new Dao_User(context);
        String avatar= Utils.url+"image/"+product.getAvatar();
        Picasso.get().load(avatar).into(holder.img_product);
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
            holder.txt_price_product.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        double khuyenmai=(Integer.parseInt(product.getPrice())*(product.getSale())/100);
        double price_sale=Integer.parseInt(product.getPrice())-khuyenmai;
        double price=Double.parseDouble(product.getPrice());
            holder.txt_price_product.setText(decimalFormat.format(price)+" VNĐ");
            holder.txt_sale_product.setText(product.getSale()+"%");

            holder.txt_price_sale_product.setText(decimalFormat.format(price_sale)+" VNĐ");
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

        holder.txt_name_product.setText(product.getName());
        check_save(dao_user.readData().getId(),product.getId(),holder.img_save);
        holder.img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dem==0)
                {
                    insert_save(dao_user.readData().getId(),product.getId());
                   holder.img_save.setImageResource(R.drawable.loved);
                            dem++;
                }else {
                    delete_save(dao_user.readData().getId(),product.getId());
                    holder.img_save.setImageResource(R.drawable.love);
                            dem--;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder
    {
        ImageView img_product,img_add,img_save;
        TextView txt_name_product,txt_price_product,txt_sale_product,txt_price_sale_product;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            img_product=itemView.findViewById(R.id.img_product);
            img_add=itemView.findViewById(R.id.img_add);
            img_save=itemView.findViewById(R.id.img_save);
            txt_name_product=itemView.findViewById(R.id.txt_name_product);
            txt_price_product=itemView.findViewById(R.id.txt_price_product);
            txt_sale_product=itemView.findViewById(R.id.txt_sale_product);
            txt_price_sale_product=itemView.findViewById(R.id.txt_price_sale_product);
        }
    }
    private  void insert_save(int id_user,int id_product)
    {
        compositeDisposable.add(serviceApi.insert_save(id_user,id_product)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                product_res -> {

                }
        ));
    }
    private  void delete_save(int id_user,int id_product)
    {
        compositeDisposable.add(serviceApi.delete_save(id_user,id_product)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(

        ));

    }
    private  void check_save(int id_user,int product_id,ImageView imageView)
    {
        compositeDisposable.add(serviceApi.check_save(id_user,product_id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                product_res -> {
                    if (product_res.isSuccess())
                    {
                        dem=1;
                        imageView.setImageResource(R.drawable.loved);
                    }else
                    {
                        dem=0;
                        imageView.setImageResource(R.drawable.love);
                    }
                }
        ));
    }
}
