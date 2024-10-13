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
import org.jaspa.eternity2.tile1.CornerTile1;
import org.jaspa.eternity2.tile1.MidTile1;
import org.jaspa.eternity2.tile1.SideTile1;
import org.jaspa.eternity2.tile4.CornerTile4;
import org.jaspa.eternity2.tile4.MidTile4;
import org.jaspa.eternity2.tile4.SideTile4;

import com.db4o.ObjectSet;

/**
 * @author Class encapsulates the solve process for Tile4
 */
public class Tile4Solver extends MultiTileSolver implements Solver {

	/**
	 * Construct the Tile4solver
	 */
	public Tile4Solver() {
		super();
		// construct the old and new generation DAOs
		oldGenDAO = EternityDaoFactory.createTile1EternityDAO();
		newGenDAO = EternityDaoFactory.createTile4EternityDAO();
	}

	/**
	 * /** Method solves 4 tiles Essentially this takes all the single tiles
	 * from the Object store. For each single tile it retrieves the three other
	 * tiles that make a coherent 4 tile and stores it into the object store
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
	 * Ie add to the Object store all possible four tile corner pieces
	 */
	private void solveCornerTiles() {

		ObjectSet<Tile> bottomLeftTiles = oldGenDAO
				.findTiles(CornerTile1.class);

		System.out.println("CornerTile1 to check: " + bottomLeftTiles.size());

		Collection<Tile> solutions = solveForTiles(bottomLeftTiles, new TileCreator() {
			public Tile createTile(String name, Tile topLeft, Tile topRight,
					Tile bottomRight, Tile bottomLeft) {
				return new CornerTile4(name, topLeft, topRight, bottomRight,
						bottomLeft);
			}
		});

		
		newGenDAO.storeAll(solutions);
		System.out.println("Added: " + solutions.size() + " CornerTile4");
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
		ObjectSet<Tile> bottomLeftTiles = oldGenDAO.findTiles(SideTile1.class);

		System.out.println("SideTile1 to check: " + bottomLeftTiles.size());

		Collection<Tile> solutions = solveForTiles(bottomLeftTiles, new TileCreator() {
			public Tile createTile(String name, Tile topLeft, Tile topRight,
					Tile bottomRight, Tile bottomLeft) {
				return new SideTile4(name, topLeft, topRight, bottomRight,
						bottomLeft);
			}
		});

		newGenDAO.storeAll(solutions);
		System.out.println("Added: " + solutions.size() + " SideTile4");
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

		ObjectSet<Tile> bottomLeftTiles = oldGenDAO.findTiles(MidTile1.class);

		System.out.println("MidTile1 to check: " + bottomLeftTiles.size());

		Collection<Tile> solutions = solveForTiles(bottomLeftTiles, new TileCreator() {
			public Tile createTile(String name, Tile topLeft, Tile topRight,
					Tile bottomRight, Tile bottomLeft) {
				return new MidTile4(name, topLeft, topRight, bottomRight,
						bottomLeft);
			}
		});

		newGenDAO.storeAll(solutions);
		System.out.println("Added: " + solutions.size() + " MidTile4");
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
