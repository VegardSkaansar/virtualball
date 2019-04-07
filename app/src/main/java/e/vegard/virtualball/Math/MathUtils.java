package e.vegard.virtualball.Math;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class MathUtils {

    // const
    public static final double EARTHGRAVITY = 9.81;
    private static DecimalFormat df2 = new DecimalFormat(".##");

    public static Double acceleration(double x, double y, double z) {
        // this function is going to calculate the acceleration
        return sqrt(x*x + y*y + z*z) - EARTHGRAVITY;
    }

    public static double secondsToPoint(double accBall) {
        return accBall / EARTHGRAVITY;
    }

    public static double distanceTravelled(double accBall) {
        // formula used V^2 = v0^2 + 2a(r-r0)
        return (accBall * accBall) / (2 * EARTHGRAVITY);
    }

    public static double calculateScore(double distance, double seconds) {

        return (distance * seconds);
    }
}
