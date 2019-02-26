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

import adapters.DailyAdapter;
import model.DailyModel;
import saibaba.watheruitest.R;

public class DailyFragment extends Fragment {

    public static RecyclerView recyclerView;
    public static DailyAdapter dailyAdapter;
    public static List<DailyModel> dailyModels=new ArrayList<>();


    public DailyFragment() {
    }

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_daily,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recy_daily);
        dailyAdapter=new DailyAdapter(dailyModels,getContext());
        LinearLayoutManager llm=new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(dailyAdapter);
        return view;
    }
}
