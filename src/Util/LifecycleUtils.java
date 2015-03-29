package Util;

import com.intellij.psi.PsiClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by armand on 3/22/15.
 */
public class LifecycleUtils {

    public static final int ACTIVITY = 1;
    public static final int FRAGMENT = 2;


    private static final List<String> ACTIVITY_PACKAGE_NAMES = new ArrayList<String>();

    static {
        ACTIVITY_PACKAGE_NAMES.add("android.app.Activity");
    }


    /**
     * The fragment package names and the support libraries that come with it.
     */
    private static final List<String> FRAGMENT_PACKAGE_NAMES = new ArrayList<String>();

    static {
        FRAGMENT_PACKAGE_NAMES.add("android.app.Fragment");
        FRAGMENT_PACKAGE_NAMES.add("android.support.v4.app.Fragment");
    }

    /**
     * To Prevent instantiation
     */
    private LifecycleUtils() {
    }


    /**
     * Checks a list of superclasses of a PsiClass against the Activity/Fragment classes and support classes.
     * @param psiClass
     * @return true if a match is found, false otherwise
     */
    public static int getLifeCycleType(PsiClass psiClass) {
        List<String> packages = buildSuperClassList(psiClass, new ArrayList<String>());
        return resolvePackageToCompare(packages);

    }

    /**
     * Cross checks the list of package names we've built up against the list of
     * Activity package names and list of Fragment package names.
     * @param superClassList The hierarchy of classes found from the current PsiClass
     * @return {@value #ACTIVITY} if the superClassList contains the appropriate package
     * name identifying that Activity has  been inherited, {@value #FRAGMENT} if the superClassList
     * contains the appropriate package name identifying that Fragment has been inherited.
     * -1 if no inheritance matches are found.
     */
    private static int resolvePackageToCompare(List<String> superClassList) {
        for (int i = 0; i < ACTIVITY_PACKAGE_NAMES.size(); i++) {
            if (superClassList.contains(ACTIVITY_PACKAGE_NAMES.get(i))) return ACTIVITY;
        }

        for (int i = 0; i < FRAGMENT_PACKAGE_NAMES.size(); i++) {
            if (superClassList.contains(FRAGMENT_PACKAGE_NAMES.get(i))) return FRAGMENT;
        }

        return -1;
    }


    /**
     * Recurses up the superclass chain of the PsiClass provided to determine
     * if the chain contains Activity or Fragment as one of the base classes.
     * This will then be used to determine which lifecycle methods to sort by.
     *
     * @param psiClass the psiClass used to traverse up the superclass chain
     * @param packages the list of packages that will be built up from traversal
     * @return the list of packages collected after traversal
     */
    private static List<String> buildSuperClassList(PsiClass psiClass, List<String> packages) {

        // After traversing up the hierarchy, either the JDK is not
        // configured or they're simply not using the Android SDK
        // which would obviously contain the required packages
        if (psiClass == null || psiClass.getQualifiedName().equals("java.lang.Object")) {
            return packages;
        } else {
            packages.add(psiClass.getQualifiedName());
            return buildSuperClassList(psiClass.getSuperClass(), packages);
        }
    }


}
