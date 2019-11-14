package com.agilefinger.labourservice.utils;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by 86251 on 2019/6/4.
 */

public class APPUtils {
    public static void removeRecyclerViewDefaultAnimation(RecyclerView recyclerView) {
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }
}
