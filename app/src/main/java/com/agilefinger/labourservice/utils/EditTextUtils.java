package com.agilefinger.labourservice.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Shinelon on 2019/7/26.
 */

public class EditTextUtils {


    public static void editWatcher(final EditText editText, final EditTextChanged editTextChanged) {


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                editTextChanged.beforeTextChanged();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                        if (s.length() - 1 - s.toString().indexOf(".") > 2) {

                            s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                            editText.setText(s);
                            editText.setSelection(s.length());
                          //  Log.d("当前反应",s.length()+":::");

                        }

                }else {
                    if (s.length()>4){
                        s = s.toString().subSequence(0,4);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public interface EditTextChanged {

        void beforeTextChanged();

    }

}
