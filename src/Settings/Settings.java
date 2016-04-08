package Settings;


import Lifecycle.ActivityLifecycle;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Lifecycle.ActivityLifecycle.getActivityLifecycleMethods;

public class Settings implements Configurable {
    private JPanel mPanel;
    private JList leftList;
    private JList rightList;
    private JButton moveLeftButton;
    private JButton moveRightButton;
    private JButton moveDownButton;
    private JButton moveUpButton;
    private JTextField addNewMethod;
    private JButton addNewMethodButton;
    private JLabel errorLabel;
    private JLabel customLabel;
    private JButton removeMethodButton;
    private JCheckBox placeConstructorsAboveLifecycleCheckBox;

    private List<String> removedCustomMethods;
    private List<String> addedCustomMethods;
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
//        reset();
        return mPanel;
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void apply() throws ConfigurationException {
        SettingsUtils.setActiveMethodList(activeMethodsList);
        SettingsUtils.setConstructorsAboveAll(placeConstructorsAboveLifecycleCheckBox.isSelected());

        if (addedCustomMethods.size() > 0 || removedCustomMethods.size() > 0) {
            List<String> currentCustomMethods = SettingsUtils.getCustomMethodsList();
            if (currentCustomMethods == null) {
                currentCustomMethods = new ArrayList<String>();
            }
            currentCustomMethods.addAll(addedCustomMethods);
            currentCustomMethods.removeAll(removedCustomMethods);
            SettingsUtils.setCustomMethodList(currentCustomMethods);

            addedCustomMethods.clear();
            removedCustomMethods.clear();
        }
        modified = false;
    }

    @Override
    public void reset() {
        modified = false;
        initLists();
        initButtons();
        initButtonListeners();
        initListListeners();
        initCheckBoxes();
        errorLabel.setVisible(false);
    }

    @Override
    public void disposeUIResources() {
        mPanel = null;
    }

    /**
     * fetch and set datasets to be used
     */
    private void initLists() {
        addedCustomMethods = new ArrayList<String>();
        removedCustomMethods = new ArrayList<String>();

        activeMethodsList = SettingsUtils.getActiveMethodsList();
        if (activeMethodsList == null || activeMethodsList.isEmpty()) {
            SettingsUtils.resetActiveMethodList();
            activeMethodsList = SettingsUtils.getActiveMethodsList();
        }
        initRightList();

        inactiveMethodsList = SettingsUtils.getInactiveMethodsList();
        initLeftList();
    }

    /**
     * disable all buttons by default
     */
    private void initButtons() {
        moveUpButton.setEnabled(false);
        moveDownButton.setEnabled(false);
        moveLeftButton.setEnabled(false);
        moveRightButton.setEnabled(false);
        removeMethodButton.setEnabled(false);
    }

    /**
     * set value and listeners for checkboxes
     */
    private void initCheckBoxes() {
        placeConstructorsAboveLifecycleCheckBox.setSelected(SettingsUtils.getConstructorsAboveAll());
        placeConstructorsAboveLifecycleCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modified = true;
            }
        });
    }

    /**
     * set active list data and layout values
     */
    private void initRightList() {
        rightList.setListData(activeMethodsList.toArray());
        rightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rightList.setLayoutOrientation(JList.VERTICAL);
        rightList.setVisibleRowCount(12);
    }

    /**
     * set inactive list data and layout values
     */
    private void initLeftList() {
        leftList.setListData(inactiveMethodsList.toArray());
        leftList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        leftList.setLayoutOrientation(JList.VERTICAL);
        leftList.setVisibleRowCount(12);
    }

    /**
     * set button click listeners
     */
    private void initButtonListeners() {
        moveRightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onMoveRightClicked();
            }
        });

        moveLeftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onMoveLeftClicked();
            }
        });

        moveUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onMoveUpClicked();
            }
        });

        moveDownButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onMoveDownClicked();
            }
        });

        addNewMethodButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAddNewMethodClicked();
            }
        });

        removeMethodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRemoveMethodClicked();
            }
        });

    }

    /**
     * setup list selection change listeners
     */
    private void initListListeners() {
        rightList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !rightList.isSelectionEmpty()) {
                    leftList.clearSelection();
                    int selected = rightList.getSelectedIndex();

                    updateRemoveButton(activeMethodsList.get(selected));
                    moveRightButton.setEnabled(false);
                    moveLeftButton.setEnabled(true);


                    if (isSelectedActiveLifecycleMethod()) {
                        moveUpButton.setEnabled(false);
                        moveDownButton.setEnabled(false);
                    } else {
                        if (selected == 0) {//can't move first item up
                            errorLabel.setVisible(false);
                            moveUpButton.setEnabled(false);
                            moveDownButton.setEnabled(true);
                        } else if (selected >= activeMethodsList.size() - 1) {//can't move last item down
                            errorLabel.setVisible(false);
                            moveUpButton.setEnabled(true);
                            moveDownButton.setEnabled(false);
                        } else {
                            errorLabel.setVisible(false);
                            moveUpButton.setEnabled(true);
                            moveDownButton.setEnabled(true);
                        }
                    }
                }
            }
        });

        leftList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !leftList.isSelectionEmpty()) {
                    rightList.clearSelection();

                    int selected = leftList.getSelectedIndex();
                    updateRemoveButton(inactiveMethodsList.get(selected));
                    moveRightButton.setEnabled(true);
                    moveLeftButton.setEnabled(false);
                    moveUpButton.setEnabled(false);
                    moveDownButton.setEnabled(false);

                }
            }
        });
    }

    private void updateRemoveButton(String method) {
        if (!TextUtils.isEmpty(method)) {
            if (SettingsUtils.isCustomMethodExists(method) || addedCustomMethods.contains(method)) {
                removeMethodButton.setEnabled(true);
            } else {
                removeMethodButton.setEnabled(false);
            }
        } else {
            removeMethodButton.setEnabled(false);
        }
    }

    /**
     * sort list data and update UI
     */
    private void onListChanged() {
        modified = true;
        sortLists();
        updateLists();
    }

    /**
     * move an item from the active list to the inactive list
     */
    private void onMoveLeftClicked() {
        modified = true;
        moveListItems(rightList.getSelectedIndices(), inactiveMethodsList, activeMethodsList);
        onListChanged();
    }

    /**
     * move an items from the inactive list ot the active list
     */
    private void onMoveRightClicked() {
        modified = true;
        moveListItems(leftList.getSelectedIndices(), activeMethodsList, inactiveMethodsList);
        onListChanged();
    }

    /**
     * move the currently selected item in the active list down one position
     */
    private void onMoveDownClicked() {
        if (!rightList.isSelectionEmpty()) {
            int selected = rightList.getSelectedIndex();
            if (selected < activeMethodsList.size()) {
                Collections.swap(activeMethodsList, selected, selected + 1);
                onListChanged();
                rightList.setSelectedIndex(selected + 1);
            }
        }
    }

    /**
     * move the currently selected item in the active list up one position
     */
    private void onMoveUpClicked() {
        if (!rightList.isSelectionEmpty()) {
            int selected = rightList.getSelectedIndex();
            if (selected > 0) {
                String aboveMethod = activeMethodsList.get(selected - 1);
                //do not attempt move if swapping with lifecycle method
                if (!ActivityLifecycle.isLifecycleMethod(aboveMethod)) {
                    Collections.swap(activeMethodsList, selected, selected - 1);
                    onListChanged();
                    rightList.setSelectedIndex(selected - 1);
                }
            }
        }
    }

    /**
     * add new method from text field to end of active list if valid name
     */
    private void onAddNewMethodClicked() {
        String newMethodName = addNewMethod.getText().trim();
        String error = isValidMethodName(newMethodName);
        if (!TextUtils.isEmpty(error)) {
            showError(error);
        } else {
            addedCustomMethods.add(newMethodName);
            activeMethodsList.add(newMethodName);
            addNewMethod.setText("");
            onListChanged();
        }
    }

    /**
     * remove currently selected method from list if it is a custom method
     */
    private void onRemoveMethodClicked() {
        String methodName;
        if (!rightList.isSelectionEmpty()) {
            methodName = activeMethodsList.get(rightList.getSelectedIndex());
            activeMethodsList.remove(methodName);
        } else {
            methodName = inactiveMethodsList.get(leftList.getSelectedIndex());
            inactiveMethodsList.remove(methodName);
        }

        if (methodName != null) {
            if (SettingsUtils.isCustomMethodExists(methodName)) {
                removedCustomMethods.add(methodName);
            } else {
                addedCustomMethods.remove(methodName);
            }
        }

        onListChanged();
    }

    /**
     * validate custom method name, return null if valid, error string in invalid
     *
     * @param newMethodName
     * @return
     */
    private String isValidMethodName(String newMethodName) {
        if (!(newMethodName.length() > 0)) {
            return "Method name cannot be empty";
        } else if (!newMethodName.matches("^[a-zA-Z0-9]*$")) {
            return "Invalid method name. Names may only contain alphanumeric characters";
        } else if (ActivityLifecycle.isBuiltInMethod(newMethodName)) {
            return "Method name already exists";
        } else if (SettingsUtils.isCustomMethodExists(newMethodName)) {
            return "Method name already exists";
        } else {
            return null;
        }
    }

    /**
     * move items from one list to another
     *
     * @param indices
     * @param addTo
     * @param removeFrom
     */
    private void moveListItems(int[] indices, List<String> addTo, List<String> removeFrom) {
        if (indices.length > 0) {
            for (int i : indices) {
                addTo.add(removeFrom.get(i));
                removeFrom.remove(i);
            }
        }
    }

    /**
     * sort list based on lifecycle order
     */
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

    /**
     * reset list view's data
     */
    private void updateLists() {
        rightList.setListData(activeMethodsList.toArray());
        leftList.setListData(inactiveMethodsList.toArray());
    }

    /**
     * is the currently selected item in the active list a life-cycle method
     *
     * @return
     */
    private boolean isSelectedActiveLifecycleMethod() {
        int selected = rightList.getSelectedIndex();
        if (selected < 0 || selected > activeMethodsList.size() - 1) {
            return false;
        }
        String methodName = activeMethodsList.get(selected);
        return ActivityLifecycle.isLifecycleMethod(methodName);
    }

    /**
     * update the error field's text
     *
     * @param msg
     */
    private void showError(String msg) {
        errorLabel.setText(msg);
        errorLabel.setVisible(true);
    }
}
