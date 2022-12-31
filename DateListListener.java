package dateSorter;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class DateListListener implements ListSelectionListener{
    
    @SuppressWarnings("unchecked")
    public void valueChanged(ListSelectionEvent event) {
        JList<Date> list = (JList<Date>) event.getSource();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Boolean flag = true;
        while(flag) {
            if(!event.getValueIsAdjusting()) {
                if(list.isSelectionEmpty()) {
                    list.setSelectedValue(null, true);
                    DateSorterGUI.resetFields();
                    DateSorterGUI.deleteButton.setEnabled(false);
                }
                Date date = (Date) list.getSelectedValue();
                if(date != null) {
                    DateSorterGUI.inputFieldYear.setText(date.getYear());
                    DateSorterGUI.inputFieldMonth.setText(date.getMonth());
                    DateSorterGUI.inputFieldDay.setText(date.getDay());
            
                    DateSorterGUI.deleteButton.setEnabled(true);
                    
                }
            }
            flag = false;
        }
    }
}
