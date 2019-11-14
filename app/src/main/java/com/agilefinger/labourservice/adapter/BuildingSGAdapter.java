package com.agilefinger.labourservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agilefinger.labourservice.R;

import java.util.List;

public class BuildingSGAdapter extends RecyclerView.Adapter<BuildingSGAdapter.ViewHolder> {
    private View mHeaderView;
    Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private final LayoutInflater mLayoutInflater;
    private final static int ITEM_HEADER = 0;
    private final static int ITEM_CONTENT = 1;
    private final static int ITEM_FOOT = 2;
    List<String> list;
    private int mHeader = 1;

    private int mFoot = 1;

    public BuildingSGAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.list = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
    }

    public View getHeaderView() {
        return mHeaderView;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_FOOT) {
            return new ViewHolder(mHeaderView);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.add_building_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (getItemViewType(position) == ITEM_FOOT) {
            return;
        } else {

            holder.m_add_text.setText(list.get(position));
            holder.m_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(v, position, holder, list);
                }
            });
        }
    }


    //View.OnKeyListener
    @Override
    public int getItemViewType(int position) {
        if (mFoot != 0 && position >= list.size()) {
            return ITEM_FOOT;
        }
        return ITEM_CONTENT;
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, ViewHolder holder, List<String> list);


    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView m_add_text;
        public ImageView m_delete;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.m_add_text = (TextView) rootView.findViewById(R.id.m_add_text);
            this.m_delete = (ImageView) rootView.findViewById(R.id.m_delete);
        }

    }
}
