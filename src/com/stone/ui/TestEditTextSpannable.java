package com.stone.ui;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.stone.R;

public class TestEditTextSpannable extends Activity {
	EditText editText;
	GridView mSmileGridView;
	
	  int l=0;////////记录字符串被删除字符之前，字符串的长度
	   int location=0;//记录光标的位置
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_edittext_spannable);
		
		map = new HashMap<String, Integer>();
		int len = mSmileStrings.length;
		for (int i = 0; i < len; ++i) {
			map.put(mSmileStrings[i], smile_id[i]);
		}
		
		editText = (EditText) findViewById(R.id.edittext);
		mSmileGridView = (GridView) findViewById(R.id.gridview);
		mSmileGridView.setAdapter(new SmileAdapter());
		
		mSmileGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				SmileAdapter mAdapter = (SmileAdapter) arg0.getAdapter();
				String smile = mAdapter.getSmile(arg2);
				System.out.println("1111111111"); 
				/*
				 * @author:shizongyin @date:2014-03-13   start
				 */
				String content = editText.getText().toString();
				int selectionEnd = editText.getSelectionEnd();
				String startString = content.substring(0, selectionEnd);
				String endString = content.substring(selectionEnd, editText.length());
				content = startString + smile + endString;
//				
//				SpannableStringBuilder builder = null;
//				try {
//					builder = parseText(content, TestEditTextSpannable.this);
//				} catch (Exception e) {
//					e.printStackTrace();
//					editText.setText(content);
//					editText.setSelection(startString.length() + smile.length());
//				}
//				
//				editText.setText(builder);
				editText.setText(content);
				editText.setSelection(startString.length() + smile.length());
				/*
				 * @author:shizongyin @date:2014-03-13    end
				 */
				Log.d("shizongyin", "click a smile:" + smile);
			}
		});
		
		editText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
//				SpannableStringBuilder builder = null;
//				try {
//					builder = parseText(s.toString(), TestEditTextSpannable.this);
//				} catch (Exception e) {
//					e.printStackTrace();
//					editText.setText(editText.getText().toString());
//				}
//				
//				editText.setText(builder);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				   l=s.length();
				   location=editText.getSelectionStart();//记录之前的光标所在
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				System.out.println("222222222");
				/*
				 * 用一个条件 来改变edittext， 不能无条件的改变——会报stackoverflow异常 
				 */
				 if (l>s.toString().length()) {
				    SpannableStringBuilder sBuilder = null;
					try {
						sBuilder = parseText(s.toString(), TestEditTextSpannable.this);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    editText.setText(sBuilder);
				    editText.setSelection(location);
				 } else if (l<s.toString().length()) {
					  SpannableStringBuilder sBuilder = null;
						try {
							sBuilder = parseText(s.toString(), TestEditTextSpannable.this);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					    editText.setText(sBuilder);
					    editText.setSelection(location);
				 }
			}
		});
	}
	
	public int[] smile_id = new int[] { R.drawable.d_aini,
			R.drawable.d_huaxin, R.drawable.d_shuijiao, R.drawable.d_aoteman,
			R.drawable.d_keai, R.drawable.d_sikao,

			R.drawable.d_baibai, R.drawable.d_kelian, R.drawable.d_taikaixin,
			R.drawable.d_beishang, R.drawable.d_kun, R.drawable.d_touxiao,
			R.drawable.d_bishi };
	public String[] mSmileStrings = new String[] { "[爱你]", "[花心]",
			"[睡觉]", "[奥特曼]", "[可爱]", "[思考]",

			"[拜拜]", "[可怜]", "[太开心]", "[悲伤]", "[困]", "[偷笑]", "[鄙视]"};
	
	class SmileAdapter extends BaseAdapter {
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return smile_id.length;
		}

		public String getSmile(int id) {
			return mSmileStrings[id];
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(TestEditTextSpannable.this).inflate(R.layout.smile_item, null);
				holder.smile = (ImageView) convertView.findViewById(R.id.iv_smile);

				convertView.setTag(holder);

			} else {

				holder = (ViewHolder) convertView.getTag();
			}

			// holder.smile.setImageBitmap((Bitmap)
			// mAlbums.get(position).get(IMAGE_KEY));

			holder.smile.setImageResource(smile_id[position]);

			return convertView;
		}

	}
	static class ViewHolder {
		ImageView smile;
	}
	
	
	/** text-image pair */
	private HashMap<String, Integer> map;
	
	private Integer getSimleResourceId(String text) {
		return map.get(text);
	}
	
	public SpannableStringBuilder parseText(String content, Context mContext) throws Exception {
		int len = content.length();
		int startIndex = 0;
		int endIndex = 0;
		ImageSpan span;
		SpannableStringBuilder builder = new SpannableStringBuilder(content);
		while (startIndex < len) {
			startIndex = content.indexOf("[", startIndex);
//			endIndex = content.indexOf("]", endIndex);//有些特殊输入如： admin[jjj[][]][]kklkjjkl[], 会出现end < start
			endIndex = content.indexOf("]", startIndex);//所以直接从startindex开始向后找end
			if (startIndex != -1 && endIndex != -1) {
				
					String subSmile = content.substring(startIndex, endIndex + 1);
					if (!map.containsKey(subSmile)) {
						endIndex++;
						startIndex = endIndex;
						continue;
					}
					Integer simleResourceId = getSimleResourceId(subSmile);
					span = new ImageSpan(mContext, simleResourceId);
					builder.setSpan(span, startIndex, endIndex + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
					endIndex++;
					startIndex = endIndex;
			} else {
				break;
			}
		}
		return builder;
	}
	
}
