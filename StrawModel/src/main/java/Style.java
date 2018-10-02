import processing.core.PApplet;

import javax.annotation.Nonnull;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Style {
    private double strokeWeight = 1;
    private int strokeColor = Color.BLACK;
    private int fillColor = Color.WHITE;
    private boolean haveFill = true;
    private boolean haveStroke = true;

    public Style() {
    }

    public void setStrokeWeight(final double strokeWeight) {
        haveStroke = true;
        this.strokeWeight = strokeWeight;
    }

    public void setStrokeColor(final int strokeColor) {
        haveStroke = true;
        this.strokeColor = strokeColor;
    }

    public void setFillColor(final int fillColor) {
        haveFill = true;
        this.fillColor = fillColor;
    }

    public void setNoStroke() {
        haveStroke = false;
    }

    public void setNoFill() {
        haveFill = false;
    }

    public void applyStyleToSketch(@Nonnull final PApplet sketch) {
        if (haveFill) {
            sketch.fill(fillColor);
        } else {
            sketch.noFill();
        }
        if (haveStroke) {
            sketch.stroke(strokeColor);
            sketch.strokeWeight((float) strokeWeight);
        } else {
            sketch.noFill();
        }
    }
}
