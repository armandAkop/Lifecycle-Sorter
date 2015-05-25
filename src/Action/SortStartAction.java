package Action;

import Sort.Sorter;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Created by akopian on 5/24/15.
 */
public class SortStartAction extends SortAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        mSortPosition = Sorter.SortPosition.START;
        super.actionPerformed(e);
    }
}
