package saibaba.watheruitest;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import adapters.ViewPagerAdapter;
import fragments.CurrentFragment;
import fragments.DailyFragment;
import fragments.HourlyFragment;
import fragments.RadarFragment;
import google.PlaceAutocompleteAdapter;
import utillities.Url;
import utillities.VolleyRequest;

public class MainActivity extends AppCompatActivity {

    protected GeoDataClient mGeoDataClient2;
    private PlaceAutocompleteAdapter mAdapter2;
    private AutoCompleteTextView mAutocompleteView2;
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY2 = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));
    Toolbar toolbar;
    TabLayout tabLayout;
    NonSwipeableViewPager nonSwipeableViewPager;
    //NonSwipeableViewPager nonSwipeableViewPager;
    ViewPagerAdapter viewPagerAdapter;
    ImageButton imgSearch;
    boolean errorCheck=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGeoDataClient2 = Places.getGeoDataClient(this, null);
        setContentView(R.layout.activity_main);

        _initViews();

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();

        mAutocompleteView2.setHint("نام شهر مورد نظر خود را وارد کنید");
        mAutocompleteView2.setSingleLine();
        mAutocompleteView2.setOnItemClickListener(mAutocompleteClickListener2);
        mAdapter2 = new PlaceAutocompleteAdapter(this, mGeoDataClient2, BOUNDS_GREATER_SYDNEY2, typeFilter);
        mAutocompleteView2.setAdapter(mAdapter2);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _cityChange();
            }
        });
        setSupportActionBar(toolbar);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new CurrentFragment(),"هوای فعلی");
        viewPagerAdapter.AddFragment(new HourlyFragment(),"پیش بینی ساعتی");
        viewPagerAdapter.AddFragment(new DailyFragment(),"پیش بینی روزانه");
        viewPagerAdapter.AddFragment(new RadarFragment(),"رادار");
        nonSwipeableViewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(nonSwipeableViewPager);
        tabLayout.setTabTextColors(Color.WHITE,Color.YELLOW);
        nonSwipeableViewPager.setOffscreenPageLimit(3);

        nonSwipeableViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                /*if(i==1 || i==2){
                    imgSearch.setVisibility(View.GONE);
                }
                if(i==0){
                    imgSearch.setVisibility(View.VISIBLE);
                }*/

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }

        });
    }

    private void _initViews(){
        mAutocompleteView2=new AutoCompleteTextView(this);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        tabLayout=(TabLayout)findViewById(R.id.tab_layout);
        nonSwipeableViewPager =(NonSwipeableViewPager) findViewById(R.id.view_pager);
        imgSearch=(ImageButton) findViewById(R.id.img_search);
    }

    private void _fillViews(){

    }

    AlertDialog dialog;

    public void _cityChange(){

        AlertDialog.Builder builder;
        builder=new AlertDialog.Builder(MainActivity.this);
        TextView title = new TextView(this);
// You Can Customise your Title here
        title.setText("انتخاب شهر");
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.RIGHT);
        title.setTextSize(20);
        builder.setTitle("انتخاب شهر");
        builder.setCustomTitle(title);
        if(mAutocompleteView2.getParent()!=null){
            ((ViewGroup)mAutocompleteView2.getParent()).removeView(mAutocompleteView2); // <- fix
        }
        builder.setView(mAutocompleteView2);

        builder.setNegativeButton("لغو", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog=builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener2
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            final AutocompletePrediction item2 = mAdapter2.getItem(position);
            final String placeId2 = item2.getPlaceId();
            final CharSequence primaryText2 = item2.getPrimaryText(null);

            //Log.i(TAG, "Autocomplete item selected: " + primaryText);

            /*
             Issue a request to the Places Geo Data Client to retrieve a Place object with
             additional details about the place.
              */
            Task<PlaceBufferResponse> placeResult = mGeoDataClient2.getPlaceById(placeId2);
            placeResult.addOnCompleteListener(mUpdatePlaceDetailsCallback);

            //Toast.makeText(getApplicationContext(), "Clicked: " + place2.getLatLng().toString(),Toast.LENGTH_SHORT).show();

            if(errorCheck==false){
                mAutocompleteView2.setText(primaryText2.toString());
            }
            //Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
        }
    };

    private OnCompleteListener<PlaceBufferResponse> mUpdatePlaceDetailsCallback
            = new OnCompleteListener<PlaceBufferResponse>() {
        @Override
        public void onComplete(Task<PlaceBufferResponse> task) {
            try {
                PlaceBufferResponse places2 = task.getResult();

                // Get the Place object from the buffer.
                final Place place2 = places2.get(0);
                //Toast.makeText(MainActivity.this,place2.getLatLng().latitude+"",Toast.LENGTH_LONG).show();

                String lat=place2.getLatLng().latitude+"";
                String lon=place2.getLatLng().longitude+"";

                toolbar.setTitle(place2.getName());
                toolbar.setTitleTextColor(Color.WHITE);
                //tvCityName.setText(place2.getName());

                Url.setBaseUrlCurrent("https://api.weatherbit.io/v2.0/current?lat="+lat+"&lon="+lon+"&key=24e7e853118e4b6594ffb9fbbfa16c31");
                Url.setBaseUrlHourly("https://api.weatherbit.io/v2.0/forecast/hourly?lat="+lat+"&lon="+lon+"&key=24e7e853118e4b6594ffb9fbbfa16c31&hours=24");
                Url.setBaseUrlDayly("https://api.weatherbit.io/v2.0/forecast/daily?lat="+lat+"&lon="+lon+"&key=24e7e853118e4b6594ffb9fbbfa16c31");
                //Url.setUrlRadar("https://www.windy.com/-Temperature-temp?temp,"+lat+","+lon);
                if(mAutocompleteView2.getText().toString().length()>0){
                    new VolleyRequest(MainActivity.this)._jsonRequestCurrent();
                    new VolleyRequest(MainActivity.this)._jsonRequestHourly();
                    new VolleyRequest(MainActivity.this)._jsonRequestDaily();
                }
                else {
                    Toast.makeText(MainActivity.this,"نام شهر نمیتواند خالی باشد",Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
                //Toast.makeText(MainActivity.this,place2.getLatLng().latitude+"",Toast.LENGTH_LONG).show();
                // Format details of the place for display and show it in a TextView.
                final CharSequence thirdPartyAttribution = places2.getAttributions();

                places2.release();

            } catch (RuntimeRemoteException e) {
                // Request did not complete successfully
                //Log.e(TAG, "Place query did not complete.", e);
                return;
            }
        }
    };
}
