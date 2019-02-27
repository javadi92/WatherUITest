package utillities;

public class Url {

    private static String baseUrlCurrent ="https://api.weatherbit.io/v2.0/current?lat=29&lon=60&key=24e7e853118e4b6594ffb9fbbfa16c31";
    private static String baseUrlHourly="https://api.weatherbit.io/v2.0/forecast/hourly?lat=29&lon=60&key=24e7e853118e4b6594ffb9fbbfa16c31&hours=24";
    private static String baseUrlDayly="https://api.weatherbit.io/v2.0/forecast/daily?lat=29&lon=60&key=24e7e853118e4b6594ffb9fbbfa16c31";
    private static String imageUrl="https://www.weatherbit.io/static/img/icons/";
    private static String urlRadar="https://www.ventusky.com/?p=9;55;2&l=temperature-2m ";

    public static String getUrlRadar() {
        return urlRadar;
    }

    public static void setUrlRadar(String urlRadar) {
        Url.urlRadar = urlRadar;
    }

    public static String getBaseUrlHourly() {
        return baseUrlHourly;
    }

    public static void setBaseUrlHourly(String baseUrlHourly) {
        Url.baseUrlHourly = baseUrlHourly;
    }

    public static String getBaseUrlCurrent() {
        return baseUrlCurrent;
    }

    public static void setBaseUrlCurrent(String baseUrlCurrent) {
        Url.baseUrlCurrent = baseUrlCurrent;
    }

    public static String getBaseUrlDayly() {
        return baseUrlDayly;
    }

    public static void setBaseUrlDayly(String baseUrlDayly) {
        Url.baseUrlDayly = baseUrlDayly;
    }

    public static String getImageUrl() {
        return imageUrl;
    }

    public static void setImageUrl(String imageUrl) {
        Url.imageUrl = imageUrl;
    }
}
