import java.util.Scanner;

/**
 * A Java Scanner class example from http://alvinalexander.com
 */
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class StrawModel {
    enum ModelType {
        PYRAMID, CROSS_OVER_2_STAGE_PYRAMID, CROSS_OVER_3_STAGE_PYRAMID, UNDEFINED;

        static ModelType[] values = {PYRAMID, CROSS_OVER_2_STAGE_PYRAMID, CROSS_OVER_3_STAGE_PYRAMID};

        public String toString() {
            if (this == CROSS_OVER_2_STAGE_PYRAMID) {
                return "Cross over 2 stage pyramid";
            }
            if (this == PYRAMID) {
                return "3 stage pyramid";
            }
            if (this == CROSS_OVER_3_STAGE_PYRAMID) {
                return "Cross over 3 stage pyramid";
            }
            if (this == UNDEFINED) {
                return "Undefined";
            }
            throw new AssertionError();
        }

        public static ModelType getModelType(int index) {
            if (index >= 0 && index < size()) {
                return values[index];
            } else {
                return UNDEFINED;
            }
        }

        public static String getDescription(int index) {
            return getModelType(index).toString();
        }

        public static int size() {
            return values().length - 1;
        }
    }

    private static String unitString = "ul";
    private static String unitStringDescription = "unit length";

    public static void main(String[] args) {
        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);

        //  prompt for the user's name

        System.out.println("This program doesn't care about the unit you use, be it inches or meters. Whatever your input length is, the output will be of that unit. So if you input the numbers in inches, the result will be in inches. Because I don't know the unit you're typing in, I will write the results in an imaginary unit called \"" + unitString + "\" (" + unitStringDescription + ").");
        System.out.println();
        System.out.println("There are " + ModelType.size() + " types of models:");
        for (int i = 0; i < ModelType.size(); i++) {
            System.out.println("- " + String.valueOf(i) + ": " + ModelType.getModelType(i).toString());
        }
        System.out.println();
        System.out.print("Enter an integer of your preferred model: ");

        ModelType modelType = ModelType.getModelType(scanner.nextInt());

        if (modelType == ModelType.UNDEFINED) {
            System.out.println("We don't recognize that model number, please try again");
            return;
        }

        System.out.println();
        System.out.println("--------------------------");
        System.out.println("INPUTS");
        System.out.println("--------------------------");
        System.out.print("Length of the straw: ");
        double strawLength = scanner.nextDouble();
        System.out.print("Cost of each straw in dollars: ");
        double costPerStraw = scanner.nextDouble();
        System.out.print("Cost of each connectors in dollars: ");
        double costPerConnector = scanner.nextDouble();
        double totalHeight = 0;
        int numberOfStrawsUsed = 0;
        int numberOfConnectorsUsed = 0;

        switch (modelType) {
            case PYRAMID: {
                System.out.print("Number of straws the side segments in each layer have: ");
                int numberOfStrawsForSideSegment = scanner.nextInt();
                int numberOfStrawsFor3Bases = 3 + 6 + 9;
                numberOfStrawsUsed = numberOfStrawsFor3Bases + numberOfStrawsForSideSegment * 3 * 3;

                double sideSegmentLength = numberOfStrawsForSideSegment * strawLength;
                double sideLength = sideSegmentLength * 3;
                double triangleBaseSideLength = strawLength * 3;
                totalHeight = reversedPythagorean(sideLength, distanceFromCenterToVertexOfATriangle(triangleBaseSideLength));

                numberOfConnectorsUsed = 3 + 3 + 3;
                {
                    ModelWindow window = new ModelWindow();
                    SimplePoint[] baseTriangle = window.addTriangle(distanceFromCenterToVertexOfATriangle(strawLength * 3), 0);
                    window.addTriangle(distanceFromCenterToVertexOfATriangle(strawLength * 2), totalHeight / 3);
                    window.addTriangle(distanceFromCenterToVertexOfATriangle(strawLength * 1), 2 * totalHeight / 3);
                    SimplePoint summit = new SimplePoint(0, 0, totalHeight);
                    window.addObjects(new Line(summit, baseTriangle[0]), new Line(summit, baseTriangle[1]), new Line(summit, baseTriangle[2]));
                    window.makeModelLookCentered();
                    window.launch();
                }
                break;
            }
            case CROSS_OVER_2_STAGE_PYRAMID: {
                System.out.print("Number of straws the side support segments in the bottom layer have: ");
                int numberOfStrawsForSupportBeamOfBottomLayer = scanner.nextInt();
                System.out.print("Number of straws the side support segments in the top layer have: ");
                int numberOfStrawsForSupportBeamOfTopLayer = scanner.nextInt();

                double heightOfBottomLayer = heightOfSkewedBlock(2, 1, numberOfStrawsForSupportBeamOfBottomLayer, strawLength);
                double heightOfTopLayer = heightOfSkewedBlock(1, 0, numberOfStrawsForSupportBeamOfTopLayer, strawLength);
                totalHeight = heightOfBottomLayer + heightOfTopLayer;
                int numberOfStrawUsedInBottomLayer = numberOfStrawsUsedInBlock(2, numberOfStrawsForSupportBeamOfBottomLayer);
                int numberOfStrawUsedInTopLayer = numberOfStrawsUsedInBlock(1, numberOfStrawsForSupportBeamOfTopLayer);
                numberOfStrawsUsed = numberOfStrawUsedInBottomLayer + numberOfStrawUsedInTopLayer;

                numberOfConnectorsUsed = 3 + 3 + 1;
                {
                    ModelWindow window = new ModelWindow();
                    window.addSkewedSection(distanceFromCenterToVertexOfATriangle(strawLength * 2), distanceFromCenterToVertexOfATriangle(strawLength * 1), 0, heightOfBottomLayer, 0);
                    window.addSkewedSection(distanceFromCenterToVertexOfATriangle(strawLength * 1), 0, heightOfBottomLayer, heightOfBottomLayer + heightOfTopLayer, 180);
                    window.makeModelLookCentered();
                    window.launch();
                }
                break;
            }
            case CROSS_OVER_3_STAGE_PYRAMID: {
                System.out.print("Number of straws the side support segments in the bottom layer have: ");
                int numberOfStrawsForSupportBeamOfBottomLayer = scanner.nextInt();
                System.out.print("Number of straws the side support segments in the middle layer have: ");
                int numberOfStrawsForSupportBeamOfMiddleLayer = scanner.nextInt();
                System.out.print("Number of straws the side support segments in the top layer have: ");
                int numberOfStrawsForSupportBeamOfTopLayer = scanner.nextInt();

                double heightOfBottomLayer = heightOfSkewedBlock(3, 2, numberOfStrawsForSupportBeamOfBottomLayer, strawLength);
                double heightOfMiddleLayer = heightOfSkewedBlock(2, 1, numberOfStrawsForSupportBeamOfMiddleLayer, strawLength);
                double heightOfTopLayer = heightOfSkewedBlock(1, 0, numberOfStrawsForSupportBeamOfTopLayer, strawLength);
                totalHeight = heightOfBottomLayer + heightOfMiddleLayer + heightOfTopLayer;
                int numberOfStrawUsedInBottomLayer = numberOfStrawsUsedInBlock(3, numberOfStrawsForSupportBeamOfBottomLayer);
                int numberOfStrawUsedInMiddleLayer = numberOfStrawsUsedInBlock(2, numberOfStrawsForSupportBeamOfMiddleLayer);
                int numberOfStrawUsedInTopLayer = numberOfStrawsUsedInBlock(1, numberOfStrawsForSupportBeamOfTopLayer);
                numberOfStrawsUsed = numberOfStrawUsedInBottomLayer + numberOfStrawUsedInMiddleLayer + numberOfStrawUsedInTopLayer;

                numberOfConnectorsUsed = 3 + 3 + 3 + 1;
                {
                    ModelWindow window = new ModelWindow();
                    window.addSkewedSection(distanceFromCenterToVertexOfATriangle(strawLength * 3), distanceFromCenterToVertexOfATriangle(strawLength * 2), 0, heightOfBottomLayer, 0);
                    window.addSkewedSection(distanceFromCenterToVertexOfATriangle(strawLength * 2), distanceFromCenterToVertexOfATriangle(strawLength * 1), heightOfBottomLayer, heightOfBottomLayer + heightOfMiddleLayer, 180);
                    window.addSkewedSection(distanceFromCenterToVertexOfATriangle(strawLength * 1), 0, heightOfBottomLayer + heightOfMiddleLayer, heightOfBottomLayer + heightOfMiddleLayer + heightOfTopLayer, 0);
                    window.makeModelLookCentered();
                    window.launch();
                }
                break;
            }
        }

        generateReport(costPerStraw, numberOfStrawsUsed, costPerConnector, numberOfConnectorsUsed, totalHeight);
    }

    private static void generateReport(double costPerStraw, int numberOfStrawsUsed, double costPerConnector, int numberOfConnectorsUsed, double height) {
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("ANSWERS");
        System.out.println("--------------------------");
        System.out.println("Height: " + String.valueOf(height) + " " + unitString);
        System.out.println("Number of straws used: " + String.valueOf(numberOfStrawsUsed));
        System.out.println("Number of connectors used: " + String.valueOf(numberOfConnectorsUsed));
        double totalCost = costPerStraw * numberOfStrawsUsed + costPerConnector * numberOfConnectorsUsed;
        System.out.println("Cost: " + String.valueOf(totalCost) + "$");
        double costPerUnitHeight = totalCost / height;
        System.out.println("Cost per unit height: " + String.valueOf(costPerUnitHeight) + " $/" + unitString);
    }

    private static double distanceFromCenterToVertexOfATriangle(double lengthOfTriangleEdge) {
        return lengthOfTriangleEdge / Math.sqrt(3);
    }

    private static double reversedPythagorean(double hypotenuseLength, double sideLength) {
        return Math.sqrt(hypotenuseLength * hypotenuseLength - sideLength * sideLength);
    }

    /**
     * Imagine 2 triangles, one small, one big, parallel to each other but one are rotated 180 degrees
     */
    private static double heightOfSkewedBlock(int numberOfStrawOfBottomTriangleSide, int numberOfStrawOfTopTriangleSide, int numberOfStrawOfSupportBeam, double strawLength) {
        double p = distanceFromCenterToVertexOfATriangle(strawLength);
        return Math.sqrt(sq(numberOfStrawOfSupportBeam * strawLength) - sq((numberOfStrawOfBottomTriangleSide + numberOfStrawOfTopTriangleSide) * p));
    }

    private static int numberOfStrawsUsedInBlock(int numberOfStrawOfBottomTriangleSide, int numberOfStrawOfSupportBeam) {
        return (numberOfStrawOfSupportBeam + numberOfStrawOfBottomTriangleSide) * 3;
    }

    private static double sq(double number) {
        return number * number;
    }
}