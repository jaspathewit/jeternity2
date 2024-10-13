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
import org.jaspa.eternity2.tile64.CornerTile64;
import org.jaspa.eternity2.tile64.MidTile64;
import org.jaspa.eternity2.tile64.SideTile64;

import com.db4o.ObjectSet;

/**
 * @author Class encapsulates the solve process for Tile4
 */
public class Tile64Solver extends MultiTileSolver implements Solver {

	/**
	 * Construct the Tile16solver
	 */
	public Tile64Solver() {
		super();
		// construct the old and new generation DAOs
		oldGenDAO = EternityDaoFactory.createTile16EternityDAO();
		newGenDAO = EternityDaoFactory.createTile64EternityDAO();
	}

	/**
	 * /** Method solves 64 tiles Essentially this takes all the 16 tiles
	 * from the Object store. For each single tile it retrieves the three other
	 * tiles that make a coherent 64 tile and stores it into the object store
	 */
	public void solve() {
		// solve all the Corner tiles
		// only need to solve for corner tiles
		solveCornerTiles();
		// solve all edge tiles
		// solveSideTiles();
		// solve all mid tiles
		// solveMidTiles();
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
	 * Ie add to the Object store all possible four tile corner pieces
	 */
	private void solveCornerTiles() {
		ObjectSet<Tile> bottomLeftTiles = oldGenDAO.findTiles(CornerTile16.class);

		// List<Tile> list = new ArrayList<Tile>();
		// list.addAll(bottomLeftTiles.subList(0, 10));

		System.out.println("CornerTile16 to check: " + bottomLeftTiles.size());

		Collection<Tile> solutions = solveForTiles(bottomLeftTiles, new TileCreator() {
			public Tile createTile(String name, Tile topLeft, Tile topRight, Tile bottomRight, Tile bottomLeft) {
				return new CornerTile64(name, topLeft, topRight, bottomRight, bottomLeft);
			}
		});

		newGenDAO.storeAll(solutions);
		System.out.println("Added: " + solutions.size() + " CornerTile64");
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
		ObjectSet<Tile> bottomLeftTiles = oldGenDAO.findTiles(SideTile16.class);

		System.out.println("SideTile16 to check: " + bottomLeftTiles.size());

		Collection<Tile> solutions = solveForTiles(bottomLeftTiles, new TileCreator() {
			public Tile createTile(String name, Tile topLeft, Tile topRight, Tile bottomRight, Tile bottomLeft) {
				return new SideTile64(name, topLeft, topRight, bottomRight, bottomLeft);
			}
		});

		newGenDAO.storeAll(solutions);
		System.out.println("Added: " + solutions.size() + " SideTile64");
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

		ObjectSet<Tile> bottomLeftTiles = oldGenDAO.findTiles(MidTile16.class);

		// List<Tile> list = new ArrayList<Tile>();
		// list.addAll(bottomLeftTiles.subList(0, 10));

		System.out.println("MidTile16 to check: " + bottomLeftTiles.size());

		Collection<Tile> solutions = solveForTiles(bottomLeftTiles, new TileCreator() {
			public Tile createTile(String name, Tile topLeft, Tile topRight, Tile bottomRight, Tile bottomLeft) {
				return new MidTile64(name, topLeft, topRight, bottomRight, bottomLeft);
			}
		});

		newGenDAO.storeAll(solutions);
		System.out.println("Added: " + solutions + " MidTile64");
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
		ObjectSet<Tile> bottomRightTiles = oldGenDAO.getBottomRightTiles(bottomLeft);
		List<Tile> result = new ArrayList<Tile>();
		result.addAll(bottomRightTiles);
		return result;
	}

}
