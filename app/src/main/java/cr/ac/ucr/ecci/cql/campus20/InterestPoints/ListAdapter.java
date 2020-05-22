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

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.DataAccess;
import cr.ac.ucr.ecci.cql.campus20.R;

/*Adaptador de lista genérica*/
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private List<GeneralData> temp;
    private List<GeneralData> originalData;
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
            mImage = (ImageView) view.findViewById(R.id.imageFactSchool);
            // mDescription = (TextView) view.findViewById(R.id.tv_item_description);
            //mImage.setImageResource(R.drawable.colegio32px);

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
        myViewHolder.mImage.setImageResource(datoGeneral.getImage());
    }

    @Override
    public int getItemCount() {
        return temp.size();
    }

    public void setListData(List<GeneralData> data){
        temp = data;
        originalData = temp;
        notifyDataSetChanged();
    }

    public void filter(String query){
        List<GeneralData> newList = new ArrayList<GeneralData>();
        for(GeneralData elemento : originalData){
            if(elemento.getTitle().toLowerCase().contains(query.toLowerCase()))
                newList.add(elemento);
        }
        temp = newList;
        notifyDataSetChanged();
    }

}

