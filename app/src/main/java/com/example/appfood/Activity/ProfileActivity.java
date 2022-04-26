package com.example.appfood.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfood.DataLocal.Dao_User;
import com.example.appfood.Model.ServerResponse;
import com.example.appfood.Model.User;
import com.example.appfood.R;
import com.example.appfood.Service.RetrofitClient;
import com.example.appfood.Service.ServiceApi;
import com.example.appfood.Utils;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    CircleImageView img_logo_pf;
    ImageView img_pen;
    TextView txt_email_pf;
    EditText edt_name_pf,edt_password_cf,edt_cfpassword_cf;
    Button btn_update;
    Dao_User dao_user;
    String mediaPath="";
    ServiceApi api;
    LoadingDialog loadingDialog;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    String link="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        iniit();
        User user=dao_user.readData();
        String avatar= Utils.url+"image/"+user.getAvatar();
        Picasso.get().load(avatar).into(img_logo_pf);
        txt_email_pf.setText("Email "+user.getEmail());
        img_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(ProfileActivity.this).crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.LoadingDialog("Loading");
            String fullname=edt_name_pf.getText().toString();
            String pass=edt_password_cf.getText().toString();
            String cfpass=edt_cfpassword_cf.getText().toString();
                if ( pass.isEmpty() || fullname.isEmpty() || cfpass.isEmpty())
                {
                    loadingDialog.HideDialog();
                    Toast.makeText(getApplicationContext(),"Bạn cần điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }else if (!cfpass.equals(pass))
                {
                    loadingDialog.HideDialog();
                    Toast.makeText(getApplicationContext(),"Mật khẩu không trùng khớp",Toast.LENGTH_SHORT).show();
                }else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            compositeDisposable.add(api.update_pass(new Dao_User(getApplicationContext()).readData().getId(),fullname,pass,link)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            serverResponse -> {
                                                if (serverResponse.isSuccess())
                                                {
                                                    loadingDialog.HideDialog();
                                                   dao_user.update(user.getId(),link,fullname);
                                                    startActivity(new Intent(ProfileActivity.this,MainActivity.class));
                                                    finish();
                                                    loadingDialog.HideDialog();
                                                }else {
                                                    Toast.makeText(getApplicationContext(),serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                    ));
                        }
                    },2000);
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

            mediaPath=data.getDataString();
            uploadMultipleFiles();
            Log.d("log",mediaPath);


        super.onActivityResult(requestCode, resultCode, data);


    }
    private String getPath(Uri uri)
    {
        String result;
        Cursor cursor=getContentResolver().query(uri,null,null,null,null);
        if (cursor==null)
        {
            result=uri.getPath();

        }else {
            cursor.moveToFirst();
            int index=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result=cursor.getString(index);
            cursor.close();
        }
        return result;
    }
    private void uploadMultipleFiles() {

        // Map is used to multipart the file using okhttp3.RequestBody
        Uri uri=Uri.parse(mediaPath);

        File file = new File(getPath(uri));
        // Parsing any Media type file
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);

        ServiceApi getResponse = RetrofitClient.getIntance().create(ServiceApi.class);
        Call< ServerResponse > call = getResponse.uploadfile(fileToUpload1);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.isSuccess()) {
                        link=serverResponse.getName();
                        String avatar= Utils.url+"image/"+serverResponse.getName();
                        Picasso.get().load(avatar).into(img_logo_pf);
                        Log.d("avartar",serverResponse.getName());
                       // Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void iniit() {
        api= RetrofitClient.getIntance().create(ServiceApi.class);;
        loadingDialog=new LoadingDialog(this);
        dao_user=new Dao_User(getApplicationContext());
        img_logo_pf=findViewById(R.id.img_logo_pf);
        img_pen=findViewById(R.id.img_pen);
        txt_email_pf=findViewById(R.id.txt_email_pf);
        edt_name_pf=findViewById(R.id.edt_name_pf);
        edt_password_cf=findViewById(R.id.edt_password_cf);
        edt_cfpassword_cf=findViewById(R.id.edt_cfpassword_cf);
        btn_update=findViewById(R.id.btn_update);
    }
}