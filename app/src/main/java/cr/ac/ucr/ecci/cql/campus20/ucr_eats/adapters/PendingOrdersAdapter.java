package cr.ac.ucr.ecci.cql.campus20.ucr_eats.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Order;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.presenters.PendingOrdersPresenter;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.presenters.PendingOrdersView;

public class PendingOrdersAdapter extends RecyclerView.Adapter<PendingOrdersAdapter.PendingOrdersViewHolder>
{
    private final Context context;
    private List<Order> orders;
    private PendingOrdersPresenter presenter;

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
        return new PendingOrdersViewHolder(v);
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

    public static class PendingOrdersViewHolder extends RecyclerView.ViewHolder implements PendingOrdersView
    {
        TextView restaurant;
        TextView meal;
        TextView date;
        Button assignButton;

        View itemView;

        public PendingOrdersViewHolder(View itemView)
        {
            super(itemView);
            this.itemView = itemView;
            this.restaurant = itemView.findViewById(R.id.assign_soda);
            this.meal = itemView.findViewById(R.id.assign_meal);
            this.date = itemView.findViewById(R.id.assign_date);
            this.assignButton = itemView.findViewById(R.id.assign_order);
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
    }
}
