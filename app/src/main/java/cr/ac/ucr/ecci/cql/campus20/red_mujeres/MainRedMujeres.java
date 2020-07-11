package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
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
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;

import cr.ac.ucr.ecci.cql.campus20.FirebaseListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;

// Imports necesarios para la interfaz de usuario de navegacion
import android.view.View;
import android.widget.Button;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import cr.ac.ucr.ecci.cql.campus20.CampusBD;
import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.FirebaseListener;
import cr.ac.ucr.ecci.cql.campus20.LoginActivity;
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
// Imports para animación de ruta


public class MainRedMujeres extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener,  PermissionsListener, NavigationListener {

    // Variables para agregar el mapa y la capa de localizacion
    private MapboxMap mapboxMap;
    private MapView mapView;
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;

    private String targerUser = null;
    private String targerUserName = null;
    List<String> params;

    // Variables para calcular y dibujar una ruta
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;

    private Handler handler;
    private Runnable runnable;

    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";

    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    private LocationEngine locationEngine;

    public DatabaseReference grupo;
    public DatabaseReference usuarios;
    private FirebaseDatabase mDatabase =  FirebaseDatabase.getInstance();;
    private FireBaseRedMujeres bd = new FireBaseRedMujeres();

    public  Double lastLatitudeKnown = 0.0;
    public Double lastLongitudeKnown = 0.0;

    ArrayList<Map<String, Object>> userArr;
    ArrayList<Map<String, Object>> groupArr;
    ArrayList<Map<String, Object>> usersLocations;

    String userID =  null;
    private Queue<Map<String, Object>> locationsQueue;

    //Instancia de compartir
    private String message = "Hola! Te comparto la ruta que planeo seguir. ";
    private Double latitudDes;
    private Double longitudDes;
    private Double latitudOri;
    private Double longitudOri;

    private Double userLat = 0.0;
    private Double userLong = 0.0;

    int posiciones = 0;

    private MainActivityLocationCallback callback = new MainActivityLocationCallback(this);

    // Botón para inicializar navegación
    private Button button;

    // Despliegue mapa al llamar a la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUserID();
        //deleteCoordinates();
        setParameters();
        saveDestination(1.0,1.0);
        userArr = new ArrayList<>();
        groupArr = new ArrayList<>();
        usersLocations = new ArrayList<>();

        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.MAPBOX_ACCESS_TOKEN)); //Tomar el token
        setContentView(R.layout.activity_red_mujeres);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        //MapboxNavigation navigation = new MapboxNavigation(context, R.string.MAPBOX_ACCESS_TOKEN);
    }

    private void setParameters(){
        //extraemos los parametros de con la informacion de quien nos compartio el viaje
        Intent intent2 = getIntent();
        Bundle intent = getIntent().getExtras();
        assert intent != null;
        try {
            targerUser = intent.getString("id");
            targerUserName = intent.getString("nombre");
        }catch (Exception e){
            Log.d("error","wao");
        }

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
                //setEmergencyPhone();
                getGroupMembersPositions();
                notificacionUnir();

                mapboxMap.addOnMapClickListener(MainRedMujeres.this);


                button = findViewById(R.id.startButton);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupRutaComunidades();
                    }
                });

                //Botón de visibilidad de la localización del usuario
                FloatingActionButton fab = findViewById(R.id.floatingActionButton);
                fab.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("MissingPermission")
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

                //Botón para compartir ruta
                FloatingActionButton share = findViewById(R.id.shareTrip);
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupCompartir();
                    }
                });

                //Botón de pánico
                FloatingActionButton sos = findViewById(R.id.sos);
                sos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setUp();
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

    public void popupRutaComunidades() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainRedMujeres.this, R.style.AppTheme_RedMujeres);

        builder.setTitle("Enviar ruta a la comunidad");
        builder.setMessage("¿Desea que las personas de la comunidad puedan unirse a su ruta?");

        String positiveText = "Sí";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enviarNotificacion();
                        setBotonEnRuta();
                    }
                });

        //No se desea compartir el viaje, cerramos el pop up
        String negativeText = "No";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setBotonEnRuta();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void setBotonEnRuta(){
        button.setBackgroundResource(R.color.verde_UCR);
        button.setText("En ruta");
    }

    public void enviarNotificacion() {
        DatabaseReference db = mDatabase.getReference("Comunidades").child("GrupoEj");
        db.child("driverID").setValue(userID);
        db.child("EnRuta").setValue(true);
        db.child("EnRuta").setValue(false);
    }

    private void notificacionUnir() {
        NotificacionUnirse not = new NotificacionUnirse();

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // Se manda a correr el servicio
                not.setUnirseNotificacionListener(getApplicationContext(), userID);
                // Se pregunta cada 20 segundos por alguna nueva respuesta
                handler.postDelayed(this, 5000);
            }
        };
        // The first time this runs we don't need a delay so we immediately post.
        handler.post(runnable);
    }

    public void panico(int truePanic, String numero) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        String num = numero;
        if (truePanic==0){
            num="911";
        }

        callIntent.setData(Uri.parse("tel:"+num));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            startActivity(callIntent);
        }
    }

    public void setUp() {
        DatabaseReference root = mDatabase.getReference();
        String currentUser = new MenuRedMujeres().getCurrentUserID();
        bd.autCallback(root, new FirebaseListener() {
            @Override
            public void exito(DataSnapshot dataSnapshot) {
                String nombre =  (String) dataSnapshot.child("usuarios_red_mujeres").child(currentUser).child("ContactoConfianza").child("Nombre").getValue();
                String numero =  (String) dataSnapshot.child("usuarios_red_mujeres").child(currentUser).child("ContactoConfianza").child("Numero").getValue();
                popupPanico(nombre,numero);

            }

            @Override
            public void fallo(DatabaseError databaseError) {

            }
        });

    }

    public void popupPanico(String nombre, String numero) {

        final String [] items = new String[] {"911", nombre};

        AlertDialog.Builder builder = new AlertDialog.Builder(MainRedMujeres.this, R.style.AppTheme_RedMujeres);
        builder.setTitle("¡EMERGENCIA!");

        builder.setIcon(R.drawable.sos);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                panico(which, numero);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    //Pop up para obtener confirmación de usuario respecto a si desea compartir su ruta
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
                        boolean valido = coordenadasValidas(latitudOri, longitudOri, latitudDes, longitudDes);
                        if(valido) {
                            enviarWhatsapp(message);
                            enviarSMS(message);
                        } else {
                            Context context = getApplicationContext();
                            CharSequence error = "Debés seleccionar la ruta antes de compartir.";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, error, duration);
                            toast.show();
                        }
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

    //Método para compartir ruta por medio de WhatsApp
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

    public boolean coordenadasValidas(Double latitudOri, Double longitudOri, Double latitudDes, Double longitudDes) {
        if(longitudDes == null || latitudDes == null || longitudOri == null || latitudOri == null) {
            return false;
        } else {
            return true;
        }
    }

    //Método para compartir ruta por medio de mensajes de texto
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
    @SuppressWarnings( {"MissingPermission"})
    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        latitudOri = locationComponent.getLastKnownLocation().getLatitude();
        longitudOri = locationComponent.getLastKnownLocation().getLongitude();
        latitudDes = point.getLatitude();
        longitudDes = point.getLongitude();
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
        saveDestination(latitudDes,longitudDes);
        button.setEnabled(true);
        button.setBackgroundResource(R.color.naranja_UCR);
        button.setText("Iniciar Viaje");
        button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_navigation, 0);
        return true;
    }

    private void saveDestination(Double laititude, Double longitude){
        DatabaseReference ref = mDatabase.getReference("usuarios_red_mujeres");
        ref.child(this.userID).child("latDestino").setValue(laititude);
        ref.child(this.userID).child("longDestino").setValue(longitude);
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
            initLocationEngine();
        }
        else
        {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
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
        //FragmentManager fm = getSupportFragmentManager();
        //CompartirRutaFragment alertDialog = CompartirRutaFragment.newInstance("Compartir ruta?");
        //alertDialog.show(fm, "fragment_compartir_ruta");
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
    // clase interna para poder escuchar los cambios de posicion en el telefono
    private class MainActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<MainRedMujeres> activityWeakReference;

        MainActivityLocationCallback(MainRedMujeres activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        @Override
        public void onSuccess(LocationEngineResult result) {
            MainRedMujeres activity = activityWeakReference.get();

            if (activity != null) {
                Location location = result.getLastLocation();

                if (location == null) {
                    return;
                }
                Double latitude = result.getLastLocation().getLatitude();
                Double longitude  = result.getLastLocation().getLongitude();
                //si la obicacion cambio tanto en latitud o longitud, actualizamos en la DB la informacion
                if(Double.compare(latitude,lastLatitudeKnown) != 0 || Double.compare(longitude,lastLongitudeKnown) != 0) {
                    System.out.println( String.valueOf(result.getLastLocation().getLatitude()) +","+ String.valueOf(result.getLastLocation().getLongitude()));
                    UpdateMyLocation(latitude, longitude);
                    userLat = latitude;
                    userLong = longitude;
                }
// Pass the new location to the Maps SDK's LocationComponent
                if (activity.mapboxMap != null && result.getLastLocation() != null) {
                    activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
                }
            }
        }

        @Override
        public void onFailure(@NonNull Exception exception) {

            MainRedMujeres activity = activityWeakReference.get();
            if (activity != null) {
                Toast.makeText(activity, exception.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getGroupMembersPositions() {

        //Solicidtamos a la base de datos informacion del grupo
        FireBaseRedMujeres db = new FireBaseRedMujeres();
        // Un handler para preguntar a la BD cad cierta cantidad de tiempo.
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //si he recuperado datos, los marco en el mapa
                readGroupData("GrupoEj");
                //System.out.println(locationsQueue);

                //pregunto cada medio segundo
                handler.postDelayed(this, 2000);
            }
        };

        // The first time this runs we don't need a delay so we immediately post.
        handler.post(runnable);
    }

    //ID HU: CI0161-371. M5 Ubicación de teléfonos de emergencia de la UCR.
    //Participantes: Driver: Denisse, Navigators: Berta, Aaron
    //Agregar coordenadas de telefonos de emergencia de la UCR.
    public  List<Feature> setEmergencyPhone() {
        List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(-84.053143, 9.9379798 )));
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(9.9365951, -84.052528)));
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(-84.051744, 9.9359527)));
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(-84.050132, 9.9358129)));
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(-84.0493615, 9.9362323)));
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(-84.0487296, 9.9377568)));
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(-84.0513553, 9.9352736)));
        return symbolLayerIconFeatureList;
    }

    //ID HU: CI0161-371. M5 Ubicación de teléfonos de emergencia de la UCR.
    //Participantes: Driver: Aaron, Navigators: Berta, Denisse
    //Agregar coordenadas de telefonos de emergencia de la UCR.

    //Recibe mapa que devuelve la base de datos con las posiciones de cada miembro del equipo
    // Marca en el mapa las posiciones de estos
    private void addMarkers(List<Map<String,Object>> map){
        List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
        symbolLayerIconFeatureList = setEmergencyPhone();
        for(int i = 0 ; i < map.size() ; ++i){
            symbolLayerIconFeatureList.add(Feature.fromGeometry(
                    Point.fromLngLat( (Double)map.get(i).get("Longitud"), (Double)map.get(i).get("Latitud"))));
        }
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat( userLong, userLong)));

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
                                iconOffset(new Float[] {0f, -9f}))
                ), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                if(targerUser!=null) {//Solo pasa si nos compartieron la ruta
                    //Se dibuja la ruta a quien me compartio el viaje
                    Point destinationPoint = Point.fromLngLat((Double)map.get(0).get("longDestino"), (Double)map.get(0).get("latDestino"));
                    Point originPoint = Point.fromLngLat((Double)map.get(0).get("Longitud"),(Double) map.get(0).get("Latitud"));;
                    getRoute(originPoint,destinationPoint);
                }

            }
        });
    }


    //Obtiene el json especifico para la referencia a la base de datos en el nodo del grupo especifcado
    public  void readGroupData( String nombreGrupo){
        groupArr = new ArrayList<>();
        grupo = mDatabase.getReference("Comunidades").child(nombreGrupo);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Json se comporta como un mapa KEY = nombre atributo, Value = valor
                Map<String,Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
                ArrayList<Integer> users = (ArrayList<Integer>)map.get("IDusuarios");

                if(targerUser != null) {//Filtro por si es una persona especifica que nos compartio el viaje
                    readGroupUsersData(targerUser);
                }else {
                    for (int i = 0; i < users.size(); ++i) {
                        //Recuperamos la informacion de los integrantes del grupo
                        readGroupUsersData("" + users.get(i));
                    }
                }

                if(!usersLocations.equals(userArr)){
                    addMarkers(userArr);
                    usersLocations = new ArrayList<>(userArr);
                    userArr = new ArrayList<>();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        grupo.addListenerForSingleValueEvent(valueEventListener);

    }



    //Obtiene el json especifico para la referencia a la base de datos en el nodo del usuario especifcado
    public  void readGroupUsersData(String id){
        usuarios = mDatabase.getReference("usuarios_red_mujeres").child(id);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Json se comporta como un mapa KEY = nombre atributo, Value = valor
                Map<String,Object> list = (HashMap<String, Object>) dataSnapshot.getValue();
                if(!userArr.contains(list)) {
                    userArr.add(list);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        usuarios.addListenerForSingleValueEvent(valueEventListener);
    }



    private void setUserID(){
        //En su momento deberá usarse el id asociado a la comunidad
        this.userID ="1";

    }

    public void UpdateMyLocation(Double laititude, Double longitude){
        // actualizamo el nodo en firebase
        DatabaseReference ref = mDatabase.getReference("usuarios_red_mujeres");
        ref.child(this.userID).child("Latitud").setValue(laititude);
        ref.child(this.userID).child("Longitud").setValue(longitude);
        //ref.child(this.userID).child("coordenadas").push().setValue(new LatLng(laititude,longitude));
    }

    private void deleteCoordinates(){
        DatabaseReference ref = mDatabase.getReference("usuarios_red_mujeres").child(this.userID).child("coordenadas");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    snapshot.getRef().setValue(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}

