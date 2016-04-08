package Sort;

import Lifecycle.Lifecycle;
import Lifecycle.LifecycleFactory;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by armand on 3/1/15.
 */

/**
 * This class determines if the current class is an Activity or a Fragment
 * and sorts the lifecycle methods based off that criteria.
 */
public class Sorter {

    private PsiClass mPsiClass;

    /**
     * Enum for specifying the offset in the file where the lifecycle methods will be placed.
     * START - Places the methods at the start of the class, after the variable declarations.
     * END - Places the methods at the end of the class
     */
    public enum SortPosition {
        START, END
    }

    private SortPosition mSortPosition;

    /**
     * @param psiClass     The class whose methods to sort
     * @param sortPosition The offset position in the class in which to place the sorted methods
     */
    public Sorter(PsiClass psiClass, SortPosition sortPosition) {
        mPsiClass = psiClass;
        mSortPosition = sortPosition;
    }

    /**
     * Formats the activity/fragment lifecycle methods
     */
    public void sort() {
        Map<String, PsiMethod> methods = getMethodsMap();

        Map<String, PsiMethod> sortedMethods = null;

        LifecycleFactory lifecycleFactory = new LifecycleFactory();
        Lifecycle lifecycle = lifecycleFactory.createLifecycle(mPsiClass, methods);

        if (lifecycle != null && !methods.isEmpty()) {
            sortedMethods = lifecycle.sort();
            appendSortedMethods(sortedMethods);

            // After obtaining and appending the new sorted list of PsiMethods,
            // we must remove the old, unsorted list
            deleteUnsortedLifecycleMethods(sortedMethods.values());
        }

    }

    /**
     * Generates a Map of all the methods in the current class
     *
     * @return a Map of all the methods in the current class
     */
    @NotNull
    private Map<String, PsiMethod> getMethodsMap() {
        PsiMethod[] psiClassMethods = mPsiClass.getMethods();

        Map<String, PsiMethod> methods = new LinkedHashMap<String, PsiMethod>();

        //TODO overloaded methods are only added once, this only one is sorted
        for (PsiMethod method : psiClassMethods) {
            if (method != null) {
                methods.put(method.getName(), method);
            }
        }
        return methods;
    }


    /**
     * Removes the collection of PsiMethods from the PsiClass
     *
     * @param methods the methods to remove from the PsiClass
     */
    private void deleteUnsortedLifecycleMethods(Collection<PsiMethod> methods) {
        for (PsiMethod method : methods) method.delete();
    }


    /**
     * Appends the sorted methods to the file
     *
     * @param sortedMethods The sorted methods to append
     */
    private void appendSortedMethods(Map<String, PsiMethod> sortedMethods) {

        switch (mSortPosition) {
            case START:
                appendToStart(sortedMethods);
                break;
            case END:
                appendToEnd(sortedMethods);
                break;
            default:
                appendToStart(sortedMethods);
                break;
        }

    }

    /**
     * Appends the sorted lifecycle methods to the end of the class.
     *
     * @param sortedMethods The sorted lifecycle methods
     */
    private void appendToEnd(Map<String, PsiMethod> sortedMethods) {
        for (PsiMethod method : sortedMethods.values()) {
            mPsiClass.add(method);
        }
    }

    /**
     * Appends the sorted lifecycle methods to the start of the class.
     *
     * @param sortedMethods The sorted lifecycle methods
     */
    private void appendToStart(Map<String, PsiMethod> sortedMethods) {
        // We want the lifecycle methods to be the first methods in the class
        // so we grab the current first method and append the lifecycle
        // methods before it.
        PsiElement anchorToAddBefore = mPsiClass.getMethods()[0];

        for (PsiMethod method : sortedMethods.values()) {
            mPsiClass.addBefore(method, anchorToAddBefore);
        }
    }

}
