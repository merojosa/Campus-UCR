package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Mapbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.CoffeShop.CoffeViewActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools.SchoolViewActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Coordinate;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.School;
import cr.ac.ucr.ecci.cql.campus20.R;

public class Map extends AppCompatActivity {
    private MapView mapView;

    TextView textViewName;
    TextView textViewNameBody;
    TextView textViewDescriptionKey;
    TextView textViewDescriptionValue;
    TextView textViewScheduleKey;
    TextView textViewScheduleValue;
    TextView textViewPhoneKey;
    TextView textViewPhoneValue;
    TextView textViewFacebook;
    TextView textViewTwitter;
    TextView textViewInstragram;
    TextView textViewWebPage;

    private double latitude;
    private double longitude;
    private String name;
    private int type;
    Map mapboxMap;

    School place;
    Coordinate coordinate;

    private Intent details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.MAPBOX_ACCESS_TOKEN));

        setContentView(R.layout.activity_map);

        Intent intentItemList = getIntent();
        String itemTitle = intentItemList.getStringExtra(Intent.EXTRA_TEXT);

        // Getting the Coordinates and School objects
        this.place = getIntent().getParcelableExtra("place");
        this.coordinate = getIntent().getParcelableExtra("coordinate");
        // -------------------------------------------

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(itemTitle);
            getSupportActionBar().show();
        }

        this.settingNames();

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                    }
                });

            }
        });
    }

    private void settingNames() {

        textViewName =          (TextView)findViewById(R.id.nameKey);
        textViewNameBody =      (TextView)findViewById(R.id.nameValue);

        textViewDescriptionKey =    (TextView)findViewById(R.id.descriptionKey);
        textViewDescriptionValue =  (TextView)findViewById(R.id.descriptionValue);
        textViewScheduleKey =       (TextView)findViewById(R.id.scheduleKey);
        textViewScheduleValue =     (TextView)findViewById(R.id.scheduleValue);
        textViewPhoneKey =          (TextView)findViewById(R.id.phoneKey);
        textViewPhoneValue =        (TextView)findViewById(R.id.phoneValue);

        /*textViewFacebook =          (TextView)findViewById(R.id.facebook);
        textViewTwitter =           (TextView)findViewById(R.id.twitter);
        textViewInstragram =        (TextView)findViewById(R.id.instagram);
        textViewWebPage =           (TextView)findViewById(R.id.website);*/


        textViewName.setText(R.string.placeNameKey);
        textViewNameBody.setText(place.getName());
        textViewDescriptionKey.setText(R.string.placeDescriptionKey);
        textViewDescriptionValue.setText(place.getDescription());
        textViewScheduleKey.setText(R.string.placeScheduleKey);
        textViewScheduleValue.setText("7:00 am -- 7:00 pm");
        textViewPhoneKey.setText("Teléfono");
        textViewPhoneValue.setText("2511 4000");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        Intent intent = getIntent();
        int tipo = intent.getExtras().getInt("typeActivity");


        getMenuInflater().inflate(R.menu.go_ip_details_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.go_IP_Details);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Según tipo de actividad se selecciona el activity correcto
                if (tipo == 0) {
                    details = new Intent(Map.this, CoffeViewActivity.class);
                }else if(tipo == 1){
                    details = new Intent(Map.this, SchoolViewActivity.class);
                }

                details.putExtra(Intent.EXTRA_TEXT, getSupportActionBar().getTitle());
                startActivity(details);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}
