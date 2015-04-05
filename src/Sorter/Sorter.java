package Sorter;

import Lifecycle.Lifecycle;
import Lifecycle.LifecycleFactory;
import com.intellij.psi.*;

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

        LifecycleFactory lifecycleFactory = new LifecycleFactory();
        Lifecycle lifecycle = lifecycleFactory.createLifecycle(mPsiClass, methods);

        if (lifecycle != null) {
            sortedMethods = lifecycle.sort();
            appendSortedMethods(sortedMethods);

            // After obtaining and appending the new sorted list of PsiMethods,
            // we must remove the old, unsorted list
            deleteUnsortedLifecycleMethods(sortedMethods.values());
        }

    }


    /**
     * Removes the collection of PsiMethods from the PsiClass
     * @param methods the methods to remove from the PsiClass
     */
    private void deleteUnsortedLifecycleMethods(Collection<PsiMethod> methods) {
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
