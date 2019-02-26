package model;

public class DailyModel {

    private String date;
    private Double temp;
    private Double max_temp;
    private Double min_temp;
    private String description;
    private int icon_code;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(Double max_temp) {
        this.max_temp = max_temp;
    }

    public Double getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(Double min_temp) {
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
