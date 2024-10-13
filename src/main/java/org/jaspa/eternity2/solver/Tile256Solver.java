/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.solver;

import com.db4o.ObjectSet;
import org.jaspa.eternity2.dao.EternityDaoFactory;
import org.jaspa.eternity2.tile.Tile;
import org.jaspa.eternity2.tile16.CornerTile16;
import org.jaspa.eternity2.tile4.CornerTile4;

import java.util.ArrayList;
import java.util.List;

/**
 * Class encapsulates the solve process for Tile1
 */
public class Tile256Solver extends MultiTileSolver implements Solver {

    /**
     * Construct the Tile256solver
     */
    public Tile256Solver() {
        super();
        // construct the old and new generation DAOs
        oldGenDAO = EternityDaoFactory.createTile64EternityDAO();
        // construct the old and new generation DAOs
        newGenDAO = EternityDaoFactory.createTile256EternityDAO();
    }

    /**
     * Method solves 256 tiles Essentially this takes all the compound tiles
     * (tile4 in this test case)
     * from the Object store. For each compound tile it retrieves the three
     * other
     * compound tiles that make a coherent 256 tile and stores it into the
     * object store
     *
     * @param odb
     */
    public void solve() {
        // solve all the Corner tiles
        solveCornerTiles();
    }

    /**
     * Solves all the corner tiles
     * <p>
     * *********
     * *...|...*
     * *.B.|.D.*
     * *___|___*
     * *...|...*
     * *.A.|.C.*
     * *********
     * <p>
     * Ie add to the Object store all possible 256 tile pieces
     *
     * @param odb
     */
    private void solveCornerTiles() {

        @SuppressWarnings("unchecked")
        ObjectSet<Tile> bottomLeftTiles = oldGenDAO
                .findTiles(CornerTile4.class);

        int count = 0;

        // go through the corner tiles
        for (Tile bottomLeft : bottomLeftTiles) {
            // System.out.println("corner: ");
            // System.out.println(Util.getTileAsGridString(bottomLeft));
            // get the possible top side tiles
            ObjectSet<Tile> topLeftTiles = oldGenDAO
                    .getTopLeftTiles(bottomLeft);

            // get the possible right side tiles
            List<Tile> bottomRightTiles = getCachedBottomRightTiles(bottomLeft);

            // loop through all the combinations
            for (Tile topLeft : topLeftTiles) {
                // check that the bottomLeft and topleft have no tiles in common
                if (topLeft.sharesTilesWith(bottomLeft)) {
                    // no point considering this top left tile
                    continue;
                }
                // System.out.println("top Left: ");
                // System.out.println(Util.getTileAsGridString(topLeft));

                for (Tile bottomRight : bottomRightTiles) {
                    // check that bottom right has no tiles in common with
                    // top left or bottom left
                    if (bottomRight.sharesTilesWith(bottomLeft)
                            || bottomRight.sharesTilesWith(topLeft)) {
                        // no point considering this tile
                        continue;
                    }
                    // System.out.println("bottom right: ");
                    // System.out.println(Util.getTileAsGridString(bottomRight));

                    ObjectSet<Tile> topRightTiles = oldGenDAO.getTopRightTiles(
                            bottomLeft, topLeft, bottomRight);
                    for (Tile topRight : topRightTiles) {
                        // check that top right has no tiles in common with
                        // top left, bottom left or bottom Right
                        if (topRight.sharesTilesWith(bottomLeft)
                                || topRight.sharesTilesWith(topLeft)
                                || topRight.sharesTilesWith(bottomRight)) {
                            // no point considering this tile
                            continue;
                        }
                        // System.out.println("top right:");
                        // System.out.println(Util.getTileAsGridString(topRight));

                        count++;
                        String name = topLeft.getName() + topRight.getName()
                                + bottomRight.getName() + bottomLeft.getName();

                        CornerTile16 newTile = new CornerTile16(name, topLeft,
                                topRight, bottomRight, bottomLeft);

                        // print out the solution
                        newGenDAO.store(newTile);

                        // rotateAndStore(newTile, odb);
                        // System.out.println("corner: " + tile);
                        // System.out.println("top: " + top);
                        // System.out.println("right: " + right);
                        // System.out.println("matching: " + mt);
                    }

                }
            }
        }
        System.out.println("Added: " + count + " CornerTile16");
        newGenDAO.commit();
    }

    /**
     * Obtains the possible bottom right tiles and caches them to avoid having
     * to pay the activation cost each time the list is iterated over
     *
     * @param bottomLeft The bottom left tile that needs to be matched
     * @return List containing the matching tiles
     */
    protected List<Tile> getCachedBottomRightTiles(Tile bottomLeft) {
        ObjectSet<Tile> bottomRightTiles = oldGenDAO
                .getBottomRightTiles(bottomLeft);
        List<Tile> result = new ArrayList<Tile>();
        result.addAll(bottomRightTiles);
        return result;
    }
}
