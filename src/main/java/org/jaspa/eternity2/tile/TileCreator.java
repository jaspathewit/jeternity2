/**
 *
 */
package org.jaspa.eternity2.tile;

/**
 * @author jhewitt
 * 
 *         Interface is implemented by Factories that can create a tile
 */
public interface TileCreator {

	public Tile createTile(String name, Tile topLeft, Tile topRight, Tile bottomRight, Tile bottomLeft);

}
