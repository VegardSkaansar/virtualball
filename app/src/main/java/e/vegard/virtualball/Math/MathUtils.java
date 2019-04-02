package e.vegard.virtualball.Math;

import java.text.DecimalFormat;

import static java.lang.Math.sqrt;

public class MathUtils {

    // const
    public static final double EARTHGRAVITY = 9.81;
    private static DecimalFormat df2 = new DecimalFormat(".##");

    public static Double acceleration(double x, double y, double z) {
        // this function is going to calculate the acceleration
        return sqrt(x*x + y*y + z*z) - EARTHGRAVITY;
    }

    public static String secondsToPoint(double accBall) {
        return df2.format(accBall / EARTHGRAVITY);
    }

    public static String distanceTravelled(double accBall) {
        // formula used V^2 = v0^2 + 2a(r-r0)
        return df2.format((accBall * accBall) / (2 * EARTHGRAVITY));
    }
}
