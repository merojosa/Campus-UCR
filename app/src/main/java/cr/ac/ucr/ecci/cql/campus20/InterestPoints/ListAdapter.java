package cr.ac.ucr.ecci.cql.campus20.InterestPoints;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;
import cr.ac.ucr.ecci.cql.campus20.R;

/*Adaptador de lista genérica*/
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private List<Place> temp;
    private List<Place> originalData;
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

        public ImageView mImage;

        public MyViewHolder(View view) {
            super(view);

            mTitle = (TextView) view.findViewById(R.id.tv_item_title);
            mImage = (ImageView) view.findViewById(R.id.imageListIP);
            // mDescription = (TextView) view.findViewById(R.id.tv_item_description);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int posicionAdaptador = getAdapterPosition();
            Place datoGeneral = temp.get(posicionAdaptador);
            mClickHandler.onClick(datoGeneral.getName());
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
        Place place = temp.get(position);

        // Para que Strings largos no queden cortados en lista
        if(place.getName() != null && place.getName().length() > 22){
            myViewHolder.mTitle.setText(place.getName().substring(0, 19) + "...");
        }else if (place.getName() != null) {
            myViewHolder.mTitle.setText(place.getName());
        } else {
            myViewHolder.mTitle.setText("Nombre default");
        }

        // myViewHolder.mDescription.setText(Util.recortarTexto(datoGeneral.getDescription(), Util.TAMANO_DESCRIPCION_LISTA));
        myViewHolder.mImage.setImageResource(place.getImage());
    }

    @Override
    public int getItemCount() {
    if(temp != null)
        return temp.size();
    else
        return 0;
    }

    public void setListData(List<Place> data){
        temp = data;
        originalData = temp;
        notifyDataSetChanged();
    }

    public void filter(String query){
        List<Place> newList = new ArrayList<Place>();
        for(Place elemento : originalData){
            if(elemento.getName().toLowerCase().contains(query.toLowerCase()))
                newList.add(elemento);
        }
        temp = newList;
        notifyDataSetChanged();
    }

}

