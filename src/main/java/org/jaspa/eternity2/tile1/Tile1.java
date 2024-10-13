/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.tile1;

import org.jaspa.eternity2.tile.Orientation;
import org.jaspa.eternity2.tile.Tile;
import org.jaspa.eternity2.tile.TileCriteria;
import org.jaspa.eternity2.tile.TileIdSet;
import org.jaspa.eternity2.util.ObjectToStringFormatter;
import org.jaspa.eternity2.util.Util;

/**
 * Class represents a single Eternity2 puzzle tile
 */
public abstract class Tile1 implements Tile {

    private static final TileCriteria TOPRIGHTCRITERIA = new TileCriteria(MidTile1.class);
    private Orientation orientation;

    private TileIdSet tileId;

    private int[][] nameKeys = new int[1][1];
    private int nameKey;
    private String topKey;
    private String rightKey;
    private String bottomKey;
    private String leftKey;

    // only used for data store operations
    private int orientationCode;
    private String leftBottomKey;

    /**
     * Parameterless constructor
     */
    public Tile1() {
        super();
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
    public Tile1(String name, TileIdSet tileId, String topKey, String rightKey, String bottomKey, String leftKey) {
        assert name != null : "Tile name cannot be null";
        assert tileId != null : "TileId cannot be null";
        assert topKey != null : "Side cannot be null";
        assert rightKey != null : "Side cannot be null";
        assert bottomKey != null : "Side cannot be null";
        assert leftKey != null : "Side cannot be null";

        // all tiles start in North orientation
        this.tileId = tileId;
        setOrientation(Orientation.NORTH);

        // store the name key (also for the Object Store)
        this.nameKeys[0][0] = Util.getNameKey(name, orientation);
        this.nameKey = this.nameKeys[0][0];

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

    /**
     * @return the name
     */
    public String getName() {
        String result = Util.getName(this);
        return result;
    }

    /**
     * @return the array of name keys
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
     * @return the single nameKey
     */
    public int getNameKey() {
        return nameKey;
    }

    /**
     * @param nameKey the nameKey to set
     */
    protected void setNameKey(int nameKey) {
        this.nameKey = nameKey;
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
     * @see java.lang.Object#clone()
     */
    public Tile1 clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
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
        assert orientation != null : "Orientation cannot be null";
        this.orientation = orientation;
        // update the orientationCode
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

        // recalculate the name key
        nameKeys[0][0] = Util.rotateNameKey(nameKeys[0][0]);
        nameKey = nameKeys[0][0];

        // Move the edge keys round
        String temp = topKey;
        setTopKey(leftKey);
        setLeftKey(bottomKey);
        setBottomKey(rightKey);
        setRightKey(temp);

    }

    /**
     * Copy the attributes of this Tile1 into the given Tile1
     *
     * @param tile The tile into which the attributes are copied
     */
    protected void copy(Tile1 tile) {
        tile.setTileId(getTileId());
        tile.setTopKey(getTopKey());
        tile.setRightKey(getRightKey());
        tile.setBottomKey(getBottomKey());
        tile.setLeftKey(getLeftKey());
        int[][] nameKeys = Util.cloneNameKeys(getNameKeys());
        tile.setNameKeys(nameKeys);
        tile.setNameKey(getNameKey());
        tile.setOrientation(getOrientation());
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

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ObjectToStringFormatter.objectToString(this);
    }

}
