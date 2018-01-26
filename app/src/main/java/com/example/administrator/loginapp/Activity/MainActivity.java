package com.example.administrator.loginapp.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.loginapp.Fragment.Fragment01;
import com.example.administrator.loginapp.Fragment.Fragment02;
import com.example.administrator.loginapp.Fragment.Fragment03;
import com.example.administrator.loginapp.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    View mView;
    CircleImageView nav_head;
    DrawerLayout my_drawerLayout;
    NavigationView navigationView;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private ViewPager viewPager;



    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }





    @Override
    protected void initListener() {
        initToolbar();
     initNavigationItemSelectedListener();
    initViewPagerSelectEvent();
        initRadioGroupCheckEvent();

    }

    private void initRadioGroupCheckEvent() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_button1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.radio_button2:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.radio_button3:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }
        });
    }

    private void initViewPagerSelectEvent() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        radioGroup.check(R.id.radio_button1);
                        break;
                    case 1:
                        radioGroup.check(R.id.radio_button2);
                        break;
                    case 2:
                        radioGroup.check(R.id.radio_button3);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initNavigationItemSelectedListener() {
        navigationView.setCheckedItem(R.id.phone);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.phone:
                        Toast.makeText(MainActivity.this, "phone", Toast.LENGTH_SHORT).show();
                        my_drawerLayout.closeDrawers();
                        break;
                    case R.id.friend:
                        Toast.makeText(MainActivity.this, "friend", Toast.LENGTH_SHORT).show();
                        my_drawerLayout.closeDrawers();
                        break;
                    case R.id.mail:
                        Toast.makeText(MainActivity.this, "mail", Toast.LENGTH_SHORT).show();
                        my_drawerLayout.closeDrawers();
                        break;

                }
                return true;
            }
        });
    }

    private void initToolbar() {
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.menu);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.backup:
               startActivity(new Intent(this,PraticeActivity.class));
                break;
            case R.id.delete:
              startActivity(new Intent(this,MySlideShow.class));
                break;
            case R.id.add:
                startActivity(new Intent(this,TextActivity.class));
                break;
            case android.R.id.home:
               my_drawerLayout.openDrawer(GravityCompat.START);
                break;


        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(my_drawerLayout.isDrawerOpen(GravityCompat.START)){
           // Toast.makeText(this, "www", Toast.LENGTH_SHORT).show();
            my_drawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void init() {
        radioGroup= (RadioGroup) findViewById(R.id.ra_control);
        radioButton1= (RadioButton) findViewById(R.id.radio_button1);
        radioButton2= (RadioButton) findViewById(R.id.radio_button2);
        radioButton3= (RadioButton) findViewById(R.id.radio_button3);
        navigationView= (NavigationView) findViewById(R.id.nav_view);
        my_drawerLayout= (DrawerLayout) findViewById(R.id.my_drawerLayout);
        mView=navigationView.getHeaderView(0);
        nav_head= (CircleImageView) mView.findViewById(R.id.head_icon);
        nav_head.setOnClickListener(this);
        initViewPager();

    }

    private void initViewPager() {
        viewPager= (ViewPager) findViewById(R.id.view_pager);
        final List<Fragment> fragmentList=new ArrayList<>();
         fragmentList.add(new Fragment01());
         fragmentList.add(new Fragment02());
         fragmentList.add(new Fragment03());
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });

    }









    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.head_icon:
                startActivity(new Intent(MainActivity.this,PersonalInfoActivity.class));
                break;
        }
    }




}
