package cr.ac.ucr.ecci.cql.campus20;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Restaurant;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.repositories.RestaurantRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RestaurantRepository repository = new RestaurantRepository(getApplication());

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), Integer.toString(repository.getAllRestaurants().size()),
                        Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int id = Integer.parseInt(((EditText)findViewById(R.id.idrest)).getText().toString());
                String name = ((EditText)findViewById(R.id.nombre)).getText().toString();

                Restaurant restaurant = new Restaurant(id, name, 0, 0.0,0.0,
                        "Mo", (short)0, (short)1000);

                if(!repository.insert(restaurant))
                    Toast.makeText(getApplicationContext(), "ID ya usado",
                            Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText)findViewById(R.id.nombre)).getText().toString();

                List<Restaurant> lista = repository.searchRestaurantByName(name);

                String text = "";

                for(int i = 0 ; i < lista.size() ; ++i)
                {
                    text = text + " " + lista.get(i).name;
                }

                ((TextView)findViewById(R.id.texto)).setText(text);
            }
        });
    }
}
