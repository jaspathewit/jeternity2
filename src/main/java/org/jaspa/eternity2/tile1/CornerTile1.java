/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.tile1;

import org.jaspa.eternity2.tile.Orientation;
import org.jaspa.eternity2.tile.TileCriteria;
import org.jaspa.eternity2.tile.TileIdSet;

/**
 * Class represent a corner tile made from a single tile
 */
public class CornerTile1 extends Tile1 {

    private static final TileCriteria TOPLEFTCRITERIA = new TileCriteria(SideTile1.class, Orientation.WEST);
    private static final TileCriteria BOTTOMRIGHTCRITERIA = new TileCriteria(SideTile1.class, Orientation.NORTH);

    /**
     * Parameterless constructor
     */
    public CornerTile1() {
        super();
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
    public CornerTile1(String name, TileIdSet tileId, String topKey, String rightKey, String bottomKey, String leftKey) {
        super(name, tileId, topKey, rightKey, bottomKey, leftKey);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
    @Override
    public CornerTile1 clone() {
        CornerTile1 result = new CornerTile1();
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
