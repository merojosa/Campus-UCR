package cr.ac.ucr.ecci.cql.campus20.ucr_eats.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites.CompraActivity;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.AssignedOrder;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Order;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.OrderStatus;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.presenters.PendingOrdersPresenter;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.presenters.PendingOrdersView;

public class PendingOrdersAdapter extends RecyclerView.Adapter<PendingOrdersAdapter.PendingOrdersViewHolder>
{
    private final Context context;
    private List<Order> orders;
    private PendingOrdersPresenter presenter;
    private String PATH = "ucr_eats/assignedOrders";

    public PendingOrdersAdapter(Context context, List<Order> orders)
    {
        this.context = context;
        this.orders = orders;
        this.presenter = new PendingOrdersPresenter(orders);
    }


    @NonNull
    @Override
    public PendingOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pending_order, parent, false);
        return new PendingOrdersViewHolder(v, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingOrdersViewHolder holder, int position)
    {
        this.presenter.onBindOrderAtPosition(position, holder);
    }

    @Override
    public int getItemCount()
    {
        return orders == null ? 0 : orders.size();
    }

    public void setOrders(List<Order> orders)
    {
        this.orders = orders;
        this.presenter.setOrders(orders);
        notifyDataSetChanged();
    }

    public class PendingOrdersViewHolder extends RecyclerView.ViewHolder implements PendingOrdersView
    {
        TextView restaurant;
        TextView meal;
        TextView date;
        Button assignButton;
        TextView distance;

        View itemView;

        public PendingOrdersViewHolder(View itemView, Context context)
        {
            super(itemView);
            this.itemView = itemView;
            this.restaurant = itemView.findViewById(R.id.assign_soda);
            this.meal = itemView.findViewById(R.id.assign_meal);
            this.date = itemView.findViewById(R.id.assign_date);
            this.distance = itemView.findViewById(R.id.distance);

            this.assignButton = itemView.findViewById(R.id.assign_order);

            this.assignButton.setOnClickListener(v ->
            {
                FirebaseBD db = new FirebaseBD();

                String email = db.obtenerCorreoActual();

                Order order = orders.get(getAdapterPosition());
                // Repartidor ya escogio, se dirige hacia la soda
                order.setStatus(OrderStatus.HACIA_SODA);
                AssignedOrder assignedOrder = new AssignedOrder(email, order);

                String id = db.obtenerIdUnicoPath(PATH);

                db.escribirDatos(PATH + "/" + id, assignedOrder);

                // Actualizo la orden con el nuevo estado
                db.escribirDatos(CompraActivity.PATH_PEDIDOS + "/" + order.getIdOrder(), order);

                Toast.makeText(context, "Listo, ahora puede recoger el pedido", Toast.LENGTH_LONG).show();

                orders.remove(getAdapterPosition());
                notifyDataSetChanged();
            });
        }

        @Override
        public void setRestaurant(String restaurant) {
            this.restaurant.setText(restaurant);
        }

        @Override
        public void setMeal(String meal) {
            this.meal.setText(meal);
        }

        @Override
        public void setDate(String date) {
            this.date.setText(date);
        }

        @Override
        public void setDistance(String distance) {
            this.distance.setText(distance);
        }
    }
}
