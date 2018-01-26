package com.example.administrator.loginapp.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.administrator.loginapp.Fragment.Fragment01;
import com.example.administrator.loginapp.R;

import java.util.ArrayList;
import java.util.List;

public class ShowFragmentActivity extends BaseActivity{
    private ViewPager myVPager;
    private List<Fragment> fragmentList=new ArrayList<>();


    @Override
    protected void initListener() {

    }

    @Override
    protected void init() {
        Fragment01 fragment01=new Fragment01();
        fragmentList.add(fragment01);

        myVPager= (ViewPager) findViewById(R.id.myViewPager);
        myVPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
    protected int getLayoutRes() {
        return R.layout.activity_show_fragment;
    }
}
