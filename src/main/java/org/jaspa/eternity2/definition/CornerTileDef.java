/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.definition;

import org.jaspa.eternity2.tile.Side;
import org.jaspa.eternity2.tile.Tile;
import org.jaspa.eternity2.tile.TileIdSet;
import org.jaspa.eternity2.tile1.CornerTile1;

/**
 * Class
 * 
 */
public class CornerTileDef extends TileDef {

	/**
	 * Construct a corner tile definition
	 * 
	 * @param name
	 *            The name of the corner tile
	 * @param top
	 *            The side definition for the top side
	 * @param right
	 *            The side definition for the right hand side
	 */
	public CornerTileDef(String name, TileIdSet tileId, Side top, Side right, Side bottom, Side left) {
		super(name, tileId, top, right, bottom, left);
	}

	/**
	 * @return The tile constructed from this definition
	 */
	public Tile createTile() {
		Tile result = new CornerTile1(getName(), getTileId(), getTopKey(), getRightKey(), getBottomKey(), getLeftKey());
		return result;
	}

}
