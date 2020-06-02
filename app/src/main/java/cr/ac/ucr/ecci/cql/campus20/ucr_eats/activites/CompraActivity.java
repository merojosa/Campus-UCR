package cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.LoginBD;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Meal;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Order;

public class CompraActivity extends AppCompatActivity
{
    private Meal meal;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        meal = getIntent().getParcelableExtra(MealsActivity.MEAL_KEY);
        String currentRestaurant = getIntent().getStringExtra(MealsActivity.NOMBRE_SODA_KEY);

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
        LoginBD loginBD = new FirebaseBD();

        String email = loginBD.obtenerCorreoActual();
        String username = email.substring(0, email.indexOf('@'));

        Order order = new Order(username, meal);
        loginBD.escribirDatos("ucr_eats/pedidos", order);

    }

}