import processing.core.PApplet;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
public class ModelWindow extends PApplet {
    private ReferenceFrame referenceFrame = new ReferenceFrame();
    private List<LocalObject> objects = new ArrayList<>();
    private boolean needsToUpdateObjectsAfterChangingReferenceFrame = true;

    public ModelWindow() {
    }

    public void launch() {
        PApplet.runSketch(new String[]{this.getClass().getSimpleName()}, this);
    }

    public String getGreeting() {
        return "Hello world.";
    }

    @Override
    public void settings() {
        size(500, 500);
    }

    @Override
    public void setup() {
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void draw() {
        referenceFrame.rotateAroundOriginalAxis(Axis.Z, 1);
        //referenceFrame.rotate(Axis.Z, 1);
        needsToUpdateObjectsAfterChangingReferenceFrame = true;
        if (needsToUpdateObjectsAfterChangingReferenceFrame) {
            background(255);
            strokeWeight(2);
            stroke(color(0));
            needsToUpdateObjectsAfterChangingReferenceFrame = false;
            pushMatrix();
            translate(width / 2, height / 2);
            update();
            popMatrix();
        }
    }

    @Override
    public void mouseDragged() {
        /*
        referenceFrame.rotate(Axis.Y, mouseX - pmouseX);
        referenceFrame.rotate(Axis.X, mouseY - pmouseY);
        needsToUpdateObjectsAfterChangingReferenceFrame = true;
        /**/
    }

    public void addObjects(@Nonnull final LocalObject... objects) {
        for (LocalObject object : objects) {
            object.changeReferenceFrame(referenceFrame);
            object.specifySketch(this);
            this.objects.add(object);
        }
    }

    private void update() {
        for (LocalObject object : objects) {
            object.updateAfterChangingReferenceFrame();
            object.display();
        }
    }

    public SimplePoint[] addTriangle(final double centerToVertexLength, final double zElevation, final double degreesOffset) {
        SimplePoint point1 = new SimplePoint(centerToVertexLength * cos(radians((float) degreesOffset)), centerToVertexLength * sin(radians((float) degreesOffset)), zElevation);
        SimplePoint point2 = new SimplePoint(centerToVertexLength * cos(radians((float) degreesOffset + 120)), centerToVertexLength * sin(radians((float) degreesOffset + 120)), zElevation);
        SimplePoint point3 = new SimplePoint(centerToVertexLength * cos(radians((float) degreesOffset - 120)), centerToVertexLength * sin(radians((float) degreesOffset - 120)), zElevation);
        addObjects(new Line(point1, point2), new Line(point2, point3), new Line(point3, point1));
        return new SimplePoint[]{point1, point2, point3};
    }

    public SimplePoint[] addTriangle(final double centerToVertexLength, final double zElevation) {
        return addTriangle(centerToVertexLength, zElevation, 0);
    }

    public void addStraightSection(final double centerToVertexLengthAtTheBottom, final double centerToVertexLengthAtTheTop, final double bottomElevation, final double topElevation) {
        SimplePoint[] firstTriangleVertices = addTriangle(centerToVertexLengthAtTheBottom, bottomElevation, 0);
        SimplePoint[] secondTriangleVertices = addTriangle(centerToVertexLengthAtTheTop, topElevation, 0);
        addObjects(new Line(firstTriangleVertices[0], secondTriangleVertices[0]), new Line(firstTriangleVertices[1], secondTriangleVertices[1]), new Line(firstTriangleVertices[2], secondTriangleVertices[2]));
    }

    public void addSkewedSection(final double centerToVertexLengthAtTheBottom, final double centerToVertexLengthAtTheTop, final double bottomElevation, final double topElevation, final double degreesOffset) {
        SimplePoint[] firstTriangleVertices = addTriangle(centerToVertexLengthAtTheBottom, bottomElevation, 0 + degreesOffset);
        SimplePoint[] secondTriangleVertices = addTriangle(centerToVertexLengthAtTheTop, topElevation, 180 + degreesOffset);
        addObjects(new Line(firstTriangleVertices[0], secondTriangleVertices[0]), new Line(firstTriangleVertices[1], secondTriangleVertices[1]), new Line(firstTriangleVertices[2], secondTriangleVertices[2]));
    }

    public void makeModelLookCentered() {
        double maxZ = objects.get(0).max(Axis.Z);
        double minZ = objects.get(0).min(Axis.Z);
        for (int i = 1; i < objects.size(); i++) {
            maxZ = Math.max(maxZ, objects.get(i).max(Axis.Z));
            minZ = Math.min(minZ, objects.get(i).min(Axis.Z));
        }
        for (LocalObject object : objects) {
            object.translate(new Vector(0, 0, -(maxZ + minZ) / 2));
            object.scale(4.0 * height / (maxZ - minZ));
        }

        referenceFrame.rotate(Axis.Y, 195);
        referenceFrame.rotate(Axis.Z, 90);
        //referenceFrame.rotate(Axis.X, 20);
        needsToUpdateObjectsAfterChangingReferenceFrame = true;
        //throw new AssertionError("TODO");//TODO
    }
}
