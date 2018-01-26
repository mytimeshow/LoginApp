package com.example.administrator.loginapp.Activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.administrator.loginapp.Adapter.MainAdapter;
import com.example.administrator.loginapp.Adapter.Myadapter;
import com.example.administrator.loginapp.BaseAdapter.BaseAdapter;
import com.example.administrator.loginapp.R;
import com.example.administrator.loginapp.util.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class PraticeActivity extends BaseActivity implements BaseAdapter.OnItemClickLisenner{
    private RecyclerView mRecyclerView;
    private List<String> mList;
    private View mHeaderView;
    private List<String> titles;
    private SliderLayout mSliderLayout;
    private  MainAdapter mainAdapter;
    private Myadapter mMyadapter;


    @Override
    protected int getLayoutRes() {

        return R.layout.activity_pratice;
    }

    @Override
    protected void init() {
        initData();
        initViews();

        initAdapter();
    }

    private void initData() {
        titles=new ArrayList<>();
        mList = new ArrayList<>();
        titles.add("a");
        titles.add("b");
        titles.add("c");
        for (int i = 0; i < 10; i++) {
            mList.add(getResources().getString(R.string.sttr_name1));
            mList.add(getResources().getString(R.string.sttr_name2));
            mList.add(getResources().getString(R.string.sttr_name3));
            mList.add(getResources().getString(R.string.sttr_name4));
            mList.add(getResources().getString(R.string.sttr_name5));
            mList.add(getResources().getString(R.string.sttr_name6));
            mList.add(getResources().getString(R.string.sttr_name7));
            mList.add(getResources().getString(R.string.sttr_name8));
            mList.add(getResources().getString(R.string.sttr_name9));


    }}

    private void initViews() {
        mRecyclerView= (RecyclerView) findViewById(R.id.my_recyclerView);




    }

    //初始化Adapter
    private void initAdapter() {
        //设置布局管理器

        //ListView形式上下滑动：
     //  GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        StaggeredGridLayoutManager layoutManager = new
               StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);


//             LinearLayoutManager magager = new LinearLayoutManager(this);
//       magager.setOrientation(LinearLayoutManager.HORIZONTAL);
       mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));

        //头布局加载器，实现轮播图
        LinearLayout slideView= (LinearLayout) LayoutInflater.from(this).inflate(R.layout.slide,mRecyclerView,false);
        mSliderLayout= (SliderLayout)slideView.findViewById(R.id.img_head);
        slideView.removeView(mSliderLayout);
        for(int i=0;i<3;i++){
            final TextSliderView tsv=new TextSliderView(this);
            tsv.description(titles.get(i)).image(R.drawable.mic);
            // 添加子界面
            mSliderLayout.addSlider(tsv);
            // 设置点击事件..
            tsv.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Toast.makeText(PraticeActivity.this, tsv.getDescription(), Toast.LENGTH_SHORT).show();
                }
            });
        }
//左右滑动：

//Grid形式上下滑动：
 //       GridLayoutManager layoutManager = new GridLayoutManager(this,4);
//Grid形式左右滑动：
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
//        layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
//瀑布流形式上下滑动：
//        StaggeredGridLayoutManager layoutManager = new
//                StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
//瀑布流形式左右滑动：
//        StaggeredGridLayoutManager layoutManager = new
//                StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL);
//
//
//
   //    mRecyclerView.setLayoutManager(layoutManager);
        //初始化和设置Adapter
        mMyadapter=new Myadapter(this);
        mMyadapter.addDatas(mList);
        mMyadapter.setOnItemClickLisenner(this);


      // mainAdapter = new MainAdapter(mList,this);
      setHeaderView(mRecyclerView);
       setFooterView(mRecyclerView);
        mRecyclerView.setAdapter(mMyadapter);



    }
    public void setHeaderView(RecyclerView recyclerView){
        View view= LayoutInflater.from(this).inflate(R.layout.recyclewview_head,recyclerView,false);
        View view1=mSliderLayout;


        mMyadapter.setHaeder(view1);
    }
    public void setFooterView(RecyclerView recyclerView){
        View view= LayoutInflater.from(this).inflate(R.layout.recyclewview_head,recyclerView,false);
        mMyadapter.setFooter(view);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void OnClick(int position, Object data) {
        Toast.makeText(this, (String)data+"  "+String.valueOf(position), Toast.LENGTH_SHORT).show();
    }
}
