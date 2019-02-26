package fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import saibaba.watheruitest.R;

public class CurrentFragment extends Fragment {

    public static TextView tvCondition;
    public static TextView tvTemprature;
    public static TextView tvUV;
    public static TextView tvVisibility;
    public static TextView tvWindSpeed;
    public static TextView tvCloudsPercent;
    public static TextView tvSunrise;
    public static TextView tvSunset;
    public static TextView tvApparanetTemprature;
    public static TextView tvprecipRate;
    public static ImageView imgWeatherState;

    public CurrentFragment(){

    }

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_current,container,false);

        tvCondition=(TextView)view.findViewById(R.id.tv_condition);
        tvTemprature=(TextView)view.findViewById(R.id.tv_temprature);
        tvUV=(TextView)view.findViewById(R.id.tv_uv);
        tvVisibility =(TextView)view.findViewById(R.id.tv_view_filed);
        tvWindSpeed =(TextView)view.findViewById(R.id.tv_wind_speed);
        tvCloudsPercent =(TextView)view.findViewById(R.id.tv_clouds_percent);
        tvSunrise=(TextView)view.findViewById(R.id.tv_sunrise);
        tvSunset=(TextView)view.findViewById(R.id.tv_sunset);
        tvApparanetTemprature=(TextView)view.findViewById(R.id.tv_apparanet_temprature);
        tvprecipRate =(TextView)view.findViewById(R.id.tv_rain_rate);
        imgWeatherState=(ImageView)view.findViewById(R.id.imageView);
        return view;
    }

}
