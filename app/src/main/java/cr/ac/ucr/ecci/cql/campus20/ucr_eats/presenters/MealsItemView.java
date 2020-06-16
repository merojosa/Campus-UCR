package cr.ac.ucr.ecci.cql.campus20.ucr_eats.presenters;

public interface MealsItemView
{
    void setName(String name);
    void setPrice(int price);
    void setPicture(String url);
    void setServings(int remaining, int max);
}
