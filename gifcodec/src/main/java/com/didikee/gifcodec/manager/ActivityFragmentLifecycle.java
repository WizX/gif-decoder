package com.didikee.gifcodec.manager;

import com.didikee.gifcodec.manager.interfaces.Lifecycle;
import com.didikee.gifcodec.manager.interfaces.LifecycleListener;
import com.didikee.gifcodec.util.Util;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;


/**
 * Created by Yangyl on 2017/3/7.
 */

public class ActivityFragmentLifecycle implements Lifecycle {

    private Set<LifecycleListener> lifecycleListeners =
            Collections.newSetFromMap(new WeakHashMap<LifecycleListener, Boolean>());

    private boolean isStarted;
    private boolean isDestroyed;
    /**
     * Adds the given listener to the set of listeners managed by this Lifecycle implementation.
     *
     * @param listener
     */
    @Override
    public void addListener(LifecycleListener listener) {
        lifecycleListeners.add(listener);
        if (isDestroyed){
            listener.onDestroy();
        }
        if (isStarted){
            listener.onStart();
        }else {
            listener.onStop();
        }
    }

    public void onStart(){
        isStarted = true;
        for (LifecycleListener listener : Util.getSnapshot(lifecycleListeners)){
            listener.onStart();
        }

    }

    public void onStop(){
        isStarted = false;
        for (LifecycleListener listener : Util.getSnapshot(lifecycleListeners)){
            listener.onStop();
        }
    }

    public void onDestroy(){
        isDestroyed = true;
        for (LifecycleListener listener : Util.getSnapshot(lifecycleListeners)){
            listener.onDestroy();
        }
    }
}
