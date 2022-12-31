package dateSorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.DefaultListModel;

public class DateSorterUtilities {

  public static void export(String fileName) {
    List<String> dates = new ArrayList<>();
    Integer modelSize = DateSorterGUI.historyModel.size();
    String fileWExtension = fileName+".csv";

    dates.add("Date History");

    for(int i = 0; i < modelSize; i++) {
      dates.add(DateSorterGUI.historyModel.getElementAt(i).toString());
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileWExtension))) {
      for (String row : dates) {
        boolean first = true;
          if (!first) {
            writer.write(",");
          }
          writer.write(row+",");
          first = false;
        
        writer.newLine();
      }
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  public static void sortDates(DefaultListModel<Date> list) {
    Integer modelSize = list.getSize();
    List<Date> tempArray = new ArrayList<>();

    for(int i = 0; i < modelSize; i++) {
      tempArray.add(list.getElementAt(i));
    }
    
    Collections.sort(tempArray, new Comparator<Date>() {
      public int compare(Date d1, Date d2) {
        if(d1.toString() == null || d2.toString() == null) {
          return 0;
        }
        return d1.toString().compareTo(d2.toString());
      }
    });

    for(int i = 0; i < modelSize; i++) {
      list.setElementAt(tempArray.get(i), i);
    }
  }

  public static Boolean validateInputFields() {
    String yearValidate = DateSorterGUI.inputFieldYear.getText();
    String monthValidate = DateSorterGUI.inputFieldMonth.getText();
    String dayValidate = DateSorterGUI.inputFieldDay.getText();

    Integer[] days30 = {4, 6, 9, 11};

    try {
      Integer yearInt = Integer.parseInt(yearValidate);
      Integer monthInt = Integer.parseInt(monthValidate);
      Integer dayInt = Integer.parseInt(dayValidate);

      if(dayValidate.length() == 2) {
        if(monthValidate.length() == 2) {
          if(yearInt > 1900 && yearInt < 10000) {
            if(monthInt > 0 && monthInt < 13) {
              if(monthInt == 2 && dayInt > 0 && dayInt < 30) {
                return true;
              }
              else if(Arrays.binarySearch(days30, monthInt) > 0) {
                if(dayInt > 0 && dayInt < 31) {
                  return true;
                }
              }
              else if(Arrays.binarySearch(days30, monthInt) < 0 && monthInt != 2 && dayInt > 0 && dayInt <32) {
                return true;
              }
            }
          }
        }
      }
    }
    catch (NumberFormatException exception) {
      return false;
    }
    return false;
  }

}
