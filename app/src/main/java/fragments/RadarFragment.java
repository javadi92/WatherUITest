package fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import saibaba.watheruitest.MainActivity;
import saibaba.watheruitest.R;
import utillities.CheckConnection;
import utillities.Url;

public class RadarFragment extends Fragment {

    public RadarFragment(){

    }

    private View view;
    public static WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_radar,container,false);
        if(new CheckConnection(getContext()).isNetworkConnected(getContext())){
            webView=(WebView)view.findViewById(R.id.webview);
            webView.clearCache(true);
            webView.clearHistory();
            webView.loadUrl(Url.getUrlRadar());
            webView.getSettings().setJavaScriptEnabled(true);
        }
        
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!new CheckConnection(getContext()).isNetworkConnected(getContext())){
            AlertDialog dialog1;
            AlertDialog.Builder builder1=new AlertDialog.Builder(getContext());
            builder1.setMessage("خطا در برقراری ارتباط! لطفا اتصال اینترنت خود را بررسی کنید");
            builder1.setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            dialog1=builder1.create();
            dialog1.show();
        }
    }
}
