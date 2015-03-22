package Sorter;

import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by armand on 3/1/15.
 */

/**
 * This class determines if the current class is an Activity or a Fragment
 * and sorts the lifecycle methods based off that criteria.
 * TODO: Differentiate between activity and fragment
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
        PsiMethod[] allMethods = mPsiClass.getAllMethods();

        Map<String, String> methods = new TreeMap<String, String>();

        for (PsiMethod method : allMethods) {
            if (method != null) {

                methods.put(method.getName(), method.getText());
                try {
                    method.delete(); //AWESOME METHOD!!!
                }
                catch (IncorrectOperationException e){}
           }

        }

        Map<String, String> sortedMethods = new ActivityLifecycle(methods).sort();


    }

}
