package com.baidu.location.demo.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;


public class ListViewItem extends FrameLayout implements Checkable {

    private RadioButton radioButton;

    public ListViewItem(Context context, @LayoutRes int resource, @IdRes int radioButtonId) {
        super(context);
        View.inflate(context, resource, this);
        this.radioButton = (RadioButton) findViewById(radioButtonId);
    }

    public void setText(@IdRes int textViewId, String text) {
        ((TextView) findViewById(textViewId)).setText(text);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void setChecked(boolean checked) {
        radioButton.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return radioButton.isChecked();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void toggle() {
        radioButton.toggle();
    }
}
