package fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import adapters.HourlyAdapter;
import model.HourlyModel;
import saibaba.watheruitest.R;
import utillities.VolleyRequest;

public class HourlyFragment extends Fragment {

    public static RecyclerView recyclerView;
    public static HourlyAdapter hourlyAdapter;
    public static List<HourlyModel> modelList=new ArrayList<>();

    public HourlyFragment() {
    }

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_hourly,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recy_hourly);
        //new VolleyRequest(getContext())._jsonRequestHourly();
        hourlyAdapter=new HourlyAdapter(getContext(),modelList);
        LinearLayoutManager llm=new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(hourlyAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
