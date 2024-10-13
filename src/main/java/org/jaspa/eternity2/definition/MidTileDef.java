/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.definition;

import org.jaspa.eternity2.tile.Side;
import org.jaspa.eternity2.tile.Tile;
import org.jaspa.eternity2.tile.TileIdSet;
import org.jaspa.eternity2.tile1.MidTile1;

/**
 * Class represents a tile in the middle of the puzzle
 * 
 */
public class MidTileDef extends TileDef {

	/**
	 * Construct a middle tile definition
	 * 
	 * @param name
	 *            The name of the corner tile
	 * @param top
	 *            The side definition for the top side
	 * @param right
	 *            The side definition for the right hand side
	 * @param left
	 *            The left hand side definition
	 * 
	 */
	public MidTileDef(String name, TileIdSet tileId, Side top, Side right,
			Side bottom, Side left) {
		super(name, tileId, top, right, bottom, left);
	}

	/**
	 * @return The tile constructed from this definition
	 */
	public Tile createTile() {
		Tile result = new MidTile1(getName(), getTileId(), getTopKey(),
				getRightKey(), getBottomKey(), getLeftKey());
		return result;
	}
}