package cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.CampusBD;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Meal;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Order;

public class CompraActivity extends AppCompatActivity
{
    private Meal meal;
    public static final String PATH_PEDIDOS = "ucr_eats/pedidos";
    private String currentRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        meal = getIntent().getParcelableExtra(MealsActivity.MEAL_KEY);
        currentRestaurant = getIntent().getStringExtra(MealsActivity.NOMBRE_SODA_KEY);

        TextView restaurant = findViewById(R.id.tituloCompra);
        restaurant.setText(currentRestaurant);

        TextView mealText = findViewById(R.id.platilloCompra);
        mealText.setText(meal.getName());

        Picasso picasso = new Picasso.Builder(this)
                .indicatorsEnabled(true)
                .loggingEnabled(true) //add other settings as needed
                .build();

        ImageView photo = findViewById(R.id.platilloImagenCompra);
        picasso.load(meal.getPhoto())
                .placeholder(R.drawable.soda_placeholder)
                .into(photo);

        Button buttonCompra = findViewById(R.id.buttonCompra);
        buttonCompra.setOnClickListener(v -> realizarCompra());

        // Map box fragment:

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        // Create supportMapFragment
        SupportMapFragment mapFragment;
        if (savedInstanceState == null)
        {

            // Create fragment
            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Build mapboxMap
            MapboxMapOptions options = MapboxMapOptions.createFromAttributes(this, null);
            options.camera(new CameraPosition.Builder()
                    .target(new LatLng(-52.6885, -70.1395))
                    .zoom(9)
                    .build());

            // Create map fragment
            mapFragment = SupportMapFragment.newInstance(options);

            // Add map fragment to parent container
            transaction.add(R.id.container, mapFragment, "com.mapbox.map");
            transaction.commit();
        }
        else
        {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentByTag("com.mapbox.map");
        }

        if (mapFragment != null)
        {
            mapFragment.getMapAsync(mapboxMap -> mapboxMap.setStyle(Style.MAPBOX_STREETS, style ->
            {

                // Map is set up and the style has loaded. Now you can add data or make other map adjustments


            }));
        }
    }

    public void realizarCompra()
    {
        CampusBD campusBD = new FirebaseBD();

        String email = campusBD.obtenerCorreoActual();
        String username = email.substring(0, email.indexOf('@'));

        Order order = new Order(username, meal, currentRestaurant, Calendar.getInstance().getTime());
        String orderId = campusBD.obtenerIdUnicoPath(PATH_PEDIDOS);
        order.setIdOrder(orderId);

        campusBD.escribirDatos(PATH_PEDIDOS + "/" + orderId, order);

        Toast.makeText(this, "Se realizó el pedido exitosamente", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainUcrEats.class);
        startActivity(intent);
        finish();

    }

}