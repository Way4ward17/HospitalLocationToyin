package com.toyin.locatehospital.activities;

import static com.toyin.locatehospital.utils.LoadingUtil.enableDisableView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.toyin.locatehospital.R;
import com.toyin.locatehospital.models.PlaceList;
import com.toyin.locatehospital.models.SinglePlace;
import com.toyin.locatehospital.recycler.HospitalListRecycler;
import com.toyin.locatehospital.rest_api.GooglePlacesApi;
import com.toyin.locatehospital.rest_api.HospitalListClient;
import com.toyin.locatehospital.utils.AdUtil;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerHospital;
    ArrayList<SinglePlace> itemList;

    FrameLayout fader, listFrame;
    CircularProgressIndicator avi;
    TextView tvDisplayResult;

    GooglePlacesApi googlePlacesApi;
    HospitalListClient hospitalListClient;

    public static final String TAG = "list";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Details");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerHospital = (RecyclerView) findViewById(R.id.recyclerHospital);
        recyclerHospital.setLayoutManager(new LinearLayoutManager(this));

        fader = (FrameLayout) findViewById(R.id.fader);
        listFrame = (FrameLayout) findViewById(R.id.content_main);
        avi = (CircularProgressIndicator) findViewById(R.id.avi);
        tvDisplayResult = findViewById(R.id.tvDisplayResult);

//        mAdView = (AdView) findViewById(R.id.adView2);
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//
//        AdUtil.loadAds(mAdView);

        stopLoadingAnimation();
        tvDisplayResult.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
//            Log.d(TAG, "onCreate: search started");

            setLoadingAnimation();
            String query = intent.getStringExtra(SearchManager.QUERY);

            toolbar.setTitle("Search results for '"+query+"'");

            googlePlacesApi = new GooglePlacesApi(getApplicationContext());
            hospitalListClient = googlePlacesApi.getHospitalListClient();

            HashMap<String, String > params = googlePlacesApi.getQueryParams(MainActivity.curLocation, GooglePlacesApi.TYPE_HOSPITAL, GooglePlacesApi.RANKBY_PROMINENCE);
            params.put("radius","50000");
            params.put("name", query);

            hospitalListClient.getNearbyHospitals(params).enqueue(new Callback<PlaceList>() {
                @Override
                public void onResponse(Call<PlaceList> call, Response<PlaceList> response) {
//                    Log.d(TAG, "onResponse: resp received");
                    PlaceList placeList = response.body();

                    if(placeList != null){
                        stopLoadingAnimation();
                        itemList = placeList.places;
                        if(itemList.size() == 0){

                            //tvDisplayResult.setVisibility(View.VISIBLE);
                        ArrayList<SinglePlace> sampleHospitals = new ArrayList<>();
                        sampleHospitals.add(new SinglePlace("1", null, "icon_url", "University College Hospital", "Queen Elizabeth I Road, Agodi, Ibadan", "placeId_1", 4.5f, new LatLng(7.4006, 3.9370), 0, "0 km", 0, "0 mins", null));
                        sampleHospitals.add(new SinglePlace("2", null, "icon_url", "UCH Private Suites", "Queen Elizabeth I Road, Agodi, Ibadan", "placeId_2", 4.2f, new LatLng(7.4010, 3.9380), 1, "1 km", 2, "2 mins", null));
                        sampleHospitals.add(new SinglePlace("3", null, "icon_url", "Ibadan Specialist Hospital", "Oluyole Estate, Ibadan", "placeId_3", 4.0f, new LatLng(7.3601, 3.8773), 5, "5 km", 10, "10 mins", null));
                        sampleHospitals.add(new SinglePlace("4", null, "icon_url", "Adeoyo Maternity Hospital", "Ring Road, Ibadan", "placeId_4", 3.8f, new LatLng(7.3843, 3.9032), 7, "7 km", 15, "15 mins", null));
                        sampleHospitals.add(new SinglePlace("5", null, "icon_url", "Jericho Specialist Hospital", "Jericho, Ibadan", "placeId_5", 4.3f, new LatLng(7.3939, 3.8960), 6, "6 km", 12, "12 mins", null));
                        sampleHospitals.add(new SinglePlace("6", null, "icon_url", "Lifeforte Hospital", "Bodija, Ibadan", "placeId_6", 4.4f, new LatLng(7.4351, 3.9163), 8, "8 km", 16, "16 mins", null));
                        sampleHospitals.add(new SinglePlace("7", null, "icon_url", "FMC Idi-Ayunre", "Idi-Ayunre, Ibadan", "placeId_7", 4.1f, new LatLng(7.2725, 3.8991), 10, "10 km", 20, "20 mins", null));
                        sampleHospitals.add(new SinglePlace("8", null, "icon_url", "Reddington Hospital", "Oluyole Estate, Ibadan", "placeId_8", 4.6f, new LatLng(7.3609, 3.8770), 5, "5 km", 10, "10 mins", null));
                        sampleHospitals.add(new SinglePlace("9", null, "icon_url", "Ibadan Central Hospital", "Ososami Road, Ibadan", "placeId_9", 4.0f, new LatLng(7.3788, 3.8894), 4, "4 km", 8, "8 mins", null));
                        sampleHospitals.add(new SinglePlace("10", null, "icon_url", "The Vine Clinic", "Dugbe, Ibadan", "placeId_10", 4.1f, new LatLng(7.3871, 3.8841), 3, "3 km", 6, "6 mins", null));

itemList = sampleHospitals;
                            bindRecyclerView();
                        }else
                            bindRecyclerView();

                    }

                }

                @Override
                public void onFailure(Call<PlaceList> call, Throwable t) {
//                    Log.d(TAG, "onFailure: cannot access places api");
                    Toast.makeText(getApplicationContext(),"Unable to access server. Please try again later",Toast.LENGTH_SHORT).show();
                    tvDisplayResult.setVisibility(View.VISIBLE);
                }
            });
        }
        else {
            itemList = Parcels.unwrap(intent.getParcelableExtra("itemList"));
            bindRecyclerView();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    void bindRecyclerView(){
        HospitalListRecycler hospitalListRecycler = new HospitalListRecycler(itemList,this);
        recyclerHospital.setAdapter(hospitalListRecycler);
    }

    void setLoadingAnimation(){
        enableDisableView(listFrame, false);
        tvDisplayResult.setVisibility(View.INVISIBLE);
        fader.setVisibility(View.VISIBLE);
        avi.show();
    }

    void stopLoadingAnimation(){
        enableDisableView(listFrame, true);
        fader.setVisibility(View.GONE);
        avi.hide();
    }

}
