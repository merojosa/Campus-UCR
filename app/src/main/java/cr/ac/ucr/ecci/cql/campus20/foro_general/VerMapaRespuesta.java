package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;

public class VerMapaRespuesta extends AppCompatActivity {

    private MapboxMap mapboxMap;
    double lat;
    double lon;
    Marker marcador;
    MapboxMapOptions options;

    //En la UCR
    private static double latInicial = 9.9373255;
    private static double lonInicial = -84.0515752;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mapa_respuesta);

        Intent mIntent = getIntent();
        lat = mIntent.getDoubleExtra("latitud", 0.0);
        lon = mIntent.getDoubleExtra("longitud", 0.0);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.MAPBOX_ACCESS_TOKEN));

        // Create supportMapFragment
        SupportMapFragment mapFragment;
        if (savedInstanceState == null) {

            // Create fragment
            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Build mapboxMap
            options = MapboxMapOptions.createFromAttributes(this, null);
            options.camera(new CameraPosition.Builder()
                    .target(new LatLng(lat, lon))
                    .zoom(15)
                    .build());

            // Create map fragment
            mapFragment = SupportMapFragment.newInstance(options);

            // Add map fragment to parent container
            transaction.add(R.id.container, mapFragment, "com.mapbox.map");
            transaction.commit();
        } else {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentByTag("com.mapbox.map");
        }

        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull MapboxMap mapboxMap) {
                    VerMapaRespuesta.this.mapboxMap = mapboxMap;
                    VerMapaRespuesta.this.mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
                            // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                            agregarMarcador(lat, lon);
                        }
                    });
                }
            });
        }
    }

    private void agregarMarcador(double lat, double lon) {
        marcador = this.mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lon))
                .title("Marcador"));
    }
}

