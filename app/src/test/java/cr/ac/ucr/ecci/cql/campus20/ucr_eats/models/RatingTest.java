package cr.ac.ucr.ecci.cql.campus20.ucr_eats.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class RatingTest {

    @Test
    public void getId() {
        assertEquals("ID incorrecto", 1, new Rating(1,1,5).getId());
    }

    @Test
    public void getRestaurant_id() {
        assertEquals("RestaurantID incorrecto", 1, new Rating(1,1,2).getRestaurant_id());
    }

    @Test
    public void getPoint() {
        assertEquals("RestaurantPoint incorrecto", 5, new Rating(1, 1,5).getPoint());
    }
}