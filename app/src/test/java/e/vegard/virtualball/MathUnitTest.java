package e.vegard.virtualball;

import org.junit.Test;
import static org.junit.Assert.*;

import e.vegard.virtualball.Math.MathUtils;

public class MathUnitTest {

    @Test
    public void testAcceleration() {
       assertEquals(0.19, (double)MathUtils.acceleration(10, 0, 0), 0.01);
    }

    @Test
    public void testSecondsToPoint() {
        assertEquals(2.03, (double)MathUtils.secondsToPoint(20), 0.01);
    }

    @Test
    public void testDistancedTravelled() {
        assertEquals(5.09, (double)MathUtils.distanceTravelled(10), 0.01);
    }

    @Test
    public void testCalculateScore() {
        assertEquals(4, MathUtils.calculateScore(2,2), 0.01);
    }
}
