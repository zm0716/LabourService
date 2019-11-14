package com.agilefinger.labourservice.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class QExListView extends ExpandableListView implements AbsListView.OnScrollListener {
    private RelativeLayout _groupLayout;
    public int _groupIndex = -1;
    private ExpandableListAdapter _exAdapter;

    @Override
    public void setAdapter(ExpandableListAdapter adapter) {
        super.setAdapter(adapter);
    }

    /**
     * @param context
     */
    public QExListView(Context context) {
        super(context);
        super.setOnScrollListener(this);
    }

    /**
     * @param context
     * @param attrs
     */
    public QExListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setOnScrollListener(this);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public QExListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        super.setOnScrollListener(this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        if (_exAdapter == null)
            _exAdapter = this.getExpandableListAdapter();

        int ptp = view.pointToPosition(0, 0);
        if (ptp != AdapterView.INVALID_POSITION) {
            QExListView qExlist = (QExListView) view;
            long pos = qExlist.getExpandableListPosition(ptp);
            int groupPos = ExpandableListView.getPackedPositionGroup(pos);
            int childPos = ExpandableListView.getPackedPositionChild(pos);

            if (childPos < 0) {
                groupPos = -1;
            }
            if (groupPos < _groupIndex) {

                _groupIndex = groupPos;

                if (_groupLayout != null) {
                    _groupLayout.removeAllViews();
                    _groupLayout.setVisibility(GONE);// 这里设置Gone 为了不让它遮挡后面header
                }
            } else if (groupPos > _groupIndex) {
                final FrameLayout fl = (FrameLayout) getParent();
                _groupIndex = groupPos;
                if (_groupLayout != null)
                    fl.removeView(_groupLayout);

                _groupLayout = (RelativeLayout) getExpandableListAdapter()
                        .getGroupView(groupPos, true, null, null);
                _groupLayout.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        collapseGroup(_groupIndex);
                    }
                });

                fl.addView(_groupLayout, fl.getChildCount(), new LayoutParams(
                        LayoutParams.FILL_PARENT, 100));

            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

}