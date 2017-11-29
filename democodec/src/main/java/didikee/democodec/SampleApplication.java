package didikee.democodec;

import android.app.Application;

/**
 * Created by Yangyl on 2017/3/7.
 */

public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        if (!LeakCanary.isInAnalyzerProcess(this)){
//            mWatcher = LeakCanary.install(this);
//        }
    }
}
