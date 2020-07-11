package cr.ac.ucr.ecci.cql.campus20.interest_points;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Comment;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Utilities.UtilDates;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit test class for Comment class on IPModel.
 * Creates a comment and verifies its data.
 * */
public class CommentUnitTest {

        private Comment comment;
        private static final int ID = 0;
        private static final int ID_PLACE_FK = 36;
        private static final String COMMENT_TEXT = "La mejor escuela de la universidad.";
        private static final float RATING = 3.5f;
        private static final int LIKE = 5;
        private static final int DISLIKE = 10;
        private static String date;

        @Before
        public void setUp() {
            date = UtilDates.DateToString(Calendar.getInstance().getTime());
            comment = new Comment(ID, ID_PLACE_FK, Place.TYPE_SCHOOL, COMMENT_TEXT, date, RATING, null, LIKE, DISLIKE);
        }

        @Test
        public void testCommentNotNull() {
            assertNotNull(comment);
        }

        @Test
        public void testCommentEqual(){
            Comment other = comment;
            assertEquals(other, comment);
        }

        @Test
        public void testCommentData() {
            assertEquals(ID, comment.getId());
            assertEquals(ID_PLACE_FK, comment.getId_place_fk());
            assertEquals(COMMENT_TEXT, comment.getDescription());
            assertEquals(date, comment.getDate());
            assertEquals(RATING, comment.getRating(), 1e-8);
            assertEquals(LIKE, comment.getLike());
            assertEquals(DISLIKE, comment.getDislike());
        }

        @After
        public void tearDown() {
            comment = null;
        }

}
