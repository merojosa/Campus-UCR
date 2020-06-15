package cr.ac.ucr.ecci.cql.campus20.ucr_eats.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class RestaurantTest {

    @Test
    public void testGetId() {
    }

    @Test
    public void testGetName() {
        assertEquals("Name incorrecto", "Soda La U",
                new Restaurant("Soda La U",
                        "la_u",
                        9.934497,
                        -84.051063,
                        "Mon-Tue-Wed-Thu-Fri-Sat-Sun",
                        8,
                        21).getName() );
    }

    @Test
    public void testGetPhoto() {
        assertEquals("Photo incorrecto", "la_u",
                new Restaurant("Soda La U",
                        "la_u",
                        9.934497,
                        -84.051063,
                        "Mon-Tue-Wed-Thu-Fri-Sat-Sun",
                        8,
                        21).getPhoto() );
    }

    @Test
    public void testGetLatitude() {
        double sodaLatitude = new Restaurant("Soda La U",
                "la_u",
                9,
                -84.051063,
                "Mon-Tue-Wed-Thu-Fri-Sat-Sun",
                8,
                21).getLatitude();
        assertEquals("Latitude incorrecto", 9, (int)sodaLatitude);

    }

    @Test
    public void testGetLongitude() {
        double sodaLongitude = new Restaurant("Soda La U",
                "la_u",
                9.934497,
                -84,
                "Mon-Tue-Wed-Thu-Fri-Sat-Sun",
                8,
                21).getLongitude();

        assertEquals("Longitude incorrecto", -84, (int)sodaLongitude );
    }

    @Test
    public void testGetDays_open() {
        assertEquals("DaysOpen incorrecto", "Mon-Tue-Wed-Thu-Fri-Sat-Sun",
                new Restaurant("Soda La U",
                        "la_u",
                        9.934497,
                        -84.051063,
                        "Mon-Tue-Wed-Thu-Fri-Sat-Sun",
                        8,
                        21).getDays_open() );
    }

    @Test
    public void testGetOpening_hour() {
        assertEquals("OpeningHours incorrecto", 8,
                new Restaurant("Soda La U",
                        "la_u",
                        9.934497,
                        -84.051063,
                        "Mon-Tue-Wed-Thu-Fri-Sat-Sun",
                        8,
                        21).getOpening_hour() );
    }

    @Test
    public void testGetClosing_hour() {
        assertEquals("ClosingHours incorrecto", 21,
                new Restaurant("Soda La U",
                        "la_u",
                        9.934497,
                        -84.051063,
                        "Mon-Tue-Wed-Thu-Fri-Sat-Sun",
                        8,
                        21).getClosing_hour() );    }

    @Test
    public void testGetRating() {

        Restaurant soda = new Restaurant("Soda La U",
                "la_u",
                9.934497,
                -84.051063,
                "Mon-Tue-Wed-Thu-Fri-Sat-Sun",
                8,
                21);

        soda.setRating(4);
        assertEquals("Rating incorrecto", 4, (int)soda.getRating());
    }

    @Test
    public void testGetCapacity() {

        Restaurant soda = new Restaurant("Soda La U",
                "la_u",
                9.934497,
                -84.051063,
                "Mon-Tue-Wed-Thu-Fri-Sat-Sun",
                8,
                21);

        soda.setCapacity(4);
        assertEquals("Capacity incorrecto", 4, soda.getCapacity() );
    }

    @Test
    public void testGetCapacity_max() {

        Restaurant soda = new Restaurant("Soda La U",
                "la_u",
                9.934497,
                -84.051063,
                "Mon-Tue-Wed-Thu-Fri-Sat-Sun",
                8,
                21);

        soda.setCapacity_max(40);
        assertEquals("CapacityMax incorrecto", 40, soda.getCapacity_max() );
    }

    @Test
    public void getAvailableServings() {
        Restaurant soda = new Restaurant("Soda La U",
                "la_u",
                9.934497,
                -84.051063,
                "Mon-Tue-Wed-Thu-Fri-Sat-Sun",
                8,
                21);

        soda.setAvailableServings(100);
        assertEquals("AvailableService incorrecto", 100, soda.getAvailableServings() );
    }

    @Test
    public void getTotalServings() {
        Restaurant soda = new Restaurant("Soda La U",
                "la_u",
                9.934497,
                -84.051063,
                "Mon-Tue-Wed-Thu-Fri-Sat-Sun",
                8,
                21);

        soda.setTotalServings(60);
        assertEquals("CapacityMax incorrecto", 60, soda.getTotalServings() );
    }

    @Test
    public void getFirebaseId() {
        Restaurant soda = new Restaurant("Soda La U",
                "la_u",
                9.934497,
                -84.051063,
                "Mon-Tue-Wed-Thu-Fri-Sat-Sun",
                8,
                21);

        soda.setFirebaseId("1");
        assertEquals("FirebaseID incorrecto", "1", soda.getFirebaseId() );
    }

}