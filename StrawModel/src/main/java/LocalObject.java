import processing.core.PApplet;

import javax.annotation.Nonnull;

@SuppressWarnings({"WeakerAccess", "unused", "SameParameterValue"})
public abstract class LocalObject {
    protected ReferenceFrame referenceFrame = new ReferenceFrame();
    protected Style style = null;
    protected PApplet sketch = null;

    public final void display(@Nonnull final ReferenceFrame referenceFrame) {
        changeReferenceFrame(referenceFrame);
        calculateDisplayCoordinatesBasedOnReferenceFrame();
        display();
    }

    public final void display() {
        if (sketch == null) {
            return;
        }
        if (style != null) {
            style.applyStyleToSketch(sketch);
        }
        internalDisplay();
    }

    abstract void internalDisplay();

    abstract double max(@Nonnull final Axis axis);

    abstract double min(@Nonnull final Axis axis);

    abstract protected void calculateDisplayCoordinatesBasedOnReferenceFrame();

    public final void changeReferenceFrame(@Nonnull final ReferenceFrame referenceFrame) {
        this.referenceFrame = referenceFrame;
        calculateDisplayCoordinatesBasedOnReferenceFrame();
    }

    public final void applyStyle(@Nonnull final Style style) {
        this.style = style;
    }

    public final void specifySketch(@Nonnull final PApplet sketch){
        this.sketch = sketch;
    }

    public final void updateAfterChangingReferenceFrame(){
        calculateDisplayCoordinatesBasedOnReferenceFrame();
    }

    abstract void translate(@Nonnull final Vector vector);

    abstract void scale(final double scale);
}
