package cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.repositories.MealRepository;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.viewmodels.MealViewModel;

public class MealsActivity extends AppCompatActivity
{
    private static final int COLUMNS = 2;
    private RecyclerView recyclerView;
    private MealsAdapter adapter;
    private MealViewModel viewModel;
    private List<Meal> meals;

    private static String FIREBASE_PATH = "ucr_eats";

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
        }

        getFirebaseMeals(1);

        // Create adapter with empty dataset
        this.adapter = new MealsAdapter(this, meals);

        // Set recycler a 2 column grid and the adapter
        recyclerView = findViewById(R.id.meals_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, COLUMNS));
        recyclerView.setAdapter(this.adapter);

        // Add an observer to the available meals
        ViewModelProvider provider = new ViewModelProvider(this);
        this.viewModel = provider.get(MealViewModel.class);
        this.viewModel.getMealsByRestId(card.getId()).observe(this, meals -> adapter.setMeals(meals));
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

    private void getFirebaseMeals(int id)
    {
        UcrEatsFirebaseDatabase db = new UcrEatsFirebaseDatabase();

        DatabaseReference ref = db.getMealsFromRestaurantRef("1");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                // Get all meals/children data from snapshot
                Iterable<DataSnapshot> meals = dataSnapshot.getChildren();
                ArrayList<Meal> m = new ArrayList<>();
                // Iterate array
                for(DataSnapshot meal : meals)
                {
                    if(meal.exists())
                        m.add(new Meal(meal));
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });

    }
}
