package com.didikee.gifcodec;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.didikee.gifcodec.manager.RequestTracker;
import com.didikee.gifcodec.request.Request;
import com.didikee.gifcodec.util.Util;


/**
 * Created by Yangyl on 2017/3/4.
 */

public class RendTask implements Request {
    private GifDecodeInfoHandle mDecodeInfo;
    private RequestTracker requestTracker;
    private ImageView imageView;
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean isRunning = false;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
                imageView.setImageBitmap(mDecodeInfo.frame());
                handler.postDelayed(runnable, mDecodeInfo.delay());
        }
    };
    RendTask(String filePath, RequestTracker requestTracker){
        this.requestTracker = requestTracker;
        load(filePath);
    }

    private RendTask init(String file){
        if (file != null){
            mDecodeInfo = new GifDecodeInfoHandle();
            mDecodeInfo.load(file);
        }
        return this;
    }

    public Request into(ImageView imageView) {
        this.imageView = imageView;
        if (mDecodeInfo == null) {
            throw new IllegalArgumentException("Gif files decode failed!");
        } else if (imageView == null) {
            throw new IllegalArgumentException("ImageView can not be null!");
        } else {
            requestTracker.runRequest(this);
        }
        return this;
    }

    private void load(String file){
        String gifFormat = ".gif";
        StringBuilder fileFormat = new StringBuilder();
        for (int i = file.length() - 4;i < file.length();i ++){
            fileFormat.append(file.charAt(i));
        }

        if (fileFormat.toString().equals(gifFormat)) {

            init(file);
        }else {
            init(null);
        }
    }

    /**
     * Starts an asynchronous load.
     */
    @Override
    public void begin() {
        isRunning = true;
        handler.post(runnable);
        imageView.setVisibility(View.VISIBLE);
    }

    /**
     * Identical to {@link #clear()} except that the request may later be restarted.
     */
    @Override
    public void pause() {
        isRunning = false;
        handler.removeCallbacks(runnable);
    }

    /**
     * Prevents any bitmaps being loaded from previous requests, releases any resources held by this request,
     * displays the current placeholder if one was provided, and marks the request as having been cancelled.
     */
    @Override
    public void clear() {
        Util.assertMainThread();
        handler.removeCallbacks(runnable);
        imageView.setVisibility(View.INVISIBLE);
        if (Log.isLoggable("", Log.WARN)){
            Log.w("","rendtask destroy");
        }
//        mDecodeInfo.destroy();
    }

    /**
     * Returns true if this request is paused and may be restarted.
     */
    @Override
    public boolean isPaused() {
        return !isRunning();
    }

    /**
     * Returns true if this request is running and has not completed or failed.
     */
    @Override
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Returns true if the request has completed successfully.
     */
    @Override
    public boolean isComplete() {
        return false;
    }

    /**
     * Returns true if a non-placeholder resource is set. For Requests that load more than one resource, isResourceSet
     * may return true even if {@link #isComplete()}} returns false.
     */
    @Override
    public boolean isResourceSet() {
        return false;
    }

    /**
     * Returns true if the request has been cancelled.
     */
    @Override
    public boolean isCancelled() {
        return false;
    }

    /**
     * Returns true if the request has failed.
     */
    @Override
    public boolean isFailed() {
        return false;
    }

    /**
     * Recycles the request object and releases its resources.
     */
    @Override
    public void recycle() {
        clear();
        requestTracker.removeRequest(this);
        mDecodeInfo.destroy();
        mDecodeInfo     = null;
        imageView       = null;
        handler         = null;
        isRunning       = false;
        requestTracker  = null;
        runnable        = null;
        handler         = null;
    }
}
