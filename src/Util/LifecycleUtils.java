package Util;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;

/**
 * Created by armand on 3/22/15.
 */
public class LifecycleUtils {

    public static final String ACTIVITY = "activity";
    public static final String FRAGMENT = "fragment";

    /**
     * To Prevent instantiation
     */
    private LifecycleUtils() {
    }

    /**
     * Recurses up the superclass chain of the PsiClass provided to determine
     * if the chain contains Activity or Fragment as one of the base classes.
     * This will then be used to determine which lifecycle methods to sort by.
     *
     * @param psiClass   The psiClass used to traverse up the superclass chain
     * @param superClass The superclass to check against a match
     * @return true if one of the superclasses is Activity or Fragment, false otherwise
     */
    public static boolean checkSuperClass(PsiClass psiClass, String superClass) {
        // We got to the chain and didn't find anything. Either the JDK is not
        // configured or they're simply not using the Android SDK
        if (psiClass == null) {
            return false;
        }
        else if (superClass.equals(psiClass.getName())) {
            return true;
        } else {
            return checkSuperClass(psiClass.getSuperClass(), superClass);
        }

    }
}
