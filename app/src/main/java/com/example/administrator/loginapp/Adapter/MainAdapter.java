package com.example.administrator.loginapp.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.loginapp.R;

import java.util.List;

/**
 * Created by Administrator on 2017/12/8 0008.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHodler>{
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的


    private List<String> mList;
    private Context context;
    private View mHaeder;
    private View mFooter;


    public MainAdapter(List<String> mList,Context context){
            this.mList=mList;
        this.context=context;
    }

    class ViewHodler extends RecyclerView.ViewHolder {
        TextView mTextView;
        public ViewHodler(View itemView) {
            super(itemView);
            if(itemView==mHaeder && itemView==mFooter){
                return;
            }
            if(itemView==mHaeder){
                return;
            }
            if(itemView==mFooter){
                return;
            }
            mTextView= (TextView) itemView.findViewById(R.id.tv_item);
        }
    }
    public void setHaeder(View header){
        mHaeder=header;
        notifyItemInserted(0);
    }
    public View getHeader(){
        return mHaeder;
    }
    public void setFooter(View footer){
        mFooter=footer;
        notifyItemInserted(getItemCount()-1);
    }
    public View getFooter(){
        return mFooter;
    }

    @Override
    public int getItemViewType(int position) {
        if(mHaeder==null && mFooter==null){
            return TYPE_NORMAL;
        }
        if(position==0){
            return TYPE_HEADER;
        }
        if(position==getItemCount()-1){
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public MainAdapter.ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {

        if(mHaeder!=null && viewType==TYPE_HEADER){

            return new ViewHodler(mHaeder);
        }
        if(mFooter!=null && viewType==TYPE_FOOTER){
            return new ViewHodler(mFooter);
        }
        View view= LayoutInflater.from(context)
                .inflate(R.layout.pratice_recycler_layout,parent,false);

        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHodler holder, int position) {
        if(getItemViewType(position)==TYPE_NORMAL){
            if(holder instanceof ViewHodler){
                holder.mTextView.setText(mList.get(position-1));
                return;
            }
            return;
        }else if(getItemViewType(position)==TYPE_HEADER){
            return;
        }else {
            return;
        }



    }

    @Override
    public int getItemCount() {
        if(mHaeder==null && mFooter==null){
            return mList.size();
        }
        if(mHaeder==null && mFooter!=null){
            return mList.size()+1;
        }
        if(mHaeder!=null && mFooter==null){
            return mList.size()+1;
        }
       else{
            return mList.size()+2;
        }

    }
//处理网格布局中headerview被当成一个item的情况
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager=recyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager= (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position)==TYPE_HEADER?gridLayoutManager.getSpanCount():1;
                }
            });
        }
    }
    //处理瀑布布局中headerview被当成一个item的情况

    @Override
    public void onViewAttachedToWindow(ViewHodler holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if(lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                if(holder.getItemViewType()==TYPE_HEADER){
                    p.setFullSpan(true);
                }
            if(holder.getItemViewType()==TYPE_FOOTER){
                p.setFullSpan(true);
            }





        }

    }
}
