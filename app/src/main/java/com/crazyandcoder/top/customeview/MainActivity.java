package com.crazyandcoder.top.customeview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.crazyandcoder.top.customeview.adapter.ViewAdapter;
import com.crazyandcoder.top.customeview.bean.ViewItemBean;
import com.crazyandcoder.top.customeview.utils.DataSourceUtils;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    //跳转的activity
    private HashMap<Integer, Class> clsList = DataSourceUtils.getClsList();

    //显示的item
    private List<ViewItemBean> viewItemLists = DataSourceUtils.getViewItems();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);


        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        ViewAdapter viewAdapter = new ViewAdapter(viewItemLists);
        recyclerView.setAdapter(viewAdapter);
        viewAdapter.setOnItemClickListener(new ViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, ViewItemBean item) {
                if (clsList.get(item.getType()) != null) {
                    startActivity(new Intent(MainActivity.this, clsList.get(item.getType())));
                }
            }
        });

    }
}