package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import model.HourlyModel;
import saibaba.watheruitest.R;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.myViewHolder> {

    Context context;
    List<HourlyModel> modelList;

    public HourlyAdapter(Context context,List<HourlyModel> modelList){
        this.context=context;
        this.modelList=modelList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.card_hourly,viewGroup,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {
        myViewHolder.localTime.setText(modelList.get(i).getLocal_time());
        myViewHolder.tvDescription.setText(modelList.get(i).getDescription());
        myViewHolder.tvTemprature.setText(modelList.get(i).getTemp()+"");
        myViewHolder.imgState.setImageResource(modelList.get(i).getIcon_code());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        public TextView tvTemprature;
        public TextView tvDescription;
        public TextView localTime;
        public ImageView imgState;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription=(TextView)itemView.findViewById(R.id.tv_description_hourly_card);
            tvTemprature=(TextView)itemView.findViewById(R.id.tv_temprature_hourly_card);
            localTime=(TextView)itemView.findViewById(R.id.tv_local_time_hourly_card);
            imgState=(ImageView)itemView.findViewById(R.id.img_state_hourly_card);
        }
    }
}
