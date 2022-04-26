package com.example.appfood.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.appfood.DataLocal.Dao_User;
import com.example.appfood.Fragment.Fragment_Home;
import com.example.appfood.Fragment.Fragment_favourite;
import com.example.appfood.Fragment.Fragment_history;
import com.example.appfood.Model.Cart;
import com.example.appfood.Model.User;
import com.example.appfood.R;

import com.example.appfood.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    NavigationView nav;
    DrawerLayout drawerlayout;
    Toolbar toolbar;
    FrameLayout frameLayout;    
    Dao_User dao_user;
    NotificationBadge menu_count;
    FrameLayout frame_layout;
    public  static List<Cart>cartList;
    ImageView img_search;
    CircleImageView img_user;
    TextView txt_name;
    int bage=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniit();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(MainActivity.this,drawerlayout,toolbar,R.string.open,R.string.close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();
        nav.setNavigationItemSelectedListener(this);
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
        if (savedInstanceState==null)
        {  FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frame,new Fragment_Home());
            getSupportActionBar().setTitle("Home");
            fragmentTransaction.commit();
        }
        frame_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
        //click header view nav
        View headerView=nav.getHeaderView(0);
       txt_name =headerView.findViewById(R.id.txt_name);
       img_user=headerView.findViewById(R.id.img_user);
       User user= dao_user.readData();
       if (user.getAvatar().equals("default"))
       {
               img_user.setImageResource(R.drawable.logo);
       }else {
           String avatar= Utils.url+"image/"+user.getAvatar();
           Picasso.get().load(avatar).into(img_user);
       }
       txt_name.setText(user.getFull_name());
       img_user.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
               startActivity(intent);
           }
       });
        Log.d("id",dao_user.readData().getId()+"");
    }

    private void iniit() {
        img_search=findViewById(R.id.img_search);
        menu_count=findViewById(R.id.menu_count);
        frame_layout=findViewById(R.id.frame_layout);
        dao_user=new Dao_User(getApplicationContext());
        nav=findViewById(R.id.nav);
        drawerlayout=findViewById(R.id.drawerlayout);
        toolbar=findViewById(R.id.toolbar);
        frameLayout=findViewById(R.id.frame);
        if (cartList==null)
        {
            cartList=new ArrayList<>();
        }

        for(int i=0;i<MainActivity.cartList.size();i++)
        {
            bage=bage+MainActivity.cartList.size();
        }
        menu_count.setText(bage+"");
        if (bage==0)
        {
            menu_count.setVisibility(View.INVISIBLE);
        }else
        {
            menu_count.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int bage=0;
        for(int i=0;i<MainActivity.cartList.size();i++)
        {
            bage=bage+MainActivity.cartList.size();
        }
        menu_count.setText(bage+"");
        if (bage==0)
        {
            menu_count.setVisibility(View.INVISIBLE);
        }else
        {
            menu_count.setVisibility(View.VISIBLE);
        }

        User user=dao_user.readData();
          String avatar= Utils.url+"image/"+user.getAvatar();
        Picasso.get().load(avatar).into(img_user);
        txt_name.setText(user.getFull_name());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment=null;
        switch (item.getItemId())
        {
            case R.id.menu_favourite:
                fragment=new Fragment_favourite();
                getSupportActionBar().setTitle("Favourie");
                break;
            case R.id.meu_history:
                fragment=new Fragment_history();
                getSupportActionBar().setTitle("History");
                break;
            case R.id.menu_home:
                fragment=new Fragment_Home();
                getSupportActionBar().setTitle("Home");
                break;
            case R.id.menu_logout:
                fragment=null;
                dao_user.delete();
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                    finish();
                break;
        }
        if (fragment!=null)
        {
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();
            drawerlayout.closeDrawer(GravityCompat.START);
        }

        return false;
    }
}