package cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Meal;

public class CompraActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        Meal meal = getIntent().getParcelableExtra(MealsActivity.MEAL_KEY);
    }
}