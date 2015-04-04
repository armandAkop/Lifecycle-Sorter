package Lifecycle;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by armand on 3/29/15.
 */
public class FragmentLifecycle implements Lifecycle {

    /**
     * Fragment Lifecycle method names
     */
    private static final String ON_ATTACH = "onAttach";
    private static final String ON_CREATE = "onCreate";
    private static final String ON_CREATE_VIEW = "onCreateView";
    private static final String ON_ACTIVITY_CREATED = "onActivityCreated";
    private static final String ON_START = "onStart";
    private static final String ON_RESUME = "onResume";
    private static final String ON_PAUSE = "onPause";
    private static final String ON_STOP = "onStop";
    private static final String ON_DESTROY_VIEW = "onDestroyView";
    private static final String ON_DESTROY = "onDestroy";
    private static final String ON_DETACH = "onDetach";


    /**
     * The ordering of the Fragment Lifecycle methods
     */
    private static final List<String> FRAGMENT_LIFECYCLE_METHODS = new ArrayList<String>();

    static {
        FRAGMENT_LIFECYCLE_METHODS.add(ON_ATTACH);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_CREATE);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_CREATE_VIEW);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_ACTIVITY_CREATED);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_START);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_RESUME);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_PAUSE);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_STOP);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_DESTROY_VIEW);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_DESTROY);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_DETACH);
    }

    /**
     * A Map representing the Fragment lifecycle methods.
     * The key is the method name, and the value is the entire method represented as a String,
     * such as any Annotations, signature, accessors, method body, etc.
     */
    private Map<String, String> mMethods;

    public FragmentLifecycle(Map<String, String> methods) {
        this.mMethods = methods;
    }

    /**
     * Sorts the lifecycle methods provided
     * @return A Map of the method names and entire method definitions, respecting the
     * sort order of FRAGMENT_LIFECYCLE_METHODS
     */

    @Override
    public Map<String, String> sort() {

        // LinkedHashMap because we must respect the ordering in which we insert
        Map<String, String> sortedMethods = new LinkedHashMap<String, String>();

        for (int i = 0; i < FRAGMENT_LIFECYCLE_METHODS.size(); i++) {
            String methodName = FRAGMENT_LIFECYCLE_METHODS.get(i);
            String method = mMethods.get(methodName);

            if (method != null) {
                sortedMethods.put(methodName, method);
            }
        }

        return sortedMethods;
    }
}
