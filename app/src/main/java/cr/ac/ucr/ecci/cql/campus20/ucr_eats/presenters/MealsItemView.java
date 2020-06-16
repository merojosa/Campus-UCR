package cr.ac.ucr.ecci.cql.campus20.ucr_eats.presenters;

import com.squareup.picasso.Picasso;

/**
 * Interface used to set the values in a meal item view
 * Important to keep the low coupling principle and make unit tests
 */
public interface MealsItemView
{
    void setName(String name);
    void setPrice(int price);
    void setPicture(Picasso picasso, String url);
    void setServings(String format, int remaining, int max);
}
