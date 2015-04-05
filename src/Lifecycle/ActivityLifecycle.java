package Lifecycle;

import com.intellij.psi.PsiMethod;

import java.util.*;

/**
 * Created by armand on 3/1/15.
 */
public class ActivityLifecycle extends Lifecycle {

    /**
     * Activity Lifecycle method names
     */
    private static final String ON_CREATE = "onCreate";
    private static final String ON_RESTART = "onRestart";
    private static final String ON_START = "onStart";
    private static final String ON_RESUME = "onResume";
    private static final String ON_PAUSE = "onPause";
    private static final String ON_STOP = "onStop";
    private static final String ON_DESTROY = "onDestroy";


    /**
     * The ordering of the Activity Lifecycle methods
     */
    private static final List<String> ACTIVITY_LIFECYCLE_METHODS = new ArrayList<String>();

    static {
        ACTIVITY_LIFECYCLE_METHODS.add(ON_CREATE);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_RESTART);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_START);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_RESUME);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_PAUSE);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_STOP);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_DESTROY);
    }


    public ActivityLifecycle(Map<String, PsiMethod> methods) {
        super(methods);
        mLifecycleOrdering = ACTIVITY_LIFECYCLE_METHODS;
    }

}
