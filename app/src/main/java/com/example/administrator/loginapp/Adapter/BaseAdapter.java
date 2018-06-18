
package com.example.administrator.loginapp.Adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/8 0008.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {
    private static final String TAG = "BaseAdapter";

    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的


    private List<T> mList = new ArrayList<>();
    private OnItemClickLisenner mLisenner;
    private View mHaeder;
    private View mFooter;

    public void addDatas(List<T> list) {
        Log.e(TAG, "addDatas: do" );
        mList.addAll(list);
        notifyDataSetChanged();
    }

public void setOnItemClickLisenner( OnItemClickLisenner li){
    mLisenner=li;
}



    public class ViewHolder extends RecyclerView.ViewHolder {
       // TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHaeder && itemView == mFooter) {
                return;
            }
            if (itemView == mHaeder) {
                return;
            }
            if (itemView == mFooter) {
                return;
            }
           // mTextView = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }

    public void setHaeder(View header) {
        mHaeder = header;
        notifyItemInserted(0);
    }

    public View getHeader() {
        return mHaeder;
    }

    public void setFooter(View footer) {
        mFooter = footer;
        notifyItemInserted(getItemCount() - 1);
    }

    public View getFooter() {
        return mFooter;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHaeder == null && mFooter == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public BaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e(TAG, "onCreateViewHolder: run" );

        if (mHaeder != null && viewType == TYPE_HEADER) {

            return new ViewHolder(mHaeder);
        }
        if (mFooter != null && viewType == TYPE_FOOTER) {
            return new ViewHolder(mFooter);
        }
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.pratice_recycler_layout, parent, false);
        Log.e(TAG, "onCreateViewHolder: create" );
        return createHolder(parent,viewType);
    }

    public abstract ViewHolder createHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(BaseAdapter.ViewHolder holder, int position) {
        Log.e(TAG, "onBindViewHolder: run1" +getItemCount());
        if (getItemViewType(position) == TYPE_HEADER) {
            Log.e(TAG, "onBindViewHolder: run2" );
            return;
        } else if (getItemViewType(position) == TYPE_FOOTER) {
            Log.e(TAG, "onBindViewHolder: run3" );
            return;
        } else if(getItemViewType(position) == TYPE_NORMAL){
            Log.e(TAG, "onBindViewHolder: run4" );
            final int realPosition = getRealPosition(holder);
            final T data = mList.get(realPosition);
            onBindDatas(holder, realPosition, data);

            if(mLisenner!=null){
               holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       mLisenner.OnClick(realPosition,data);
                   }
               });
            }
        }


    }


    private int getRealPosition(RecyclerView.ViewHolder holder) {
        return mHaeder == null ? holder.getLayoutPosition() : holder.getLayoutPosition() - 1;
    }


    //绑定数据
    public abstract void onBindDatas(RecyclerView.ViewHolder holder, int realPosition, T data);

    @Override
    public int getItemCount() {
        if (mHaeder == null && mFooter == null) {
            return mList.size();
        }
        if (mHaeder == null && mFooter != null) {
            return mList.size() + 1;
        }
        if (mHaeder != null && mFooter == null) {
            return mList.size() + 1;
        } else {
            return mList.size() + 2;
        }

    }

    //处理网格布局中headerview被当成一个item的情况
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }
    //处理瀑布布局中headerview被当成一个item的情况

    @Override
    public void onViewAttachedToWindow(BaseAdapter.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            if (holder.getItemViewType() == TYPE_HEADER) {
                p.setFullSpan(true);
            }



        }
    }

    public interface OnItemClickLisenner<T>{
        void OnClick(int position,T data);
    }
}
