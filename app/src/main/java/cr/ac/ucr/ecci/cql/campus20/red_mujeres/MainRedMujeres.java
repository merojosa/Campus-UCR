package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.listeners.NavigationListener;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cr.ac.ucr.ecci.cql.campus20.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;


// Imports especificos de Directions API
// Clases para calcular una ruta
// Imports necesarios para la interfaz de usuario de navegacion
// Imports especificos de Directions API

public class MainRedMujeres extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener,  PermissionsListener, NavigationListener {

    static final int REQUEST_SELECT_CONTACT = 1;
    // Variables para agregar el mapa y la capa de localizacion
    private MapboxMap mapboxMap;
    private MapView mapView;
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;

    // Variables para calcular y dibujar una ruta
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;

    private Handler handler;
    private Runnable runnable;

    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";

    private List<Map<String, Object>> map = new ArrayList<>();

    // Botón para inicializar navegación
    private Button button;

    //Instancia de compartir
    private String message = "Hola! Te comparto la ruta que planeo seguir. ";
    private Double latitudDes;
    private Double longitudDes;
    private Double latitudOri;
    private Double longitudOri;

    // Despliegue mapa al llamar a la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.MAPBOX_ACCESS_TOKEN)); //Tomar el token
        setContentView(R.layout.activity_red_mujeres);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        //MapboxNavigation navigation = new MapboxNavigation(context, R.string.MAPBOX_ACCESS_TOKEN);
    }

    // Determinar estilo de mapa y como reacciona a interacciones con el usuario
    // En este caso, si el usuario hace tap en un punto del mapa se despliegan
    // funcionalidades del Navigation API, trazando una ruta sugerida al destino desde el punto actual.
    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        MainRedMujeres.this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/streets-v11"), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                //Estilo cargado y mapa está listo
                enableLocationComponent(style);

                addDestinationIconSymbolLayer(style);
                //getGroupMembersPositions();

                mapboxMap.addOnMapClickListener(MainRedMujeres.this);
                button = findViewById(R.id.startButton);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iniciarRuta();
                    }
                });

                //Botón de visibilidad de la localización del usuario
                FloatingActionButton fab = findViewById(R.id.floatingActionButton);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (locationComponent.isLocationComponentEnabled()) {
                            locationComponent.setLocationComponentEnabled(false);
                            fab.setImageResource(R.drawable.closed);
                        } else {
                            locationComponent.setLocationComponentEnabled(true);
                            fab.setImageResource(R.drawable.open);
                        }
                    }
                });

                //Botón de visibilidad de la localización del usuario
                FloatingActionButton share = findViewById(R.id.shareTrip);
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupCompartir();
                    }
                });

            }
        });
    }

    public void iniciarRuta() {
        boolean simulateRoute = false; //Simulación de ruta para testing
        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                .directionsRoute(currentRoute)
                .shouldSimulateRoute(simulateRoute)
                .build();

        NavigationLauncher.startNavigation(MainRedMujeres.this, options);
    }

    public void popupCompartir() {
        // create a dialog with AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(MainRedMujeres.this, R.style.AppTheme_RedMujeres);

        builder.setTitle("Compartir viaje");
        builder.setMessage("¿Desea compartir el viaje con sus contactos?");

        String positiveText = "Sí";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: Refactor
                        enviarWhatsapp(message);
                        enviarSMS(message);
                    }
                });

        //No se desea compartir el viaje, cerramos el pop up
        String negativeText = "No";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void enviarWhatsapp(String message) {
        PackageManager pm = getPackageManager();
        String text = message + "http://maps.google.com/maps?saddr=" + latitudOri + "," + longitudOri + "&daddr=" + latitudDes + "," + longitudDes;
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");

            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Verifica que la app esté instalada
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Compartir con"));

        } catch (PackageManager.NameNotFoundException e) {
            //TODO:Mejorar mensaje de error
            Context context = getApplicationContext();
            CharSequence error = "Whatsapp no está instalado";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, error, duration);
            toast.show();
        }
    }

    public void enviarSMS(String message) {
        String text = message + "http://maps.google.com/maps?saddr=" + latitudOri + "," + longitudOri + "&daddr=" + latitudDes + "," + longitudDes;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("smsto:"));  // This ensures only SMS apps respond
        intent.putExtra("sms_body", text);
        // intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    // Iconos de navegacion
    private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("destination-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);
    }

    // Se determina un punto de origen y destino para navegacion
    // a partir de interaccion de usuario con el mapa
    @SuppressWarnings({"MissingPermission"})
    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        //LocationComponent locationComponent = null;
        latitudDes = point.getLatitude();
        longitudDes = point.getLongitude();
        latitudOri = locationComponent.getLastKnownLocation().getLatitude();
        longitudOri = locationComponent.getLastKnownLocation().getLongitude();
        Point destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());

        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            source.setGeoJson(Feature.fromGeometry(destinationPoint));
        }

        // Llamado a calculo de ruta con puntos de origen y destino establecidos
        getRoute(originPoint, destinationPoint);
        button.setEnabled(true);
        button.setBackgroundResource(R.color.mapboxBlue);
        return true;
    }

    // Calculo de ruta entre dos puntos
    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .profile(DirectionsCriteria.PROFILE_WALKING)
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        // You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

                        // Draw the route on the map
                        if (navigationMapRoute != null) {
                            //navigationMapRoute.removeRoute(); //Deprecated
                            navigationMapRoute.updateRouteVisibilityTo(false);
                            navigationMapRoute.updateRouteArrowVisibilityTo(false);
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);

                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }


    // Manejo de permisos para location services
    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // Get an instance of the component
            locationComponent = mapboxMap.getLocationComponent();

            // Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationComponent(mapboxMap.getStyle());
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onCancelNavigation() {
        Toast.makeText(this, "So you gonna cancel this whole thing?", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNavigationFinished() {
        Toast.makeText(this, "And we're done!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNavigationRunning() {
        // Pregunta a usuario si desea compartir ruta con personas de confianza
        FragmentManager fm = getSupportFragmentManager();
        CompartirRutaFragment alertDialog = CompartirRutaFragment.newInstance("Compartir ruta?");
        alertDialog.show(fm, "fragment_compartir_ruta");
    }


    @Override
    @SuppressWarnings({"MissingPermission"})
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
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void getGroupMembersPositions() {

        //Solicidtamos a la base de datos informacion del grupo
        FireBaseRedMujeres db = new FireBaseRedMujeres();
        db.fetchGroupAsync("GrupoEj");
        // Un handler para preguntar a la BD cad cierta cantidad de tiempo.
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //si he recuperado datos, los marco en el mapa
                if (db.userArr.size() > 0) {
                    map = db.userArr;
                    addMarkers(map);
                    //volvemos a consultar por cambios
                    db.fetchGroupAsync("GroupEj");
                }
                //pregunto cada medio segundo
                handler.postDelayed(this, 2000);
            }
        };

        // The first time this runs we don't need a delay so we immediately post.
        handler.post(runnable);
    }

    //Recibe mapa que devuelve la base de datos con las posiciones de cada miembro del equipo
    // Marca en el mapa las posiciones de estos
    private void addMarkers(List<Map<String, Object>> map) {
        List<Feature> symbolLayerIconFeatureList = new ArrayList<>();

        for (int i = 0; i < map.size(); ++i) {
            symbolLayerIconFeatureList.add(Feature.fromGeometry(
                    Point.fromLngLat((Double) map.get(i).get("Longitud"), (Double) map.get(i).get("Latitud"))));
        }

        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/streets-v11")

                .withImage(ICON_ID, BitmapFactory.decodeResource(
                        MainRedMujeres.this.getResources(), R.drawable.mapbox_marker_icon_default))

                .withSource(new GeoJsonSource(SOURCE_ID,
                        FeatureCollection.fromFeatures(symbolLayerIconFeatureList)))


                .withLayer(new SymbolLayer(LAYER_ID, SOURCE_ID)
                        .withProperties(
                                iconImage(ICON_ID),
                                iconAllowOverlap(true),
                                iconIgnorePlacement(true),
                                iconOffset(new Float[]{0f, -9f}))
                ), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                // Map is set up and the style has loaded. Now you can add additional data or make other map adjustments.


            }
        });
    }
}