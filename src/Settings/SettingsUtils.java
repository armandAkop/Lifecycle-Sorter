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
    private static final String SORT_NON_LIFECYCLE = "sort-non-lifecycle";
    private static final String SORT_LIST = "sort-list";

    public static boolean getSortNonLifecycle() {
        return Boolean.parseBoolean(PropertiesComponent.getInstance().getValue(SORT_NON_LIFECYCLE));
    }

    public static void setSortNonLifecycle(boolean value) {
        PropertiesComponent.getInstance().setValue(SORT_NON_LIFECYCLE, value);
    }

    public static List<String> getActiveMethodsList() {
        String[] values = PropertiesComponent.getInstance().getValues(SORT_LIST);
        if (values != null) {
            return new ArrayList<String>(Arrays.asList(values));
        } else {
            return null;
        }
    }

    public static List<String> getInactiveMethodsList() {
        List<String> active = getActiveMethodsList();
        List<String> inactive = ActivityLifecycle.getAllActivityMethods();
        if (inactive != null && active != null) {
            inactive.removeAll(active);
        }

        return inactive;

    }

    public static void setMethodList(String[] values) {
        PropertiesComponent.getInstance().setValues(SORT_LIST, values);
    }

    public static void setMethodList(List<String> values) {
        setMethodList(values.toArray(new String[0]));
    }


    public static void resetMethodList() {
        ActivityLifecycle.getAllActivityMethods();
        PropertiesComponent.getInstance().setValues(SORT_LIST, ActivityLifecycle.getActivityLifecycleMethods().toArray(new String[0]));
    }
}
