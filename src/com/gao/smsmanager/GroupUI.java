package com.gao.smsmanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GroupUI extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("群组");
        setContentView(textView);
    }
}
