package com.ss.android.glide;

import android.content.Context;
import android.widget.ImageView;

public interface IGlideContext {
    void loadImage(Context context, int width, int height, ImageView imageView, String url);
    void clearCache(Context context);
}
