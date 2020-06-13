package cr.ac.ucr.ecci.cql.campus20.ucr_eats.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class MealTest {

    @Test
    public void getName() {
        Meal meal = new Meal();

        assertEquals(meal.getName(), "");

    }
}