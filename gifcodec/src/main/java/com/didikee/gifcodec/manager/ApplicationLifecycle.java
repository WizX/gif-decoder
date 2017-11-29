package com.didikee.gifcodec.manager;


import com.didikee.gifcodec.manager.interfaces.Lifecycle;
import com.didikee.gifcodec.manager.interfaces.LifecycleListener;

/**
 * Created by Yangyl on 2017/3/7.
 */

public class ApplicationLifecycle implements Lifecycle {
    /**
     * Adds the given listener to the set of listeners managed by this Lifecycle implementation.
     *
     * @param listener
     */
    @Override
    public void addListener(LifecycleListener listener) {
        listener.onStart();
    }
}
