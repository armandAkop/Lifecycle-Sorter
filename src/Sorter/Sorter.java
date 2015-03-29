package Sorter;

import Lifecycle.ActivityLifecycle;
import Util.LifecycleUtils;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;

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

        Map<String, String> methods = new TreeMap<String, String>();

        // TODO: Filter out parent class methods
        for (PsiMethod method : psiClassMethods) {
            if (method != null) {

                methods.put(method.getName(), method.getText());

                try {
                    method.delete();
                }
                catch (IncorrectOperationException e){}
           }

        }

        // Does our current class extend from Activity or Fragment?
        if (LifecycleUtils.getLifeCycleType(mPsiClass) == LifecycleUtils.ACTIVITY) {
            Map<String, String> sortedMethods = new ActivityLifecycle(methods).sort();
            appendSortedMethods(sortedMethods);
        }

    }


    /**
     * Appends the sorted methods to the end of the file
     * @param sortedMethods The sorted methods to append
     */
    private void appendSortedMethods(Map<String, String> sortedMethods) {
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(mPsiClass.getProject());

        for (Map.Entry<String, String> entry : sortedMethods.entrySet()) {

            PsiMethod method = elementFactory.createMethodFromText(entry.getValue(), mPsiClass);
            mPsiClass.add(method);
        }
    }

}
