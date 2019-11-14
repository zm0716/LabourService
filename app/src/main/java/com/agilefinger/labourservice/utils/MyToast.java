package com.agilefinger.labourservice.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by wang_ on 2017/11/1.
 */
public class MyToast {
    private static MyToast myToast;
    private Toast mytoast;
    private MyToast(){};
    public static MyToast getInstands(){
        if(myToast == null){
            myToast = new MyToast();
        }
        return myToast;
    }

    public void toastShow(Context context,String string){
//        Toast toast = Toast.makeText(context, ""+string, Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();
        if (mytoast == null){
            mytoast = Toast.makeText(context, ""+string, Toast.LENGTH_SHORT);
            mytoast.setGravity(Gravity.CENTER, 0, 0);
        } else{
            mytoast.setText(""+string);
        }
        mytoast.show();
    }

}
