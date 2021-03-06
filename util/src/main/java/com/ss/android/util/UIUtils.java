package com.ss.android.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;



import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.ViewCompat;

public class UIUtils {

    public static final int LAYOUT_PARAMS_KEEP_OLD = -3;

    public static final char ELLIPSIS_CHAR = '\u2026';

    public static float sp2px(Context context, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static float sp2px(Context context, int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static float dip2Px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dipValue * scale + 0.5f;
    }

    public static float dip2Px(Context context, int dipValue) {
        return dip2Px(context, (float) dipValue);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int setColorAlpha(int color, int alpha) {
        if (alpha > 0xff) {
            alpha = 0xff;
        } else if (alpha < 0) {
            alpha = 0;
        }
        return (color & 0xffffff) | (alpha * 0x1000000);
    }

    public static int[] getLocationInAncestor(View child, View ancestor) {
        if (child == null || ancestor == null) {
            return null;
        }
        int[] location = new int[2];
        float[] position = new float[2];
        position[0] = position[1] = 0.0f;

        position[0] += child.getLeft();
        position[1] += child.getTop();

        boolean matched = false;
        ViewParent viewParent = child.getParent();
        while (viewParent instanceof View) {
            final View view = (View) viewParent;
            if (viewParent == ancestor) {
                matched = true;
                break;
            }
            position[0] -= view.getScrollX();
            position[1] -= view.getScrollY();

            position[0] += view.getLeft();
            position[1] += view.getTop();

            viewParent = view.getParent();
        }
        if (!matched) {
            return null;
        }
        location[0] = (int) (position[0] + 0.5f);
        location[1] = (int) (position[1] + 0.5f);
        return location;
    }

    /**
     * get location of view relative to given upView. get center location if
     * getCenter is true.
     */
    public static void getLocationInUpView(View upView, View view, int[] loc, boolean getCenter) {
        if (upView == null || view == null || loc == null || loc.length < 2) {
            return;
        }
        upView.getLocationInWindow(loc);
        int x1 = loc[0];
        int y1 = loc[1];
        view.getLocationInWindow(loc);
        int x2 = loc[0] - x1;
        int y2 = loc[1] - y1;
        if (getCenter) {
            int w = view.getWidth();
            int h = view.getHeight();
            x2 = x2 + w / 2;
            y2 = y2 + h / 2;
        }
        loc[0] = x2;
        loc[1] = y2;
    }

    public static void updateLayout(View view, int w, int h) {
        if (view == null)
            return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null || (params.width == w && params.height == h))
            return;
        if (w != LAYOUT_PARAMS_KEEP_OLD)
            params.width = w;
        if (h != LAYOUT_PARAMS_KEEP_OLD)
            params.height = h;
        view.setLayoutParams(params);
    }

    public static void updateLayoutMargin(View view, int l, int t, int r, int b) {
        if (view == null)
            return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null)
            return;
        if (params instanceof ViewGroup.MarginLayoutParams) {
            updateMargin(view, (ViewGroup.MarginLayoutParams) params, l, t, r, b);
        }
    }

    private static void updateMargin(View view, ViewGroup.MarginLayoutParams params, int l, int t, int r, int b) {
        if (view == null
                || params == null
                || (params.leftMargin == l && params.topMargin == t && params.rightMargin == r && params.bottomMargin == b))
            return;
        if (l != LAYOUT_PARAMS_KEEP_OLD)
            params.leftMargin = l;
        if (t != LAYOUT_PARAMS_KEEP_OLD)
            params.topMargin = t;
        if (r != LAYOUT_PARAMS_KEEP_OLD)
            params.rightMargin = r;
        if (b != LAYOUT_PARAMS_KEEP_OLD)
            params.bottomMargin = b;
        view.setLayoutParams(params);
    }

    /***
     * 如果传入 {@link Integer#MIN_VALUE} ，那么传入的值将会被忽略
     */
    public static void setLayoutParams(View view, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            return;
        }
        // ViewGroup.LayoutParams.FILL_PARENT 的值小于 0，所以不能简单的 if(width > 0)
        if (width != Integer.MIN_VALUE) {
            params.width = width;
        }
        if (height != Integer.MIN_VALUE) {
            params.height = height;
        }
    }

    public static void setTxtAndAdjustVisible(TextView tv, CharSequence txt) {
        if (tv == null) {
            return;
        }
        if (TextUtils.isEmpty(txt)) {
            setViewVisibility(tv, View.GONE);
        } else {
            setViewVisibility(tv, View.VISIBLE);
            tv.setText(txt);
        }
    }


    private static boolean visibilityValid(int visiable) {
        return visiable == View.VISIBLE || visiable == View.GONE || visiable == View.INVISIBLE;
    }

    public static void setViewVisibility(View v, int visiable) {
        if (v == null || !visibilityValid(visiable) || v.getVisibility() == visiable) {
            return;
        }
        v.setVisibility(visiable);
    }

    /**
     * @param visible true -- VISIBLE; false -- GONE
     **/
    public static void setViewVisibility(@Nullable View v, boolean visible) {
        setViewVisibility(v, visible ? View.VISIBLE : View.GONE);
    }

    public static int getScreenWidth(Context context) {
        if (context == null) {
            return 0;
        }

        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        return (dm == null) ? 0 : dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        if (context == null) {
            return 0;
        }

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int height = (dm == null) ? 0 : dm.heightPixels;

//        if (checkDeviceHasNavigationBar(context)) {
//            height = height - getNavigationBarHeight(context);
//        }
        return height;
    }

    public static int getStatusBarHeight(Context context) {
        if (context == null) {
            return 0;
        }
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * DO NOT use this method because it does nothing but sets a background
     */
    @Deprecated
    public static void setViewBackgroundWithPadding(View view, int resid) {
        if (view != null) {
            int left = view.getPaddingLeft();
            int right = view.getPaddingRight();
            int top = view.getPaddingTop();
            int bottom = view.getPaddingBottom();
            view.setBackgroundResource(resid);
            view.setPadding(left, top, right, bottom);
        }
    }

    /**
     * DO NOT use this method because it does nothing but sets a background
     */
    @Deprecated
    public static void setViewBackgroundWithPadding(View view, Resources res, int colorid) {
        if (view != null && res != null) {
            int left = view.getPaddingLeft();
            int right = view.getPaddingRight();
            int top = view.getPaddingTop();
            int bottom = view.getPaddingBottom();
            view.setBackgroundColor(res.getColor(colorid));
            view.setPadding(left, top, right, bottom);
        }
    }

    /**
     * DO NOT use this method because it does nothing but sets a background
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public static void setViewBackgroundWithPadding(View view, Drawable drawable) {
        if (view == null) {
            return;
        }
        int left = view.getPaddingLeft();
        int right = view.getPaddingRight();
        int top = view.getPaddingTop();
        int bottom = view.getPaddingBottom();
        view.setBackgroundDrawable(drawable);
        view.setPadding(left, top, right, bottom);
    }

    /**
     * DO NOT use this method because it does nothing but sets a background
     */
    @Deprecated
    public static void setViewBackgroundWithPadding(View view, int resid, int left, int top, int right, int bottom) {
        if (view == null)
            return;
        view.setBackgroundResource(resid);
        view.setPadding(left, top, right, bottom);
    }

    public static boolean isLayoutRtl(View v) {
        return ViewCompat.getLayoutDirection(v) == ViewCompat.LAYOUT_DIRECTION_RTL;
    }


    public static void setViewBackground(View view, Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            try {
                view.setBackground(background);
            } catch (Throwable tr) {
                view.setBackgroundDrawable(background);
            }
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    public static void setImageViewBitmap(ImageView imageView, Bitmap bitmap) {
        if (null == imageView || null == bitmap) {
            return;
        }
        imageView.setImageBitmap(bitmap);
    }

    public static void setBackgroundResource(View view, int resId) {
        if (null == view || resId < 0) {
            return;
        }
        view.setBackgroundResource(resId);
    }

    public static void setVisibility(View view, int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
        }
    }


    public static void setOnClickable(View view, boolean clickable) {
        if (view != null) {
            view.setClickable(clickable);
        }
    }

    public static class DayNightAlertDlgBuilder extends AlertDialog.Builder {

        public DayNightAlertDlgBuilder(Context context) {
            super(context, R.style.Widget_AppCompat_Light_ActionBar);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    static class HoloProgressDialogBuilder {
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public static ProgressDialog buildProgressDialog(Context context, boolean nightMode) {
            return new ProgressDialog(context,
                    nightMode ? ProgressDialog.THEME_HOLO_DARK : ProgressDialog.THEME_HOLO_LIGHT);
        }
    }

    public static ProgressDialog getThemedProgressDialog(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return HoloProgressDialogBuilder.buildProgressDialog(context, false);
        } else {
            return new ProgressDialog(context);
        }
    }

    public static AlertDialog.Builder getThemedAlertDlgBuilder(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return new DayNightAlertDlgBuilder(context);
        } else {
            return new AlertDialog.Builder(context);
        }
    }

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        if (Build.VERSION.SDK_INT < 17) {
            for (; ; ) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }
    }

    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public static void setTextAppearance(Context context, TextView text, int resId) {
        if (text != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                text.setTextAppearance(resId);
            } else if (context != null) {
                text.setTextAppearance(context, resId);
            }
        }
    }

    /**
     * Common getDrawable method, avoid crash in lollipop devices.
     * Use this method if you are not sure that the resource can be found.
     *
     * @param resources
     * @param drawableResId
     * @return
     */
    public static Drawable getDrawable(Resources resources, @DrawableRes int drawableResId) {
        if (resources == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return resources.getDrawable(drawableResId, null);
        }
        return resources.getDrawable(drawableResId);
    }

    /**
     * try get Activity by bubble up through base context.
     *
     * @param v
     * @return
     */
    public static Activity getActivity(View v) {
        Context c = v != null ? v.getContext() : null;
        while (c != null) {
            if (c instanceof Activity) {
                return (Activity) c;
            } else if (c instanceof ContextWrapper) {
                c = ((ContextWrapper) c).getBaseContext();
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * Check whether screen's orientation current is landScape
     **/
    public static boolean isScreenLandScape(Context context) {
        int orientation = 0;
        if (null != context) {
            orientation = context.getResources().getConfiguration().orientation;
        }
        return orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 获取屏幕比例
     **/
    public static float getScreenScale(Context context) {
        if (null == context) {
            return 1;
        }

        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        float scale = dm.widthPixels / (dm.density * 360);
        return (dm == null) ? 1 : scale;
    }

    public static void expandViewTouchDelegate(final View view, final int left, final int top,
                                               final int bottom, final int right) {

        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                view.setEnabled(true);
                view.getHitRect(bounds);

                bounds.top -= top;
                bounds.bottom += bottom;
                bounds.left -= left;
                bounds.right += right;

                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }

    public static boolean clearAnimation(View view) {
        if (view == null || view.getAnimation() == null) {
            return false;
        }
        view.clearAnimation();
        return true;
    }

    public static EllipsisMeasureResult sTempEllipsisResult = new EllipsisMeasureResult();

    public static class EllipsisMeasureResult {
        public String ellipsisStr;
        public int length;
    }

    public static void ellipseSingleLineStr(String str, final int maxLength, Paint paint, int ellipsisLength, EllipsisMeasureResult out) {
        if (maxLength <= ellipsisLength || str == null || str.isEmpty()) {
            out.ellipsisStr = "";
            out.length = 0;
            return;
        }
        int length = (int) ((paint.measureText(str)) + 0.999f);
        if (length <= maxLength) {
            out.ellipsisStr = str;
            out.length = length;
            return;
        }
        int maxLengthLeft = maxLength - ellipsisLength;
        StringBuilder sb = new StringBuilder();
        int end = paint.breakText(str, 0, str.length(), true, maxLengthLeft, null);
        if (end < 1) {
            out.ellipsisStr = "";
            out.length = 0;
            return;
        }
        sb.append(str.substring(0, end));
        sb.append(ELLIPSIS_CHAR);
        out.ellipsisStr = sb.toString();
        out.length = maxLength;
    }

    public static void setOnTouchBackground(View v) {
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setAlpha(0.6f);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.setAlpha(1.0f);
                        break;
                }
                return false;
            }
        });
    }

    public final static boolean isViewVisible(View view) {
        if (view == null) {
            return false;
        }

        return view.getVisibility() == View.VISIBLE;
    }

    public static void setTextMarquee(TextView textView) {
        if (textView != null) {
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.setSingleLine(true);
            textView.setSelected(true);
            textView.setFocusable(true);
            textView.setFocusableInTouchMode(true);
        }
    }

    public static void setTextViewText(TextView textView, String text) {
        if (text == null || textView == null) {
            return;
        }
        textView.setText(text);
    }

    public static void setOnClickListener(@Nullable View view, @Nullable View.OnClickListener listener) {
        if (null == view || null == listener) {
            return;
        }
        view.setOnClickListener(listener);
    }

    public static void logViewSize(final View view) {
        if (null == view) {
            return;
        }

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });

    }

}
