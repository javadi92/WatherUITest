package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import model.DailyModel;
import saibaba.watheruitest.R;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.myViewHolder> {

    Context mContext;
    List<DailyModel> dailyModels;

    public DailyAdapter(List<DailyModel> dailyModels,Context context){
        this.dailyModels=dailyModels;
        this.mContext=context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.card_daily,viewGroup,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {
        //myViewHolder.tvTemp.setText(dailyModels.get(i).getTemp()+"");
        myViewHolder.tvMaxTemp.setText(dailyModels.get(i).getMax_temp()+"");
        myViewHolder.tvMinTemp.setText(dailyModels.get(i).getMin_temp()+"");
        myViewHolder.tvDate.setText(dailyModels.get(i).getDate());
        myViewHolder.imgWeatherState.setImageResource(dailyModels.get(i).getIcon_code());

    }

    @Override
    public int getItemCount() {
        return dailyModels.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        //TextView tvTemp;
        TextView tvMaxTemp;
        TextView tvMinTemp;
        TextView tvDate;
        ImageView imgWeatherState;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            //tvTemp=(TextView)itemView.findViewById(R.id.tv_temp);
            tvMaxTemp=(TextView)itemView.findViewById(R.id.tv_max_temp);
            tvMinTemp=(TextView)itemView.findViewById(R.id.tv_min_temp);
            tvDate=(TextView)itemView.findViewById(R.id.tv_date);
            imgWeatherState=(ImageView)itemView.findViewById(R.id.img_weather_state_card_daily);
        }
    }
}
