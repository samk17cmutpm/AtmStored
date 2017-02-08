package com.sam_nguyen.atmstored.utils;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.sam_nguyen.atmstored.R;

/**
 * Created by samnguyen on 2/8/17.
 */

public class ToolbarUtils {

    public static void initialize(Toolbar toolbar, Activity activity, String title, int icon) {
        ((AppCompatActivity) activity).setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        if (icon != 0) {
            ((AppCompatActivity) activity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(icon);
        }

        if (!title.isEmpty()) {
            TextView titleToolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
            titleToolbar.setText(title);
        }
        ((AppCompatActivity)activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public static void initialize(Toolbar toolbar, Activity activity, int title, int icon) {
        ((AppCompatActivity) activity).setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        if (icon != 0) {
            ((AppCompatActivity) activity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(icon);
        }

        if (title != 0) {
            TextView titleToolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
            titleToolbar.setText(title);
        }
        ((AppCompatActivity)activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
