package model;

public class DailyModel {

    private String date;
    //private int temp;
    private int max_temp;
    private int min_temp;
    private String description;
    private int icon_code;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //public int getTemp() {
        //return temp;
    //}

    //public void setTemp(int temp) {
        //this.temp = temp;
    //}

    public int getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(int max_temp) {
        this.max_temp = max_temp;
    }

    public int getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(int min_temp) {
        this.min_temp = min_temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIcon_code() {
        return icon_code;
    }

    public void setIcon_code(int icon_code) {
        this.icon_code = icon_code;
    }
}
