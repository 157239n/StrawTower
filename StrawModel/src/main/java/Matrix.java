import javax.annotation.Nonnull;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Matrix {
    private double[][] values;
    private int numberOfRows;
    private int numberOfColumns;

    private Matrix(int numberOfRows, int numberOfColumns) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        values = new double[numberOfRows][numberOfColumns];
        clear();
    }

    private void clear() {
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                setValue(i, j, 0);
            }
        }
    }

    private void setValue(final int rowNumber, final int columnNumber, final double value) {
        values[rowNumber][columnNumber] = value;
    }

    public static Matrix generateRotationalMatrix(@Nonnull final Vector vector, final double degrees) {
        double cosine = Math.cos(Math.toRadians(degrees));
        double oneMinusCosine = 1 - cosine;
        double sine = Math.sin(Math.toRadians(degrees));
        double vectorLength = vector.length();
        double x = vector.get(Axis.X) / vectorLength;
        double y = vector.get(Axis.Y) / vectorLength;
        double z = vector.get(Axis.Z) / vectorLength;
        Matrix ans = new Matrix(3, 3);
        ans.setValue(0, 0, cosine + x * x * oneMinusCosine);
        ans.setValue(0, 1, x * y * oneMinusCosine - z * sine);
        ans.setValue(0, 2, x * z * oneMinusCosine + y * sine);
        ans.setValue(1, 0, y * x * oneMinusCosine + z * sine);
        ans.setValue(1, 1, cosine + y * y * oneMinusCosine);
        ans.setValue(1, 2, y * z * oneMinusCosine - x * sine);
        ans.setValue(2, 0, z * x * oneMinusCosine - y * sine);
        ans.setValue(2, 1, z * y * oneMinusCosine + x * sine);
        ans.setValue(2, 2, cosine + z * z * oneMinusCosine);
        return ans;
    }

    public Vector multiply(Vector vector) {
        double x = vector.get(Axis.X);
        double y = vector.get(Axis.Y);
        double z = vector.get(Axis.Z);
        return new Vector(values[0][0] * x + values[0][1] * y + values[0][2] * z, values[1][0] * x + values[1][1] * y + values[1][2] * z, values[2][0] * x + values[2][1] * y + values[2][2] * z);
    }
}
