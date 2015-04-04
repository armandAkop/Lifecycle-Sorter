package Sorter;

import Lifecycle.ActivityLifecycle;
import Lifecycle.FragmentLifecycle;
import Util.LifecycleUtils;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by armand on 3/1/15.
 */

/**
 * This class determines if the current class is an Activity or a Fragment
 * and sorts the lifecycle methods based off that criteria.
 */
public class Sorter {

    private PsiClass mPsiClass;

    public Sorter(PsiClass psiClass) {
        mPsiClass = psiClass;
    }


    /**
     * Formats the activity/fragment lifecycle methods
     */
    public void sort() {
        PsiMethod[] psiClassMethods = mPsiClass.getMethods();

        Map<String, PsiMethod> methods = new TreeMap<String, PsiMethod>();

        for (PsiMethod method : psiClassMethods) {
            if (method != null) {
                methods.put(method.getName(), method);
            }
        }

        Map<String, PsiMethod> sortedMethods = null;

        // Does our current class extend from Activity or Fragment?
        if (LifecycleUtils.getLifeCycleType(mPsiClass) == LifecycleUtils.ACTIVITY) {
            sortedMethods = new ActivityLifecycle(methods).sort();
        } else if (LifecycleUtils.getLifeCycleType(mPsiClass) == LifecycleUtils.FRAGMENT) {
            sortedMethods = new FragmentLifecycle(methods).sort();
        }

        appendSortedMethods(sortedMethods);

        // After obtaining and appending the new sorted list of PsiMethods,
        // we must remove the old methods
        deletePsiMethods(sortedMethods.values());

    }


    /**
     * Removes the collection of PsiMethods from the PsiClass
     * @param methods the methods to remove from the PsiClass
     */
    private void deletePsiMethods(Collection<PsiMethod> methods) {
        for (PsiMethod method : methods) method.delete();
    }


    /**
     * Appends the sorted methods to the end of the file
     *
     * @param sortedMethods The sorted methods to append
     */
    private void appendSortedMethods(Map<String, PsiMethod> sortedMethods) {
        for (PsiMethod method : sortedMethods.values()) {
            mPsiClass.add(method);
        }
    }

}
