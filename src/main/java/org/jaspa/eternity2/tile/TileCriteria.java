/**
 *
 */
package org.jaspa.eternity2.tile;

/**
 * Class encapsulates the Criteria of a tile used for searching
 * 
 * @author jhewitt
 * 
 */
public class TileCriteria {
	/**
	 * @return the tileClass
	 */
	public Class<? extends Tile> getTileClass() {
		return tileClass;
	}

	/**
	 * @return the orientation
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	private Class<? extends Tile> tileClass;
	private Orientation orientation;

	/**
	 * Constructs a TileCriteria
	 * 
	 * @param tileClass
	 *            The class of the Tile
	 * @param orientation
	 *            The Orientation of the Tile
	 */
	public TileCriteria(Class<? extends Tile> tileClass, Orientation orientation) {
		super();
		this.tileClass = tileClass;
		this.orientation = orientation;
	}

	/**
	 * Construct a TileCriteria where the orientation does not matter
	 * 
	 * @param clazz
	 */
	public TileCriteria(Class<? extends Tile> clazz) {
		this(clazz, Orientation.DNM);
	}

}
