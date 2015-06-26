package com.stone.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;

import com.stone.R;

public class ViewStubActivity extends Activity {
    /*
    在调用inflate后  才延迟加载layout属性所指向的layout， 以替换当前ViewStub
    inflate只能有一次，
     */
    ViewStub mViewStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stub);

        mViewStub = (ViewStub) findViewById(R.id.vs_stub);

    }

    View inflate;
    public void show(View view) {

        if (inflate == null)
            inflate = mViewStub.inflate();
        else
            inflate.setVisibility(View.VISIBLE);
    }

    public void hide(View view) {

        if (inflate == null)
            inflate = mViewStub.inflate();

        inflate.setVisibility(View.GONE);
    }

}
