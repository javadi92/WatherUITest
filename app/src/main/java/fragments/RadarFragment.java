package fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import saibaba.watheruitest.R;
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
        webView=(WebView)view.findViewById(R.id.webview);
        webView.clearCache(true);
        webView.clearHistory();
        webView.loadUrl(Url.getUrlRadar());
        webView.getSettings().setJavaScriptEnabled(true);
        return view;
    }
}
