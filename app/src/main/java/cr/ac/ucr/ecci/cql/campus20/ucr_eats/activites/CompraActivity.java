package cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
    }

    public void realizarCompra()
    {
        CampusBD campusBD = new FirebaseBD();

        String email = campusBD.obtenerCorreoActual();
        String username = email.substring(0, email.indexOf('@'));

        Order order = new Order(username, meal, currentRestaurant);
        String orderId = campusBD.obtenerIdUnicoPath(PATH_PEDIDOS);
        order.setIdOrder(orderId);

        campusBD.escribirDatos(PATH_PEDIDOS + "/" + orderId, order);

        Toast.makeText(this, "Se realizó el pedido exitosamente", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainUcrEats.class);
        startActivity(intent);
        finish();

    }

}