package cr.ac.ucr.ecci.cql.campus20.ucr_eats.presenters;

import android.location.Location;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.UcrEatsFirebaseDatabase;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Order;

public class PendingOrdersPresenter
{
    private List<Order> orders;

    public PendingOrdersPresenter(List<Order> orders)
    {
        this.setOrders(orders);
    }

    public void onBindOrderAtPosition(int position, PendingOrdersView view)
    {
        if(this.orders != null && this.orders.size() > 0)
        {
            Order order = this.orders.get(position);

            view.setRestaurant(order.getRestaurant());
            view.setMeal(order.getMeal().getName());
            view.setDate(order.getDate().toString());

            view.setDistance(calculteDistance(order));
        }
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String calculteDistance(Order order)
    {

         double orderLatitude = order.getLatitude();
         double orderLongitude = order.getLongitude();

        Location locationRest = new Location("");
        locationRest.setLatitude(order.getRestLatitude());
        locationRest.setLongitude(order.getRestLongitude());

        Location locationUser = new Location("");
        locationUser.setLatitude(orderLatitude);
        locationUser.setLongitude(orderLongitude);

        double distance = locationUser.distanceTo(locationRest)/1000;

        String distanceString = String.format("Distancia: %.2fkm", distance);
        return distanceString;

    }
}
