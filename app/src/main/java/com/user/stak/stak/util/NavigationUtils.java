package com.user.stak.stak.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.user.stak.stak.MainActivity;

public final class NavigationUtils {

    private NavigationUtils() {
        //Non instantiable class
    }

    public static void startApp(Activity context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        context.finish();
    }

}
