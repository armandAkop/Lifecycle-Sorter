package Sorter;

import Lifecycle.Lifecycle;
import Util.LifecycleUtils;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;

import java.util.*;

/**
 * Created by armand on 3/1/15.
 */
public class ActivityLifecycle implements Lifecycle{

    /**
     * Activity Lifecycle.Lifecycle method names
     */
    public static final String ON_CREATE = "onCreate";
    public static final String ON_START = "onStart";
    public static final String ON_RESUME = "onResume";
    public static final String ON_PAUSE = "onPause";
    public static final String ON_STOP = "onStop";
    public static final String ON_RESTART = "onRestart";
    public static final String ON_DESTROY = "onDestroy";


    public static final List<String> mActivityLifecycleMethods = new ArrayList<String>();

    static {
        mActivityLifecycleMethods.add(ON_CREATE);
        mActivityLifecycleMethods.add(ON_START);
        mActivityLifecycleMethods.add(ON_RESUME);
        mActivityLifecycleMethods.add(ON_PAUSE);
        mActivityLifecycleMethods.add(ON_STOP);
        mActivityLifecycleMethods.add(ON_RESTART);
        mActivityLifecycleMethods.add(ON_DESTROY);
    }

    /**
     * A Map representing the activity lifecycle method.
     * The key is the method name, and the value is the entire method represented as a String,
     * such as any Annotations, signature, accessors, method body, etc.
     */
    public Map<String, String> mMethods;

    public ActivityLifecycle(Map<String, String> methods) {
        this.mMethods = methods;
    }


    @Override
    public Map<String, String> sort() {

        // LinkedHashMap because we must respect the ordering in which we insert
        Map<String, String> sortedMethods = new LinkedHashMap<String, String>();

        for (int i = 0; i < mActivityLifecycleMethods.size(); i++) {
            String methodName = mActivityLifecycleMethods.get(i);
            String method = mMethods.get(methodName);


            if (method != null) {
                //System.out.println(String.sort("Method Name is %s, method is %s", methodName, method));
                sortedMethods.put(methodName, method);
            }
        }

        /*for (Map.Entry<String, String> method : sortedMethods.entrySet()) {
            System.out.println(method.getValue());
        }*/

        return sortedMethods;
    }
}
