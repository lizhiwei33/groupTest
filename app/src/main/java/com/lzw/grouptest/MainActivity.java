package com.lzw.grouptest;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> list = null;
    private List<String> groupkey = new ArrayList<String>();
    private List<String> aList = new ArrayList<String>();
    private List<String> bList = new ArrayList<String>();
    private ListView listview;

    MyAdapter adapter;
    private Boolean isShow = false;/////////////////////
    private Button bt;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                adapter = new MyAdapter(list, getApplicationContext(), true);
                listview.setAdapter(adapter);
                isShow = true;
            } else if (msg.what == 0) {
                adapter = new MyAdapter(list, getApplicationContext(), false);
                listview.setAdapter(adapter);
                isShow = false;
            } else if (msg.what == 2) {
                //Toast.makeText(FaceListActivityTest.this, "服务器异常，请稍后再试", Toast.LENGTH_LONG).show();
            }
        }

        ;
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.listView_list);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyAdapter.ViewHolder holder = (MyAdapter.ViewHolder) view.getTag();
                // 改变CheckBox的状态
                holder.cb.toggle();
                // 将CheckBox的选中状况记录下来
                adapter.getIsSelected().put(position, holder.cb.isChecked());
            }
        });
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    Message message = Message.obtain();
                    message.what = 0;
                    handler.sendMessage(message);
                } else {
                    Message message = Message.obtain();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }
        });
        initData();
        adapter = new MyAdapter(list, getApplicationContext(), false);
        listview.setAdapter(adapter);

    }

    public void initData() {
        list = new ArrayList<String>();

        groupkey.add("A组");
        groupkey.add("B组");

        for (int i = 0; i < 5; i++) {
            aList.add("0000" + i);
        }
        list.add("A组");
        list.addAll(aList);

        for (int i = 0; i < 14; i++) {
            bList.add("B组" + i);
        }
        list.add("B组");
        list.addAll(bList);
    }

    private class MyAdapter extends BaseAdapter {
        private List list;
        private Context context;
        private boolean isShow = false;
        private HashMap<Integer, Boolean> isSelected;
        private String tag = "test";

        public MyAdapter(List list, Context context, boolean isShow) {
            this.list = list;
            this.context = context;
            this.isShow = isShow;
            isSelected = new HashMap<Integer, Boolean>();
            // 初始化数据
            initDate();

        }

        // 初始化isSelected的数据
        private void initDate() {
            for (int i = 0; i < list.size(); i++) {
                getIsSelected().put(i, false);
            }
        }

        public HashMap<Integer, Boolean> getIsSelected() {
            return isSelected;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean isEnabled(int position) {
            if (groupkey.contains(getItem(position))) {
                return false;
            }
            return super.isEnabled(position);
        }
        String color="";

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            ViewHolder holder = null;
            //if (view == null) {
                holder = new ViewHolder();
                if (groupkey.contains(getItem(position))) {
                    view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.addexam_list_item_tag, null);
                   color = "gray";
                } else {
                    view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item, null);
                   color="green";
                }

                holder.text = (TextView) view.findViewById(R.id.addexam_list_item_text);
                view.setTag(holder);
          ////  } else {
          //      holder = (ViewHolder) view.getTag();
          //  }

            if (color.equals("gray")){
                view.setBackgroundColor(Color.parseColor("#DDDDDD"));
            }
            if(color.equals("green")){
                view.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.cb = (CheckBox) view.findViewById(R.id.check_box);
                holder.cb.setChecked(getIsSelected().get(position));
                if (isShow) {
                    holder.cb.setVisibility(View.VISIBLE);
                } else {
                    holder.cb.setVisibility(View.GONE);
                }
            }

            holder.text.setText((CharSequence) getItem(position));
            return view;
        }
       /* *//** 用来标记左边的常量 0 **//*
        private static final int LEFT = 0;
        *//** 用来标记右边的常量 1 **//*
        private static final int RIGHT = 1;
        //重写下面两个方法就能使得滑动的时候不错位
        @Override
        public int getItemViewType(int position) {
            if (list.get(position) == LEFT) {
                return LEFT;
            } else {
                return RIGHT;
            }

        }

        @Override
        public int getViewTypeCount() {
            // TODO Auto-generated method stub
            return 2;
        }*/
        public class ViewHolder {
            public CheckBox cb;
            TextView text;
        }
    }
}