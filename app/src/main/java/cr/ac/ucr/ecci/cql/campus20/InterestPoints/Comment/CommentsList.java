package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Comment;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.red_mujeres.ComunidadAdapter;

public class CommentsList extends RecyclerView.Adapter<CommentsList.MyViewHolderComments> {

    private List<Comment> temp;
    private List<Comment> originalData;
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
        public TextView mLike;
        public TextView mDislike;

        public MyViewHolderComments(View view) {
            super(view);
            mComment = (TextView) view.findViewById(R.id.comment);
            mLike = (TextView) view.findViewById(R.id.no_like);
            mDislike = (TextView) view.findViewById(R.id.no_dislike);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int posicionAdaptador = getAdapterPosition();
            Comment comment = temp.get(posicionAdaptador);
            mClickHandler.onClick(comment.getDescription());
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
        Comment comment = temp.get(position);
        holder.mComment.setText(comment.getDescription());
        holder.mLike.setText(String.valueOf(comment.getLike()));
        holder.mDislike.setText(String.valueOf(comment.getDislike()));
    }

    @Override
    public int getItemCount() {
        if(temp != null)
            return temp.size();
        else
            return 0;
    }

    public void setListData(List<Comment> comments){
        temp = comments;
        originalData = temp;
        notifyDataSetChanged();
    }
}
