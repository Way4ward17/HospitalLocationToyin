package com.toyin.locatehospital.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.squareup.picasso.Picasso;
import com.toyin.locatehospital.R;
import com.toyin.locatehospital.models.DetailResult;
import com.toyin.locatehospital.models.DetailSinglePlace;
import com.toyin.locatehospital.rest_api.GooglePlacesApi;
import com.toyin.locatehospital.rest_api.HospitalListClient;


import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    GooglePlacesApi googlePlacesApi;
    HospitalListClient hospitalListClient;

    Context ctx;
    DetailSinglePlace place;

    public static final String TAG = "HL";

    ImageView imageView2;
    TextView tvName, tvAddress, tvPhone, tvWebsite, tvRating, tvOpening;
    Button btnCall, btnDirections;
    FrameLayout fader;
    CircularProgressIndicator avi;

    String placeId;
    double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        placeId = intent.getStringExtra("placeId");
        lat = intent.getDoubleExtra("latitude",28.5);
        lng = intent.getDoubleExtra("longitude",77);

        imageView2 = (ImageView) findViewById(R.id.imageView2);
        tvName = (TextView) findViewById(R.id.tvName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvRating = (TextView) findViewById(R.id.tvRating);
        tvWebsite = (TextView) findViewById(R.id.tvWebsite);
        tvOpening = (TextView) findViewById(R.id.tvOpening);
        btnCall = (Button) findViewById(R.id.btnCall);
        btnDirections = (Button) findViewById(R.id.btnDirections);

        fader = (FrameLayout) findViewById(R.id.fader);
        avi = (CircularProgressIndicator) findViewById(R.id.avi);
        setLoadingAnimation();

        ctx = this;
        googlePlacesApi = new GooglePlacesApi(ctx);
        hospitalListClient = googlePlacesApi.getHospitalListClient();

        getDetails(placeId);
    }

    void setDetails(){
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+place.getPhone()));
                startActivity(i);
            }
        });

        final String latitude = String.valueOf(lat), longitude = String.valueOf(lng);
        btnDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri navUri = Uri.parse("google.navigation:q="+latitude+','+longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        tvName.setText(place.getName());

        checkAndSet(tvAddress,place.getAddress());
        checkAndSet(tvPhone,place.getPhone());
        checkAndSet(tvWebsite,place.getWebsite());
        checkAndSet(tvRating,String.valueOf(place.getRating()));

        if(place.getWebsite() != null){
            tvWebsite.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            tvWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW);
                    webIntent.setData(Uri.parse(place.getWebsite()));
                    startActivity(webIntent);
                }
            });
        }

        String opening = "";
        if(place.getOpeningHours() != null) {
            for (String s : place.getOpeningHours().getWeekday()) {
                opening += s + "\n";
            }
            tvOpening.setText(opening);
        }
        else
        tvOpening.setText("-");

        stopLoadingAnimation();
    }

    void checkAndSet(TextView tv, String value){
        if(value == null)
            tv.setText("-");
        else tv.setText(value);
    }

    void getDetails(String placeId){
        HashMap<String,String> params = new HashMap<>();
        params.put("key", GooglePlacesApi.WEB_KEY);
        params.put("placeid",placeId);

        hospitalListClient.getHospitalDetails(params).enqueue(new Callback<DetailResult>() {
            @Override
            public void onResponse(Call<DetailResult> call, Response<DetailResult> response) {
                place = response.body().getResult();
                Toast.makeText(ctx,"HELLO",Toast.LENGTH_SHORT).show();
                if(place.getName().isEmpty()) {
                    ArrayList<DetailSinglePlace> sampleHospitals = new ArrayList<>();
                    sampleHospitals.add(createSampleHospital("1", "University College Hospital", "icon_url", "placeId_1", "Queen Elizabeth I Road, Agodi, Ibadan", 4.5f, "0700 000 0000", null, "http://www.uch-ibadan.org.ng", "http://www.uch-ibadan.org.ng"));
                    sampleHospitals.add(createSampleHospital("2", "UCH Private Suites", "icon_url", "placeId_2", "Queen Elizabeth I Road, Agodi, Ibadan", 4.2f, "0800 000 0001", null, "http://www.uchprivatesuites.org.ng", "http://www.uchprivatesuites.org.ng"));
                    sampleHospitals.add(createSampleHospital("3", "Ibadan Specialist Hospital", "icon_url", "placeId_3", "Oluyole Estate, Ibadan", 4.0f, "0900 000 0002", null, null, null));
                    sampleHospitals.add(createSampleHospital("4", "Adeoyo Maternity Hospital", "icon_url", "placeId_4", "Ring Road, Ibadan", 3.8f, "0900 000 0003", null, null, null));
                    sampleHospitals.add(createSampleHospital("5", "Jericho Specialist Hospital", "icon_url", "placeId_5", "Jericho, Ibadan", 4.3f, "0900 000 0004", null, null, null));
                    sampleHospitals.add(createSampleHospital("6", "Lifeforte Hospital", "icon_url", "placeId_6", "Bodija, Ibadan", 4.4f, "0900 000 0005", null, null, null));
                    sampleHospitals.add(createSampleHospital("7", "FMC Idi-Ayunre", "icon_url", "placeId_7", "Idi-Ayunre, Ibadan", 4.1f, "0900 000 0006", null, null, null));
                    sampleHospitals.add(createSampleHospital("8", "Reddington Hospital", "icon_url", "placeId_8", "Oluyole Estate, Ibadan", 4.6f, "0900 000 0007", null, null, null));
                    sampleHospitals.add(createSampleHospital("9", "Ibadan Central Hospital", "icon_url", "placeId_9", "Ososami Road, Ibadan", 4.0f, "0900 000 0008", null, null, null));
                    sampleHospitals.add(createSampleHospital("10", "The Vine Clinic", "icon_url", "placeId_10", "Dugbe, Ibadan", 4.1f, "0900 000 0009", null, null, null));

                    // Set the first sample hospital as the "place"
                    place = sampleHospitals.get(0);
                    Toast.makeText(ctx, response.body().getError_message(), Toast.LENGTH_SHORT).show();
                    setDetails();
                }else {
                    if(place.getPhotos() != null)
                    setPhoto(place.getPhotos().get(0).getPhotoReference());

                    setDetails();
                }
            }

            @Override
            public void onFailure(Call<DetailResult> call, Throwable t) {
//                Log.d(TAG, "onFailure: Unable to fetch details");
                Toast.makeText(ctx,"Unable to fetch details.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private DetailSinglePlace createSampleHospital(String id, String name, String icon, String placeId, String vicinity, float rating, String phone, ArrayList<String> weekday, String url, String website) {
        DetailSinglePlace sampleHospital = new DetailSinglePlace();
        sampleHospital.setId(id);
        sampleHospital.setName(name);
        sampleHospital.setIcon(icon);
        sampleHospital.setPlaceId(placeId);
        sampleHospital.setVicinity(vicinity);
        sampleHospital.setRating(rating);
        sampleHospital.setPhone(phone);
        sampleHospital.setUrl(url);
        sampleHospital.setWebsite(website);
        return sampleHospital;
    }


    void setLoadingAnimation(){
        fader.setVisibility(View.VISIBLE);
        avi.show();
    }

    void stopLoadingAnimation(){
        fader.setVisibility(View.GONE);
        avi.hide();
    }

    void setPhoto(String photoReference){
        if(photoReference == null)
            return;

        String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=1200&photoreference=" + photoReference +
                "&key=" + GooglePlacesApi.WEB_KEY;

        Picasso.with(ctx)
                .load(url)
                .fit()
                .placeholder(R.drawable.medical_placeholder)
                .into(imageView2);
    }
}
