package Settings;


import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static Lifecycle.ActivityLifecycle.getActivityLifecycleMethods;

public class Settings implements Configurable {
    private JPanel mPanel;
    private JList leftList;
    private JList rightList;
    private JButton removeButton;
    private JButton addButton;

    private List<String> activeMethodsList;
    private List<String> inactiveMethodsList;

    boolean modified;

    @Nls
    @Override
    public String getDisplayName() {
        return "Lifecycle Sorter+";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        reset();
        return mPanel;
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void apply() throws ConfigurationException {
        SettingsUtils.setMethodList(activeMethodsList);
        modified = false;
    }

    @Override
    public void reset() {
        modified = false;
        initLists();
        initButtonListeners();
    }

    @Override
    public void disposeUIResources() {
        mPanel = null;
    }

    private void initButtonListeners() {
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAddClicked();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onRemoveClicked();
            }
        });
    }

    private void initLists() {
        activeMethodsList = SettingsUtils.getActiveMethodsList();
        if (activeMethodsList == null || activeMethodsList.isEmpty()) {
            SettingsUtils.resetMethodList();
            activeMethodsList = SettingsUtils.getActiveMethodsList();
        }
        initRightList();

        inactiveMethodsList = SettingsUtils.getInactiveMethodsList();
        initLeftList();
    }

    private void initRightList() {
        rightList.setListData(activeMethodsList.toArray());
        rightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rightList.setLayoutOrientation(JList.VERTICAL);
        rightList.setVisibleRowCount(12);
    }

    private void initLeftList() {
        leftList.setListData(inactiveMethodsList.toArray());
        leftList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        leftList.setLayoutOrientation(JList.VERTICAL);
        leftList.setVisibleRowCount(12);
    }

    private void onRemoveClicked() {
        modified = true;
        moveListItems(rightList.getSelectedIndices(), inactiveMethodsList, activeMethodsList);
        sortLists();
        updateLists();
    }

    private void onAddClicked() {
        modified = true;
        moveListItems(leftList.getSelectedIndices(), activeMethodsList, inactiveMethodsList);
        sortLists();
        updateLists();
    }

    private void moveListItems(int[] indices, List<String> addTo, List<String> removeFrom) {
        if (indices.length > 0) {
            for (int i : indices) {
                addTo.add(removeFrom.get(i));
                removeFrom.remove(i);
            }
        }
    }

    private void sortLists() {
        List<String> tempActive = new ArrayList<String>();
        List<String> tempInactive = new ArrayList<String>();

        //place lifecycle methods in order at start of lists
        for (String methodName : getActivityLifecycleMethods()) {
            if (activeMethodsList.contains(methodName)) {
                activeMethodsList.remove(methodName);
                tempActive.add(methodName);
            }
            if (inactiveMethodsList.contains(methodName)) {
                inactiveMethodsList.remove(methodName);
                tempInactive.add(methodName);
            }
        }

        //add non-lifecycle methods in the order they were added/removed
        tempActive.addAll(activeMethodsList);
        tempInactive.addAll(inactiveMethodsList);

        activeMethodsList = tempActive;
        inactiveMethodsList = tempInactive;

    }

    private void updateLists() {
        rightList.setListData(activeMethodsList.toArray());
        leftList.setListData(inactiveMethodsList.toArray());
    }
}
