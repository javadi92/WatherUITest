package utillities;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import adapters.DailyAdapter;
import adapters.HourlyAdapter;
import fragments.CurrentFragment;
import fragments.DailyFragment;
import fragments.HourlyFragment;
import model.DailyModel;
import model.HourlyModel;
import saibaba.watheruitest.R;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class VolleyRequest {

    private Context mContext;
    private RequestQueue queue;

    public VolleyRequest(Context context) {
        this.mContext = context;
    }

    ProgressDialog progressDialog;

    public void weatherbitJsonRequestCurrent() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Url.getBaseUrlCurrent_weatherBit(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //CurrentModel.setTemprature(response.getJSONObject("currently").getDouble("temperature"));
                    CurrentFragment.tvTemprature.setText(Math.round(response.getJSONArray("data").getJSONObject(0).getDouble("temp")) + "");
                    //CurrentFragment.tvCondition.setText(response.getJSONArray("data").getJSONObject(0).getJSONObject("weather").getString("description"));
                    double uv = (response.getJSONArray("data").getJSONObject(0).getDouble("uv"));
                    if (uv == 0.0) {
                        CurrentFragment.tvUV.setText("بی خطر");
                    }
                    if (uv > 0.0 && uv < 3.0) {
                        CurrentFragment.tvUV.setText("کم");
                    }
                    if (uv >= 3.0 && uv < 6.0) {
                        CurrentFragment.tvUV.setText("متوسط");
                    }
                    if (uv >= 6.0 && uv < 8.0) {
                        CurrentFragment.tvUV.setText("زیاد");
                    }
                    if (uv >= 8.0 && uv < 11.0) {
                        CurrentFragment.tvUV.setText("خیلی زیاد");
                    }
                    if (uv >= 11.0) {
                        CurrentFragment.tvUV.setText(" بسیار شدید");
                    }

                    int code = response.getJSONArray("data").getJSONObject(0).getJSONObject("weather").getInt("code");
                    CurrentFragment.tvCondition.setText(weatherbitDescription(code));
                    CurrentFragment.tvVisibility.setText(Math.round(response.getJSONArray("data").getJSONObject(0).getDouble("vis") * 1000) + " m");
                    CurrentFragment.tvWindSpeed.setText(Math.round(response.getJSONArray("data").getJSONObject(0).getDouble("wind_spd") * 3.6) + " km/h");
                    CurrentFragment.tvCloudsPercent.setText(Math.round(response.getJSONArray("data").getJSONObject(0).getDouble("clouds")) + " %");
                    //CurrentFragment.tvprecipProbability.setText(Math.round(response.getJSONArray("data").getJSONObject(0).getDouble("precip"))+" mm/h");
                    CurrentFragment.tvApparanetTemprature.setText(Math.round(response.getJSONArray("data").getJSONObject(0).getDouble("app_temp")) + "");
                    CurrentFragment.tvprecipRate.setText(response.getJSONArray("data").getJSONObject(0).getDouble("rh") + " %");
                    String icon_code = response.getJSONArray("data").getJSONObject(0).getJSONObject("weather").getString("icon");
                    //Picasso.get().load(Url.getImageUrl_weatherBit() + icon_code + ".png").into(CurrentFragment.imgWeatherState);
                    String s=icon_code.substring(icon_code.length()-1);
                    CurrentFragment.imgWeatherState.setImageResource(weatherbitIcon(code,s));
                    String timeZone = response.getJSONArray("data").getJSONObject(0).getString("timezone");
                    DateFormat utcFormat = new SimpleDateFormat("HH:mm");
                    utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    Date date_sunrise = utcFormat.parse(response.getJSONArray("data").getJSONObject(0).getString("sunrise"));
                    Date date_sunset = utcFormat.parse(response.getJSONArray("data").getJSONObject(0).getString("sunset"));
                    DateFormat pstFormat = new SimpleDateFormat("HH:mm");
                    pstFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
                    CurrentFragment.tvSunrise.setText(pstFormat.format(date_sunrise));
                    CurrentFragment.tvSunset.setText(pstFormat.format(date_sunset));
                    //weatherbitJsonRequestHourly();
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i("er1", e.getMessage());
                    progressDialog.dismiss();
                } catch (ParseException e) {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i("er2", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

        Volley.newRequestQueue(mContext).add(jsonObjectRequest);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("در حال دریافت اطلاعات ...");
        progressDialog.show();
    }

    public void weatherbitJsonRequestHourly() {
        JsonRequest jsonRequestHourly = new JsonObjectRequest(Request.Method.POST, Url.getBaseUrlHourly_weatherBit(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArrayHourly = response.getJSONArray("data");
                    List<HourlyModel> hourlyModel = new ArrayList<>();
                    hourlyModel.clear();
                    for (int i = 1; i < jsonArrayHourly.length(); i++) {
                        String local_time_first = jsonArrayHourly.getJSONObject(i).getString("timestamp_local");
                        String local_time = local_time_first.substring(11, local_time_first.length() - 3);
                        String tem = Math.round(jsonArrayHourly.getJSONObject(i).getDouble("temp"))+"";
                        int temp=Integer.parseInt(tem);
                        //String description = jsonArrayHourly.getJSONObject(i).getJSONObject("weather").getString("description");
                        int code=jsonArrayHourly.getJSONObject(i).getJSONObject("weather").getInt("code");
                        String dayOrNight=jsonArrayHourly.getJSONObject(i).getJSONObject("weather").getString("icon");
                        String s=dayOrNight.substring(dayOrNight.length()-1);
                        String description= weatherbitDescription(code);
                        int icon= weatherbitIcon(code,s);
                        HourlyModel hModel = new HourlyModel();
                        hModel.setLocal_time(local_time);
                        //hModel.setTemp(temp);
                        hModel.setDescription(description);
                        hModel.setIcon_code(icon);
                        hourlyModel.add(hModel);
                    }
                    HourlyFragment.modelList = hourlyModel;
                    HourlyFragment.hourlyAdapter = new HourlyAdapter(mContext, hourlyModel);
                    HourlyFragment.recyclerView.setAdapter(HourlyFragment.hourlyAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "2", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("error3", error.toString());
            }
        });
        Volley.newRequestQueue(mContext).add(jsonRequestHourly);
    }

    public void weatherbitJsonRequestDaily() {
        JsonRequest jsonRequestDaily = new JsonObjectRequest(Request.Method.POST, Url.getBaseUrlDaily_weatherBit(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArrayDaily = response.getJSONArray("data");
                    List<DailyModel> dailyModel = new ArrayList<>();
                    //dailyModel.clear();
                    for (int i = 1; i < jsonArrayDaily.length(); i++) {
                        //String local_time_first = jsonArrayDaily.getJSONObject(i).getString("timestamp_local");
                        //String local_time = local_time_first.substring(11, local_time_first.length() - 3);
                        //String tem = Math.round(jsonArrayDaily.getJSONObject(i).getDouble("temp"))+"";
                        String max_tem = Math.round(jsonArrayDaily.getJSONObject(i).getDouble("max_temp"))+"";
                        String min_tem = Math.round(jsonArrayDaily.getJSONObject(i).getDouble("min_temp"))+"";
                        //int temp=Integer.parseInt(tem);
                        int max_temp=Integer.parseInt(max_tem);
                        int min_temp=Integer.parseInt(min_tem);
                        String dateTime=jsonArrayDaily.getJSONObject(i).getString("datetime");
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date;
                        date = dateFormat.parse(dateTime);
                        PersianDate pdate = new PersianDate(date);
                        PersianDateFormat pdformater1 = new PersianDateFormat("Y/m/d");
                        //String description = jsonArrayDaily.getJSONObject(i).getJSONObject("weather").getString("description");
                        int code=jsonArrayDaily.getJSONObject(i).getJSONObject("weather").getInt("code");
                        //String dayOrNight=jsonArrayDaily.getJSONObject(i).getJSONObject("weather").getString("icon");
                        //String s=dayOrNight.substring(dayOrNight.length()-1);
                        String description= weatherbitDescription(code);
                        int icon= weatherbitIcon(code,"d");
                        DailyModel dModel = new DailyModel();
                        dModel.setDate(pdformater1.format(pdate));
                        //dModel.setTemp(temp);
                        dModel.setMax_temp(max_temp);
                        dModel.setMin_temp(min_temp);
                        dModel.setIcon_code(icon);
                        dailyModel.add(dModel);
                    }
                    DailyFragment.dailyModels=dailyModel;
                    DailyFragment.dailyAdapter = new DailyAdapter(dailyModel, mContext);
                    DailyFragment.recyclerView.setAdapter(DailyFragment.dailyAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("error3", error.toString());
            }
        });
        Volley.newRequestQueue(mContext).add(jsonRequestDaily);
    }

    private String weatherbitDescription(int code) {
        String des=null;
        switch (code) {
            case (200):
                des = "رعد و برق و باران";
                break;
            case (201):
                des = "رعد و برق و باران";
                break;
            case (202):
                des = "رعد و برق و باران";
                break;
            case (230):
                des = "رعد و برق و باران";
                break;
            case (231):
                des = "رعد و برق و باران";
                break;
            case (232):
                des = "رعد و برق و باران";
                break;
            case (233):
                des = "رعد و برق و تگرگ";
                break;
            case (300):
                des = "باران کم";
                break;
            case (301):
                des = "باران";
                break;
            case (302):
                des = "باران";
                break;
            case (500):
                des = "باران سبک";
                break;
            case (501):
                des = "بارانی";
                break;
            case (502):
                des = "باران شدید";
                break;
            case (511):
                des = "باران منحمد";
                break;
            case (520):
                des = "باران سبک";
                break;
            case (522):
                des = "بارانی";
                break;
            case (600):
                des = "برف سبک";
                break;
            case (601):
                des = "برفی";
                break;
            case (610):
                des = "برف و باران";
                break;
            case (611):
                des = "باران و یخ";
                break;
            case (612):
                des = "باران و یخ";
                break;
            case (621):
                des = "برفی";
                break;
            case (622):
                des = "برفی";
                break;
            case (623):
                des = "برفی";
                break;
            case (700):
                des = "مه پراکنده";
                break;
            case (711):
                des = "دود";
                break;
            case (721):
                des = "مه";
                break;
            case (731):
                des = "گرد و غبار";
                break;
            case (741):
                des = "مه";
                break;
            case (751):
                des = "مه و یخ";
                break;
            case (800):
                des = "صاف";
                break;
            case (801):
                des = "صاف تا پاره ای ابری";
                break;
            case (802):
                des = "ابری پراکنده";
                break;
            case (803):
                des = "نیمه ابری";
                break;
            case (804):
                des = "ابری";
                break;
            case (900):
                des = "غیر مشخص";
                break;
        }
        return des;
    }

    private int weatherbitIcon(int code, String s) {
        int icon=0;
        switch (code) {
                case (200):
                    icon=R.drawable.thunder_storms_and_rain;
                    break;
                case (201):
                    icon=R.drawable.thunder_storms_and_rain;
                    break;
                case (202):
                    icon=R.drawable.thunder_storms_and_rain;
                    break;
                case (230):
                    icon=R.drawable.thunder_storms_and_rain;
                    break;
                case (231):
                    icon=R.drawable.thunder_storms_and_rain;
                    break;
                case (232):
                    icon=R.drawable.thunder_storms_and_rain;
                    break;
                case (233):
                    icon=R.drawable.thunder_storms_and_rain;
                    break;
                case (300):
                    icon=R.drawable.low_rain;
                    break;
                case (301):
                    icon=R.drawable.rain;
                    break;
                case (302):
                    icon=R.drawable.rain;
                    break;
                case (500):
                    icon=R.drawable.low_rain;
                    break;
                case (501):
                    icon=R.drawable.rain;
                    break;
                case (502):
                    icon=R.drawable.rain;
                    break;
                case (511):
                    icon=R.drawable.snow_rain;
                    break;
                case (520):
                    icon=R.drawable.low_rain;
                    break;
                case (522):
                    icon=R.drawable.rain;
                    break;
                case (600):
                    icon=R.drawable.low_snow;
                    break;
                case (601):
                    icon=R.drawable.snow;
                    break;
                case (610):
                    icon=R.drawable.snow_rain;
                    break;
                case (611):
                    icon=R.drawable.snow_rain;
                    break;
                case (612):
                    icon=R.drawable.snow_rain;
                    break;
                case (621):
                    icon=R.drawable.snow;
                    break;
                case (622):
                    icon=R.drawable.snow;
                    break;
                case (623):
                    icon=R.drawable.snow;
                    break;
                case (700):
                    icon=R.drawable.fog;
                    break;
                case (711):
                    icon=R.drawable.smoke;
                    break;
                case (721):
                    icon=R.drawable.fog;
                    break;
                case (731):
                    icon=R.drawable.smoke;
                    break;
                case (741):
                    icon=R.drawable.fog;
                    break;
                case (751):
                    icon=R.drawable.fog;
                    break;
                case (800):
                    if(s.equals("d")){
                        icon=R.drawable.clear_day;
                    } else if (s.equals("n")) {
                        icon=R.drawable.clear_night;
                    }
                    break;
                case (801):
                    if (s.equals("d")) {
                        icon=R.drawable.clear_low_cloud_day;
                    } else if (s.equals("n")) {
                        icon=R.drawable.clear_low_cloud_night;
                    }
                    break;
                case (802):
                    if (s.equals("d")) {
                        icon=R.drawable.scatter_cloud_day;
                    } else if (s.equals("n")) {

                        icon=R.drawable.scatter_cloud_night;
                    }
                    break;
                case (803):
                    if (s.equals("d")) {
                        icon=R.drawable.scatter_cloud_day;
                    } else if (s.equals("n")) {

                        icon=R.drawable.scatter_cloud_night;
                    }
                    break;
                case (804):
                    icon=R.drawable.cloud;
                    break;
                case (900):
                    icon=R.drawable.unknown;
                    break;
        }
        return icon;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private HashMap<String,Integer> darkSkyDescription(String icon){
        HashMap<String,Integer> hashMap=new HashMap<String,Integer>();
        String des=null;
        switch (icon){
            case ("clear-day"):
                hashMap.put("صاف",R.drawable.clear_day);
                break;
            case ("clear-night"):
                hashMap.put("صاف",R.drawable.clear_night);
                break;
            case ("rain"):
                hashMap.put("بارانی",R.drawable.rain);
                break;
            case ("snow"):
                hashMap.put("برفی",R.drawable.snow);
                break;
            case ("sleet"):
                hashMap.put("باران و برف",R.drawable.snow_rain);
                break;
            case ("wind"):
                hashMap.put("باد",R.drawable.smoke);
                break;
            case ("fog"):
                hashMap.put("مه",R.drawable.fog);
                break;
            case ("cloudy"):
                hashMap.put("ابری",R.drawable.cloud);
                break;
            case ("partly-cloudy-day"):
                hashMap.put("صاف تا قسمتی ابری",R.drawable.scatter_cloud_day);
                break;
            case ("partly-cloudy-night"):
                hashMap.put("صاف تا قسمتی ابری",R.drawable.scatter_cloud_night);
                break;
        }
        return hashMap;
    }

    public void darkSkyJsonRequest(){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Url.getBaaseUrlCurrent_darkSky(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject=response.getJSONObject("currently");
                    String icon=jsonObject.getString("icon");
                    for(String key:darkSkyDescription(icon).keySet()){
                        CurrentFragment.tvCondition.setText(key);
                        CurrentFragment.imgWeatherState.setImageResource(darkSkyDescription(icon).get(key));
                    }
                    darkSkyDescription(icon);
                    int uvIndex=jsonObject.getInt("uvIndex");
                    double uv=uvIndex;
                    if (uv == 0.0) {
                        CurrentFragment.tvUV.setText("بی خطر");
                    }
                    if (uv > 0.0 && uv < 3.0) {
                        CurrentFragment.tvUV.setText("کم");
                    }
                    if (uv >= 3.0 && uv < 6.0) {
                        CurrentFragment.tvUV.setText("متوسط");
                    }
                    if (uv >= 6.0 && uv < 8.0) {
                        CurrentFragment.tvUV.setText("زیاد");
                    }
                    if (uv >= 8.0 && uv < 11.0) {
                        CurrentFragment.tvUV.setText("خیلی زیاد");
                    }
                    if (uv >= 11.0) {
                        CurrentFragment.tvUV.setText(" بسیار شدید");
                    }
                    double temperature=jsonObject.getDouble("temperature");
                    CurrentFragment.tvTemprature.setText(Math.round(temperature)+"");
                    double visibility=jsonObject.getDouble("visibility");
                    CurrentFragment.tvVisibility.setText(Math.round(visibility)+" Km");
                    double windSpeed=jsonObject.getDouble("windSpeed");
                    CurrentFragment.tvWindSpeed.setText(Math.round(windSpeed)+" km/h");
                    double cloudCover=jsonObject.getDouble("cloudCover");
                    CurrentFragment.tvCloudsPercent.setText(Math.round(cloudCover*100)+" %");
                    double apparentTemperature=jsonObject.getDouble("apparentTemperature");
                    CurrentFragment.tvApparanetTemprature.setText(Math.round(apparentTemperature)+"");
                    double humidity=jsonObject.getDouble("humidity");
                    CurrentFragment.tvprecipRate.setText(Math.round(humidity*100)+" %");
                    long sunriseTime=response.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getInt("sunriseTime");
                    long sunsetTime=response.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getInt("sunsetTime");
                    Date date = new Date(Long.valueOf(sunriseTime*1000L));
                    Date date2 = new Date(Long.valueOf(sunsetTime*1000L));
                    SimpleDateFormat myDate = new SimpleDateFormat("HH:mm");
                    myDate.setTimeZone(TimeZone.getTimeZone(response.getString("timezone")));
                    String formatted = myDate.format(date);
                    String formatted2 = myDate.format(date2);
                    CurrentFragment.tvSunrise.setText(formatted);
                    CurrentFragment.tvSunset.setText(formatted2);
                    /////////////////////////////////////////////
                    /////////////////////////////////////////////

                    JSONArray jsonArrayHourly=response.getJSONObject("hourly").getJSONArray("data");
                    List<HourlyModel> hourlyModel = new ArrayList<>();
                    hourlyModel.clear();
                    for (int i = 1; i < jsonArrayHourly.length(); i++) {
                        long local_time=jsonArrayHourly.getJSONObject(i).getLong("time")*1000L ;
                        Date date3 = new Date(local_time);
                        SimpleDateFormat myDate2 = new SimpleDateFormat("HH:mm");
                        myDate2.setTimeZone(TimeZone.getTimeZone(response.getString("timezone")));
                        String formatted3 = myDate2.format(date3);
                        double tem = Math.round(jsonArrayHourly.getJSONObject(i).getDouble("temperature"));
                        int temp= (int) Math.round(tem);
                        //String description = jsonArrayHourly.getJSONObject(i).getJSONObject("weather").getString("description");
                        icon=jsonArrayHourly.getJSONObject(i).getString("icon") ;
                        HourlyModel hModel = new HourlyModel();
                        hModel.setLocal_time(formatted3);
                        hModel.setTemp(temp);
                        for(String key:darkSkyDescription(icon).keySet()){
                            hModel.setDescription(key);
                            hModel.setIcon_code(darkSkyDescription(icon).get(key));
                        }
                        hourlyModel.add(hModel);
                    }
                    HourlyFragment.modelList = hourlyModel;
                    HourlyFragment.hourlyAdapter = new HourlyAdapter(mContext, hourlyModel);
                    HourlyFragment.recyclerView.setAdapter(HourlyFragment.hourlyAdapter);
                    /////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////

                    JSONArray jsonArrayDaily=response.getJSONObject("daily").getJSONArray("data");
                    List<DailyModel> dailyModel = new ArrayList<>();
                    //dailyModel.clear();
                    for (int i = 1; i < jsonArrayDaily.length(); i++) {
                        double max_tem = Math.round(jsonArrayDaily.getJSONObject(i).getDouble("temperatureMax"));
                        double min_tem = Math.round(jsonArrayDaily.getJSONObject(i).getDouble("temperatureMin"));
                        int max_temp=(int)max_tem;
                        int min_temp=(int)min_tem;
                        long dateTime=jsonArrayDaily.getJSONObject(i).getLong("time")*1000L;
                        PersianDate pdate = new PersianDate(dateTime);
                        PersianDateFormat pdformater1 = new PersianDateFormat("l Y/m/d");
                        //String description = jsonArrayDaily.getJSONObject(i).getJSONObject("weather").getString("description");
                        String ico=jsonArrayDaily.getJSONObject(i).getString("icon");
                        //String dayOrNight=jsonArrayDaily.getJSONObject(i).getJSONObject("weather").getString("icon");
                        //String s=dayOrNight.substring(dayOrNight.length()-1);
                        DailyModel dModel = new DailyModel();
                        dModel.setDate(pdformater1.format(pdate));
                        dModel.setMax_temp(max_temp);
                        dModel.setMin_temp(min_temp);
                        for(String key:darkSkyDescription(ico).keySet()){
                            dModel.setIcon_code(darkSkyDescription(ico).get(key));
                        }
                        dailyModel.add(dModel);
                    }
                    DailyFragment.dailyModels=dailyModel;
                    DailyFragment.dailyAdapter = new DailyAdapter(dailyModel, mContext);
                    DailyFragment.recyclerView.setAdapter(DailyFragment.dailyAdapter);

                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("errordark",e.getMessage());
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

            }
        });
        Volley.newRequestQueue(mContext).add(jsonObjectRequest);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("در حال دریافت اطلاعات ...");
        progressDialog.show();
    }
}