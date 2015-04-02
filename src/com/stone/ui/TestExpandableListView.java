package com.stone.ui;

import com.stone.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TestExpandableListView extends Activity {
	ExpandableListView mView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expandable_listview);
		
		mView = (ExpandableListView) findViewById(R.id.el_list);
		mView.setAdapter(new MyAdapter());
		
	}
	
	class MyAdapter extends BaseExpandableListAdapter {
		
		 //设置组视图的图片
//        int[] logos = new int[] { R.drawable.wei, R.drawable.shu,R.drawable.wu};
        //设置组视图的显示文字
        private String[] generalsTypes = new String[] { "魏", "蜀", "吴" };
        //子视图显示文字
        private String[][] generals = new String[][] {
                { "夏侯惇", "甄姬", "许褚", "郭嘉", "司马懿", "杨修" },
                { "马超", "张飞", "刘备", "诸葛亮", "黄月英", "赵云" },
                { "吕蒙", "陆逊", "孙权", "周瑜", "孙尚香" }

        };
        
        //子视图图片
//        public int[][] generallogos = new int[][] {
//                { R.drawable.xiahoudun, R.drawable.zhenji,
//                        R.drawable.xuchu, R.drawable.guojia,
//                        R.drawable.simayi, R.drawable.yangxiu },
//                { R.drawable.machao, R.drawable.zhangfei,
//                        R.drawable.liubei, R.drawable.zhugeliang,
//                        R.drawable.huangyueying, R.drawable.zhaoyun },
//                { R.drawable.lvmeng, R.drawable.luxun, R.drawable.sunquan,
//                        R.drawable.zhouyu, R.drawable.sunshangxiang } };

        //自己定义一个获得textview的方法
        TextView getTextView() {
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 100);
            TextView textView = new TextView(TestExpandableListView.this);
            textView.setLayoutParams(lp);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(36, 0, 0, 0);
            textView.setTextSize(20);
            textView.setTextColor(Color.BLACK);
            return textView;
        }


		@Override
		public int getGroupCount() {
			return generalsTypes.length;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return generals[groupPosition].length;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return generalsTypes[groupPosition];
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return generals[groupPosition][childPosition];
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			 LinearLayout ll = new LinearLayout(TestExpandableListView.this);
             ll.setOrientation(0);
//             ImageView logo = new ImageView(ExpandableList.this);
//             logo.setImageResource(logos[groupPosition]);
//             logo.setPadding(50, 0, 0, 0);
//             ll.addView(logo);
             TextView textView = getTextView();
             textView.setTextColor(Color.BLUE);
             textView.setText(getGroup(groupPosition).toString());
             ll.addView(textView);
             ll.setPadding(100, 10, 10, 10);
             return ll;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			 LinearLayout ll = new LinearLayout(TestExpandableListView.this);
             ll.setOrientation(0);
//             ImageView generallogo = new ImageView(TestExpandableListView.this);
//             generallogo.setImageResource(generallogos[groupPosition][childPosition]);
//             ll.addView(generallogo);
             TextView textView = getTextView();
             textView.setText(getChild(groupPosition, childPosition).toString());
             ll.addView(textView);
             return ll;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
		
	}
}
