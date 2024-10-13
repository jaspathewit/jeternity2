/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.tile256;

import org.jaspa.eternity2.tile.Orientation;
import org.jaspa.eternity2.tile.Tile;
import org.jaspa.eternity2.tile.TileCriteria;
import org.jaspa.eternity2.tile.TileIdSet;
import org.jaspa.eternity2.util.ObjectToStringFormatter;
import org.jaspa.eternity2.util.Util;

/**
 * Class represents an Eternity2 puzzle tile made up from 256 other tiles
 */
public class Tile256 implements Tile {

    protected Tile[] tiles = new Tile[4];
    private TileIdSet tileId;
    private Orientation orientation;
    private String topKey;
    private String rightKey;
    private String leftKey;
    private String bottomKey;

    /**
     * Parameterless constructor
     */
    public Tile256() {
        super();
    }

    /**
     * Construct a composite tile with the given name, orientation and tile
     * definitions
     *
     * @param name
     * @param top
     * @param right
     * @param Bottom
     * @param left
     */
    public Tile256(String name, Tile topLeft, Tile topRight, Tile bottomRight, Tile bottomLeft) {
        // all composite tiles start straight up
        this.tileId = Util.getTileIdUnion(bottomLeft, topLeft, topRight, bottomRight);
        this.orientation = Orientation.NORTH;
        tiles[0] = topLeft;
        tiles[1] = topRight;
        tiles[2] = bottomRight;
        tiles[3] = bottomLeft;

        recalculateKeys();
    }

    private void recalculateKeys() {
        // calculate the keys for this tile
        topKey = calcTopKey();
        rightKey = calcRightKey();
        leftKey = calcLeftKey();
        bottomKey = calcBottomKey();
    }

    private String calcLeftBottomKey() {
        return tiles[0].getLeftKey() + tiles[3].getLeftKey() + "-" + tiles[3].getBottomKey() + tiles[2].getBottomKey();
    }

    private String calcTopKey() {
        return tiles[0].getTopKey() + tiles[1].getTopKey();
    }

    private String calcBottomKey() {
        return tiles[3].getBottomKey() + tiles[2].getBottomKey();
    }

    private String calcRightKey() {
        return tiles[1].getRightKey() + tiles[3].getRightKey();
    }

    private String calcLeftKey() {
        return tiles[0].getLeftKey() + tiles[3].getLeftKey();
    }

    /**
     * @return the name
     */
    public String getName() {
        String result = Util.getName(this);
        return result;
    }

    /**
     * @return the tileId
     */
    public TileIdSet getTileId() {
        return tileId;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.jaspa.eternity2.tile.Tile#sharesTilesWith(org.jaspa.eternity2.tile
     * .Tile)
     */
    public boolean sharesTilesWith(Tile tile) {
        // get the TileId which is the intersection of the two TileIds
        TileIdSet tileId = this.getTileId().intersection(tile.getTileId());
        return !tileId.isEmptySet();
    }

    /**
     * @return the topKey
     */
    public String getTopKey() {
        return topKey;
    }

    /**
     * @return the rightKey
     */
    public String getRightKey() {
        return rightKey;
    }

    /**
     * @return the leftKey
     */
    public String getLeftKey() {
        return leftKey;
    }

    /**
     * @return the bottomKey
     */
    public String getBottomKey() {
        return bottomKey;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ObjectToStringFormatter.objectToString(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
    public Tile256 clone() throws CloneNotSupportedException {
        // clone all the tiles
        Tile topLeft = tiles[0].clone();
        Tile topRight = tiles[1].clone();
        Tile bottomRight = tiles[2].clone();
        Tile bottomLeft = tiles[3].clone();
        Tile256 result = new Tile256(getName(), topLeft, topRight, bottomRight, bottomLeft);
        result.setOrientation(orientation);
        return result;
    }

    /**
     * @return the orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * @param orientation the orientation to set
     */
    protected void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.Tile#rotate()
     */
    public void rotate() {
        // move the orientation on
        orientation = Orientation.rotate(orientation);

        // move the tiles round and rotate them
        Tile temp = tiles[0];

        tiles[0] = tiles[3];
        tiles[0].rotate();
        tiles[3] = tiles[2];
        tiles[3].rotate();
        tiles[2] = tiles[1];
        tiles[2].rotate();
        tiles[1] = temp;
        tiles[1].rotate();

        // recalculate the keys
        recalculateKeys();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.tile.Tile#getTopLeftCriteria()
     */
    public TileCriteria getTopLeftCriteria() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.tile.Tile#getBottomRightCriteria()
     */
    public TileCriteria getBottomRightCriteria() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.tile.Tile#getTopRightCriteria()
     */
    public TileCriteria getTopRightCriteria() {
        return null;
    }

    public int[][] getNameKeys() {
        return null;
    }

}
