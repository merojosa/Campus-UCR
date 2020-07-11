package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Meal;

public class UcrEatsFirebaseDatabase extends FirebaseBD
{
    private static String ROOT_PATH = "ucr_eats";
    private static String RESTAURANTS_PATH = "restaurant";
    private static String MEALS_PATH = "meals";
    private static String RATINGS_PATH = "ratings";
    private static String ASSIGNED_ORDERS_PATH = "assignedOrders";
    private static String PENDING_ORDERS_PATH = "pedidos";
    private static String USER_CONFIG_PATH = "config_usuarios";
    private static String USERS_RATES_PATH = "users_rates";

    private static String DEFAULT_ROLE = "default_role";
    private static String DELIVERYMAN_PATH = "repartidores_estatus";


    private DatabaseReference rootReference = null;

    public UcrEatsFirebaseDatabase()
    {
        this.rootReference = FirebaseDatabase.getInstance().getReference(ROOT_PATH);
    }

    public DatabaseReference getRestaurantsRef()
    {
        return this.rootReference.child(RESTAURANTS_PATH);
    }

    public DatabaseReference getAssignedOrdersRef()
    {
        return this.rootReference.child(ASSIGNED_ORDERS_PATH);
    }

    public DatabaseReference getPendingOrdersRef()
    {
        return this.rootReference.child(PENDING_ORDERS_PATH);
    }

    public DatabaseReference getMealsFromRestaurantRef(String id)
    {
        return this.rootReference.child(RESTAURANTS_PATH) // get Restaurants
                                 .child(id)   // get restaurant by id
                                 .child(MEALS_PATH); // Get restaurant's meals
    }

    public DatabaseReference getRestaurantLatitudeByName(String name)
    {
        String id = name == "Soda la U" ? "1" : "2";
        return this.rootReference.child(RESTAURANTS_PATH) // get Restaurants
                                 .child(id)
                                 .child("latitude");   // get restaurant by id
    }

    public DatabaseReference getRestaurantLongitudeById(String id)
    {
        return this.rootReference.child(RESTAURANTS_PATH) // get Restaurants
                .child(id);   // get restaurant by id
    }

    public DatabaseReference getRestaurantRef(String id)
    {
        return this.rootReference.child(RESTAURANTS_PATH);
    }

    public DatabaseReference getDeliverymanOrder(String user)
    {
        return this.rootReference.child(DELIVERYMAN_PATH).child(user);
    }

    public DatabaseReference getRestaurantRateByUser(String restaurant_id, String user)
    {
        return this.rootReference.child(RESTAURANTS_PATH)
                                 .child(restaurant_id)
                                 .child(USERS_RATES_PATH)
                                 .child(user)
                                 .child("rate");
    }

    // Utilizado para transformar todos los carácteres que son válidos para
    // una dirección
    public static String encodeMailForFirebase(String uncodedMail)
    {
        String codedMail = uncodedMail;
        codedMail = codedMail.replace("@", "<a>");
        codedMail = codedMail.replace(".", "<dot>");
        //...

        return codedMail;
    }

    // Utilizado para transformar todos los carácteres que son válidos para
    // una dirección
    public static String decodeMailFromFirebase(String codedMail)
    {
        String decodedMail = codedMail;
        decodedMail = decodedMail.replace("<a>", "@");
        decodedMail = decodedMail.replace("<dot>", ".");
        //...

        return decodedMail;
    }

    public DatabaseReference getRoleReference()
    {
        String user = super.obtenerCorreoActual();
        user = user.substring(0, user.indexOf('@'));

        return super.mDatabase.getReference(USER_CONFIG_PATH)
                .child(user).child(DEFAULT_ROLE);
    }

    public void saveDefaultRole(int role)
    {
        String user = super.obtenerCorreoActual();
        user = user.substring(0, user.indexOf('@'));

        super.escribirDatos(USER_CONFIG_PATH + "/" + user + "/" + DEFAULT_ROLE, role);
    }

}
