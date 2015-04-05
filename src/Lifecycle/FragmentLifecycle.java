package Lifecycle;

import com.intellij.psi.PsiMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by armand on 3/29/15.
 */
public class FragmentLifecycle extends Lifecycle {

    /**
     * Fragment Lifecycle method names
     */
    private static final String ON_ATTACH = "onAttach";
    private static final String ON_CREATE = "onCreate";
    private static final String ON_CREATE_VIEW = "onCreateView";
    private static final String ON_VIEW_CREATED = "onViewCreated";
    private static final String ON_ACTIVITY_CREATED = "onActivityCreated";
    private static final String ON_VIEW_STATE_RESTORED = "onViewStateRestored";
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
        FRAGMENT_LIFECYCLE_METHODS.add(ON_VIEW_CREATED);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_ACTIVITY_CREATED);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_VIEW_STATE_RESTORED);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_START);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_RESUME);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_PAUSE);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_STOP);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_DESTROY_VIEW);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_DESTROY);
        FRAGMENT_LIFECYCLE_METHODS.add(ON_DETACH);
    }

    public FragmentLifecycle(Map<String, PsiMethod> methods) {
        super(methods);
        mLifecycleOrdering = FRAGMENT_LIFECYCLE_METHODS;
    }

}
