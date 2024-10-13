/**
 *
 */
package org.jaspa.eternity2.solver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jaspa.eternity2.dao.EternityDAO;
import org.jaspa.eternity2.tile.Tile;
import org.jaspa.eternity2.tile.TileCreator;

import com.db4o.ObjectSet;

/**
 * @author jhewitt
 * 
 */
public abstract class MultiTileSolver {

	protected abstract List<Tile> getCachedBottomRightTiles(Tile bottomLeft);

	protected EternityDAO oldGenDAO;
	protected EternityDAO newGenDAO;

	/**
	 * @return the oldGenDAO
	 */
	public EternityDAO getOldGenDAO() {
		return oldGenDAO;
	}

	/**
	 * @return the newGenDAO
	 */
	public EternityDAO getNewGenDAO() {
		return newGenDAO;
	}

	/**
	 * Method solves four tile puzzles for the given list of bottom left tiles
	 * 
	 * @param dao
	 * @param bottomLeftTiles
	 *            Iterable containing the list of tiles to process
	 * @return
	 */
	protected Collection<Tile> solveForTiles(Iterable<Tile> bottomLeftTiles, TileCreator creator) {

		// int bottomLeftToProc = bottomLeftTiles.size();
		
		Collection<Tile> solutions = Collections.emptyList();
		
		int bottomLeftProc = 0;
		// go through the corner tiles
		for (Tile bottomLeft : bottomLeftTiles) {
			System.out.println("Tiles checked=" + bottomLeftProc);

			// System.out.println("bottomLeft");
			// System.out.println(Util.getTileAsGridString(bottomLeft));

			// create a collection to hold the solutions
			solutions = new ArrayList<Tile>();
			
			// get the possible top side tiles
			ObjectSet<Tile> topLeftTiles = oldGenDAO.getTopLeftTiles(bottomLeft);

			// get the possible right side tiles in an apart list to avoid the
			// constant object re-activation when iterating over the tiles from the
			// ObjectStore
			// List<Tile> bottomRightTiles =
			// getCachedBottomRightTiles(bottomLeft);
			ObjectSet<Tile> bottomRightTiles = oldGenDAO.getBottomRightTiles(bottomLeft);
			int combinationToProc = (topLeftTiles.size() * bottomRightTiles.size());
			int bottomRightSize = bottomRightTiles.size();
			System.out.println("Combinations to check = " + combinationToProc);
			int combinationProc = 0;
			// loop through all the combinations
			for (Tile topLeft : topLeftTiles) {
				// check that the bottomLeft and topleft have no tiles in common
				if (topLeft.sharesTilesWith(bottomLeft)) {
					// no point considering this top left tile
					combinationProc += bottomRightSize;
					continue;
				}
				// System.out.println("top: ");
				// System.out.println(Util.getTileAsGridString(topLeft));
				for (Tile bottomRight : bottomRightTiles) {
					// check that bottom right has no tiles in common with
					// top left or bottom left
					if (bottomRight.sharesTilesWith(bottomLeft) || bottomRight.sharesTilesWith(topLeft)) {
						// no point considering this tile
						combinationProc++;
						continue;
					}

					// System.out.println("bottom right: ");
					// System.out.println(Util.getTileAsGridString(bottomRight));
					combinationProc++;
					ObjectSet<Tile> topRightTiles = oldGenDAO.getTopRightTiles(bottomLeft, topLeft, bottomRight);
					// int size = topRightTiles.size();
					// if (size > 0) {
					// System.out.println("Found matching toprights:" + size);
					// }
					for (Tile topRight : topRightTiles) {
						// check that top right has no tiles in common with
						// top left, bottom left or bottom Right
						if (topRight.sharesTilesWith(bottomLeft) || topRight.sharesTilesWith(topLeft)
								|| topRight.sharesTilesWith(bottomRight)) {
							// no point considering this tile
							continue;
						}
						// System.out.println("Top right: ");
						// System.out.println(Util.getTileAsGridString(topRight));
						String name = topLeft.getName() + topRight.getName() + bottomRight.getName()
								+ bottomLeft.getName();

						Tile newTile = creator.createTile(name, topLeft, topRight, bottomRight, bottomLeft);

						// System.out.println(Util.getTileAsGridString(newTile));
						Collection<Tile> rotatedTiles = createRotatedTiles(newTile);
						solutions.addAll(rotatedTiles);
						if ((solutions.size() % 10000) == 0) {
							System.out.println("Tiles Added: " + solutions.size());
							//newGenDAO.commit();
						}
					}
					// purge the top right tiles
					oldGenDAO.purge(topRightTiles);
				}
			}
			System.out.println("Combinations Processed = " + combinationProc);
			// purge the topleft and the bottom right tiles
			oldGenDAO.purge(topLeftTiles);
			oldGenDAO.purge(bottomRightTiles);

			bottomLeftProc++;
		}
		return solutions;
	}

	/**
	 * Creates the 4 versions of the tile by rotating the tile four times and adding
	 * it to the given collection
	 * 
	 * @param tile
	 * @throws CloneNotSupportedException
	 */
	private Collection<Tile> createRotatedTiles(Tile tile) {
		Collection<Tile> buffer = new ArrayList<Tile>();
		try {
			
			// must work on the clone of the tile
			Tile newTile = tile.clone();
			for (int i = 0; i < 4; i++) {
				buffer.add(newTile);
				// System.out.println(Util.getTileAsGridString(newTile));
				// clone the current newTile
				newTile = newTile.clone();
				// rotate the newTile
				newTile.rotate();
			}
		} catch (CloneNotSupportedException e) {
			assert false : "Cannot Happen as clone is supported by all tiles";
		}
		return buffer;
	}

}
