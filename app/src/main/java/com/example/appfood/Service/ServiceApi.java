package com.example.appfood.Service;

import com.example.appfood.Model.Banner_Res;
import com.example.appfood.Model.Category_Res;
import com.example.appfood.Model.Order_Res;
import com.example.appfood.Model.Product_Res;
import com.example.appfood.Model.Product_Sub_Res;
import com.example.appfood.Model.Result;
import com.example.appfood.Model.ServerResponse;
import com.example.appfood.Model.User_Res;


import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceApi {
    @GET("fetch_banner.php")
    Observable<Banner_Res> get_banner();
    @GET("fetch_category.php")
    Observable<Category_Res> get_category();
    @POST("login.php")
    @FormUrlEncoded
    Observable<User_Res> login(@Field("email")String email, @Field("pass")String pass);
    @FormUrlEncoded
    @POST("register.php")
    Observable<User_Res>register(@Field("email")String email,@Field("full_name")String full_name, @Field("pass")String pass);
    @FormUrlEncoded
    @POST("insert_save.php")
    Observable<Product_Res>insert_save(@Field("id_user")int id_user,@Field("id_product") int id_product);
    @FormUrlEncoded
    @POST("delete_save_product.php")
    Observable<Product_Res>delete_save(@Field("id_user")int id_user,@Field("id_product") int id_product);
    @FormUrlEncoded
    @POST("check_save.php")
    Observable<Product_Res>check_save(@Field("id_user")int id_user,@Field("id_product") int id_product);
    @FormUrlEncoded
    @POST("fetch_product_save.php")
    Observable<Product_Res>fetch_product_save(@Field("id_user")int id_user);
    //filter
    @FormUrlEncoded
    @POST("high_to_low.php")
    Observable<Product_Res>high_to_low(@Field("category_id") int category_id);
    @FormUrlEncoded
    @POST("low_to_high.php")
    Observable<Product_Res>low_to_high(@Field("category_id") int category_id);
    @FormUrlEncoded
    @POST("get_product.php")
    Observable<Product_Res>getProducts(@Field("category_id") int category_id);
    //order
    @FormUrlEncoded
    @POST("order.php")
    Observable<User_Res>creatOrder(@Field("iduser") int  iduser,
                             @Field("address") String address,
                             @Field("phone") String phone,
                             @Field("count") int count,
                             @Field("email") String email,
                             @Field("total_money") String total_money,
                             @Field("json") String json
    );
    @FormUrlEncoded
    @POST("get_order.php")
    Observable<Order_Res>get_order(@Field("iduser") int iduser,@Field("status") int status);
    @Multipart
    @POST("upload_image2.php")
    Call<ServerResponse>uploadfile(@Part MultipartBody.Part file);
    @FormUrlEncoded
    @POST("search.php")
    Observable<Product_Res>search(@Field("search") String search);
    @FormUrlEncoded
    @POST("reset.php")
    Observable<User_Res>resetpass(@Field("email") String email);
    @GET("fetch_product_sug.php")
    Observable<Product_Sub_Res>product_sub();
    @FormUrlEncoded
    @POST("update_avatar_pass.php")
    Observable<ServerResponse>update_pass(@Field("id")int id,@Field("full_name") String full_name,@Field("pass") String pass,@Field("avatar") String avatar);
}
