package Lifecycle;

import Settings.SettingsUtils;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.apache.http.util.TextUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by armand on 3/15/15.
 * <p>
 * Abstract class that represents a Lifecycle (could be an Activity/Fragment/etc).
 */
public abstract class Lifecycle {

    /**
     * A Map representing all methods in a PsiClass. This Map will be filtered down to create
     * a new Map with the lifecycle methods of an Activity or Fragment in the proper ordering.
     * The key is the method name, and the value is the entire method represented as a String,
     * such as any Annotations, signature, accessors, method body, etc.
     */
    private Map<String, PsiMethod> mAllMethods;

    /**
     * A List containing the proper ordering of the lifecycle methods, whether it's
     * the activity lifecycle or fragment lifecycle
     */
    protected List<String> mLifecycleOrdering;

    private PsiClass psiClass;


    public Lifecycle(PsiClass psiClass, Map<String, PsiMethod> methods) {
        this.mAllMethods = methods;
        this.psiClass = psiClass;
    }

    /**
     * Sorts the lifecycle methods provided
     *
     * @return A Map of the method names and entire method definitions, respecting the
     * sort order of mLifecycleOrdering
     */
    public Map<String, PsiMethod> sort() {

        // LinkedHashMap because we must respect the ordering in which we insert
        Map<String, PsiMethod> sortedMethods = new LinkedHashMap<String, PsiMethod>();

        if (SettingsUtils.getConstructorsAboveAll()) {
            PsiMethod[] constructors = psiClass.getConstructors();
            if (constructors.length > 0) {
                for (PsiMethod constructor : constructors) {
                    sortedMethods.put(psiClass.getName(), constructor);
                }
            }
        }

        for (int i = 0; i < mLifecycleOrdering.size(); i++) {
            String methodName = mLifecycleOrdering.get(i);
            PsiMethod method = mAllMethods.get(methodName);

            if (method != null) {
                sortedMethods.put(methodName, method);
            }
        }

        return sortedMethods;
    }
}
