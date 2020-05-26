package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

// Imports especificos de Directions API
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import android.graphics.Color;
import timber.log.Timber;

import android.widget.ProgressBar;
import android.widget.Toast;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

// Clases para calcular una ruta
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.listeners.NavigationListener;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigation;
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigationOptions;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;

// Imports necesarios para la interfaz de usuario de navegacion
import android.view.View;
import android.widget.Button;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.v5.routeprogress.ProgressChangeListener;
import com.mapbox.services.android.navigation.v5.routeprogress.RouteProgress;
import com.mapbox.services.android.navigation.v5.utils.LocaleUtils;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;

// Imports especificos de Directions API
import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

public class MainRedMujeres extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener,  PermissionsListener, NavigationListener {

    // Variables para agregar el mapa y la capa de localizacion
    private MapboxMap mapboxMap;
    private MapView mapView;
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;

    // Variables para calcular y dibujar una ruta
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;

//    // Builder para cambiar perfil de navegacion
//    private MapboxDirections mapboxDirections;
//    private MapboxDirections.Builder directionsBuilder;

    // Botón para inicializar navegación
    private Button button;

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

                mapboxMap.addOnMapClickListener(MainRedMujeres.this);
                button = findViewById(R.id.startButton);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        // Pregunta a usuario si desea compartir ruta con personas de confianza
//                        FragmentManager fm = getSupportFragmentManager();
//                        CompartirRutaFragment alertDialog = CompartirRutaFragment.newInstance("Compartir ruta?");
//                        alertDialog.show(fm, "fragment_compartir_ruta");

                        //*PENDIENTE*dialogo debe manejar respuesta afirmativa/negativa y LUEGO llamar a navegacion
                        // posiblemente se deba colocar en el metodo de despliegue de navegacion del listener
                        // al implementar la navegación con navigationView

                        boolean simulateRoute = false; //Simulación de ruta para testing
                        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                .directionsRoute(currentRoute)
                                .shouldSimulateRoute(simulateRoute)
                                .build();

                        NavigationLauncher.startNavigation(MainRedMujeres.this, options);
                    }
                });

                //Botón de visibilidad de la localización del usuario
                FloatingActionButton fab = findViewById(R.id.floatingActionButton);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(locationComponent.isLocationComponentEnabled()){
                            locationComponent.setLocationComponentEnabled(false);
                            fab.setImageResource(R.drawable.closed);
                        }
                        else {
                            locationComponent.setLocationComponentEnabled(true);
                            fab.setImageResource(R.drawable.open);
                        }
                    }
                });

            }

        });
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
    @SuppressWarnings( {"MissingPermission"})
    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        //LocationComponent locationComponent = null;
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
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
    // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this))
        {
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
        }
        else
        {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain)
    {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted)
    {
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
    @SuppressWarnings( {"MissingPermission"})
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

//    public static final class Builder {
//        private final MapboxDirections.Builder directionsBuilder;
//
//        private Builder() {
//            directionsBuilder = MapboxDirections.builder();
//        }
//
//        public Builder Profile(@NonNull @DirectionsCriteria.ProfileCriteria String profile) {
//            directionsBuilder.profile(profile);
//            return this;
//        }
//
//        public static Builder builder(Context context) {
//            return builder(context, new LocaleUtils());
//        }
//
//        static Builder builder(Context context, LocaleUtils localeUtils) {
//            return new Builder()
//                    .Profile(DirectionsCriteria.PROFILE_WALKING);
//        }
//
//    }

}


//********************************************************************************************
//DIRECTIONS API TEST
//********************************************************************************************
//public class MainRedMujeres extends AppCompatActivity {
//
//    private static final String ROUTE_LAYER_ID = "route-layer-id";
//    private static final String ROUTE_SOURCE_ID = "route-source-id";
//    private static final String ICON_LAYER_ID = "icon-layer-id";
//    private static final String ICON_SOURCE_ID = "icon-source-id";
//    private static final String RED_PIN_ICON_ID = "red-pin-icon-id";
//    private MapView mapView;
//    private DirectionsRoute currentRoute;
//    private MapboxDirections client;
//    private Point origin;
//    private Point destination;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//// Mapbox access token is configured here. This needs to be called either in your application
//// object or in the same activity which contains the mapview.
//        Mapbox.getInstance(this, getString(R.string.MAPBOX_ACCESS_TOKEN));
//
//// This contains the MapView in XML and needs to be called after the access token is configured.
//        setContentView(R.layout.activity_red_mujeres);
//
//// Setup the MapView
//        mapView = findViewById(R.id.mapView);
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(@NonNull MapboxMap mapboxMap) {
//                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
//                    @Override
//                    public void onStyleLoaded(@NonNull Style style) {
//// Set the origin location to the Alhambra landmark in Granada, Spain.
//                        origin = Point.fromLngLat(-3.588098, 37.176164);
//
//// Set the destination location to the Plaza del Triunfo in Granada, Spain.
//                        destination = Point.fromLngLat(-3.601845, 37.184080);
//
//                        initSource(style);
//
//                        initLayers(style);
//
//// Get the directions route from the Mapbox Directions API
//                        getRoute(mapboxMap, origin, destination);
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * Add the route and marker sources to the map
//     */
//    private void initSource(@NonNull Style loadedMapStyle) {
//        loadedMapStyle.addSource(new GeoJsonSource(ROUTE_SOURCE_ID));
//
//        GeoJsonSource iconGeoJsonSource = new GeoJsonSource(ICON_SOURCE_ID, FeatureCollection.fromFeatures(new Feature[] {
//                Feature.fromGeometry(Point.fromLngLat(origin.longitude(), origin.latitude())),
//                Feature.fromGeometry(Point.fromLngLat(destination.longitude(), destination.latitude()))}));
//        loadedMapStyle.addSource(iconGeoJsonSource);
//    }
//
//    /**
//     * Add the route and marker icon layers to the map
//     */
//    private void initLayers(@NonNull Style loadedMapStyle) {
//        LineLayer routeLayer = new LineLayer(ROUTE_LAYER_ID, ROUTE_SOURCE_ID);
//
//// Add the LineLayer to the map. This layer will display the directions route.
//        routeLayer.setProperties(
//                lineCap(Property.LINE_CAP_ROUND),
//                lineJoin(Property.LINE_JOIN_ROUND),
//                lineWidth(5f),
//                lineColor(Color.parseColor("#009688"))
//        );
//        loadedMapStyle.addLayer(routeLayer);
//
//// Add the red marker icon image to the map
//        loadedMapStyle.addImage(RED_PIN_ICON_ID, BitmapUtils.getBitmapFromDrawable(
//                getResources().getDrawable(R.drawable.closed)));
//
//// Add the red marker icon SymbolLayer to the map
//        loadedMapStyle.addLayer(new SymbolLayer(ICON_LAYER_ID, ICON_SOURCE_ID).withProperties(
//                iconImage(RED_PIN_ICON_ID),
//                iconIgnorePlacement(true),
//                iconAllowOverlap(true),
//                iconOffset(new Float[] {0f, -9f})));
//    }
//
//    /**
//     * Make a request to the Mapbox Directions API. Once successful, pass the route to the
//     * route layer.
//     * @param mapboxMap the Mapbox map object that the route will be drawn on
//     * @param origin      the starting point of the route
//     * @param destination the desired finish point of the route
//     */
//    private void getRoute(MapboxMap mapboxMap, Point origin, Point destination) {
//        client = MapboxDirections.builder()
//                .origin(origin)
//                .destination(destination)
//                .overview(DirectionsCriteria.OVERVIEW_FULL)
//                .profile(DirectionsCriteria.PROFILE_DRIVING)
//                .accessToken(getString(R.string.MAPBOX_ACCESS_TOKEN))
//                .build();
//
//        client.enqueueCall(new Callback<DirectionsResponse>() {
//            @Override
//            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
//// You can get the generic HTTP info about the response
//                Timber.d("Response code: " + response.code());
//                if (response.body() == null) {
//                    Timber.e("No routes found, make sure you set the right user and access token.");
//                    return;
//                } else if (response.body().routes().size() < 1) {
//                    Timber.e("No routes found");
//                    return;
//                }
//
//// Get the directions route
//                currentRoute = response.body().routes().get(0);
//
//// Make a toast which displays the route's distance
//                Toast.makeText(MainRedMujeres.this, String.format(
//                        getString(R.string.directions_activity_toast_message),
//                        currentRoute.distance()), Toast.LENGTH_SHORT).show();
//
//                if (mapboxMap != null) {
//                    mapboxMap.getStyle(new Style.OnStyleLoaded() {
//                        @Override
//                        public void onStyleLoaded(@NonNull Style style) {
//
//// Retrieve and update the source designated for showing the directions route
//                            GeoJsonSource source = style.getSourceAs(ROUTE_SOURCE_ID);
//
//// Create a LineString with the directions route's geometry and
//// reset the GeoJSON source for the route LineLayer source
//                            if (source != null) {
//                                source.setGeoJson(LineString.fromPolyline(currentRoute.geometry(), PRECISION_6));
//                            }
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
//                Timber.e("Error: " + throwable.getMessage());
//                Toast.makeText(MainRedMujeres.this, "Error: " + throwable.getMessage(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mapView.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mapView.onStop();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//// Cancel the Directions API request
//        if (client != null) {
//            client.cancelCall();
//        }
//        mapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }
//}
