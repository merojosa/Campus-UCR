package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Comment;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.R;

public class CommentDetail extends AppCompatActivity {

    private Comment comment;
    private TextView date;
    private TextView commentDescription;
    private RatingBar ratingBar;
    private TextView like;
    private TextView dislike;
    private ImageView photo;
    private Button goBack;
    private FirebaseDB db;
    private StorageReference mStorageRef;

    /*
    * MPS4-01 Detalle de comentarios
    * D: Luis Carvajal N: Sebastián Cruz
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new FirebaseDB();
        mStorageRef = FirebaseStorage.getInstance().getReference(FirebaseDB.CLOUDSTORE_PREFIX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);
        this.comment = getIntent().getParcelableExtra("comment");

        date = findViewById(R.id.date);
        commentDescription = findViewById(R.id.commentText);
        ratingBar = findViewById(R.id.ratingBar);
        like = findViewById(R.id.no_like_detail);
        dislike = findViewById(R.id.no_dislike_detail);
        photo = findViewById(R.id.commentPhoto);
        goBack = findViewById(R.id.commentGoBack);

        commentDescription.setText(comment.getDescription());
        date.setText(comment.getDate());
        like.setText(Integer.toString(comment.getLike()));
        dislike.setText(Integer.toString(comment.getDislike()));

        if(comment != null && comment.getPhotoPath() != null) {
            mStorageRef.child(comment.getPhotoPath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(photo);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }

        Log.d("Comment", comment.getDescription());

    }
}