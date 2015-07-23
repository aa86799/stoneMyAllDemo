package com.stone.ui;

import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.stone.R;
import com.stone.util.ImageUtil;

/**
 * 官方示例，actionbar、drawerlayout、 android.support.v4.app.ActionBarDrawerToggle
 * @author stone
 *
 */
public class DrawerLayoutActivity extends Activity {
	 private DrawerLayout mDrawerLayout;
	    private ListView mDrawerList;
	    private ActionBarDrawerToggle mDrawerToggle;

	    private CharSequence mDrawerTitle;
	    private CharSequence mTitle;
	    private String[] mPlanetTitles;

	int status = 0;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.drawer_layout);

	        mTitle = mDrawerTitle = getTitle();
	        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
	        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	        mDrawerList = (ListView) findViewById(R.id.left_drawer);//抽屉里的view

	        // set a custom shadow that overlays the main content when the drawer opens
	        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);//设置shadow
	        // set up the drawer's list view with items and click listener
	        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
	                R.layout.drawer_list_item, mPlanetTitles));
	        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

	        // enable ActionBar app icon to behave as action to toggle nav drawer   需要api level 11
	        getActionBar().setDisplayHomeAsUpEnabled(true);//给home icon的左边加上一个返回的图标
	        getActionBar().setHomeButtonEnabled(true); //需要api level 14  使用home-icon 可点击

	        // ActionBarDrawerToggle ties together the the proper interactions
	        // between the sliding drawer and the action bar app icon
	        mDrawerToggle = new ActionBarDrawerToggle(//v4控件  actionbar上的抽屉开关
	                this,                  /* host Activity */
	                mDrawerLayout,         /* DrawerLayout object */
	                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */		//上一级图标 返回图标
	                R.string.drawer_open,  /* "open drawer" description for accessibility */
	                R.string.drawer_close  /* "close drawer" description for accessibility */
	                ) {
	            public void onDrawerClosed(View view) {//抽屉关闭后
	                getActionBar().setTitle(mDrawerTitle);
	                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }

	            public void onDrawerOpened(View drawerView) {//抽屉打开后
	                getActionBar().setTitle(mTitle);
	                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }
	            
	            @Override
	            public boolean onOptionsItemSelected(MenuItem item) {
	            	if (item != null && item.getItemId() == android.R.id.home) {//actionbar上的home icon
	            		//END即gravity.right 从右向左显示   START即left  从左向右弹出显示
						if (status == 0) {
							if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
								mDrawerLayout.closeDrawer(GravityCompat.START);//关闭抽屉
							} else {
								mDrawerLayout.openDrawer(GravityCompat.START);//打开抽屉
							}
						} else {
							if (mDrawerLayout.isDrawerVisible(GravityCompat.END)) {
								mDrawerLayout.closeDrawer(GravityCompat.END);//关闭right抽屉
							} else {
								mDrawerLayout.openDrawer(GravityCompat.END);//打开抽屉
							}
						}

	                    return true;
	                }
	                return false;
	            }
	        };
	        mDrawerLayout.setDrawerListener(mDrawerToggle);//设置抽屉监听

	        if (savedInstanceState == null) {
//	            selectItem(0);
	        }
	    }

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {//加载menu  sdk3.0以后menu包含在actionbar中
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.drawerlayout, menu);
	        return super.onCreateOptionsMenu(menu);
	    }

	    /* Called whenever we call invalidateOptionsMenu() */
	    @Override
	    public boolean onPrepareOptionsMenu(Menu menu) {
	        // If the nav drawer is open, hide action items related to the content view
	        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
	        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);//search的显示与drawer的显示相反
	        menu.findItem(R.id.action_left).setVisible(!drawerOpen);
	        menu.findItem(R.id.action_right).setVisible(!drawerOpen);
	        return super.onPrepareOptionsMenu(menu);
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	         // The action bar home/up action should open or close the drawer.
	         // ActionBarDrawerToggle will take care of this.
	        if (mDrawerToggle.onOptionsItemSelected(item)) {
	            return true;
	        }
	        // Handle action buttons
	        switch(item.getItemId()) {
	       		 case R.id.action_websearch:
					// create intent to perform web search for this planet
					Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
					intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
					// catch event that there's no activity to handle intent
					if (intent.resolveActivity(getPackageManager()) != null) {
						startActivity(intent);
					} else {
						Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
					}
					return true;
				case R.id.action_left:
					status = 0;
					DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) mDrawerList.getLayoutParams();
					params.gravity = Gravity.LEFT;
					mDrawerList.setLayoutParams(params);
					mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);//设置shadow

					if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
						mDrawerLayout.closeDrawer(GravityCompat.START);//关闭right抽屉
					} else {
						mDrawerLayout.openDrawer(GravityCompat.START);//打开抽屉
					}
					return true;

				case R.id.action_right:
					status = 1;
					DrawerLayout.LayoutParams params2 = (DrawerLayout.LayoutParams) mDrawerList.getLayoutParams();
					params2.gravity = Gravity.RIGHT;
					mDrawerList.setLayoutParams(params2);
					mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.END);//设置shadow

					if (mDrawerLayout.isDrawerVisible(GravityCompat.END)) {
						mDrawerLayout.closeDrawer(GravityCompat.END);//关闭right抽屉
					} else {
						mDrawerLayout.openDrawer(GravityCompat.END);//打开抽屉
					}
					return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }

	    /* The click listner for ListView in the navigation drawer */
	    private class DrawerItemClickListener implements ListView.OnItemClickListener {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            selectItem(position);
	        }
	    }
	    
	  //内容区显示PlanetFragment
	    private void selectItem(int position) {
	        // update the main content by replacing fragments
	        Fragment fragment = new PlanetFragment();
	        Bundle args = new Bundle();
	        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
	        fragment.setArguments(args);

	        FragmentManager fragmentManager = getFragmentManager();
	        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

	        // update selected item and title, then close the drawer
//	        mDrawerList.setItemChecked(position, true);
//	        setTitle(mPlanetTitles[position]);
	        mDrawerLayout.closeDrawer(mDrawerList);
	    }

	    @Override
	    public void setTitle(CharSequence title) {
	    	mDrawerTitle = title;
	        getActionBar().setTitle(mDrawerTitle);
	    }

	    /**
	     * When using the ActionBarDrawerToggle, you must call it during
	     * onPostCreate() and onConfigurationChanged()...
	     */

	    @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
	        mDrawerToggle.syncState();
	    }

	    @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	        // Pass any configuration change to the drawer toggls
	        mDrawerToggle.onConfigurationChanged(newConfig);
	    }

	    /**
	     * Fragment that appears in the "content_frame", shows a planet
	     */
	    public static class PlanetFragment extends Fragment {
	        public static final String ARG_PLANET_NUMBER = "planet_number";

	        public PlanetFragment() {
	            // Empty constructor required for fragment subclasses
	        }

	        @Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                Bundle savedInstanceState) {
	            View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
	            int i = getArguments().getInt(ARG_PLANET_NUMBER);
	            String planet = getResources().getStringArray(R.array.planets_array)[i];
	            //查找出 res-drawable资源的id
	            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
	                            "drawable", getActivity().getPackageName());
	            
	            //测试imageutil 方法 start
	            Drawable drawable = getResources().getDrawable(imageId);
	            Bitmap drawableToBitmap = ImageUtil.drawableToBitmap(drawable);
	            Bitmap createReflectionImageWithOrigin = ImageUtil.createReflectionImageWithOrigin(drawableToBitmap);//倒影
	            ((ImageView) rootView.findViewById(R.id.image)).setImageBitmap(createReflectionImageWithOrigin);
	            //测试imageutil 方法 end
	            
//	            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
	            getActivity().setTitle(planet);
	            return rootView;
	        }
	    }
}
