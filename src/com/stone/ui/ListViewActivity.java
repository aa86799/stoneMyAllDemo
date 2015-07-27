package com.stone.ui;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.util.GsonObjectRequest;
import com.stone.R;
import com.stone.model.JsonAllUserRequest;
import com.stone.model.User;
import com.stone.util.StoneListAdapter;
import com.stone.util.StoneViewHolder;

import java.util.List;

/**
 * author : stone
 * email  : aa86799@163.com
 * time   : 15/7/24 10 00
 */
public class ListViewActivity extends ListActivity {

    ListView mListView;
    List<User> mData;
    StoneListAdapter<User> stoneBaseAdapter;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
//                    mAdapter = new MyAdapter(ListViewActivity.this, R.layout
//                            .activity_listview_item, mData);
//                    System.out.println(mData.size());

                    stoneBaseAdapter = new StoneListAdapter<User>(ListViewActivity.this,
                            R.layout.activity_listview_item, mData) {

                        @Override
                        protected void getView(Context context, final StoneViewHolder holder, final
                        int
                                position) {
                            //            TextView tvId = holder.getView(R.id.tv_id);
//            TextView tvName = holder.getView(R.id.tv_name);
//            TextView tvAge = holder.getView(R.id.tv_age);

                            User user = getItem(position);

//            tvId.setText(user.getId());
//            tvName.setText(user.getName());
//            tvAge.setText(user.getAge() + "");
                            holder.setText(R.id.tv_id, user.getId()).setText(R.id.tv_name, user.getName())
                                    .setText(R.id.tv_age, user.getAge() + "");

                            holder.getView(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    System.out.println("click a button");
                                }
                            });


//
//                            ImageView iv = holder.getView(R.id.iv_img);
//                            if (TextUtils.isEmpty(holder.getImgUrlByPosition(position))) {//当前位置没有维护
////                                iv.setImageBitmap(bm);  //bm from list(position)-data
//                                holder.setImgUrlByPosition(position, "url" + position);
//                            } else {
//                                if (TextUtils.equals(holder.getImgUrlByPosition(position),
//                                        "position-url")) {
////                                    iv.setImageBitmap(bm);
//                                } else {
////                                    iv.setImageBitmap(bm);
//                                    holder.setImgUrlByPosition(position, "url" + position);
//                                }
//                            }
                        }
                    };
//                    mListView.setAdapter(stoneBaseAdapter);
                    mListView.setAdapter(new MyAdapter(ListViewActivity.this,
                            R.layout.activity_listview_item, mData));
                    break;
            }
        }
    };

    //解决复用时，造成checkBox选择状态也被复用的问题
    class MyAdapter extends StoneListAdapter<User> {
        private SparseBooleanArray mCheckStateArray;

        public MyAdapter(Context context, int layoutID, List data) {
            super(context, layoutID, data);
            this.mCheckStateArray = new SparseBooleanArray();
        }

        public void setChecked(int position, boolean isChecked) {
            mCheckStateArray.put(position, isChecked);
        }

        public boolean isChecked(int position) {
            return mCheckStateArray.get(position);
        }

        @Override
        protected void getView(Context context, final StoneViewHolder holder, final int position) {
            User user = getItem(position);

            holder.setText(R.id.tv_id, user.getId()).setText(R.id.tv_name, user.getName())
                    .setText(R.id.tv_age, user.getAge() + "");

            holder.getView(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("click a button");
                }
            });

            CheckBox cb = holder.getView(R.id.cb_check);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setChecked(position, isChecked);//记录状态，防缓存显示
                }
            });
            cb.setChecked(isChecked(position));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(android.R.layout.list_content);
        mListView = (ListView) findViewById(android.R.id.list);

        requestGson();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("click position is " + position);
            }
        });



    }

    public void requestGson() {
        String ALLUSER_JSON_ADDRESS = "http://123.57.138.77:8080/ssh/getAllUser";

        RequestQueue mRequestQueue =  Volley.newRequestQueue(this); //初始化volley 请求队列
        final StringBuilder builder = new StringBuilder();

        GsonObjectRequest<JsonAllUserRequest> requestAllUser = new GsonObjectRequest<JsonAllUserRequest>(
                ALLUSER_JSON_ADDRESS, JsonAllUserRequest.class, null,
                new Response.Listener<JsonAllUserRequest>() {
                    @Override
                    public void onResponse(JsonAllUserRequest response) {
                        mData = response.getData();
                        handler.obtainMessage(0).sendToTarget();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("requestGson - error");
                    }
                }
        );
        mRequestQueue.add(requestAllUser);

        mRequestQueue.start();
    }
}
