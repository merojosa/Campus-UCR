package cr.ac.ucr.ecci.cql.campus20.ucr_eats.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.shape.CornerFamily;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Meal;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.presenters.MealsItemView;

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
        Resources resources = this.context.getResources();

        Meal meal = meals.get(position);
        mealsViewHolder.mealName.setText(meal.getName());
        mealsViewHolder.mealPrice.setText(Integer.toString(meal.getPrice()));
        mealsViewHolder.mealsLeft.setText(
                String.format(resources.getString(R.string.servings_placeholder),
                        meal.getAvailableServings(), meal.getMaxServings()));

        if(meal.getPhoto() != null)
            this.loadImage(mealsViewHolder.mealPicture, meal.getPhoto());

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

    public List<Meal> getMeals()
    {
        return meals;
    }

    public static class MealsViewHolder extends RecyclerView.ViewHolder implements MealsItemView
    {
        TextView mealName;
        TextView mealPrice;
        ImageView mealPicture;
        TextView mealsLeft;

        public MealsViewHolder(View itemView)
        {
            super(itemView);

            mealName = itemView.findViewById(R.id.meal_name);
            mealPrice = itemView.findViewById(R.id.meal_price);
            mealPicture = itemView.findViewById(R.id.meal_picture);
            mealsLeft = itemView.findViewById(R.id.meals_left_text);
        }

        @Override
        public void setName(String name)
        {
            this.mealName.setText(name);
        }

        @Override
        public void setPrice(int price)
        {
            this.mealPrice.setText(Integer.toString(price));
        }

        @Override
        public void setPicture(Picasso picasso, String url) {
            picasso.load(url)
                    .placeholder(R.drawable.soda_placeholder)
                    .into(this.mealPicture);
        }

        @Override
        public void setServings(String format, int remaining, int max)
        {
            this.mealsLeft.setText(String.format(format, remaining, max));
        }
    }


    private void loadImage(ImageView imageView, String url)
    {
        picasso.load(url)
            .placeholder(R.drawable.soda_placeholder)
            .into(imageView);
    }
}