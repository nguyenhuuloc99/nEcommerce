package com.example.appfood.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.appfood.DataLocal.Dao_User;
import com.example.appfood.Model.User;
import com.example.appfood.Model.User_Res;
import com.example.appfood.R;
import com.example.appfood.Service.RetrofitClient;
import com.example.appfood.Service.ServiceApi;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
        EditText edt_email,edt_password;
        Button btn_login;
        LinearLayout ll2;
        Dao_User dao_user;
        ServiceApi serviceApi;
        TextView txt_forget;
    ProgressDialog progressDialog;
    LoadingDialog loadingDialog;
        CompositeDisposable compositeDisposable=new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniit();
        loadingDialog=new LoadingDialog(this);
      /*  progressDialog=new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("Loading");
        progressDialog.show();*/
        dao_user=new Dao_User(getApplicationContext());
        User user=dao_user.readData();
        if (user!=null)
        {
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
        serviceApi= RetrofitClient.getIntance().create(ServiceApi.class);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            loadingDialog.LoadingDialog("Loading");
                String email=edt_email.getText().toString();
               String pass=edt_password.getText().toString();
               if (email.isEmpty() || pass.isEmpty())
               {
                   loadingDialog.HideDialog();
                   Toast.makeText(getApplicationContext(),"Bạn cần điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
               }else
               {

                   compositeDisposable.add(serviceApi.login(email,pass)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(
                                user_res -> {
                                    if (user_res.isSuccess())
                                    {
                                       // progressDialog.dismiss();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                                inertData(user_res.getUser());
                                                startActivity(intent);
                                                finish();
                                                loadingDialog.HideDialog();

                                            }
                                        },2000);


                                    }else{
                                        loadingDialog.HideDialog();
                                        Toast.makeText(getApplicationContext(),"Sai mật khẩu",Toast.LENGTH_SHORT).show();
                                    }
                                },throwable -> {
                                    Log.d("throwable",throwable.getMessage());
                           }
                   ));

               }


            }
        });
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
        txt_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this,R.style.AlerDialog);
              View view1= LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog,null);
              EditText edt_email_dialog=view1.findViewById(R.id.edt_email_dialog);
              AppCompatButton bt_cf_dialog=view1.findViewById(R.id.bt_cf_dialog);
            builder.setView(view1);
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                bt_cf_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String email=edt_email_dialog.getText().toString();
                        compositeDisposable.add(serviceApi.resetpass(email)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                user_res1 -> {
                                        if (user_res1.isSuccess())
                                        {
                                            Toast.makeText(getApplicationContext(),user_res1.getMessage(),Toast.LENGTH_SHORT).show();

                                        }else
                                        {
                                            Toast.makeText(getApplicationContext(),user_res1.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                },throwable -> {
                                    Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                        ));
                        if (alertDialog.getWindow()!=null)
                        {
                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        }
                        alertDialog.dismiss();
                    }
                });
                }
        });
    }

    private void inertData(User user) {
        dao_user=new Dao_User(getApplicationContext());
        dao_user.inset(user);
    }

    private void iniit() {
        txt_forget=findViewById(R.id.txt_forget);
        edt_email=findViewById(R.id.edt_email);
        edt_password=findViewById(R.id.edt_password);
        btn_login=findViewById(R.id.btn_login);
        ll2=findViewById(R.id.ll2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}