package com.stone.fragment;

import java.util.Date;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

import com.stone.R;
import com.stone.util.LogUtils;

/*
 * 自定义偏好
 */
public class NumberPickerPreference extends DialogPreference {
	private final String TAG = "NumberPickerPreference";
	private long mValue ;
	private TimePicker mPicker;
	
	public NumberPickerPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		LogUtils.printInfo(TAG, "--------NumberPickerPreference()-----------");
		//指定用户界面
		 setDialogLayoutResource(R.layout.numberpicker_dialog);
//	     setPositiveButtonText(android.R.string.ok);
//	     setNegativeButtonText(android.R.string.cancel);
	     
	     setDialogIcon(null);
	}
	
	@Override	//弹出对话框的时候进行初始化
	protected void onBindDialogView(View view) {
		// TODO Auto-generated method stub
		super.onBindDialogView(view);
		LogUtils.printInfo(TAG, "--------onBindDialogView-----------");
		mPicker = (TimePicker) view.findViewById(R.id.tp_preference);
		if(mPicker != null) {
			mPicker.setIs24HourView(true);
			long value = mValue;
			Date d = new Date(value);
			mPicker.setCurrentHour(d.getHours());
			mPicker.setCurrentMinute(d.getMinutes());
		}	
	}
	
	@Override
	protected void onClick() {
		// TODO Auto-generated method stub
		super.onClick();
		LogUtils.printInfo(TAG, "--------onClick-----------");
	}
	
	@Override //关闭时 保存设置的值
	protected void onDialogClosed(boolean positiveResult) {
		// TODO Auto-generated method stub
		super.onDialogClosed(positiveResult);
		LogUtils.printInfo(TAG, "--------onDialogClosed-----------");
		if (positiveResult) {
			Date d = new Date(0, 0, 0, mPicker.getCurrentHour(), mPicker.getCurrentMinute(), 0);
			long value = d.getTime();
			mValue = value;
			if(callChangeListener(value)) {
				persistLong(value); //它的内部实现: getEditor().putLong(getKey(), value);
			}
	    }
	}
	
	
	
	@Override	//初始化当前值
	protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
		LogUtils.printInfo(TAG, "--------onSetInitialValue-----------");
		long value = 0;
	    if (restorePersistedValue) {
	        // Restore existing state
	        value = getPersistedLong(mValue);
	    } else {
	        // Set default state from the XML attribute
	       value = Long.parseLong(defaultValue.toString());
	    }
	    setDefaultValue(value);
	    mValue = value;
	}
	
	@Override //提供一个默认值
	protected Object onGetDefaultValue(TypedArray a, int index) {
		// TODO Auto-generated method stub
		LogUtils.printInfo(TAG, "--------onGetDefaultValue-----------");
		return Long.parseLong(a.getString(index));
	}
	
	
	private static class SavedState extends BaseSavedState {
	    // Member that holds the setting's value
	    // Change this data type to match the type saved by your Preference
	    long value;

	    public SavedState(Parcelable superState) {
	        super(superState);
	    }

	    public SavedState(Parcel source) {
	        super(source);
	        // Get the current preference's value
	        value = source.readLong();  // Change this to read the appropriate data type
	    }

	    @Override
	    public void writeToParcel(Parcel dest, int flags) {
	        super.writeToParcel(dest, flags);
	        // Write the preference's value
	        dest.writeLong(value);  // Change this to write the appropriate data type
	    }

	    // Standard creator object using an instance of this class
	    public static final Parcelable.Creator<SavedState> CREATOR =
	            new Parcelable.Creator<SavedState>() {

	        public SavedState createFromParcel(Parcel in) {
	            return new SavedState(in);
	        }

	        public SavedState[] newArray(int size) {
	            return new SavedState[size];
	        }
	    };
	}
	
	@Override
	protected Parcelable onSaveInstanceState() {
		LogUtils.printInfo(TAG, "--------onSaveInstanceState-----------");
	    final Parcelable superState = super.onSaveInstanceState();
	    // Check whether this Preference is persistent (continually saved)
	    if (isPersistent()) {
	        // No need to save instance state since it's persistent, use superclass state
	        return superState;
	    }

	    // Create instance of custom BaseSavedState
	    final SavedState myState = new SavedState(superState);
	    // Set the state's value with the class member that holds current setting value
	    Date d = new Date(0, 0, 0, mPicker.getCurrentHour(), mPicker.getCurrentMinute(), 0);
	    myState.value = d.getTime();
	    System.out.println("---------onSaveInstanceState:::" + d.getTime()); //本例中上面直接return了
	    return myState;
	}
	
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		LogUtils.printInfo(TAG, "--------onRestoreInstanceState-----------");
	    // Check whether we saved the state in onSaveInstanceState
	    if (state == null || !state.getClass().equals(SavedState.class)) {
	        // Didn't save the state, so call superclass
	        super.onRestoreInstanceState(state);
	        return;
	    }

	    // Cast state to custom BaseSavedState and pass to superclass
	    SavedState myState = (SavedState) state;
	    super.onRestoreInstanceState(myState.getSuperState());
	    
	    // Set this Preference's widget to reflect the restored state
	    setValue(myState.value);
	    System.out.println("------onRestoreInstanceState:::" + myState.value);//本例中上面直接return了
	}
	
	private void setValue(long value) {
		Date d = new Date(value);
		mPicker.setCurrentHour(d.getHours());
		mPicker.setCurrentMinute(d.getMinutes());
	}
}
