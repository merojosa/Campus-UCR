package cr.ac.ucr.ecci.cql.campus20.ucr_eats.presenters;

import com.squareup.picasso.Picasso;

/**
 * Interface used to set the values in a meal item view
 * Important to keep the low coupling principle and make unit tests
 * Based on https://android.jlelse.eu/recyclerview-in-mvp-passive-views-approach-8dd74633158
 */
public interface MealsItemView
{
    void setName(String name);
    void setPrice(int price);
    void setPicture(String url);
    void setServings(int remaining, int max);
}
