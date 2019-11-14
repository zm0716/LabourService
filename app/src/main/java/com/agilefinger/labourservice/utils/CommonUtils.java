package com.agilefinger.labourservice.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;

import java.io.File;
import java.util.Arrays;
import java.util.List;


/**
 * Created by 86251 on 2018/12/19.
 */

public class CommonUtils {
    public static List<String> getResourceArray(Context context, int arrays) {
        Resources resources = context.getResources();
        String[] payWayArray = resources.getStringArray(arrays);
        return Arrays.asList(payWayArray);
    }

    public static void removeRecyclerViewDefaultAnimation(RecyclerView recyclerView) {
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }
}
