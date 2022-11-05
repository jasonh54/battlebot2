package despacito7.util;

public class Utils {
    public static double easeOutBack(double x) {
        double c1 = 6.6;
        double c3 = c1 + 1; // FIXME: negative number to decimal exponent
        return 1 + c3 * Math.pow(x - 1, 1.8) + c1 * Math.pow(x - 1, 1.6);
    }

    private static final double c4 = (3 * Math.PI) / 2;
    public static double easeOutElastic(double x) {
        return Math.pow(1.5, -14 * x) * Math.sin((-3*x + 1) * c4) + 1;
        // x == 0
        //   ? 0
        //   : x == 1
        //   ? 1
        //   : Math.pow(2, -10 * x) * Math.sin((x * 10 - 0.75) * c4) + 1;
        }
}
