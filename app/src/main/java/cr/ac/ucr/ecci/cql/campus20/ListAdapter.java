package cr.ac.ucr.ecci.cql.campus20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private List<GeneralData> temp;
    Context context;

    private final ListAdapterOnClickHandler mClickHandler;


    public interface ListAdapterOnClickHandler{
        void onClick(String title);
    }


    public ListAdapter(ListAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTitle, mDescription;

        public MyViewHolder(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.tv_item_title);
            // mDescription = (TextView) view.findViewById(R.id.tv_item_description);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int posicionAdaptador = getAdapterPosition();
            GeneralData datoGeneral = temp.get(posicionAdaptador);
            mClickHandler.onClick(datoGeneral.getTitle());
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        //View view = inflater.inflate(R.layout.list_item, viewGroup, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        GeneralData datoGeneral = temp.get(position);
        // myViewHolder.mTitle.setTextColor(Util.getColor(position, context));
        myViewHolder.mTitle.setText(datoGeneral.getTitle());
        // myViewHolder.mDescription.setText(Util.recortarTexto(datoGeneral.getDescription(), Util.TAMANO_DESCRIPCION_LISTA));
    }

    @Override
    public int getItemCount() {
        return temp.size();
    }

    public void setListData(List<GeneralData> data){
        temp = data;
        notifyDataSetChanged();
    }

}

