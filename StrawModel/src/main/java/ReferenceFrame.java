import javax.annotation.Nonnull;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.radians;
import static processing.core.PApplet.sin;

@SuppressWarnings("WeakerAccess")
public class ReferenceFrame {
    private Vector i = new Vector(1, 0, 0);
    private Vector j = new Vector(0, 1, 0);
    private Vector k = new Vector(0, 0, 1);

    public ReferenceFrame(@Nonnull final Vector i, @Nonnull final Vector j, @Nonnull final Vector k) {
        this.i = (Vector) i.clone();
        this.j = (Vector) j.clone();
        this.k = (Vector) k.clone();
    }

    public ReferenceFrame() {
        this(new Vector(1, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, 1));
    }

    public SimplePoint transform(@Nonnull final SimplePoint point) {
        return Vector.add(i.scale(point.get(Axis.X)), j.scale(point.get(Axis.Y)), k.scale(point.get(Axis.Z))).convertToSimplePoint();
    }

    public void rotate(@Nonnull final Axis axis, final double degrees) {
        double sine = sin(radians((float) degrees)), cosine = cos(radians((float) degrees));
        i.rotate(axis, sine, cosine);
        j.rotate(axis, sine, cosine);
        k.rotate(axis, sine, cosine);
    }

    public void rotate(@Nonnull Vector axis, final double degrees) {
        Matrix rotationalMatrix = Matrix.generateRotationalMatrix(axis, degrees);
        i = rotationalMatrix.multiply(i);
        j = rotationalMatrix.multiply(j);
        k = rotationalMatrix.multiply(k);
    }

    public void rotateAroundOriginalAxis(@Nonnull Axis axis, final double degrees) {
        switch (axis) {
            case X: rotate(i, degrees);
            case Y: rotate(j, degrees);
            case Z: rotate(k, degrees);
        }
    }
}
