package dateSorter;

public class Date {
    private String day;
    private String month;
    private String year;
    
    public Date(String day, String month, String year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return this.day;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonth() {
        return this.month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return this.year;
    }

    @Override
    public String toString() {
        return (this.getYear()+"-"+this.getMonth()+"-"+this.getDay());
    }
}
