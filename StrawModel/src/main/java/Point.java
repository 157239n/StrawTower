import javax.annotation.Nonnull;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Point extends LocalObject {
    private SimplePoint realPoint = new SimplePoint();
    private SimplePoint displayedPoint = new SimplePoint();

    public Point(final double x, final double y, final double z) {
        realPoint = new SimplePoint(x, y, z);
        displayedPoint = referenceFrame.transform(realPoint);
    }

    public Point(final double x, final double y) {
        this(x, y, 0);
    }

    @Override
    protected void internalDisplay() {
        sketch.point((float) get(Axis.X), (float) get(Axis.Y));
    }

    @Override
    public double max(@Nonnull Axis axis) {
        return realPoint.get(axis);
    }

    @Override
    public double min(@Nonnull Axis axis) {
        return realPoint.get(axis);
    }

    public double distanceTo(@Nonnull final Point point) {
        return Math.sqrt(Environment.square(get(Axis.X) - point.get(Axis.X)) + Environment.square(get(Axis.Y) - point.get(Axis.Y)) + Environment.square(get(Axis.Z) - point.get(Axis.Z)));
    }

    @Override
    protected void calculateDisplayCoordinatesBasedOnReferenceFrame() {
        displayedPoint = referenceFrame.transform(realPoint);
    }

    @Override
    public void translate(@Nonnull final Vector vector) {
        realPoint = realPoint.translate(vector);
        displayedPoint = referenceFrame.transform(realPoint);
    }

    @Override
    public void scale(final double scale) {
        realPoint = realPoint.scale(scale);
        displayedPoint = referenceFrame.transform(realPoint);
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Object clone() {
        return new Point(get(Axis.X), get(Axis.Y), get(Axis.Z));
    }

    public double get(@Nonnull final Axis axis) {
        return realPoint.get(axis);
    }

    public double getDisplayed(@Nonnull final Axis axis) {
        return displayedPoint.get(axis);
    }
}
