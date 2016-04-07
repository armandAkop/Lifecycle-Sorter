package Settings;

import Lifecycle.ActivityLifecycle;
import com.intellij.ide.util.PropertiesComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kevinbojarski on 4/5/16.
 */
public class SettingsUtils {
    private static final String TAG = "SettingsUtils";

    private static final String SAVED_SORT_ORDER = "sort-list";
    private static final String CUSTOM_METHODS_SORT_LIST = "custom-methods-list";

    /**
     * gets list of methods are are are to be sorted in proper order
     *
     * @return
     */
    public static List<String> getActiveMethodsList() {
        String[] values = PropertiesComponent.getInstance().getValues(SAVED_SORT_ORDER);
        if (values != null) {
            return new ArrayList<String>(Arrays.asList(values));
        } else {
            return null;
        }
    }

    /**
     * saves (overwrites) the list of methods to be sorted in order
     *
     * @param values
     */
    public static void setActiveMethodList(String[] values) {
        PropertiesComponent.getInstance().setValues(SAVED_SORT_ORDER, values);
    }

    /**
     * saves (overwrites) the list of methods to be sorted in order
     *
     * @param values
     */
    public static void setActiveMethodList(List<String> values) {
        setActiveMethodList(values.toArray(new String[0]));
    }

    public static void resetActiveMethodList() {
        ActivityLifecycle.getAllActivityMethods();
        PropertiesComponent.getInstance().setValues(SAVED_SORT_ORDER, ActivityLifecycle.getActivityLifecycleMethods().toArray(new String[0]));
    }

    /**
     * gets list of methods that are not currently being sorted (custom and default)
     */
    public static List<String> getInactiveMethodsList() {
        List<String> active = getActiveMethodsList();
        List<String> inactive = ActivityLifecycle.getAllActivityMethods();
        List<String> custom = getCustomMethodsList();

        if (inactive != null) {
            if (active != null) {
                inactive.removeAll(active);
            }
            if (custom != null) {
                custom.removeAll(active);
                inactive.addAll(custom);
            }
        }
        return inactive;
    }

    /**
     * get the list of custom methods that the user has added
     *
     * @return
     */
    public static List<String> getCustomMethodsList() {
        String[] values = PropertiesComponent.getInstance().getValues(CUSTOM_METHODS_SORT_LIST);
        if (values != null) {
            return new ArrayList<String>(Arrays.asList(values));
        } else {
            return null;
        }
    }

    /**
     * adds a new method to the list of custom methods to be sorted
     *
     * @param method
     */
    public static void addToCustomMethodList(String method) {
        //do not add method if it already exists
        if (isCustomMethodExists(method)) {
            return;
        }

        String[] list = PropertiesComponent.getInstance().getValues(CUSTOM_METHODS_SORT_LIST);
        List<String> values;
        if (list != null) {
            values = new ArrayList<String>(Arrays.asList(list));
        } else {
            values = new ArrayList<String>();
        }
        values.add(method);
        setCustomMethodList(values);
    }

    /**
     * removes a method from the list of custom methods
     *
     * @param method
     */
    public static void removeCustomMethod(String method) {
        if (isCustomMethodExists(method)) {
            List<String> values = getCustomMethodsList();
            values.remove(method);
            setCustomMethodList(values);
        }
    }

    /**
     * checks if the method name exists the the current list of custom methods
     *
     * @param method
     * @return
     */
    public static boolean isCustomMethodExists(String method) {
        List<String> values = getCustomMethodsList();
        if (values == null || values.size() < 1) {
            return false;
        } else {
            return values.contains(method);
        }
    }

    /**
     * saves (overwrite) the list as the currently saved custom methods
     *
     * @param values
     */
    public static void setCustomMethodList(String[] values) {
        PropertiesComponent.getInstance().setValues(CUSTOM_METHODS_SORT_LIST, values);
    }

    /**
     * saves (overwrite) the list as the currently saved custom methods
     *
     * @param values
     */
    public static void setCustomMethodList(List<String> values) {
        setCustomMethodList(values.toArray(new String[0]));
    }

    /**
     * remove all saved custom methods
     */
    public static void resetCustomMethodList() {
        List<String> values = getCustomMethodsList();
        values.clear();
        setCustomMethodList(values);
    }

}
