package cr.ac.ucr.ecci.cql.campus20.ucr_eats.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Meal;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealsViewHolder>
{
    private final Context context;
    private List<Meal> meals;
    Picasso picasso;

    public MealsAdapter(Context context, List<Meal> meals)
    {
        this.context = context;
        this.meals = meals;
        this.picasso = new Picasso.Builder(this.context)
                .indicatorsEnabled(true)
                .loggingEnabled(true) //add other settings as needed
                .build();
    }

    @NotNull
    @Override
    public MealsAdapter.MealsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_meal, viewGroup, false);

        return new MealsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MealsViewHolder mealsViewHolder, int position)
    {
        mealsViewHolder.mealName.setText(meals.get(position).getName());
        mealsViewHolder.mealPrice.setText(Integer.toString(meals.get(position).getPrice()));
        //sodaViewHolder.imagenSoda.setImageResource(sodaCards.get(i).getFoto());
//        loadCardImage(sodaViewHolder, position);
    }

    @Override
    public int getItemCount()
    {
        return meals == null ? 0 : meals.size();
    }

    public void setMeals(List<Meal> meals)
    {
        this.meals = meals;
        notifyDataSetChanged();
    }

    // El holder del adapter. Aqui va el contenido del card.
    public static class MealsViewHolder extends RecyclerView.ViewHolder
    {
        TextView mealName;
        TextView mealPrice;
        ImageView mealPicture;


        public MealsViewHolder(View itemView)
        {
            super(itemView);

            mealName = itemView.findViewById(R.id.meal_name);
            mealPrice = itemView.findViewById(R.id.meal_price);
            mealPicture = itemView.findViewById(R.id.meal_picture);
        }
    }


    private void loadCardImage(MealsViewHolder mealsViewHolder, int i)
    {
        // Acá la primera opción debería ser extraer la imagen de la memoria, pero dado que aún no
        // se descarga, se usará imágenes en resources/drawable para pruebas de concepto
        // por ahora, el id de la soda = R.drawable..., lo que nos es útil para esta prueba
        this.picasso.load(meals.get(i).getId() /* ToDo: acá la imagen que está en memoria*/)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.soda_placeholder)
                .into(mealsViewHolder.mealPicture, new Callback() {
                    // Si la imagen no está en cache, se busca en linea
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onError(Exception e) {
                        picasso.load("https://url-a-la-imagen")
                                .placeholder(R.drawable.soda_placeholder)
                                .into(mealsViewHolder.mealPicture);
                    }
                });
    }
}