package cr.ac.ucr.ecci.cql.campus20.interest_points;

import android.os.Parcel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Comment;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Faculty;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.School;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Utilities.UtilDates;
import cr.ac.ucr.ecci.cql.campus20.MockParcel;
import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Meal;

import static org.junit.Assert.assertEquals;

public class InterestPointsTest {

    /**
     * Simula el paso de un objeto Comment a través de Intent por un objeto Parcel, y verifica su integridad.
     * */
    @Test
    public void testCommentsParcel(){
        Comment comment = new Comment(0, 0, "School", "Soy un comentario", UtilDates.DateToString(Calendar.getInstance().getTime()), (float) 0.0, "", 0, 0);

        // Parcelable mock taken from internet, see class link
        Parcel parcel = MockParcel.obtain();

        comment.writeToParcel(parcel, comment.describeContents());

        parcel.setDataPosition(0);

        Comment parcelledComment =  Comment.CREATOR.createFromParcel(parcel);

        assert(comment.equals(parcelledComment));
    }

    @Test
    public void testFacultyParcel(){
        Place faculty = new Faculty(0, "Artes", "", R.drawable.artes512px, Place.TYPE_FACULTY, new ArrayList<Comment>());

        Parcel parcel = MockParcel.obtain();

        faculty.writeToParcel(parcel, faculty.describeContents());

        parcel.setDataPosition(0);

        Place parcelledFaculty = Faculty.CREATOR.createFromParcel(parcel);

        assert(faculty.equals(parcelledFaculty));
    }

    @Test
    public void testSchoolParcel(){
        School school = new School(5, 1, 0, "Tecnología de Alimentos", "", R.drawable.alimentos512px, 0.0, 0.0, new ArrayList<Comment>());

        Parcel parcel = MockParcel.obtain();

        school.writeToParcel(parcel, school.describeContents());

        parcel.setDataPosition(0);

        Place parcelledSchool = School.CREATOR.createFromParcel(parcel);

        assert(school.equals(parcelledSchool));
    }
}
