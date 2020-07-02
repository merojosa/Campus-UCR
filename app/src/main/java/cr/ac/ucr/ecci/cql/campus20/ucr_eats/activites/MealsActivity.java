package cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cr.ac.ucr.ecci.cql.campus20.CampusBD;
import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.SodaCard;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.UcrEatsFirebaseDatabase;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.adapters.MealsAdapter;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Meal;

public class MealsActivity extends AppCompatActivity
{
    private static final int COLUMNS = 2;
    private RecyclerView recyclerView;
    private MealsAdapter adapter;
    private ImageView sodaRatingStar;
    private TextView sodaRatingNum;
    private List<Meal> meals;

    private String currentRestaurant;

    public String getRestLatitude() {
        return restLatitude;
    }

    public void setRestLatitude(String restLatitude) {
        this.restLatitude = restLatitude;
    }

    public String getRestLongitude() {
        return restLongitude;
    }

    public void setRestLongitude(String restLongitude) {
        this.restLongitude = restLongitude;
    }

    private String restLatitude;
    private String restLongitude;


    private static String FIREBASE_PATH = "ucr_eats";
    public final static String MEAL_KEY = "Meals";
    public final static String NOMBRE_SODA_KEY = "Soda";
    public final static String LATITUDE_SODA_KEY = "0";
    public final static String LONGITUDE_SODA_KEY = "1";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Get restaurant id from intent
        SodaCard card = Objects.requireNonNull(getIntent().getExtras()).getParcelable("SODACARD");
        if(card != null)
        {
            ((TextView)findViewById(R.id.meal_rest_name)).setText(card.getNombre());
            this.setRestaurantImage(card);
            this.currentRestaurant = card.getNombre();
            this.restLatitude = String.valueOf(card.getLatitud());
            this.restLongitude = String.valueOf(card.getLongitud());

            this.startMealsSync(card.getFirebaseId());

            sodaRatingNum = findViewById(R.id.rating_num);

            // SET RATING OF USER FOR THIS SODA WITH FIREBASE
            getFirebaseUserRate("1"); // hard coded by now

            sodaRatingStar = findViewById(R.id.rating_star);


            sodaRatingStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        AlertDialog.Builder builder = new AlertDialog.Builder(MealsActivity.this);
                        View layout= null;
                        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        layout = inflater.inflate(R.layout.soda_rating_dialog, null);
                        final RatingBar ratingBar = (RatingBar)layout.findViewById(R.id.ratingBar);
                        ratingBar.setStepSize((float)1.0);
                        builder.setTitle("Califica la soda");
                        //builder.setMessage("Thank you for rating us , it will help us to provide you the best service .");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Float value = ratingBar.getRating();
                                // SET RATING OF USER FOR THIS SODA WITH FIREBASE
                                updateFirebaseUserRate("1", (double)value);
                                // UPDATE TOTAL RATING FOR SODA
                                // ...
                                sodaRatingNum.setText(value.toString());
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setCancelable(false);
                        builder.setView(layout);
                        builder.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        this.setupRecyclerView();
    }

    public void setupRecyclerView()
    {
        // Create adapter with empty dataset
        this.adapter = new MealsAdapter(this, meals);

        // Set recycler a 2 column grid and the adapter
        recyclerView = findViewById(R.id.meals_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, COLUMNS));
        recyclerView.setAdapter(this.adapter);

        // Performance
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView , (view, position) ->
                {
                    // Go checkout
                    Intent intent = new Intent(this, CompraActivity.class);
                    intent.putExtra(LONGITUDE_SODA_KEY, restLongitude);
                    intent.putExtra(NOMBRE_SODA_KEY, currentRestaurant);
                    intent.putExtra(LATITUDE_SODA_KEY, restLatitude);
                    intent.putExtra(MEAL_KEY, adapter.getMeals().get(position));
                    startActivity(intent);
                })
        );
    }

    private void setRestaurantImage(SodaCard card)
    {
        Picasso picasso = new Picasso.Builder(this).indicatorsEnabled(true)
                .loggingEnabled(true).build();

        // Image should have been saved in cache when loaded in previous activity
        picasso.load(card.getId())
                .placeholder(R.drawable.soda_placeholder)
                .into((ImageView) findViewById(R.id.meals_rest_img));
    }

    private void startMealsSync(String id)
    {
        UcrEatsFirebaseDatabase db = new UcrEatsFirebaseDatabase();

        DatabaseReference ref = db.getMealsFromRestaurantRef(id);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                // Get all meals/children data from snapshot
                Iterable<DataSnapshot> mealsData = dataSnapshot.getChildren();
                ArrayList<Meal> meals = new ArrayList<>();
                // Iterate array
                for(DataSnapshot meal : mealsData)
                {
                    if(meal.exists())
                        meals.add(new Meal(meal));
                }

                adapter.setMeals(meals);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });

    }

    private void getFirebaseUserRate(String restaurantId)
    {
        CampusBD logged = new FirebaseBD();
        UcrEatsFirebaseDatabase db = new UcrEatsFirebaseDatabase();

        // Para comperar con la codificación de caracteres especiales en Firebase
        String encodedMail = db.encodeMailForFirebase(logged.obtenerCorreoActual());

        DatabaseReference ref = db.getRestaurantRateByUser(restaurantId, encodedMail);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    Double rate = dataSnapshot.getValue(Double.class);
                    if(rate != null)
                        sodaRatingNum.setText(rate.toString());
                }
                else
                {
                    sodaRatingNum.setText("Sin calificación");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", databaseError.toException());
                sodaRatingNum.setText(0);
            }
        });
    }




    private void updateFirebaseUserRate(String restaurantId, Double value)
    {
        CampusBD logged = new FirebaseBD();
        UcrEatsFirebaseDatabase db = new UcrEatsFirebaseDatabase();

        // Para comperar con la codificación de caracteres especiales en Firebase
        String encodedMail = db.encodeMailForFirebase(logged.obtenerCorreoActual());

        DatabaseReference ref = db.getRestaurantRateByUser(restaurantId, encodedMail);

        Map<String, Object> map = new HashMap<>();
        map.put("rate", value);

        ref.getParent().updateChildren(map);
    }
}
