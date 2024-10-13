/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.tile64;

import org.jaspa.eternity2.tile.Orientation;
import org.jaspa.eternity2.tile.Tile;
import org.jaspa.eternity2.tile.TileCriteria;
import org.jaspa.eternity2.tile.TileIdSet;

/**
 * Class represents a side tile made from 64 tiles.
 */
public class SideTile64 extends Tile64 {

    private static final TileCriteria BOTTOMRIGHTCRITERIA = new TileCriteria(SideTile64.class, Orientation.NORTH);
    private static final TileCriteria TOPLEFTCRITERIA = new TileCriteria(MidTile64.class);

    /**
     * Parameterless constructor
     */
    public SideTile64() {
        super();
    }

    /**
     * Construct a Side tile composed of four Tile1
     *
     * @param name        the name of the composit tile
     * @param topLeft     The tile in the topleft location
     * @param topRight    The tile in the topRight location
     * @param bottomRight The tile in the bottomRight location
     * @param bottomLeft  The tile in the bottom left location
     */
    public SideTile64(String name, Tile topLeft, Tile topRight, Tile bottomRight, Tile bottomLeft) {
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
    public SideTile64(String name, TileIdSet tileId, String topKey, String rightKey, String bottomKey, String leftKey) {
        super(name, tileId, topKey, rightKey, bottomKey, leftKey);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
    @Override
    public SideTile64 clone() {
        SideTile64 result = new SideTile64();
        copy(result);
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.tile.Tile#getTopLeftCriteria()
     */
    public TileCriteria getTopLeftCriteria() {
        return TOPLEFTCRITERIA;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.tile.Tile#getTopLeftClass()
     */
    public TileCriteria getBottomRightCriteria() {
        return BOTTOMRIGHTCRITERIA;
    }

}
