package com.crazyandcoder.top.customeview.utils;

import com.crazyandcoder.top.customeview.bean.ViewItemBean;
import com.crazyandcoder.top.customeview.bean.TView;
import com.crazyandcoder.top.customeview.ui.ProgressViewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataSourceUtils {
    public static List<ViewItemBean> getViewItems() {
        List<ViewItemBean> list = new ArrayList<>();
        list.add(new ViewItemBean(TView.PROGRESS_VIEW, "圆形进度条"));
        return list;
    }

    public static HashMap<Integer, Class> getClsList() {
        HashMap<Integer, Class> clsList = new HashMap<>();
        clsList.put(TView.PROGRESS_VIEW, ProgressViewActivity.class);
        return clsList;
    }
}
