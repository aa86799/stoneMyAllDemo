package com.stone.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.stone.R;
import com.stone.ui.TestFragmentActivity;
import com.stone.util.LogUtils;

public class MyDialogFragment extends DialogFragment {
	private final String TAG = "MyDialogFragment";
	int mNum;

	/**
	 * Create a new instance of MyDialogFragment, providing "num" as an
	 * argument.
	 */
	public static MyDialogFragment newInstance(int num) {
		MyDialogFragment f = new MyDialogFragment();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putInt("num", num);
		f.setArguments(args);

		return f;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		LogUtils.printInfo(TAG, "---------onCreateDialog----------");
		return super.onCreateDialog(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtils.printInfo(TAG, "---------onCreate----------");
		
		mNum = getArguments().getInt("num");

		// Pick a style based on the num.
		int style = DialogFragment.STYLE_NORMAL, theme = 0;
		switch ((mNum - 1) % 6) {
		case 0:
			style = DialogFragment.STYLE_NO_FRAME;
			break;
		case 1:
			style = DialogFragment.STYLE_NO_TITLE;
			break;
		case 2:
			style = DialogFragment.STYLE_NO_FRAME;
			break;
		case 3:
			style = DialogFragment.STYLE_NO_INPUT;
			break;
		case 4:
			style = DialogFragment.STYLE_NORMAL;
			break;
		case 5:
			style = DialogFragment.STYLE_NORMAL;
			break;
		}
		switch ((mNum - 1) % 6) {
		case 3:
			theme = android.R.style.Theme_Holo_Light_Panel;
			break;
		case 4:
			theme = android.R.style.Theme_Holo;
			break;
		case 5:
			theme = android.R.style.Theme_Holo_Light_Dialog;
			break;
		case 6:
			theme = android.R.style.Theme_Holo_Light;
			break;
		}
		
		setStyle(style, theme);//
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LogUtils.printInfo(TAG, "---------onCreateView----------");
		
		View v = inflater.inflate(R.layout.fragment_dialog, container, false);
		View tv = v.findViewById(R.id.tv_text);
		((TextView) tv).setText("Dialog #" + mNum + ": using style " + getNameForNum(mNum));
		((TextView) tv).setTextColor(Color.GREEN);

		// Watch for button clicks.
		Button button = (Button) v.findViewById(R.id.btn_show);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// When button is clicked, call up to owning activity.
				((TestFragmentActivity)getActivity()).showDialog();
			}
		});

		return v;
	}

	private String getNameForNum(int num) {
		String style = null;
		switch ((num - 1) % 6) {
		case 1:
			style = "DialogFragment.STYLE_NO_TITLE";
			break;
		case 2:
			style = "DialogFragment.STYLE_NO_FRAME";
			break;
		case 3:
			style = "DialogFragment.STYLE_NO_INPUT";
			break;
		case 4:
		case 5:
			style = "DialogFragment.STYLE_NORMAL";
			break;
		}
		return style;
	}
	
	public static class MyAlertDialogFragment extends DialogFragment {

	    public static MyAlertDialogFragment newInstance(String title) {
	        MyAlertDialogFragment frag = new MyAlertDialogFragment();
	        Bundle args = new Bundle();
	        args.putString("title", title);
	        frag.setArguments(args);
	        return frag;
	    }

	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	    	LogUtils.printInfo("MyAlertDialogFragment", "---------onCreateDialog-----");
	        String title = getArguments().getString("title");

	        return new AlertDialog.Builder(getActivity())
	                .setIcon(R.drawable.video_stop)
	                .setTitle(title)
	                .setPositiveButton("ok",
	                    new DialogInterface.OnClickListener() {
	                        public void onClick(DialogInterface dialog, int whichButton) {
	                            ((TestFragmentActivity)getActivity()).doPositiveClick();
	                        }
	                    }
	                )
	                .setNegativeButton("cancel",
	                    new DialogInterface.OnClickListener() {
	                        public void onClick(DialogInterface dialog, int whichButton) {
	                            ((TestFragmentActivity)getActivity()).doNegativeClick();
	                        }
	                    }
	                )
	                .create();
	    }
	}
}
