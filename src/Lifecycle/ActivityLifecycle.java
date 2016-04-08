package Lifecycle;


import Settings.SettingsUtils;
import com.intellij.psi.PsiClass;
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
    private static final String ON_POST_RESUME = "onPostResume";
    private static final String ON_PAUSE = "onPause";
    private static final String ON_STOP = "onStop";
    private static final String ON_DESTROY = "onDestroy";
    private static final String ON_NEW_INTENT = "onNewIntent";

    /**
     * Activity non-Lifecycle method names
     */
    private static final String ON_RESTORE_INSTANCE_STATE = "onRestoreInstanceState";
    private static final String ON_SAVE_INSTANCE_STATE = "onSaveInstanceState";
    private static final String ON_CREATE_OPTIONS_MENU = "onCreateOptionsMenu";
    private static final String ON_OPTIONS_ITEM_SELECTED = "onOptionsItemSelected";
    private static final String ON_ACTIVITY_RESULT = "onActivityResult";
    private static final String ON_BACK_PRESSED = "onBackPressed";


    public static List<String> getActivityLifecycleMethods() {
        return ACTIVITY_LIFECYCLE_METHODS;
    }

    public static List<String> getActivityNonLifecycleMethods() {
        return ACTIVITY_NON_LIFECYCLE_METHODS;
    }

    public static List<String> getAllActivityMethods() {
        List<String> result = new ArrayList<String>();
        result.addAll(getActivityLifecycleMethods());
        result.addAll(getActivityNonLifecycleMethods());
        return result;
    }


    /**
     * The ordering of the Activity Lifecycle methods
     */
    private static final List<String> ACTIVITY_LIFECYCLE_METHODS = new ArrayList<String>();

    static {
        ACTIVITY_LIFECYCLE_METHODS.add(ON_CREATE);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_RESTART);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_START);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_RESUME);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_POST_RESUME);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_PAUSE);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_STOP);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_DESTROY);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_NEW_INTENT);
    }

    /**
     * The ordering of the Activity Lifecycle methods
     */
    private static final List<String> ACTIVITY_NON_LIFECYCLE_METHODS = new ArrayList<String>();

    static {
        ACTIVITY_NON_LIFECYCLE_METHODS.add(ON_ACTIVITY_RESULT);
        ACTIVITY_NON_LIFECYCLE_METHODS.add(ON_CREATE_OPTIONS_MENU);
        ACTIVITY_NON_LIFECYCLE_METHODS.add(ON_OPTIONS_ITEM_SELECTED);
        ACTIVITY_NON_LIFECYCLE_METHODS.add(ON_SAVE_INSTANCE_STATE);
        ACTIVITY_NON_LIFECYCLE_METHODS.add(ON_RESTORE_INSTANCE_STATE);
        ACTIVITY_NON_LIFECYCLE_METHODS.add(ON_BACK_PRESSED);
    }

    public ActivityLifecycle(PsiClass psiClass, Map<String, PsiMethod> methods) {
        super(psiClass, methods);
        mLifecycleOrdering = ACTIVITY_LIFECYCLE_METHODS;

        List<String> activeMethods = SettingsUtils.getActiveMethodsList();
        if (activeMethods != null && !activeMethods.isEmpty()) {
            mLifecycleOrdering = SettingsUtils.getActiveMethodsList();
        }
    }


    /**
     * check is the method name exists in the list of lifecycle methods
     *
     * @param method
     * @return
     */
    public static boolean isLifecycleMethod(String method) {
        return getActivityLifecycleMethods().contains(method);
    }

    /**
     * check is the method name exists in the list of lifecycle and non-lifecycle methods
     *
     * @param method
     * @return
     */
    public static boolean isBuiltInMethod(String method) {
        return getActivityLifecycleMethods().contains(method) && getActivityNonLifecycleMethods().contains(method);
    }

}
