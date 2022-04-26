package com.example.appfood.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.appfood.DataLocal.Dao_User;
import com.example.appfood.Model.User_Res;
import com.example.appfood.R;
import com.example.appfood.Service.RetrofitClient;
import com.example.appfood.Service.ServiceApi;

import org.json.JSONObject;

import java.net.URL;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText edt_name_signup, edt_email_signup, edt_password_signup, edt_cfpassword_signup;
    AppCompatButton btn_signup;
    TextView txt_back;
    ServiceApi api;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Dao_User dao_user;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        iniit();
    loadingDialog=new LoadingDialog(this);
//        login_button.setReadPermissions(Arrays.asList("public_profile","email"));
       /* login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String accessToken=loginResult.getAccessToken().getToken();
                GraphRequest request=GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                       getdata(object);
                    }
                });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });*/
        /*if(AccessToken.getCurrentAccessToken()!=null)
        {
            txt_email.setText(AccessToken.getCurrentAccessToken().getUserId());
        }*/
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.LoadingDialog("Loading");
                String email = edt_email_signup.getText().toString();
                String username = edt_name_signup.getText().toString();
                String password = edt_password_signup.getText().toString();
                String cf_pass = edt_cfpassword_signup.getText().toString();
                if (email.isEmpty() || password.isEmpty() || username.isEmpty() || cf_pass.isEmpty())
                {
                    loadingDialog.HideDialog();
                    Toast.makeText(getApplicationContext(),"Bạn cần điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }else if (!cf_pass.equals(password))
                {
                    loadingDialog.HideDialog();
                    Toast.makeText(getApplicationContext(),"Mật khẩu không trùng khớp",Toast.LENGTH_SHORT).show();
                }else
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            compositeDisposable.add(api.register(email,username,password)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            user_res1 -> {
                                                if (user_res1.isSuccess())
                                                {
                                                    startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                                    finish();
                                                    loadingDialog.HideDialog();
                                                }else
                                                {
                                                    Toast.makeText(getApplicationContext(),"Vui lòng thử lại",Toast.LENGTH_SHORT).show();
                                                }
                                            },throwable -> {
                                                Log.d("throwable",throwable.getMessage());
                                            }
                                    ));
                        }
                    },2000);


                }

            }
        });
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void iniit() {
        dao_user = new Dao_User(getApplicationContext());
        api = RetrofitClient.getIntance().create(ServiceApi.class);
        // login_button=findViewById(R.id.login_button);
        edt_name_signup = findViewById(R.id.edt_name_signup);
        edt_email_signup = findViewById(R.id.edt_email_signup);
        edt_password_signup = findViewById(R.id.edt_password_signup);
        edt_cfpassword_signup = findViewById(R.id.edt_cfpassword_signup);
        btn_signup = findViewById(R.id.btn_signup);
        txt_back = findViewById(R.id.txt_back);
    }

    private void getdata(JSONObject object) {
        try {
            URL profile_picture = new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?width=250&height=250");
            //Picasso.get().load(profile_picture.toString()).into();
            //txtemail=setText(object.getString"email")
        } catch (Exception e) {

        }
    }

    ;


  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);

    }*/
    /*
      <com.facebook.login.widget.LoginButton
        app:layout_constraintTop_toBottomOf="@id/txt_back"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
       app:layout_constraintBottom_toBottomOf="parent"
        android:id="@+id/login_button"
        android:layout_width="300dp"
        android:paddingTop="13dp"
        android:paddingBottom="13dp"
        android:layout_marginTop="40dp"
        android:textSize="18sp"
        style="@style/loginFacebook"/>
     *

     */
  @Override
  protected void onDestroy() {
      compositeDisposable.clear();
      super.onDestroy();

  }
}