package com.ss.android.glide;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ss.android.template.SingleInstance;

public class GlideClient implements IGlideContext {
    private static boolean mInit = false;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    private static SingleInstance<GlideClient> mSingleTon = new SingleInstance<GlideClient>() {
        @Override
        protected GlideClient create() {
            return new GlideClient();
        }
    };

    public static SingleInstance<GlideClient> getInstantce() {
        return mSingleTon;
    }

    private GlideClient() {

    }


    @Override
    public void loadImage(Context context, int width, int height, ImageView imageView, String url) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("can't update Ui in work thread");
        }

        Glide.with(context)
                .load(url)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(600, 600)
                .into(imageView);
    }

    @Override
    public void clearCache(Context context) {
        Glide.get(context).clearMemory();
        Glide.get(context).clearDiskCache();
    }

}
