package Lifecycle;

import Util.LifecycleUtils;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;

import java.util.Map;

/**
 * Created by armand on 4/4/15.
 */
public class LifecycleFactory {

    public Lifecycle createLifecycle(PsiClass psiClass, Map<String, PsiMethod> methods) {
        switch (LifecycleUtils.getLifeCycleType(psiClass)) {

            case LifecycleUtils.ACTIVITY:
                return new ActivityLifecycle(psiClass, methods);

            case LifecycleUtils.FRAGMENT:
                return new FragmentLifecycle(psiClass, methods);

            default:
                return null;
        }
    }
}
