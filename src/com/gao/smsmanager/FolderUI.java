package com.gao.smsmanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FolderUI extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("文件夹");
        setContentView(textView);
    }
}
