package dateSorter;

import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionEvent;

public class DateDeleteListener implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent event) {

        Boolean flag = true;
        DateSorterGUI.dateHistory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Integer confirmDialogue = JOptionPane.showConfirmDialog(DateSorterGUI.mainFrame, "Delete this date from the history?");

        while(flag) {
            if(confirmDialogue == JOptionPane.YES_OPTION) {
                Integer deleteVal = DateSorterGUI.dateHistory.getSelectedIndex();
                if(deleteVal != -1) {
                    DateSorterGUI.historyModel.remove(deleteVal);
                }
                JOptionPane.getRootFrame().dispose();
                DateSorterGUI.resetFields();
                DateSorterGUI.dateHistory.clearSelection();
                DateSorterGUI.deleteButton.setEnabled(false);
            }
            else if(confirmDialogue == JOptionPane.NO_OPTION) {
                JOptionPane.getRootFrame().dispose();
                DateSorterGUI.deleteButton.setEnabled(false);
            }
            else if(confirmDialogue == JOptionPane.CLOSED_OPTION) {
                JOptionPane.getRootFrame().dispose();
                DateSorterGUI.deleteButton.setEnabled(false);
            }
            flag = false;
        }
    }
}
