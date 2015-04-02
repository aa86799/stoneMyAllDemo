package com.stone.view;

import java.util.ArrayList;

import android.animation.LayoutTransition;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewDebug.IntToString;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LayoutAnimationController;
import android.view.animation.Transformation;

public class CustomeView3 extends ViewGroup {

	public CustomeView3(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub

	}

	@Override
	@ExportedProperty(category = "focus", mapping = {
			@IntToString(from = 131072, to = "FOCUS_BEFORE_DESCENDANTS"),
			@IntToString(from = 262144, to = "FOCUS_AFTER_DESCENDANTS"),
			@IntToString(from = 393216, to = "FOCUS_BLOCK_DESCENDANTS") })
	public int getDescendantFocusability() {
		// TODO Auto-generated method stub
		return super.getDescendantFocusability();
	}

	@Override
	public void setDescendantFocusability(int focusability) {
		// TODO Auto-generated method stub
		super.setDescendantFocusability(focusability);
	}

	@Override
	public void requestChildFocus(View child, View focused) {
		// TODO Auto-generated method stub
		super.requestChildFocus(child, focused);
	}

	@Override
	public void focusableViewAvailable(View v) {
		// TODO Auto-generated method stub
		super.focusableViewAvailable(v);
	}

	@Override
	public boolean showContextMenuForChild(View originalView) {
		// TODO Auto-generated method stub
		return super.showContextMenuForChild(originalView);
	}

	@Override
	public ActionMode startActionModeForChild(View originalView,
			Callback callback) {
		// TODO Auto-generated method stub
		return super.startActionModeForChild(originalView, callback);
	}

	@Override
	public View focusSearch(View focused, int direction) {
		// TODO Auto-generated method stub
		return super.focusSearch(focused, direction);
	}

	@Override
	public boolean requestChildRectangleOnScreen(View child, Rect rectangle,
			boolean immediate) {
		// TODO Auto-generated method stub
		return super.requestChildRectangleOnScreen(child, rectangle, immediate);
	}

	@Override
	public boolean requestSendAccessibilityEvent(View child,
			AccessibilityEvent event) {
		// TODO Auto-generated method stub
		return super.requestSendAccessibilityEvent(child, event);
	}

	@Override
	public boolean onRequestSendAccessibilityEvent(View child,
			AccessibilityEvent event) {
		// TODO Auto-generated method stub
		return super.onRequestSendAccessibilityEvent(child, event);
	}

	@Override
	public void childHasTransientStateChanged(View child,
			boolean childHasTransientState) {
		// TODO Auto-generated method stub
		super.childHasTransientStateChanged(child, childHasTransientState);
	}
	
	@Override
	public boolean hasTransientState() {
		// TODO Auto-generated method stub
		return super.hasTransientState();
	}

	@Override
	public boolean dispatchUnhandledMove(View focused, int direction) {
		// TODO Auto-generated method stub
		return super.dispatchUnhandledMove(focused, direction);
	}

	@Override
	public void clearChildFocus(View child) {
		// TODO Auto-generated method stub
		super.clearChildFocus(child);
	}

	@Override
	public void clearFocus() {
		// TODO Auto-generated method stub
		super.clearFocus();
	}

	@Override
	public View getFocusedChild() {
		// TODO Auto-generated method stub
		return super.getFocusedChild();
	}

	@Override
	public boolean hasFocus() {
		// TODO Auto-generated method stub
		return super.hasFocus();
	}

	@Override
	public View findFocus() {
		// TODO Auto-generated method stub
		return super.findFocus();
	}

	@Override
	public boolean hasFocusable() {
		// TODO Auto-generated method stub
		return super.hasFocusable();
	}

	@Override
	public void addFocusables(ArrayList<View> views, int direction,
			int focusableMode) {
		// TODO Auto-generated method stub
		super.addFocusables(views, direction, focusableMode);
	}

	@Override
	public void findViewsWithText(ArrayList<View> outViews, CharSequence text,
			int flags) {
		// TODO Auto-generated method stub
		super.findViewsWithText(outViews, text, flags);
	}

	@Override
	public void dispatchWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.dispatchWindowFocusChanged(hasFocus);
	}

	@Override
	public void addTouchables(ArrayList<View> views) {
		// TODO Auto-generated method stub
		super.addTouchables(views);
	}

	@Override
	public void dispatchDisplayHint(int hint) {
		// TODO Auto-generated method stub
		super.dispatchDisplayHint(hint);
	}

	@Override
	protected void dispatchVisibilityChanged(View changedView, int visibility) {
		// TODO Auto-generated method stub
		super.dispatchVisibilityChanged(changedView, visibility);
	}

	@Override
	public void dispatchWindowVisibilityChanged(int visibility) {
		// TODO Auto-generated method stub
		super.dispatchWindowVisibilityChanged(visibility);
	}

	@Override
	public void dispatchConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.dispatchConfigurationChanged(newConfig);
	}

	@Override
	public void recomputeViewAttributes(View child) {
		// TODO Auto-generated method stub
		super.recomputeViewAttributes(child);
	}

	@Override
	public void bringChildToFront(View child) {
		// TODO Auto-generated method stub
		super.bringChildToFront(child);
	}

	@Override
	public boolean dispatchDragEvent(DragEvent event) {
		// TODO Auto-generated method stub
		return super.dispatchDragEvent(event);
	}

	@Override
	public void dispatchWindowSystemUiVisiblityChanged(int visible) {
		// TODO Auto-generated method stub
		super.dispatchWindowSystemUiVisiblityChanged(visible);
	}

	@Override
	public void dispatchSystemUiVisibilityChanged(int visible) {
		// TODO Auto-generated method stub
		super.dispatchSystemUiVisibilityChanged(visible);
	}

	@Override
	public boolean dispatchKeyEventPreIme(KeyEvent event) {
		// TODO Auto-generated method stub
		return super.dispatchKeyEventPreIme(event);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		return super.dispatchKeyEvent(event);
	}

	@Override
	public boolean dispatchKeyShortcutEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		return super.dispatchKeyShortcutEvent(event);
	}

	@Override
	public boolean dispatchTrackballEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.dispatchTrackballEvent(event);
	}

	@Override
	protected boolean dispatchHoverEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.dispatchHoverEvent(event);
	}

	@Override
	public void addChildrenForAccessibility(
			ArrayList<View> childrenForAccessibility) {
		// TODO Auto-generated method stub
		super.addChildrenForAccessibility(childrenForAccessibility);
	}

	@Override
	public boolean onInterceptHoverEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onInterceptHoverEvent(event);
	}

	@Override
	protected boolean dispatchGenericPointerEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.dispatchGenericPointerEvent(event);
	}

	@Override
	protected boolean dispatchGenericFocusedEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.dispatchGenericFocusedEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public void setMotionEventSplittingEnabled(boolean split) {
		// TODO Auto-generated method stub
		super.setMotionEventSplittingEnabled(split);
	}

	@Override
	public boolean isMotionEventSplittingEnabled() {
		// TODO Auto-generated method stub
		return super.isMotionEventSplittingEnabled();
	}

	@Override
	public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
		// TODO Auto-generated method stub
		super.requestDisallowInterceptTouchEvent(disallowIntercept);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		return super.requestFocus(direction, previouslyFocusedRect);
	}

	@Override
	protected boolean onRequestFocusInDescendants(int direction,
			Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);
	}

	@Override
	public void notifySubtreeAccessibilityStateChanged(View child, View source,
			int changeType) {
		// TODO Auto-generated method stub
		super.notifySubtreeAccessibilityStateChanged(child, source, changeType);
	}

	@Override
	protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
		// TODO Auto-generated method stub
		super.dispatchSaveInstanceState(container);
	}

	@Override
	protected void dispatchFreezeSelfOnly(SparseArray<Parcelable> container) {
		// TODO Auto-generated method stub
		super.dispatchFreezeSelfOnly(container);
	}

	@Override
	protected void dispatchRestoreInstanceState(
			SparseArray<Parcelable> container) {
		// TODO Auto-generated method stub
		super.dispatchRestoreInstanceState(container);
	}

	@Override
	protected void dispatchThawSelfOnly(SparseArray<Parcelable> container) {
		// TODO Auto-generated method stub
		super.dispatchThawSelfOnly(container);
	}

	@Override
	protected void setChildrenDrawingCacheEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		super.setChildrenDrawingCacheEnabled(enabled);
	}

	@Override
	protected void onAnimationStart() {
		// TODO Auto-generated method stub
		super.onAnimationStart();
	}

	@Override
	protected void onAnimationEnd() {
		// TODO Auto-generated method stub
		super.onAnimationEnd();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.dispatchDraw(canvas);
	}

	@Override
	public ViewGroupOverlay getOverlay() {
		// TODO Auto-generated method stub
		return super.getOverlay();
	}

	@Override
	protected int getChildDrawingOrder(int childCount, int i) {
		// TODO Auto-generated method stub
		return super.getChildDrawingOrder(childCount, i);
	}

	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		// TODO Auto-generated method stub
		return super.drawChild(canvas, child, drawingTime);
	}

	@Override
	public boolean getClipChildren() {
		// TODO Auto-generated method stub
		return super.getClipChildren();
	}

	@Override
	public void setClipChildren(boolean clipChildren) {
		// TODO Auto-generated method stub
		super.setClipChildren(clipChildren);
	}

	@Override
	public void setClipToPadding(boolean clipToPadding) {
		// TODO Auto-generated method stub
		super.setClipToPadding(clipToPadding);
	}

	@Override
	public void dispatchSetSelected(boolean selected) {
		// TODO Auto-generated method stub
		super.dispatchSetSelected(selected);
	}

	@Override
	public void dispatchSetActivated(boolean activated) {
		// TODO Auto-generated method stub
		super.dispatchSetActivated(activated);
	}

	@Override
	protected void dispatchSetPressed(boolean pressed) {
		// TODO Auto-generated method stub
		super.dispatchSetPressed(pressed);
	}

	@Override
	protected void setStaticTransformationsEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		super.setStaticTransformationsEnabled(enabled);
	}

	@Override
	protected boolean getChildStaticTransformation(View child, Transformation t) {
		// TODO Auto-generated method stub
		return super.getChildStaticTransformation(child, t);
	}

	@Override
	public void addView(View child) {
		// TODO Auto-generated method stub
		super.addView(child);
	}

	@Override
	public void addView(View child, int index) {
		// TODO Auto-generated method stub
		super.addView(child, index);
	}

	@Override
	public void addView(View child, int width, int height) {
		// TODO Auto-generated method stub
		super.addView(child, width, height);
	}

	@Override
	public void addView(View child, LayoutParams params) {
		// TODO Auto-generated method stub
		super.addView(child, params);
	}

	@Override
	public void addView(View child, int index, LayoutParams params) {
		// TODO Auto-generated method stub
		super.addView(child, index, params);
	}

	@Override
	public void updateViewLayout(View view, LayoutParams params) {
		// TODO Auto-generated method stub
		super.updateViewLayout(view, params);
	}

	@Override
	protected boolean checkLayoutParams(LayoutParams p) {
		// TODO Auto-generated method stub
		return super.checkLayoutParams(p);
	}

	@Override
	public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
		// TODO Auto-generated method stub
		super.setOnHierarchyChangeListener(listener);
	}

	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
	}

	@Override
	protected boolean addViewInLayout(View child, int index, LayoutParams params) {
		// TODO Auto-generated method stub
		return super.addViewInLayout(child, index, params);
	}

	@Override
	protected boolean addViewInLayout(View child, int index,
			LayoutParams params, boolean preventRequestLayout) {
		// TODO Auto-generated method stub
		return super.addViewInLayout(child, index, params, preventRequestLayout);
	}

	@Override
	protected void cleanupLayoutState(View child) {
		// TODO Auto-generated method stub
		super.cleanupLayoutState(child);
	}

	@Override
	protected void attachLayoutAnimationParameters(View child,
			LayoutParams params, int index, int count) {
		// TODO Auto-generated method stub
		super.attachLayoutAnimationParameters(child, params, index, count);
	}

	@Override
	public void removeView(View view) {
		// TODO Auto-generated method stub
		super.removeView(view);
	}

	@Override
	public void removeViewInLayout(View view) {
		// TODO Auto-generated method stub
		super.removeViewInLayout(view);
	}

	@Override
	public void removeViewsInLayout(int start, int count) {
		// TODO Auto-generated method stub
		super.removeViewsInLayout(start, count);
	}

	@Override
	public void removeViewAt(int index) {
		// TODO Auto-generated method stub
		super.removeViewAt(index);
	}

	@Override
	public void removeViews(int start, int count) {
		// TODO Auto-generated method stub
		super.removeViews(start, count);
	}

	@Override
	public void setLayoutTransition(LayoutTransition transition) {
		// TODO Auto-generated method stub
		super.setLayoutTransition(transition);
	}

	@Override
	public LayoutTransition getLayoutTransition() {
		// TODO Auto-generated method stub
		return super.getLayoutTransition();
	}

	@Override
	public void removeAllViews() {
		// TODO Auto-generated method stub
		super.removeAllViews();
	}

	@Override
	public void removeAllViewsInLayout() {
		// TODO Auto-generated method stub
		super.removeAllViewsInLayout();
	}

	@Override
	protected void removeDetachedView(View child, boolean animate) {
		// TODO Auto-generated method stub
		super.removeDetachedView(child, animate);
	}

	@Override
	protected void attachViewToParent(View child, int index, LayoutParams params) {
		// TODO Auto-generated method stub
		super.attachViewToParent(child, index, params);
	}

	@Override
	protected void detachViewFromParent(View child) {
		// TODO Auto-generated method stub
		super.detachViewFromParent(child);
	}

	@Override
	protected void detachViewFromParent(int index) {
		// TODO Auto-generated method stub
		super.detachViewFromParent(index);
	}

	@Override
	protected void detachViewsFromParent(int start, int count) {
		// TODO Auto-generated method stub
		super.detachViewsFromParent(start, count);
	}

	@Override
	protected void detachAllViewsFromParent() {
		// TODO Auto-generated method stub
		super.detachAllViewsFromParent();
	}

	@Override
	public ViewParent invalidateChildInParent(int[] location, Rect dirty) {
		// TODO Auto-generated method stub
		return super.invalidateChildInParent(location, dirty);
	}

	@Override
	public boolean getChildVisibleRect(View child, Rect r, Point offset) {
		// TODO Auto-generated method stub
		return super.getChildVisibleRect(child, r, offset);
	}

	@Override
	protected boolean canAnimate() {
		// TODO Auto-generated method stub
		return super.canAnimate();
	}

	@Override
	public void startLayoutAnimation() {
		// TODO Auto-generated method stub
		super.startLayoutAnimation();
	}

	@Override
	public void scheduleLayoutAnimation() {
		// TODO Auto-generated method stub
		super.scheduleLayoutAnimation();
	}

	@Override
	public void setLayoutAnimation(LayoutAnimationController controller) {
		// TODO Auto-generated method stub
		super.setLayoutAnimation(controller);
	}

	@Override
	public LayoutAnimationController getLayoutAnimation() {
		// TODO Auto-generated method stub
		return super.getLayoutAnimation();
	}

	@Override
	@ExportedProperty
	public boolean isAnimationCacheEnabled() {
		// TODO Auto-generated method stub
		return super.isAnimationCacheEnabled();
	}

	@Override
	public void setAnimationCacheEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		super.setAnimationCacheEnabled(enabled);
	}

	@Override
	@ExportedProperty(category = "drawing")
	public boolean isAlwaysDrawnWithCacheEnabled() {
		// TODO Auto-generated method stub
		return super.isAlwaysDrawnWithCacheEnabled();
	}

	@Override
	public void setAlwaysDrawnWithCacheEnabled(boolean always) {
		// TODO Auto-generated method stub
		super.setAlwaysDrawnWithCacheEnabled(always);
	}

	@Override
	@ExportedProperty(category = "drawing")
	protected boolean isChildrenDrawnWithCacheEnabled() {
		// TODO Auto-generated method stub
		return super.isChildrenDrawnWithCacheEnabled();
	}

	@Override
	protected void setChildrenDrawnWithCacheEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		super.setChildrenDrawnWithCacheEnabled(enabled);
	}

	@Override
	@ExportedProperty(category = "drawing")
	protected boolean isChildrenDrawingOrderEnabled() {
		// TODO Auto-generated method stub
		return super.isChildrenDrawingOrderEnabled();
	}

	@Override
	protected void setChildrenDrawingOrderEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		super.setChildrenDrawingOrderEnabled(enabled);
	}

	@Override
	@ExportedProperty(category = "drawing", mapping = {
			@IntToString(from = 0, to = "NONE"),
			@IntToString(from = 1, to = "ANIMATION"),
			@IntToString(from = 2, to = "SCROLLING"),
			@IntToString(from = 3, to = "ALL") })
	public int getPersistentDrawingCache() {
		// TODO Auto-generated method stub
		return super.getPersistentDrawingCache();
	}

	@Override
	public void setPersistentDrawingCache(int drawingCacheToKeep) {
		// TODO Auto-generated method stub
		super.setPersistentDrawingCache(drawingCacheToKeep);
	}

	@Override
	public int getLayoutMode() {
		// TODO Auto-generated method stub
		return super.getLayoutMode();
	}

	@Override
	public void setLayoutMode(int layoutMode) {
		// TODO Auto-generated method stub
		super.setLayoutMode(layoutMode);
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		// TODO Auto-generated method stub
		return super.generateLayoutParams(attrs);
	}

	@Override
	protected LayoutParams generateLayoutParams(LayoutParams p) {
		// TODO Auto-generated method stub
		return super.generateLayoutParams(p);
	}

	@Override
	protected LayoutParams generateDefaultLayoutParams() {
		// TODO Auto-generated method stub
		return super.generateDefaultLayoutParams();
	}

	@Override
	protected void debug(int depth) {
		// TODO Auto-generated method stub
		super.debug(depth);
	}

	@Override
	public int indexOfChild(View child) {
		// TODO Auto-generated method stub
		return super.indexOfChild(child);
	}

	@Override
	public int getChildCount() {
		// TODO Auto-generated method stub
		return super.getChildCount();
	}

	@Override
	public View getChildAt(int index) {
		// TODO Auto-generated method stub
		return super.getChildAt(index);
	}

	@Override
	protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.measureChildren(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void measureChild(View child, int parentWidthMeasureSpec,
			int parentHeightMeasureSpec) {
		// TODO Auto-generated method stub
		super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
	}

	@Override
	protected void measureChildWithMargins(View child,
			int parentWidthMeasureSpec, int widthUsed,
			int parentHeightMeasureSpec, int heightUsed) {
		// TODO Auto-generated method stub
		super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed,
				parentHeightMeasureSpec, heightUsed);
	}

	@Override
	public void clearDisappearingChildren() {
		// TODO Auto-generated method stub
		super.clearDisappearingChildren();
	}

	@Override
	public void startViewTransition(View view) {
		// TODO Auto-generated method stub
		super.startViewTransition(view);
	}

	@Override
	public void endViewTransition(View view) {
		// TODO Auto-generated method stub
		super.endViewTransition(view);
	}

	@Override
	public boolean gatherTransparentRegion(Region region) {
		// TODO Auto-generated method stub
		return super.gatherTransparentRegion(region);
	}

	@Override
	public void requestTransparentRegion(View child) {
		// TODO Auto-generated method stub
		super.requestTransparentRegion(child);
	}

	@Override
	protected boolean fitSystemWindows(Rect insets) {
		// TODO Auto-generated method stub
		return super.fitSystemWindows(insets);
	}

	@Override
	public AnimationListener getLayoutAnimationListener() {
		// TODO Auto-generated method stub
		return super.getLayoutAnimationListener();
	}

	@Override
	protected void drawableStateChanged() {
		// TODO Auto-generated method stub
		super.drawableStateChanged();
	}

	@Override
	public void jumpDrawablesToCurrentState() {
		// TODO Auto-generated method stub
		super.jumpDrawablesToCurrentState();
	}

	@Override
	protected int[] onCreateDrawableState(int extraSpace) {
		// TODO Auto-generated method stub
		return super.onCreateDrawableState(extraSpace);
	}

	@Override
	public void setAddStatesFromChildren(boolean addsStates) {
		// TODO Auto-generated method stub
		super.setAddStatesFromChildren(addsStates);
	}

	@Override
	public boolean addStatesFromChildren() {
		// TODO Auto-generated method stub
		return super.addStatesFromChildren();
	}

	@Override
	public void childDrawableStateChanged(View child) {
		// TODO Auto-generated method stub
		super.childDrawableStateChanged(child);
	}

	@Override
	public void setLayoutAnimationListener(AnimationListener animationListener) {
		// TODO Auto-generated method stub
		super.setLayoutAnimationListener(animationListener);
	}

	@Override
	public boolean shouldDelayChildPressedState() {
		// TODO Auto-generated method stub
		return super.shouldDelayChildPressedState();
	}
	
	
	

}
