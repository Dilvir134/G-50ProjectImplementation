package com.example.g_50projectimplementation;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import java.util.Objects;

public class Util {

    public static void fixStatusBarColorLight(Window window, Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try{
                Objects.requireNonNull(window.getInsetsController()).setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
            } catch (Exception e) {
                Log.d("STATUS_BAR", "onCreate: Failed to set colors. {0}", e);
            }
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        window.setStatusBarColor(ContextCompat.getColor(context, R.color.background));
    }

}
