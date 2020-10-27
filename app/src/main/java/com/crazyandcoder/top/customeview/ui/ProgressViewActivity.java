package com.crazyandcoder.top.customeview.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.crazyandcoder.top.customeview.R;
import com.crazyandcoder.top.customeview.widget.CircleProgressView;

public class ProgressViewActivity extends AppCompatActivity {

    private TextView startView;
    private TextView releaseView;
    private CircleProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_view);
        startView = findViewById(R.id.start_tv);
        releaseView = findViewById(R.id.release_tv);
        progressView = findViewById(R.id.progress);

        startView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressView.setProgress(88);
            }
        });
        releaseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressView.setProgress(0);
            }
        });
    }
}