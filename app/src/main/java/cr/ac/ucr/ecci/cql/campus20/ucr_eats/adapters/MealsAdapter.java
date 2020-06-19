package cr.ac.ucr.ecci.cql.campus20.ucr_eats.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Meal;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.presenters.MealsItemView;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.presenters.MealsPresenter;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealsViewHolder>
{
    private final Context context;
    private List<Meal> meals;
    private MealsPresenter presenter;
    private Picasso picasso;

    public MealsAdapter(Context context, List<Meal> meals)
    {
        this.context = context;
        this.meals = meals;
        this.presenter = new MealsPresenter(meals);
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
        this.presenter.onBindMealItemAtPosition(position, mealsViewHolder);
    }

    @Override
    public int getItemCount()
    {
        return meals == null ? 0 : meals.size();
    }

    public void setMeals(List<Meal> meals)
    {
        this.meals = meals;
        this.presenter.setMeals(meals);
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
        View itemView;

        public MealsViewHolder(View itemView)
        {
            super(itemView);
            this.itemView = itemView;
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
        public void setPicture(String url)
        {
            if(url != null)
            {
                Picasso picasso = new Picasso.Builder(this.itemView.getContext())
                        .indicatorsEnabled(true)
                        .loggingEnabled(true)
                        .build();

                picasso.load(url)
                        .placeholder(R.drawable.soda_placeholder)
                        .into(this.mealPicture);
            }
        }

        @Override
        public void setServings(int remaining, int max)
        {
            Resources resources = this.itemView.getContext().getResources();
            String format = resources.getString(R.string.servings_placeholder);

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