package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.red_mujeres.ComunidadAdapter;

public class CommentsList extends RecyclerView.Adapter<CommentsList.MyViewHolderComments> {

    private List<GeneralData> temp;
    private List<GeneralData> originalData;
    Context context;


    private final CommentListOnClickHandler mClickHandler;

    public interface CommentListOnClickHandler{
        void onClick(String title);
    }

    public CommentsList(CommentListOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }


    public class MyViewHolderComments extends RecyclerView.ViewHolder implements View
            .OnClickListener{

        public TextView mComment;

        public MyViewHolderComments(View view) {
            super(view);
            mComment = (TextView) view.findViewById(R.id.comment);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int posicionAdaptador = getAdapterPosition();
            GeneralData datoGeneral = temp.get(posicionAdaptador);
            mClickHandler.onClick(datoGeneral.getDescription());
        }

    }

    @NonNull
    @Override
    public MyViewHolderComments onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popup, parent, false);
        return new MyViewHolderComments(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolderComments holder, int position) {
        GeneralData datoGeneral = temp.get(position);

        // Para que Strings largos no queden cortados en lista
        if(datoGeneral.getDescription().length() > 22){
            holder.mComment.setText(datoGeneral.getDescription().substring(0, 19) + "...");
        }else{
            holder.mComment.setText(datoGeneral.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setListData(List<GeneralData> data){
        temp = data;
        originalData = temp;
        notifyDataSetChanged();
    }
}
