package com.example.administrator.loginapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.loginapp.BaseAdapter.BaseAdapter;
import com.example.administrator.loginapp.R;

/**
 * Created by Administrator on 2017/12/11 0011.
 */

public class Myadapter extends BaseAdapter<String> {
    private static final String TAG = "Myadapter";
    private Context context;
    public Myadapter(Context context){
        this.context=context;
    }

    @Override
    public ViewHolder createHolder(ViewGroup parent, int viewType) {
        Log.e(TAG, "onBindDatas: run11" );
        View view= LayoutInflater.from(context).inflate(R.layout.pratice_recycler_layout,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindDatas(RecyclerView.ViewHolder holder, int realPosition, String data) {
        Log.e(TAG, "onBindDatas: run22" );
        if(holder instanceof MyHolder){
            ((MyHolder) holder).mTextView.setText(data);
            Log.e(TAG, "onBindDatas: run33" );
        }

    }







    public class MyHolder extends BaseAdapter.ViewHolder{
        TextView mTextView;
        public MyHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }

}
