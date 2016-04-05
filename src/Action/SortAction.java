package Action;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import Sort.Sorter;

/**
 * Created by armand on 3/1/15.
 */
public class SortAction extends AnAction {

    protected Sorter.SortPosition mSortPosition;

    @Override
    public void actionPerformed(AnActionEvent e) {

        PsiClass psiClass = getPsiClassFromContext(e);

        if (psiClass != null) {
            sortLifecycleMethods(psiClass);
        }

    }

    private void sortLifecycleMethods(final PsiClass psiClass) {
        new WriteCommandAction.Simple(psiClass.getProject(), psiClass.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                new Sorter(psiClass, mSortPosition).sort();
            }
        }.execute();


    }


    /**
     * @param e the action event that occurred
     * @return The PSIClass object based on which class your mouse cursor was in
     */
    protected PsiClass getPsiClassFromContext(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);

        if (psiFile == null || editor == null) {
            return null;
        }

        int offSet = editor.getCaretModel().getOffset();
        PsiElement elementAt = psiFile.findElementAt(offSet);
        PsiClass psiClass = PsiTreeUtil.getParentOfType(elementAt, PsiClass.class);

        return psiClass;
    }
}
