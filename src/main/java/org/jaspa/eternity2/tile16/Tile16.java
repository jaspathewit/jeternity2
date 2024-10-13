/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.tile16;

import org.jaspa.eternity2.tile.Orientation;
import org.jaspa.eternity2.tile.Tile;
import org.jaspa.eternity2.tile.TileCriteria;
import org.jaspa.eternity2.tile.TileIdSet;
import org.jaspa.eternity2.util.ObjectToStringFormatter;
import org.jaspa.eternity2.util.Util;

/**
 * Class encapsulates an Eternity2 puzzle tile made up from 16 other tiles
 */
public abstract class Tile16 implements Tile {

    private static final TileCriteria TOPRIGHTCRITERIA = new TileCriteria(MidTile16.class);
    private TileIdSet tileId;
    private Orientation orientation;
    private int[][] nameKeys = new int[4][4];

    private String topKey;
    private String rightKey;
    private String leftKey;
    private String bottomKey;
    // only used for data store operations
    private int orientationCode;
    private String leftBottomKey;

    /**
     * Parameterless constructor
     */
    public Tile16() {
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
    public Tile16(String name, Tile topLeft, Tile topRight, Tile bottomRight, Tile bottomLeft) {
        // all composite tiles are constructed in north orientation
        this.tileId = Util.getTileIdUnion(topLeft, topRight, bottomRight, bottomLeft);
        setOrientation(Orientation.NORTH);

        // store the nameKeys
        // This can be generalised
        this.nameKeys = Util.cloneNameKeys(topLeft.getNameKeys(), topRight.getNameKeys(), bottomRight.getNameKeys(),
                bottomLeft.getNameKeys());

        // Store the side keys
        setTopKey(topLeft.getTopKey() + topRight.getTopKey());
        setRightKey(topRight.getRightKey() + bottomRight.getRightKey());
        setBottomKey(bottomLeft.getBottomKey() + bottomRight.getBottomKey());
        setLeftKey(topLeft.getLeftKey() + bottomLeft.getLeftKey());

    }

    /**
     * Construct a single tile with the given name, orientation and side
     * definitions
     *
     * @param name
     * @param tileId
     * @param top
     * @param right
     * @param left
     * @param Bottom
     */
    public Tile16(String name, TileIdSet tileId, String topKey, String rightKey, String bottomKey, String leftKey) {
        assert name != null : "Tile name cannot be null";
        assert tileId != null : "TileId cannot be null";
        assert topKey != null : "Side cannot be null";
        assert rightKey != null : "Side cannot be null";
        assert bottomKey != null : "Side cannot be null";
        assert leftKey != null : "Side cannot be null";

        // all tiles start in North orientation
        this.tileId = tileId;
        setOrientation(Orientation.NORTH);

        // store the edge Keys
        setTopKey(topKey);
        setRightKey(rightKey);
        setBottomKey(bottomKey);
        setLeftKey(leftKey);

    }

    /**
     * @return the tileId
     */
    public TileIdSet getTileId() {
        return tileId;
    }

    /**
     * @param tileId the tileId to set
     */
    protected void setTileId(TileIdSet tileId) {
        this.tileId = tileId;
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

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.tile.Tile#getTopLeftClass()
     */
    abstract public TileCriteria getTopLeftCriteria();

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.tile.Tile#getBottomRightCriteria()
     */
    abstract public TileCriteria getBottomRightCriteria();

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.tile.Tile#getTopRightCriteria()
     */
    public TileCriteria getTopRightCriteria() {
        return TOPRIGHTCRITERIA;
    }

    /**
     * @return the name
     */
    public String getName() {
        String result = Util.getName(this);
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.tile.Tile#getNameKey()
     */
    public int[][] getNameKeys() {
        return nameKeys;
    }

    /**
     * @param nameKeys The nameKeys to set
     */
    protected void setNameKeys(int[][] nameKeys) {
        this.nameKeys = nameKeys;
    }

    /**
     * @return the topKey
     */
    public String getTopKey() {
        return topKey;
    }

    /**
     * @param topKey the topKey to set
     */
    protected void setTopKey(String topKey) {
        this.topKey = topKey;
    }

    /**
     * @return the rightKey
     */
    public String getRightKey() {
        return rightKey;
    }

    /**
     * @param rightKey the rightKey to set
     */
    protected void setRightKey(String rightKey) {
        this.rightKey = rightKey;
    }

    /**
     * @return the leftKey
     */
    public String getLeftKey() {
        return leftKey;
    }

    /**
     * @param leftKey the leftKey to set
     */
    protected void setLeftKey(String leftKey) {
        this.leftKey = leftKey;
        // also update the leftBottomKey
        this.leftBottomKey = this.leftKey + this.bottomKey;
    }

    /**
     * @return the bottomKey
     */
    public String getBottomKey() {
        return bottomKey;
    }

    /**
     * @param bottomKey the bottomKey to set
     */
    protected void setBottomKey(String bottomKey) {
        this.bottomKey = bottomKey;
        // also update the leftBottomKey
        this.leftBottomKey = this.leftKey + this.bottomKey;
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
    public Tile16 clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Copy the attributes of this Tile1 into the given Tile1
     *
     * @param tile The tile into wich the attributes are copied
     */
    protected void copy(Tile16 tile) {
        tile.setTileId(getTileId());
        tile.setTopKey(getTopKey());
        tile.setRightKey(getRightKey());
        tile.setBottomKey(getBottomKey());
        tile.setLeftKey(getLeftKey());
        int[][] nameKeys = Util.cloneNameKeys(getNameKeys());
        tile.setNameKeys(nameKeys);
        tile.setOrientation(getOrientation());
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
        // set the orientation code
        this.orientationCode = orientation.code();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.Tile#rotate()
     */
    public void rotate() {
        // move the orientation on
        setOrientation(Orientation.rotate(orientation));

        String temp = topKey;
        // need to reverse the sense of the key
        // ie 12345678 -> 56781234
        setTopKey(Util.reverseKeySense(leftKey));
        // no need to swap as the sense is the same
        setLeftKey(bottomKey);
        // need to reverse the sense of the key
        // ie 12345678 -> 56781234
        setBottomKey(Util.reverseKeySense(rightKey));
        // no need to swap as the sense is the same
        setRightKey(temp);

        int[][] newNameKeys = Util.rotate(nameKeys);
        nameKeys = newNameKeys;
    }
}
