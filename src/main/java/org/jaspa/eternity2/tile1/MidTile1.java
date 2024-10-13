/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.tile1;

import org.jaspa.eternity2.tile.TileCriteria;
import org.jaspa.eternity2.tile.TileIdSet;

/**
 * Class represents a tile in the middle of the puzzle made of a single tile
 */
public class MidTile1 extends Tile1 {

    private static final TileCriteria TOPLEFTCRITERIA = new TileCriteria(MidTile1.class);
    private static final TileCriteria BOTTOMRIGHTCRITERIA = new TileCriteria(MidTile1.class);

    /**
     * Parameterless constructor
     */
    public MidTile1() {
        super();
    }

    /**
     * Construct a middle tile
     *
     * @param name      Name of the tile
     * @param tileId    The Id of the tile
     * @param topKey    Edge key of the top edge
     * @param rightKey  Edge key of the right edge
     * @param bottomKey Edge key of the bottom edge
     * @param leftKey   Edge key of the left edge
     */
    public MidTile1(String name, TileIdSet tileId, String topKey, String rightKey, String bottomKey, String leftKey) {
        super(name, tileId, topKey, rightKey, bottomKey, leftKey);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
    @Override
    public MidTile1 clone() {
        // MidTile1 result = new MidTile1(getName(), getTileId(), getTopKey(),
        // getRightKey(), getBottomKey(), getLeftKey());
        MidTile1 result = new MidTile1();
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