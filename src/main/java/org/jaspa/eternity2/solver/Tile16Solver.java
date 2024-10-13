/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.solver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jaspa.eternity2.dao.EternityDaoFactory;
import org.jaspa.eternity2.tile.Tile;
import org.jaspa.eternity2.tile.TileCreator;
import org.jaspa.eternity2.tile16.CornerTile16;
import org.jaspa.eternity2.tile16.MidTile16;
import org.jaspa.eternity2.tile16.SideTile16;
import org.jaspa.eternity2.tile4.CornerTile4;
import org.jaspa.eternity2.tile4.MidTile4;
import org.jaspa.eternity2.tile4.SideTile4;

import com.db4o.ObjectSet;

/**
 * @author Class encapsulates the solve process for Tile4
 */
public class Tile16Solver extends MultiTileSolver implements Solver {

	/**
	 * Construct the Tile16solver
	 */
	public Tile16Solver() {
		super();
		// construct the old and new generation DAOs
		oldGenDAO = EternityDaoFactory.createTile4EternityDAO();
		newGenDAO = EternityDaoFactory.createTile16EternityDAO();
	}

	/**
	 * /** Method solves 16 tiles Essentially this takes all the 4 tiles
	 * from the Object store. For each single tile it retrieves the three other
	 * tiles that make a coherent 16 tile and stores it into the object store
	 */
	public void solve() {
		// solve all the Corner tiles
		solveCornerTiles();
		// solve all edge tiles
		solveSideTiles();
		// solve all mid tiles
		solveMidTiles();
	}

	/**
	 * Solves all the corner tiles
	 * 
	 * _______
	 * *...|...|
	 * *.B.|.D.|
	 * *___|___|
	 * *...|...|
	 * *.A.|.C.|
	 * *********
	 * 
	 * Ie add to the Object store all possible 16 tile corner pieces
	 */
	private void solveCornerTiles() {

		ObjectSet<Tile> bottomLeftTiles = oldGenDAO
				.findTiles(CornerTile4.class);

		System.out.println("CornerTile4 to check: " + bottomLeftTiles.size());

		Collection<Tile> solutions = solveForTiles(bottomLeftTiles, new TileCreator() {
			public Tile createTile(String name, Tile topLeft, Tile topRight,
					Tile bottomRight, Tile bottomLeft) {
				return new CornerTile16(name, topLeft, topRight, bottomRight,
						bottomLeft);
			}
		});

		newGenDAO.storeAll(solutions);
		System.out.println("Added: " + solutions + " CornerTile16");
		newGenDAO.commit();
	}

	/**
	 * Solves all the Side tiles
	 * 
	 * *_______
	 * *...|...|
	 * *.B.|.D.|
	 * *___|___|
	 * *...|...|
	 * *.A.|.C.|
	 * *___|___|
	 * 
	 * Ie adds to the object store all possible four tile Side tiles
	 * 
	 */
	private void solveSideTiles() {

		ObjectSet<Tile> bottomLeftTiles = oldGenDAO.findTiles(SideTile4.class);

		System.out.println("SideTile16 to check: " + bottomLeftTiles.size());

		Collection<Tile> solutions = solveForTiles(bottomLeftTiles, new TileCreator() {
			public Tile createTile(String name, Tile topLeft, Tile topRight,
					Tile bottomRight, Tile bottomLeft) {
				return new SideTile16(name, topLeft, topRight, bottomRight,
						bottomLeft);
			}
		});

		newGenDAO.storeAll(solutions);
		System.out.println("Added: " + solutions.size() + " SideTile16");
		newGenDAO.commit();
	}

	/**
	 * Solves all the Mid tiles
	 * 
	 * |___|___
	 * |...|...|
	 * |.B.|.D.|
	 * |___|___|
	 * |...|...|
	 * |.A.|.C.|
	 * |___|___|
	 * |...|.
	 * 
	 * Ie adds to the object store all possible four tile Mid tiles
	 * 
	 * @param odb
	 */
	private void solveMidTiles() {

		ObjectSet<Tile> bottomLeftTiles = oldGenDAO.findTiles(MidTile4.class);

		System.out.println("MidTile16 to check: " + bottomLeftTiles.size());

		Collection<Tile> solutions = solveForTiles(bottomLeftTiles, new TileCreator() {
			public Tile createTile(String name, Tile topLeft, Tile topRight,
					Tile bottomRight, Tile bottomLeft) {
				return new MidTile16(name, topLeft, topRight, bottomRight,
						bottomLeft);
			}
		});

		newGenDAO.storeAll(solutions);
		System.out.println("Added: " + solutions.size() + " MidTile16");
		newGenDAO.commit();
	}

	/**
	 * Obtains the possible bottom right tiles and caches them to avoid having
	 * to pay the activation cost each time the list is iterated over
	 * 
	 * @param bottomLeft
	 *            The bottom left tile that needs to be matched
	 * @return List containing the matching tiles
	 */
	@Override
	protected List<Tile> getCachedBottomRightTiles(Tile bottomLeft) {
		ObjectSet<Tile> bottomRightTiles = oldGenDAO
				.getBottomRightTiles(bottomLeft);
		List<Tile> result = new ArrayList<Tile>();
		result.addAll(bottomRightTiles);
		return result;
	}

}
