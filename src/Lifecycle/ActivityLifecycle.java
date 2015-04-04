package Lifecycle;

import com.intellij.psi.PsiMethod;

import java.util.*;

/**
 * Created by armand on 3/1/15.
 */
public class ActivityLifecycle implements Lifecycle{

    /**
     * Activity Lifecycle method names
     */
    private static final String ON_CREATE = "onCreate";
    private static final String ON_START = "onStart";
    private static final String ON_RESUME = "onResume";
    private static final String ON_PAUSE = "onPause";
    private static final String ON_STOP = "onStop";
    private static final String ON_RESTART = "onRestart";
    private static final String ON_DESTROY = "onDestroy";


    /**
     * The ordering of the Activity Lifecycle methods
     */
    private static final List<String> ACTIVITY_LIFECYCLE_METHODS = new ArrayList<String>();

    static {
        ACTIVITY_LIFECYCLE_METHODS.add(ON_CREATE);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_START);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_RESUME);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_PAUSE);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_STOP);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_RESTART);
        ACTIVITY_LIFECYCLE_METHODS.add(ON_DESTROY);
    }

    /**
     * A Map representing the activity lifecycle method.
     * The key is the method name, and the value is the entire method represented as a String,
     * such as any Annotations, signature, accessors, method body, etc.
     */
    private Map<String, PsiMethod> mMethods;

    public ActivityLifecycle(Map<String, PsiMethod> methods) {
        this.mMethods = methods;
    }

    /**
     * Sorts the lifecycle methods provided
     * @return A Map of the method names and entire method definitions, respecting the
     * sort order of ACTIVITY_LIFECYCLE_METHODS
     */
    @Override
    public Map<String, PsiMethod> sort() {

        // LinkedHashMap because we must respect the ordering in which we insert
        Map<String, PsiMethod> sortedMethods = new LinkedHashMap<String, PsiMethod>();

        for (int i = 0; i < ACTIVITY_LIFECYCLE_METHODS.size(); i++) {
            String methodName = ACTIVITY_LIFECYCLE_METHODS.get(i);
            PsiMethod method = mMethods.remove(methodName);

            if (method != null) {
                sortedMethods.put(methodName, method);
            }
        }

        return sortedMethods;
    }

}
