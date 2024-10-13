/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.definition;

import org.jaspa.eternity2.tile.Side;
import org.jaspa.eternity2.tile.Tile;
import org.jaspa.eternity2.tile.TileIdSet;
import org.jaspa.eternity2.tile1.SideTile1;

/**
 * Class represents a tile on the side of the puzzle
 * 
 */
public class SideTileDef extends TileDef {

	/**
	 * Construct a side tile definition
	 * 
	 * @param name
	 *            The name of the corner tile
	 * @param top
	 *            The side definition for the top side
	 * @param right
	 *            The side definition for the right hand side
	 * 
	 */
	public SideTileDef(String name, TileIdSet tileId, Side top, Side right, Side bottom, Side left) {
		super(name, tileId, top, right, bottom, left);

	}

	/**
	 * @return The tile constructed from this definition
	 */
	public Tile createTile() {
		Tile result = new SideTile1(getName(), getTileId(), getTopKey(), getRightKey(), getBottomKey(), getLeftKey());
		return result;
	}
}
