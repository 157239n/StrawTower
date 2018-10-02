import javax.annotation.Nonnull;

@SuppressWarnings({"WeakerAccess", "unused"})
final public class SimplePoint implements Cloneable {
    private double x = 0.0;
    private double y = 0.0;
    private double z = 0.0;

    public SimplePoint(final double x, final double y, final double z) {
        setCoordinates(x, y, z);
    }

    public SimplePoint(final double x, final double y) {
        this(x, y, 0);
    }

    public SimplePoint() {
        this(0, 0, 0);
    }

    private void setCoordinates(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public final double get(@Nonnull final Axis axis) {
        switch (axis) {
            case X:
                return x;
            case Y:
                return y;
            case Z:
                return z;
        }
        throw new AssertionError();
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public final Object clone() {
        return new SimplePoint(x, y, z);
    }

    public final SimplePoint multiply(@Nonnull final Vector vector) {
        return new SimplePoint(get(Axis.X) * vector.get(Axis.X), get(Axis.Y) * vector.get(Axis.Y), get(Axis.Z) * vector.get(Axis.Z));
    }

    public static SimplePoint add(@Nonnull final SimplePoint... points) {
        double totalX = 0.0;
        double totalY = 0.0;
        double totalZ = 0.0;
        for (SimplePoint point : points) {
            totalX += point.x;
            totalY += point.y;
            totalZ += point.z;
        }
        return new SimplePoint(totalX, totalY, totalZ);
    }

    public Vector convertToVector() {
        return new Vector(x, y, z);
    }

    public SimplePoint translate(@Nonnull final Vector vector) {
        return new SimplePoint(x + vector.get(Axis.X), y + vector.get(Axis.Y), z + vector.get(Axis.Z));
    }

    public SimplePoint scale(final double scale) {
        return new SimplePoint(x * scale, y * scale, z * scale);
    }
}
