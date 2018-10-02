@SuppressWarnings({"WeakerAccess", "unused"})
public class Color {

    public static final int BLACK = generateColor(0);
    public static final int WHITE = generateColor(255);
    public static final int RED = generateColor(255, 0, 0);
    public static final int GREEN = generateColor(0, 255, 0);
    public static final int BLUE = generateColor(0, 0, 255);
    public static final int DEEP_GREEN = generateColor(69, 128, 100);

    public static int generateColor(int red, int green, int blue) {
        if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
            throw new IllegalArgumentException();
        }
        return -1 - red * 256 * 256 - green * 256 - blue;
    }

    public static int generateColor(int greyScaleValue) {
        return generateColor(greyScaleValue, greyScaleValue, greyScaleValue);
    }
}
