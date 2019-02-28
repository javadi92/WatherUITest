package model;

public class HourlyModel {
    private String local_time;
    private int temp;
    private String description;
    private int icon_code;

    public String getLocal_time() {
        return local_time;
    }

    public void setLocal_time(String local_time) {
        this.local_time = local_time;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
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
