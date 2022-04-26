package com.example.appfood.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appfood.DataLocal.Dao_User;
import com.example.appfood.Model.User;
import com.example.appfood.Model.User_Res;
import com.example.appfood.R;
import com.example.appfood.Service.RetrofitClient;
import com.example.appfood.Service.ServiceApi;

import com.google.gson.Gson;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmActivity extends AppCompatActivity implements BottomSheetDialog.Sharedata {
    Intent intent;
    TextView edt_total_cf,edt_email_cf,txt5,txt_phone,txt_address;
    Button btn_comfirm;
    BottomSheetDialog bottomSheetDialog;
    ServiceApi serviceApi;
    Dao_User dao_user;
    Toolbar toolbar_comfirm;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confrim);
       iniit();
        intent=getIntent();
        User user=dao_user.readData();
        edt_email_cf.setText(user.getEmail());
        long total_money=intent.getLongExtra("total",-1);
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        edt_total_cf.setText(decimalFormat.format(total_money)+" VND");
        txt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.show(getSupportFragmentManager(),"tag");
            }
        });
        btn_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.LoadingDialog("Loading");
                String adress=txt_address.getText().toString();
                String email=edt_email_cf.getText().toString();
                String phone=txt_phone.getText().toString();
                String total=edt_total_cf.getText().toString();
                if (adress.isEmpty() || phone.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Bạn cần điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }else {
                    int id=user.getId();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            compositeDisposable.add(serviceApi.creatOrder(id,adress,phone,countItem(),email,total,new Gson().toJson(MainActivity.cartList))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            user_res -> {
                                                if (user_res.isSuccess())
                                                {
                                                    loadingDialog.HideDialog();
                                                    Intent intent=new Intent(ConfirmActivity.this,MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }


                                            },throwable -> {
                                                Log.d("thowable",throwable.getMessage());
                                            }
                                    ));
                        }
                    },2000);
                }

            }
        });

    }
    private void iniit() {
        loadingDialog=new LoadingDialog(this);
        toolbar_comfirm=findViewById(R.id.toolbar_comfirm);
        setSupportActionBar(toolbar_comfirm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_comfirm.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dao_user=new Dao_User(getApplicationContext());
        serviceApi= RetrofitClient.getIntance().create(ServiceApi.class);
        btn_comfirm=findViewById(R.id.btn_comfirm);
        bottomSheetDialog=new BottomSheetDialog();
        edt_total_cf=findViewById(R.id.edt_total_cf);
        edt_email_cf=findViewById(R.id.edt_email_cf);
        txt5=findViewById(R.id.txt5);
        txt_phone=findViewById(R.id.txt_phone);
        txt_address=findViewById(R.id.txt_address);
    }
    private int countItem() {
        int total=0;
        for (int i=0;i<MainActivity.cartList.size();i++)
        {
            total=total+MainActivity.cartList.get(i).getCount();
        }
        return total;
    }
    @Override
    public void senddata(String adress, String phone) {
        txt_phone.setText(phone);
        txt_address.setText(adress);
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();

    }
}