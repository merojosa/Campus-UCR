package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;

public class UcrEatsFirebaseDatabase extends FirebaseBD
{
    private static String ROOT_PATH = "ucr_eats";
    private static String RESTAURANTS_PATH = "restaurant";
    private static String MEALS_PATH = "meals";
    private static String RATINGS_PATH = "ratings";

    private DatabaseReference rootReference = null;

    public UcrEatsFirebaseDatabase()
    {
        this.rootReference = FirebaseDatabase.getInstance().getReference(ROOT_PATH);
    }

    public DatabaseReference getRestaurantsRef()
    {
        return this.rootReference.child(RESTAURANTS_PATH);
    }

    public DatabaseReference getMealsFromRestaurantRef(String id)
    {
        return this.rootReference.child(RESTAURANTS_PATH) // get Restaurants
                                 .child(id)   // get restaurant by id
                                 .child(MEALS_PATH); // Get restaurant's meals
    }
}
