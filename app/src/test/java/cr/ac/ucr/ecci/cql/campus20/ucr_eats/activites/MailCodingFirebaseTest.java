package cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites;

import org.junit.jupiter.api.Test;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.UcrEatsFirebaseDatabase;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Meal;

import static org.junit.jupiter.api.Assertions.*;

class MailCodingFirebaseTest {

    @Test
    void encodeMailForFirebase() throws Exception
    {
        assertEquals("prueba<dot>coded<a>ucr<dot>ac<dot>cr",
                UcrEatsFirebaseDatabase.encodeMailForFirebase("prueba.coded@ucr.ac.cr"));
    }

    @Test
    void decodeMailFromFirebase() {
        assertEquals("prueba.coded@ucr.ac.cr",
                UcrEatsFirebaseDatabase.decodeMailFromFirebase("prueba<dot>coded<a>ucr<dot>ac<dot>cr"));
    }
}