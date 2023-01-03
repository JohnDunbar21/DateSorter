package dateSorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

  public static String[] countNumberOfDates() {
    ArrayList<String> dates = new ArrayList<>();
    Integer modelSize = DateSorterGUI.historyModel.getSize();
    Integer numberOfDates = 0;
    String[] returnValues = new String[3];
    String newestDate = "";
    String oldestDate = "";

    if(modelSize != null) {
      for(int i = 0; i < modelSize; i++) {
        dates.add(DateSorterGUI.historyModel.getElementAt(i).toString());
        numberOfDates += 1;
      }
    }

    if(dates.size() > 0) {
      newestDate = Collections.max(dates);
      oldestDate = Collections.min(dates);
    }

    returnValues[0] = numberOfDates.toString();
    returnValues[1] = newestDate;
    returnValues[2] = oldestDate;

    return returnValues;
  }

  public static String quarterlyStatistics() {
    ArrayList<Date> dates = new ArrayList<>();
    Integer modelSize = DateSorterGUI.historyModel.getSize();

    for(int i = 0; i < modelSize; i++) {
      dates.add(DateSorterGUI.historyModel.getElementAt(i));
    }

    String currentYear = "";
    if(DateSorterGUI.historyModel.getSize() > 0) {
       currentYear = DateSorterGUI.historyModel.lastElement().getYear();
    }

    Integer[][] quarterlyLimits = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
    Integer[] quarterly = {0, 0, 0, 0};

    for(int i = 0; i < dates.size(); i++) {
      if(dates.get(i) != null) {
        if(dates.get(i).getYear() == currentYear) {
          Integer temp = Integer.parseInt(dates.get(i).getMonth());
          for(Integer j = 0; j < quarterlyLimits.length; j++) {
            for(Integer k = 0; k < quarterlyLimits[j].length; k++) {
              if(temp.equals(quarterlyLimits[j][k])) {
                quarterly[j] += 1;
              }
            }
          }
        }
      }

    }

    String result = "Quarterly Statistics\n\nFirst Quarter Dates: "+quarterly[0].toString()+"\n\nSecond Quarter Dates: "+quarterly[1].toString()+"\n\nThird Quarter Dates: "+quarterly[2].toString()+"\n\nFourth Quarter Dates: "+quarterly[3].toString();
    return result;
  }
}
