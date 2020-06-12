package cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.SodaCard;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.UcrEatsFirebaseDatabase;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.adapters.MealsAdapter;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Meal;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.viewmodels.MealViewModel;

public class MealsActivity extends AppCompatActivity
{
    private static final int COLUMNS = 2;
    private RecyclerView recyclerView;
    private MealsAdapter adapter;
    private MealViewModel viewModel;
    private ImageView sodaRatingStar;
    private TextView sodaRatingNum;
    private List<Meal> meals;

    private String currentRestaurant;

    private static String FIREBASE_PATH = "ucr_eats";
    public final static String MEAL_KEY = "Meals";
    public final static String NOMBRE_SODA_KEY = "Soda";

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
            currentRestaurant = card.getNombre();
            /*
            sodaRatingNum = findViewById(R.id.rating_num);
            // SET RATING OF USER FOR THIS SODA WITH FIREBASE
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
                        builder.setTitle("Califica la soda");
                        //builder.setMessage("Thank you for rating us , it will help us to provide you the best service .");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Float value = ratingBar.getRating();
                                // SET RATING OF USER FOR THIS SODA WITH FIREBASE
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
            */
        }

        // Hardcoded restaurant id until the restaurant story gets done
        this.getFirebaseMeals("1");

        this.setupRecyclerView();

        // Add an observer to the available meals (commented, data is updated from firebase now)
        //ViewModelProvider provider = new ViewModelProvider(this);
        //this.viewModel = provider.get(MealViewModel.class);
        //this.viewModel.getMealsByRestId(card.getId()).observe(this, meals -> adapter.setMeals(meals));
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
                    intent.putExtra(NOMBRE_SODA_KEY, currentRestaurant);
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

    private void getFirebaseMeals(String id)
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
}
