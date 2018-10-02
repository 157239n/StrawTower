import javax.annotation.Nonnull;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Line extends LocalObject {
    private SimplePoint point1;
    private SimplePoint point2;

    private SimplePoint displayedPoint1 = new SimplePoint();
    private SimplePoint displayedPoint2 = new SimplePoint();

    public Line(@Nonnull final SimplePoint point1, @Nonnull final SimplePoint point2) {
        this.point1 = (SimplePoint) point1.clone();
        this.point2 = (SimplePoint) point2.clone();
    }

    @Override
    void internalDisplay() {
        sketch.line((float) displayedPoint1.get(Axis.X), (float) displayedPoint1.get(Axis.Z), (float) displayedPoint2.get(Axis.X), (float) displayedPoint2.get(Axis.Z));
    }

    @Override
    public double max(@Nonnull Axis axis) {
        return Math.max(point1.get(axis), point2.get(axis));
    }

    @Override
    public double min(@Nonnull Axis axis) {
        return Math.min(point1.get(axis), point2.get(axis));
    }

    @Override
    protected void calculateDisplayCoordinatesBasedOnReferenceFrame() {
        displayedPoint1 = referenceFrame.transform(point1);
        displayedPoint2 = referenceFrame.transform(point2);
    }

    @Override
    void translate(@Nonnull final Vector vector) {
        point1 = point1.translate(vector);
        point2 = point2.translate(vector);
        displayedPoint1 = referenceFrame.transform(point1);
        displayedPoint2 = referenceFrame.transform(point2);
    }

    @Override
    void scale(final double scale) {
        point1 = point1.scale(scale);
        point2 = point2.scale(scale);
        displayedPoint1 = referenceFrame.transform(point1);
        displayedPoint2 = referenceFrame.transform(point2);
    }
}
