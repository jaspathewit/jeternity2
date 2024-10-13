/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.tile16;

import org.jaspa.eternity2.tile.Tile;
import org.jaspa.eternity2.tile.TileCriteria;
import org.jaspa.eternity2.tile.TileIdSet;

/**
 * Class represents a middle tile made up of 16 tiles
 */
public class MidTile16 extends Tile16 {

    private static final TileCriteria BOTTOMRIGHTCRITERIA = new TileCriteria(MidTile16.class);
    private static final TileCriteria TOPLEFTCRITERIA = BOTTOMRIGHTCRITERIA;

    /**
     * Parameterless constructor
     */
    public MidTile16() {
        super();
    }

    /**
     * Construct a Mid tile composed of four Tile1
     *
     * @param name        the name of the composit tile
     * @param topLeft     The tile in the topleft location
     * @param topRight    The tile in the topRight location
     * @param bottomRight The tile in the bottomRight location
     * @param bottomLeft  The tile in the bottom left location
     */
    public MidTile16(String name, Tile topLeft, Tile topRight, Tile bottomRight, Tile bottomLeft) {
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
    public MidTile16(String name, TileIdSet tileId, String topKey, String rightKey, String bottomKey, String leftKey) {
        super(name, tileId, topKey, rightKey, bottomKey, leftKey);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
    @Override
    public MidTile16 clone() {
        MidTile16 result = new MidTile16();
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
     * @see org.jaspa.eternity2.tile.Tile#getBottomRightCriteria()
     */
    public TileCriteria getBottomRightCriteria() {
        return BOTTOMRIGHTCRITERIA;
    }
}
