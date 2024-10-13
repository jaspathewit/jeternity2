/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.tile;

/**
 * Class encapsulates the orientation of a tile (according to points of
 * the compass) Normally this would be implemented as an ENUM but OME
 * has a problem
 * displaying the Enum
 * Orientation DNM is used in queries where the orientation Does Not
 * Matter
 */
public class Orientation {
    public static final Orientation DNM = new Orientation(0);
    public static final Orientation NORTH = new Orientation(1);
    public static final Orientation EAST = new Orientation(2);
    public static final Orientation SOUTH = new Orientation(3);
    public static final Orientation WEST = new Orientation(4);

    private final int code;

    public Orientation(int code) {
        this.code = code;
    }

    /**
     * Rotate an orientation
     *
     * @param orientation The orientation to rotate
     * @return The next orientaion on the rotation
     */
    public static Orientation rotate(Orientation orientation) {
        assert orientation != DNM : "Cannot increment from the DNM orientation";
        switch (orientation.code()) {
            case 1:
                return WEST;
            case 4:
                return SOUTH;
            case 3:
                return EAST;
            case 2:
                return NORTH;
        }
        // can't happen
        return null;
    }

    /**
     * retrieve the
     *
     * @param ch
     * @return
     */
    public static Orientation fromChar(char i) {
        // should not happen
        assert i == '1' || i == '2' || i == '3' || i == '4' : "Unknown Orientation CodeChar i";
        switch (i) {
            case '1':
                return NORTH;
            case '2':
                return EAST;
            case '3':
                return SOUTH;
            case '4':
                return WEST;
        }
        return null;
    }

    public int code() {
        return code;
    }

    @Override
    public String toString() {
        switch (code) {
            case 1:
                return "NORTH";
            case 2:
                return "EAST";
            case 3:
                return "SOUTH";
            case 4:
                return "WEST";
        }
        // can't happen
        return null;
    }
}
