import javax.annotation.Nonnull;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.radians;
import static processing.core.PApplet.sin;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Vector implements Cloneable {
    private double x = 0.0;
    private double y = 0.0;
    private double z = 0.0;

    public Vector(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(final double x, final double y) {
        this(x, y, 0);
    }

    public Vector() {
        this(0, 0, 0);
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Object clone() {
        return new Vector(x, y, z);
    }

    public Vector multiply(@Nonnull final SimplePoint point) {
        return new Vector(x * point.get(Axis.X), y * point.get(Axis.Y), z * point.get(Axis.Z));
    }

    public double dot(@Nonnull final Vector vector) {
        return get(Axis.X) * vector.get(Axis.X) + get(Axis.Y) * vector.get(Axis.Y) + get(Axis.Z) * vector.get(Axis.Z);
    }

    public double get(Axis axis) {
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

    public SimplePoint convertToSimplePoint() {
        return new SimplePoint(x, y, z);
    }

    public void rotate(@Nonnull final Axis axis, final double degrees) {
        rotate(axis, sin(radians((float) degrees)), cos(radians((float) degrees)));
    }

    public void rotate(@Nonnull final Axis axis, final double sine, final double cosine) {
        switch (axis) {
            case X:
                rotateX(sine, cosine);
            case Y:
                rotateY(sine, cosine);
            case Z:
                rotateZ(sine, cosine);
        }
    }

    private void rotateZ(final double sine, final double cosine) {
        double oldX = x, oldY = y;
        x = oldX * cosine - oldY * sine;
        y = oldY * cosine + oldX * sine;
    }

    private void rotateY(final double sine, final double cosine) {
        double oldX = x, oldZ = z;
        x = oldX * cosine - oldZ * sine;
        z = oldZ * cosine + oldX * sine;
    }

    private void rotateX(final double sine, final double cosine) {
        double oldZ = z, oldY = y;
        z = oldZ * cosine - oldY * sine;
        y = oldY * cosine + oldZ * sine;
    }

    public static Vector add(@Nonnull final Vector... vectors) {
        double totalX = 0.0;
        double totalY = 0.0;
        double totalZ = 0.0;
        for (Vector vector : vectors) {
            totalX += vector.get(Axis.X);
            totalY += vector.get(Axis.Y);
            totalZ += vector.get(Axis.Z);
        }
        return new Vector(totalX, totalY, totalZ);
    }

    public Vector scale(double scale) {
        return new Vector(x * scale, y * scale, z * scale);
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }
}
