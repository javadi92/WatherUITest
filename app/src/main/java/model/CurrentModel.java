package model;

public class CurrentModel {

    private static String imageUrl;

    public static String getCondition() {
        return condition;
    }

    public static void setCondition(String condition) {
        CurrentModel.condition = condition;
    }

    private static String condition;
    private static double temprature;
    private static int uv;
    private static int visibility;
    private static double windSpeed;
    private static double humidity;
    private static int sunrise;
    private static int sunset;
    private static double apparenTemprature;
    private static double precipProbability;

    public static String getImageUrl() {
        return imageUrl;
    }

    public static void setImageUrl(String imageUrl) {
        CurrentModel.imageUrl = imageUrl;
    }

    public static double getTemprature() {
        return temprature;
    }

    public static void setTemprature(double temprature) {
        CurrentModel.temprature = temprature;
    }

    public static int getUv() {
        return uv;
    }

    public static void setUv(int uv) {
        CurrentModel.uv = uv;
    }

    public static int getVisibility() {
        return visibility;
    }

    public static void setVisibility(int visibility) {
        CurrentModel.visibility = visibility;
    }

    public static double getWindSpeed() {
        return windSpeed;
    }

    public static void setWindSpeed(double windSpeed) {
        CurrentModel.windSpeed = windSpeed;
    }

    public static double getHumidity() {
        return humidity;
    }

    public static void setHumidity(double humidity) {
        CurrentModel.humidity = humidity;
    }

    public static int getSunrise() {
        return sunrise;
    }

    public static void setSunrise(int sunrise) {
        CurrentModel.sunrise = sunrise;
    }

    public static int getSunset() {
        return sunset;
    }

    public static void setSunset(int sunset) {
        CurrentModel.sunset = sunset;
    }

    public static double getApparenTemprature() {
        return apparenTemprature;
    }

    public static void setApparenTemprature(double apparenTemprature) {
        CurrentModel.apparenTemprature = apparenTemprature;
    }

    public static double getPrecipProbability() {
        return precipProbability;
    }

    public static void setPrecipProbability(double precipProbability) {
        CurrentModel.precipProbability = precipProbability;
    }
}
