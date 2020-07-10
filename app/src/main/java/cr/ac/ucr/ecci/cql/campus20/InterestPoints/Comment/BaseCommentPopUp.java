package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

/** Helper class to be inherited by classes which use CommentPopUp to manage comments.
 * Those methos are implemented here because CommentPopUp is not an activity, and does not suport
 * activity callbacks.
 * */
public abstract class BaseCommentPopUp extends AppCompatActivity {
    protected CommentPopUp commentPopUp;
    public static final int GALLERY_REQUEST_CODE = 20;
    public static final int CAMERA_REQUEST_CODE = 21;
    public static final int CAMERA_PERMISSION_REQUEST = 22;
    public static final int STORAGE_PERMISSION_REQUEST = 23;

    /**
     * Utility callback method when CommentPopUp selects an image.
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    Uri selectedImage = data.getData();
                    commentPopUp.setImg(selectedImage);
                    break;
                case CAMERA_REQUEST_CODE:
                    commentPopUp.setImg(Uri.parse(commentPopUp.getCameraFilePath()));
                    commentPopUp.notifyPhotoTaken();
            }
    }

    /**
     * Utility callback to request permissions when CommentPopUp needs them. Asks for
     * camera access and external storage permissions, needed when uploading a photo to a comment.
     * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE_PERMISSION_REQUEST:
            case CAMERA_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    commentPopUp.takePicture();
                }
            }
        }
    }
}
