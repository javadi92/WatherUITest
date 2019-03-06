package utillities;

public class Url {

    private static String baseUrlCurrent_weatherBit ="https://api.weatherbit.io/v2.0/current?lat=29&lon=60&key=24e7e853118e4b6594ffb9fbbfa16c31";
    private static String baseUrlHourly_weatherBit ="https://api.weatherbit.io/v2.0/forecast/hourly?lat=29&lon=60&key=24e7e853118e4b6594ffb9fbbfa16c31&hours=24";
    private static String baseUrlDaily_weatherBit ="https://api.weatherbit.io/v2.0/forecast/daily?lat=29&lon=60&key=24e7e853118e4b6594ffb9fbbfa16c31";
    private static String imageUrl_weatherBit ="https://www.weatherbit.io/static/img/icons/";
    private static String urlRadar="https://www.ventusky.com/?p=9;55;2&l=temperature-2m ";

    private static String baaseUrlCurrent_darkSky="https://api.darksky.net/forecast/918bd678391742f5c06a4ed7215aca75/37.8267,-122.4233?units=si";


    public static String getBaaseUrlCurrent_darkSky() {
        return baaseUrlCurrent_darkSky;
    }

    public static void setBaaseUrlCurrent_darkSky(String baaseUrlCurrent_darkSky) {
        Url.baaseUrlCurrent_darkSky = baaseUrlCurrent_darkSky;
    }

    public static String getUrlRadar() {
        return urlRadar;
    }

    public static void setUrlRadar(String urlRadar) {
        Url.urlRadar = urlRadar;
    }

    public static String getBaseUrlHourly_weatherBit() {
        return baseUrlHourly_weatherBit;
    }

    public static void setBaseUrlHourly_weatherBit(String baseUrlHourly_weatherBit) {
        Url.baseUrlHourly_weatherBit = baseUrlHourly_weatherBit;
    }

    public static String getBaseUrlCurrent_weatherBit() {
        return baseUrlCurrent_weatherBit;
    }

    public static void setBaseUrlCurrent_weatherBit(String baseUrlCurrent_weatherBit) {
        Url.baseUrlCurrent_weatherBit = baseUrlCurrent_weatherBit;
    }

    public static String getBaseUrlDaily_weatherBit() {
        return baseUrlDaily_weatherBit;
    }

    public static void setBaseUrlDaily_weatherBit(String baseUrlDaily_weatherBit) {
        Url.baseUrlDaily_weatherBit = baseUrlDaily_weatherBit;
    }

    public static String getImageUrl_weatherBit() {
        return imageUrl_weatherBit;
    }

    public static void setImageUrl_weatherBit(String imageUrl_weatherBit) {
        Url.imageUrl_weatherBit = imageUrl_weatherBit;
    }
}
