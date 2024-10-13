/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.tile16;

import org.jaspa.eternity2.tile.Orientation;
import org.jaspa.eternity2.tile.Tile;
import org.jaspa.eternity2.tile.TileCriteria;
import org.jaspa.eternity2.tile.TileIdSet;

/**
 * Class represents a corner tile made from 16 other tiles
 */
public class CornerTile16 extends Tile16 {

    private static final TileCriteria BOTTOMRIGHTCRITERIA = new TileCriteria(SideTile16.class, Orientation.NORTH);
    private static final TileCriteria TOPLEFTCRITERIA = new TileCriteria(SideTile16.class, Orientation.WEST);

    /**
     * Parameterless constructor
     */
    public CornerTile16() {
        super();
    }

    /**
     * Construct a corner tile
     *
     * @param name  The name of the corner tile
     * @param top   The side definition for the top side
     * @param right The side definition for the right hand side
     */
    public CornerTile16(String name, Tile topLeft, Tile topRight, Tile bottomRight, Tile bottomLeft) {
        super(name, topLeft, topRight, bottomRight, bottomLeft);
    }

    /**
     * Construct a corner tile setting the orientation
     *
     * @param name      Name of the tile
     * @param tileId    The Id of the tile
     * @param topKey    Edge key of the top edge
     * @param rightKey  Edge key of the right edge
     * @param bottomKey Edge key of the bottom edge
     * @param leftKey   Edge key of the left edge
     */
    public CornerTile16(String name, TileIdSet tileId, String topKey, String rightKey, String bottomKey, String leftKey) {
        super(name, tileId, topKey, rightKey, bottomKey, leftKey);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
    @Override
    public CornerTile16 clone() {
        CornerTile16 result = new CornerTile16();
        copy(result);
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.tile.Tile#getTopLeftClass()
     */
    public TileCriteria getTopLeftCriteria() {
        return TOPLEFTCRITERIA;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.tile.Tile#getBottomRightCriteria()
     */
    public TileCriteria getBottomRightCriteria() {
        return BOTTOMRIGHTCRITERIA;
    }

}
