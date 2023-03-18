package com.github.kevindagame.brush;

import com.github.kevindagame.brush.perform.PerformerBrush;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.location.BaseLocation;

/**
 * https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#triangle-brush
 *
 * @author Giltwist
 */
public class TriangleBrush extends PerformerBrush {

    private final double[] coordsOne = new double[3]; // Three corners
    private final double[] coordsTwo = new double[3];
    private final double[] coordsThree = new double[3];
    private final double[] currentCoords = new double[3]; // For loop tracking
    private final double[] vectorOne = new double[3]; // Point 1 to 2
    private final double[] vectorTwo = new double[3]; // Point 1 to 3
    private final double[] vectorThree = new double[3]; // Point 2 to 3, for area calculations
    private final double[] normalVector = new double[3];
    private int cornernumber = 1;


    private void triangleA(final SnipeData v) {
        switch (this.cornernumber) {
            case 1:
                this.coordsOne[0] = this.getTargetBlock().getX() + .5 * this.getTargetBlock().getX() / Math.abs(this.getTargetBlock().getX()); // I hate you sometimes, Notch. Really? Every quadrant is
                // different?
                this.coordsOne[1] = this.getTargetBlock().getY() + .5;
                this.coordsOne[2] = this.getTargetBlock().getZ() + .5 * this.getTargetBlock().getZ() / Math.abs(this.getTargetBlock().getZ());
                this.cornernumber = 2;
                v.sendMessage(Messages.FIRST_POINT_SELECTED);
                break;
            case 2:
                this.coordsTwo[0] = this.getTargetBlock().getX() + .5 * this.getTargetBlock().getX() / Math.abs(this.getTargetBlock().getX()); // I hate you sometimes, Notch. Really? Every quadrant is
                // different?
                this.coordsTwo[1] = this.getTargetBlock().getY() + .5;
                this.coordsTwo[2] = this.getTargetBlock().getZ() + .5 * this.getTargetBlock().getZ() / Math.abs(this.getTargetBlock().getZ());
                this.cornernumber = 3;
                v.sendMessage(Messages.SECOND_POINT_SELECTED);
                break;
            case 3:
                this.coordsThree[0] = this.getTargetBlock().getX() + .5 * this.getTargetBlock().getX() / Math.abs(this.getTargetBlock().getX()); // I hate you sometimes, Notch. Really? Every quadrant is
                // different?
                this.coordsThree[1] = this.getTargetBlock().getY() + .5;
                this.coordsThree[2] = this.getTargetBlock().getZ() + .5 * this.getTargetBlock().getZ() / Math.abs(this.getTargetBlock().getZ());
                this.cornernumber = 1;
                v.sendMessage(Messages.THIRD_POINT_SELECTED);
                break;
            default:
                break;

        }

    }

    private void triangleP(final SnipeData v) {
        double lengthOne = 0;
        double lengthTwo = 0;
        double lengthThree = 0;
        double heronBig = 0;

        // Calculate slope vectors
        for (int i = 0; i < 3; i++) {
            this.vectorOne[i] = this.coordsTwo[i] - this.coordsOne[i];
            this.vectorTwo[i] = this.coordsThree[i] - this.coordsOne[i];
            this.vectorThree[i] = this.coordsThree[i] - this.coordsTwo[i];
        }

        // Calculate the cross product of vectorone and vectortwo
        this.normalVector[0] = this.vectorOne[1] * this.vectorTwo[2] - this.vectorOne[2] * this.vectorTwo[1];
        this.normalVector[1] = this.vectorOne[2] * this.vectorTwo[0] - this.vectorOne[0] * this.vectorTwo[2];
        this.normalVector[2] = this.vectorOne[0] * this.vectorTwo[1] - this.vectorOne[1] * this.vectorTwo[0];

        // Calculate magnitude of slope vectors
        lengthOne = Math.pow(Math.pow(this.vectorOne[0], 2) + Math.pow(this.vectorOne[1], 2) + Math.pow(this.vectorOne[2], 2), .5);
        lengthTwo = Math.pow(Math.pow(this.vectorTwo[0], 2) + Math.pow(this.vectorTwo[1], 2) + Math.pow(this.vectorTwo[2], 2), .5);
        lengthThree = Math.pow(Math.pow(this.vectorThree[0], 2) + Math.pow(this.vectorThree[1], 2) + Math.pow(this.vectorThree[2], 2), .5);

        // Bigger vector determines brush size
        final int brushSize = (int) Math.ceil(Math.max(lengthOne, lengthTwo));

        // Calculate constant term
        final double planeConstant = this.normalVector[0] * this.coordsOne[0] + this.normalVector[1] * this.coordsOne[1] + this.normalVector[2] * this.coordsOne[2];

        // Calculate the area of the full triangle
        heronBig = .25 * Math.pow(Math.pow(Math.pow(lengthOne, 2) + Math.pow(lengthTwo, 2) + Math.pow(lengthThree, 2), 2) - 2 * (Math.pow(lengthOne, 4) + Math.pow(lengthTwo, 4) + Math.pow(lengthThree, 4)), .5);

        if (lengthOne == 0 || lengthTwo == 0 || (this.coordsOne[0] == 0 && this.coordsOne[1] == 0 && this.coordsOne[2] == 0) || (this.coordsTwo[0] == 0 && this.coordsTwo[1] == 0 && this.coordsTwo[2] == 0) || (this.coordsThree[0] == 0 && this.coordsThree[1] == 0 && this.coordsThree[2] == 0)) {
            v.sendMessage(Messages.ERROR_INVALID_POINTS);
        } else {
            // Make the Changes
            final double[] cVectorOne = new double[3];
            final double[] cVectorTwo = new double[3];
            final double[] cVectorThree = new double[3];

            for (int y = -brushSize; y <= brushSize; y++) { // X DEPENDENT
                for (int z = -brushSize; z <= brushSize; z++) {
                    this.currentCoords[1] = this.coordsOne[1] + y;
                    this.currentCoords[2] = this.coordsOne[2] + z;
                    this.currentCoords[0] = (planeConstant - this.normalVector[1] * this.currentCoords[1] - this.normalVector[2] * this.currentCoords[2]) / this.normalVector[0];

                    // Area of triangle currentcoords, coordsone, coordstwo
                    for (int i = 0; i < 3; i++) {
                        cVectorOne[i] = this.coordsTwo[i] - this.coordsOne[i];
                        cVectorTwo[i] = this.currentCoords[i] - this.coordsOne[i];
                        cVectorThree[i] = this.currentCoords[i] - this.coordsTwo[i];
                    }
                    double cLengthOne = Math.pow(Math.pow(cVectorOne[0], 2) + Math.pow(cVectorOne[1], 2) + Math.pow(cVectorOne[2], 2), .5);
                    double cLengthTwo = Math.pow(Math.pow(cVectorTwo[0], 2) + Math.pow(cVectorTwo[1], 2) + Math.pow(cVectorTwo[2], 2), .5);
                    double cLengthThree = Math.pow(Math.pow(cVectorThree[0], 2) + Math.pow(cVectorThree[1], 2) + Math.pow(cVectorThree[2], 2), .5);

                    final double heronOne = .25 * Math.pow(Math.pow(Math.pow(cLengthOne, 2) + Math.pow(cLengthTwo, 2) + Math.pow(cLengthThree, 2), 2) - 2 * (Math.pow(cLengthOne, 4) + Math.pow(cLengthTwo, 4) + Math.pow(cLengthThree, 4)), .5);

                    // Area of triangle currentcoords, coordsthree, coordstwo
                    for (int i = 0; i < 3; i++) {
                        cVectorOne[i] = this.coordsTwo[i] - this.coordsThree[i];
                        cVectorTwo[i] = this.currentCoords[i] - this.coordsThree[i];
                        cVectorThree[i] = this.currentCoords[i] - this.coordsTwo[i];
                    }
                    cLengthOne = Math.pow(Math.pow(cVectorOne[0], 2) + Math.pow(cVectorOne[1], 2) + Math.pow(cVectorOne[2], 2), .5);
                    cLengthTwo = Math.pow(Math.pow(cVectorTwo[0], 2) + Math.pow(cVectorTwo[1], 2) + Math.pow(cVectorTwo[2], 2), .5);
                    cLengthThree = Math.pow(Math.pow(cVectorThree[0], 2) + Math.pow(cVectorThree[1], 2) + Math.pow(cVectorThree[2], 2), .5);
                    final double heronTwo = .25 * Math.pow(Math.pow(Math.pow(cLengthOne, 2) + Math.pow(cLengthTwo, 2) + Math.pow(cLengthThree, 2), 2) - 2 * (Math.pow(cLengthOne, 4) + Math.pow(cLengthTwo, 4) + Math.pow(cLengthThree, 4)), .5);

                    // Area of triangle currentcoords, coordsthree, coordsone
                    for (int i = 0; i < 3; i++) {
                        cVectorOne[i] = this.coordsOne[i] - this.coordsThree[i];
                        cVectorTwo[i] = this.currentCoords[i] - this.coordsThree[i];
                        cVectorThree[i] = this.currentCoords[i] - this.coordsOne[i];
                    }
                    cLengthOne = Math.pow(Math.pow(cVectorOne[0], 2) + Math.pow(cVectorOne[1], 2) + Math.pow(cVectorOne[2], 2), .5);
                    cLengthTwo = Math.pow(Math.pow(cVectorTwo[0], 2) + Math.pow(cVectorTwo[1], 2) + Math.pow(cVectorTwo[2], 2), .5);
                    cLengthThree = Math.pow(Math.pow(cVectorThree[0], 2) + Math.pow(cVectorThree[1], 2) + Math.pow(cVectorThree[2], 2), .5);
                    final double heronThree = .25 * Math.pow(Math.pow(Math.pow(cLengthOne, 2) + Math.pow(cLengthTwo, 2) + Math.pow(cLengthThree, 2), 2) - 2 * (Math.pow(cLengthOne, 4) + Math.pow(cLengthTwo, 4) + Math.pow(cLengthThree, 4)), .5);

                    final double barycentric = (heronOne + heronTwo + heronThree) / heronBig;

                    if (barycentric <= 1.1) {

                        this.positions.add(new BaseLocation(getWorld(), (int) this.currentCoords[0], (int) this.currentCoords[1], (int) this.currentCoords[2]));

                    }

                }
            } // END X DEPENDENT

            for (int x = -brushSize; x <= brushSize; x++) { // Y DEPENDENT
                for (int z = -brushSize; z <= brushSize; z++) {
                    this.currentCoords[0] = this.coordsOne[0] + x;
                    this.currentCoords[2] = this.coordsOne[2] + z;
                    this.currentCoords[1] = (planeConstant - this.normalVector[0] * this.currentCoords[0] - this.normalVector[2] * this.currentCoords[2]) / this.normalVector[1];

                    // Area of triangle currentcoords, coordsone, coordstwo
                    for (int i = 0; i < 3; i++) {
                        cVectorOne[i] = this.coordsTwo[i] - this.coordsOne[i];
                        cVectorTwo[i] = this.currentCoords[i] - this.coordsOne[i];
                        cVectorThree[i] = this.currentCoords[i] - this.coordsTwo[i];
                    }
                    double cLengthOne = Math.pow(Math.pow(cVectorOne[0], 2) + Math.pow(cVectorOne[1], 2) + Math.pow(cVectorOne[2], 2), .5);
                    double cLengthTwo = Math.pow(Math.pow(cVectorTwo[0], 2) + Math.pow(cVectorTwo[1], 2) + Math.pow(cVectorTwo[2], 2), .5);
                    double cLengthThree = Math.pow(Math.pow(cVectorThree[0], 2) + Math.pow(cVectorThree[1], 2) + Math.pow(cVectorThree[2], 2), .5);

                    final double heronOne = .25 * Math.pow(Math.pow(Math.pow(cLengthOne, 2) + Math.pow(cLengthTwo, 2) + Math.pow(cLengthThree, 2), 2) - 2 * (Math.pow(cLengthOne, 4) + Math.pow(cLengthTwo, 4) + Math.pow(cLengthThree, 4)), .5);

                    // Area of triangle currentcoords, coordsthree, coordstwo
                    for (int i = 0; i < 3; i++) {
                        cVectorOne[i] = this.coordsTwo[i] - this.coordsThree[i];
                        cVectorTwo[i] = this.currentCoords[i] - this.coordsThree[i];
                        cVectorThree[i] = this.currentCoords[i] - this.coordsTwo[i];
                    }
                    cLengthOne = Math.pow(Math.pow(cVectorOne[0], 2) + Math.pow(cVectorOne[1], 2) + Math.pow(cVectorOne[2], 2), .5);
                    cLengthTwo = Math.pow(Math.pow(cVectorTwo[0], 2) + Math.pow(cVectorTwo[1], 2) + Math.pow(cVectorTwo[2], 2), .5);
                    cLengthThree = Math.pow(Math.pow(cVectorThree[0], 2) + Math.pow(cVectorThree[1], 2) + Math.pow(cVectorThree[2], 2), .5);
                    final double heronTwo = .25 * Math.pow(Math.pow(Math.pow(cLengthOne, 2) + Math.pow(cLengthTwo, 2) + Math.pow(cLengthThree, 2), 2) - 2 * (Math.pow(cLengthOne, 4) + Math.pow(cLengthTwo, 4) + Math.pow(cLengthThree, 4)), .5);

                    // Area of triangle currentcoords, coordsthree, coordsone
                    for (int i = 0; i < 3; i++) {
                        cVectorOne[i] = this.coordsOne[i] - this.coordsThree[i];
                        cVectorTwo[i] = this.currentCoords[i] - this.coordsThree[i];
                        cVectorThree[i] = this.currentCoords[i] - this.coordsOne[i];
                    }
                    cLengthOne = Math.pow(Math.pow(cVectorOne[0], 2) + Math.pow(cVectorOne[1], 2) + Math.pow(cVectorOne[2], 2), .5);
                    cLengthTwo = Math.pow(Math.pow(cVectorTwo[0], 2) + Math.pow(cVectorTwo[1], 2) + Math.pow(cVectorTwo[2], 2), .5);
                    cLengthThree = Math.pow(Math.pow(cVectorThree[0], 2) + Math.pow(cVectorThree[1], 2) + Math.pow(cVectorThree[2], 2), .5);
                    final double heronThree = .25 * Math.pow(Math.pow(Math.pow(cLengthOne, 2) + Math.pow(cLengthTwo, 2) + Math.pow(cLengthThree, 2), 2) - 2 * (Math.pow(cLengthOne, 4) + Math.pow(cLengthTwo, 4) + Math.pow(cLengthThree, 4)), .5);

                    final double barycentric = (heronOne + heronTwo + heronThree) / heronBig;

                    if (barycentric <= 1.1) {

                        this.positions.add(new BaseLocation(getWorld(), (int) this.currentCoords[0], (int) this.currentCoords[1], (int) this.currentCoords[2]));

                    }

                }
            } // END Y DEPENDENT
            for (int x = -brushSize; x <= brushSize; x++) { // Z DEPENDENT
                for (int y = -brushSize; y <= brushSize; y++) {
                    this.currentCoords[0] = this.coordsOne[0] + x;
                    this.currentCoords[1] = this.coordsOne[1] + y;
                    this.currentCoords[2] = (planeConstant - this.normalVector[0] * this.currentCoords[0] - this.normalVector[1] * this.currentCoords[1]) / this.normalVector[2];

                    // Area of triangle currentcoords, coordsone, coordstwo
                    for (int i = 0; i < 3; i++) {
                        cVectorOne[i] = this.coordsTwo[i] - this.coordsOne[i];
                        cVectorTwo[i] = this.currentCoords[i] - this.coordsOne[i];
                        cVectorThree[i] = this.currentCoords[i] - this.coordsTwo[i];
                    }
                    double cLengthOne = Math.pow(Math.pow(cVectorOne[0], 2) + Math.pow(cVectorOne[1], 2) + Math.pow(cVectorOne[2], 2), .5);
                    double cLengthTwo = Math.pow(Math.pow(cVectorTwo[0], 2) + Math.pow(cVectorTwo[1], 2) + Math.pow(cVectorTwo[2], 2), .5);
                    double cLengthThree = Math.pow(Math.pow(cVectorThree[0], 2) + Math.pow(cVectorThree[1], 2) + Math.pow(cVectorThree[2], 2), .5);

                    final double heronOne = .25 * Math.pow(Math.pow(Math.pow(cLengthOne, 2) + Math.pow(cLengthTwo, 2) + Math.pow(cLengthThree, 2), 2) - 2 * (Math.pow(cLengthOne, 4) + Math.pow(cLengthTwo, 4) + Math.pow(cLengthThree, 4)), .5);

                    // Area of triangle currentcoords, coordsthree, coordstwo
                    for (int i = 0; i < 3; i++) {
                        cVectorOne[i] = this.coordsTwo[i] - this.coordsThree[i];
                        cVectorTwo[i] = this.currentCoords[i] - this.coordsThree[i];
                        cVectorThree[i] = this.currentCoords[i] - this.coordsTwo[i];
                    }
                    cLengthOne = Math.pow(Math.pow(cVectorOne[0], 2) + Math.pow(cVectorOne[1], 2) + Math.pow(cVectorOne[2], 2), .5);
                    cLengthTwo = Math.pow(Math.pow(cVectorTwo[0], 2) + Math.pow(cVectorTwo[1], 2) + Math.pow(cVectorTwo[2], 2), .5);
                    cLengthThree = Math.pow(Math.pow(cVectorThree[0], 2) + Math.pow(cVectorThree[1], 2) + Math.pow(cVectorThree[2], 2), .5);
                    final double heronTwo = .25 * Math.pow(Math.pow(Math.pow(cLengthOne, 2) + Math.pow(cLengthTwo, 2) + Math.pow(cLengthThree, 2), 2) - 2 * (Math.pow(cLengthOne, 4) + Math.pow(cLengthTwo, 4) + Math.pow(cLengthThree, 4)), .5);

                    // Area of triangle currentcoords, coordsthree, coordsone
                    for (int i = 0; i < 3; i++) {
                        cVectorOne[i] = this.coordsOne[i] - this.coordsThree[i];
                        cVectorTwo[i] = this.currentCoords[i] - this.coordsThree[i];
                        cVectorThree[i] = this.currentCoords[i] - this.coordsOne[i];
                    }
                    cLengthOne = Math.pow(Math.pow(cVectorOne[0], 2) + Math.pow(cVectorOne[1], 2) + Math.pow(cVectorOne[2], 2), .5);
                    cLengthTwo = Math.pow(Math.pow(cVectorTwo[0], 2) + Math.pow(cVectorTwo[1], 2) + Math.pow(cVectorTwo[2], 2), .5);
                    cLengthThree = Math.pow(Math.pow(cVectorThree[0], 2) + Math.pow(cVectorThree[1], 2) + Math.pow(cVectorThree[2], 2), .5);
                    final double heronThree = .25 * Math.pow(Math.pow(Math.pow(cLengthOne, 2) + Math.pow(cLengthTwo, 2) + Math.pow(cLengthThree, 2), 2) - 2 * (Math.pow(cLengthOne, 4) + Math.pow(cLengthTwo, 4) + Math.pow(cLengthThree, 4)), .5);

                    final double barycentric = (heronOne + heronTwo + heronThree) / heronBig;

                    // VoxelSniper.log.info("Bary: "+barycentric+", hb: "+heronbig+", h1: "+heronone+", h2: "+herontwo+", h3: "+heronthree);
                    if (barycentric <= 1.1) {
                        this.positions.add(new BaseLocation(getWorld(), (int) this.currentCoords[0], (int) this.currentCoords[1], (int) this.currentCoords[2]));
                    }
                }
            } // END Z DEPENDENT

        }

        // RESET BRUSH
        this.coordsOne[0] = 0;
        this.coordsOne[1] = 0;
        this.coordsOne[2] = 0;
        this.coordsTwo[0] = 0;
        this.coordsTwo[1] = 0;
        this.coordsTwo[2] = 0;
        this.coordsThree[0] = 0;
        this.coordsThree[1] = 0;
        this.coordsThree[2] = 0;

        this.cornernumber = 1;

    }

    @Override
    protected final void doArrow(final SnipeData v) {
        this.triangleA(v);
    }

    @Override
    protected final void doPowder(final SnipeData v) { // Add a point
        this.triangleP(v);
    }

    @Override
    public final void info(final VoxelMessage vm) { // Make the triangle
        vm.brushName(this.getName());
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.TRIANGLE_BRUSH_INFO);
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
        sendPerformerMessage(triggerHandle, v);
    }
}
