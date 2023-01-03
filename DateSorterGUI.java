package dateSorter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.border.TitledBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

public class DateSorterGUI {
    protected static JFrame mainFrame = new JFrame("Date Sorter");
    protected static DefaultListModel<Date> historyModel = new DefaultListModel<>();
    protected static JList<Date> dateHistory = new JList<Date>(historyModel);
    private JPanel inputPanel, buttonPanel, leftPanel, rightPanel, contentPanel;
    private JScrollPane scrollHistory;
    protected static JButton saveButton, deleteButton, exportButton;
    private JLabel year, month, day;
    protected static JTextField inputFieldYear, inputFieldMonth, inputFieldDay;
    private TitledBorder leftPanelBorder = BorderFactory.createTitledBorder("Date Details");
    private TitledBorder buttonBorder = BorderFactory.createTitledBorder("Functionality");
    private TitledBorder rightPanelBorder = BorderFactory.createTitledBorder("Date History");
    private JMenuBar menuBar;
    private JMenu menuOne, menuTwo;
    private JMenuItem about, statistics, quarterlyReport;

    public JFrame initialiseWindow() {
        mainFrame.setSize(1000, 500);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setJMenuBar(initialiseMenuBar());
        mainFrame.add(initialiseContentPanel(), BorderLayout.CENTER);
        mainFrame.setVisible(true);
        return mainFrame;
    }

    public JMenuBar initialiseMenuBar() {
        menuBar = new JMenuBar();
        menuOne = new JMenu("Application");
        menuTwo = new JMenu("Statistics");

        about = new JMenuItem("About");
        about.addActionListener(e -> applicationInfo());

        statistics = new JMenuItem("Statistics");
        statistics.addActionListener(e -> generateStatistics());

        quarterlyReport = new JMenuItem("Quarterly Report");
        quarterlyReport.addActionListener(e -> generateQuarterlyReport());

        menuOne.add(about);
        menuTwo.add(statistics);
        menuTwo.add(quarterlyReport);
        menuBar.add(menuOne);
        menuBar.add(menuTwo);
        return menuBar;
    }

    public JPanel initialiseContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setSize(1000, 500);
        contentPanel.setLayout(new GridLayout(1, 2));
        contentPanel.add(initialiseLeftPanel());
        contentPanel.add(initialiseListPanel());
        contentPanel.setVisible(true);
        return contentPanel;
    }

    public JPanel initialiseLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(2, 1, 0, 5));
        leftPanel.setSize(500, 500);

        leftPanel.add(initialiseInputPanel());
        leftPanel.add(initialiseButtonPanel());

        return leftPanel;
    }

    public JPanel initialiseInputPanel() {
        inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBorder(leftPanelBorder);
        inputPanel.setSize(500, 250);

        year = new JLabel("Year");
        year.setBounds(10, 25, 50, 40);
        month = new JLabel("Month");
        month.setBounds(10, 75, 50, 40);
        day = new JLabel("Day");
        day.setBounds(10, 125, 50, 40);

        inputFieldYear = new JTextField();
        inputFieldYear.setBounds(65, 25, 400, 40);
        inputFieldMonth = new JTextField();
        inputFieldMonth.setBounds(65, 75, 400, 40);
        inputFieldDay = new JTextField();
        inputFieldDay.setBounds(65, 125, 400, 40);

        inputPanel.add(year);
        inputPanel.add(inputFieldYear);
        inputPanel.add(month);
        inputPanel.add(inputFieldMonth);
        inputPanel.add(day);
        inputPanel.add(inputFieldDay);

        return inputPanel;
    }

    public JPanel initialiseButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBorder(buttonBorder);
        buttonPanel.setSize(500, 250);

        saveButton = new JButton("Save Date");
        saveButton.setBounds(10, 25, 250, 40);
        saveButton.addActionListener(e -> saveDate());
        deleteButton = new JButton("Delete");
        deleteButton.setBounds(10, 75, 250, 40);
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(new DateDeleteListener());
        exportButton = new JButton("Export");
        exportButton.setBounds(10, 125, 250, 40);
        exportButton.addActionListener(e -> exportData());

        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exportButton);

        return buttonPanel;
    }

    public JScrollPane initialiseListPanel() {
        rightPanel = new JPanel(new GridLayout(1,1));
        rightPanel.setSize(500, 500);
        rightPanel.setBorder(rightPanelBorder);
        rightPanel.add(dateHistory);

        dateHistory.addListSelectionListener(new DateListListener());

        scrollHistory = new JScrollPane(rightPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollHistory.setVisible(true);
        return scrollHistory;
    }

    public static void resetFields() {
        inputFieldYear.setText("");
        inputFieldMonth.setText("");
        inputFieldDay.setText("");
    }

    public void saveDate() {
        String year = inputFieldYear.getText();
        String month = inputFieldMonth.getText();
        String day = inputFieldDay.getText();
        ArrayList<Date> storedDates = new ArrayList<>();
        Integer modelSize = historyModel.size();

        for(int i = 0; i < modelSize; i++) {
            storedDates.add(historyModel.getElementAt(i));
        }

        if(DateSorterUtilities.validateInputFields()) {
            Date date = new Date(day, month, year);
            if(dateHistory.isSelectionEmpty() == false) {
                Date storedDate = (Date) dateHistory.getSelectedValue();
                Integer storedDateLocation = dateHistory.getSelectedIndex();
                if(historyModel.contains(storedDate)) {
                    storedDate.setYear(year);
                    storedDate.setMonth(month);
                    storedDate.setDay(day);
                    historyModel.setElementAt(storedDate, storedDateLocation);
                    dateHistory.setSelectedValue(null, true);
                    resetFields();
                    DateSorterUtilities.sortDates(historyModel);
                }
            }
            else {
                historyModel.addElement(date);
                resetFields();
                DateSorterUtilities.sortDates(historyModel);
            }  
        }
        else {
            JOptionPane.showMessageDialog(mainFrame, "Invalid data entered into the fields. Re-enter the data.");
        }
    }

    public void applicationInfo() {
        JOptionPane.showMessageDialog(mainFrame, "Application version Alpha 0.1.2");
    }

    public void generateStatistics() {
        String[] data = DateSorterUtilities.countNumberOfDates();
        String dataString= "Data Statistics\n\nNumber of dates: "+data[0]+"\n\nMost recent date: "+data[1]+"\n\nOldest date: "+data[2];

        JOptionPane.showMessageDialog(mainFrame, dataString);
    }

    public void generateQuarterlyReport() {
        String data = DateSorterUtilities.quarterlyStatistics();

        JOptionPane.showMessageDialog(mainFrame, data);
    }

    public void exportData() {

        String fileName = JOptionPane.showInputDialog("Enter the file name that the data will be saved to.");
        if(fileName.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "No file name given, cancelling function...");
        }
        else {
            DateSorterUtilities.export(fileName);
            JOptionPane.showMessageDialog(mainFrame, "Export successful.");
        }
    }
}
