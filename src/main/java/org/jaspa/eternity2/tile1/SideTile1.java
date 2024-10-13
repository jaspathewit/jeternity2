/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.tile1;

import org.jaspa.eternity2.tile.Orientation;
import org.jaspa.eternity2.tile.TileCriteria;
import org.jaspa.eternity2.tile.TileIdSet;

/**
 *
 */
public class SideTile1 extends Tile1 {

    private static final TileCriteria BOTTOMRIGHTCRITERIA = new TileCriteria(SideTile1.class, Orientation.NORTH);
    private static final TileCriteria TOPLEFTCRITERIA = new TileCriteria(MidTile1.class);

    /**
     * Parameterless constructor
     */
    public SideTile1() {
        super();
    }

    /**
     * Construct a single tile with the given name and edge key definitions
     *
     * @param name
     * @param topKey    Edge key of the top edge
     * @param rightKey  Edge key of the right edge
     * @param BottomKey Edge key of the bottom edge
     * @param leftKey   Edge key of the left edge
     */
    public SideTile1(String name, TileIdSet tileId, String topKey, String rightKey, String bottomKey, String leftKey) {
        super(name, tileId, topKey, rightKey, bottomKey, leftKey);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
    @Override
    public SideTile1 clone() {
        // SideTile1 result = new SideTile1(getName(), getTileId(), getTopKey(),
        // getRightKey(), getBottomKey(),
        // getLeftKey());
        SideTile1 result = new SideTile1();
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
