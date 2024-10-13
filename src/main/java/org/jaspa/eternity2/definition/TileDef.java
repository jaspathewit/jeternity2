/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.definition;

import org.jaspa.eternity2.tile.Orientation;
import org.jaspa.eternity2.tile.Side;
import org.jaspa.eternity2.tile.Tile;
import org.jaspa.eternity2.tile.TileIdSet;

/**
 * Class represents an Eternity2 puzzle tile
 */
public abstract class TileDef {

    private final String name;
    private final TileIdSet tileId;
    private final Side top;
    private final Side right;
    private final Side bottom;
    private final Side left;
    private Orientation orientation;

    /**
     * Construct a single tileDefinition with the given name, orientation and
     * side
     * definitions
     *
     * @param name   the name of the tile
     * @param tileId the id of the tile
     * @param top    the top side definition
     * @param right  the right side definition
     * @param bottom the bottom definition
     * @param left   the left side definition
     */
    public TileDef(String name, TileIdSet tileId, Side top, Side right, Side bottom, Side left) {
        assert name != null : "Tile name cannot be null";
        assert tileId != null : "TileId cannot be null";
        assert top != null : "Side cannot be null";
        assert right != null : "Side cannot be null";
        assert bottom != null : "Side cannot be null";
        assert left != null : "Side cannot be null";

        // all tiles start in North orientation
        this.name = name;
        this.tileId = tileId;
        this.orientation = Orientation.NORTH;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }

    /**
     * @return the tileId
     */
    public TileIdSet getTileId() {
        return tileId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the topKey
     */
    public String getTopKey() {
        return top.getEdgeKey();
    }

    /**
     * @return the rightKey
     */
    public String getRightKey() {
        return right.getEdgeKey();
    }

    /**
     * @return the leftKey
     */
    public String getLeftKey() {
        return left.getEdgeKey();
    }

    /**
     * @return the bottomKey
     */
    public String getBottomKey() {
        return bottom.getEdgeKey();
    }

    /**
     * @return the top
     */
    public Side getTop() {
        return top;
    }

    /**
     * @return the right
     */
    public Side getRight() {
        return right;
    }

    /**
     * @return the bottom
     */
    public Side getBottom() {
        return bottom;
    }

    /**
     * @return the left
     */
    public Side getLeft() {
        return left;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String sb = name + "\n" +
                String.format("%-10s,", top.getBackground()) +
                String.format("%-10s,", top.getForeground()) +
                String.format("%-10s\n", top.getPattern()) +
                String.format("%-10s,", right.getBackground()) +
                String.format("%-10s,", right.getForeground()) +
                String.format("%-10s\n", right.getPattern()) +
                String.format("%-10s,", bottom.getBackground()) +
                String.format("%-10s,", bottom.getForeground()) +
                String.format("%-10s\n", bottom.getPattern()) +
                String.format("%-10s,", left.getBackground()) +
                String.format("%-10s,", left.getForeground()) +
                String.format("%-10s\n", left.getPattern());
        return sb;
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
    }

    abstract public Tile createTile();
}
