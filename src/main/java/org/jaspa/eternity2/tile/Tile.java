package org.jaspa.eternity2.tile;

public interface Tile {

	/**
	 * @return a clone of the tile
	 */
	public abstract Tile clone() throws CloneNotSupportedException;

	/**
	 * Rotate the tile one orientation clockwise
	 */
	public abstract void rotate();

	/**
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * @return the nameKeys array containing the namekeys of the contained
	 *         tiles.
	 *         A name key is an integer vale of the following format 1 +
	 *         three digit tile number + orientation code 1 = North, 2 = East, 3
	 *         = South, 4 = West
	 */
	public abstract int[][] getNameKeys();

	/**
	 * @return the tileIds
	 */
	public abstract TileIdSet getTileId();

	/**
	 * @return the Orientation of the tile
	 */
	public abstract Orientation getOrientation();

	/**
	 * @return the topKey
	 */
	public abstract String getTopKey();

	/**
	 * @return the rightKey
	 */
	public abstract String getRightKey();

	/**
	 * @return the leftKey
	 */
	public abstract String getLeftKey();

	/**
	 * @return the bottomKey
	 */
	public abstract String getBottomKey();

	/**
	 * @param tile
	 *            The tile to test
	 * @return true if this tile shares tiles with the given tile
	 */
	public abstract boolean sharesTilesWith(Tile tile);

	/**
	 * @return The TileCriteria of tiles that can fit to the Top left of this
	 *         Tile
	 */
	public abstract TileCriteria getTopLeftCriteria();

	/**
	 * @return The TileCriteria of tiles that can fit to the bottom right of
	 *         this
	 *         Tile
	 */
	public abstract TileCriteria getBottomRightCriteria();

	/**
	 * @return The TileCriteria of tiles that can fit to the Top Right of
	 *         this Tile
	 */
	public abstract TileCriteria getTopRightCriteria();

}