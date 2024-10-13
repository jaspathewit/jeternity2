/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.solver;

import com.db4o.ObjectSet;
import org.jaspa.eternity2.dao.EternityDAO;
import org.jaspa.eternity2.tile.Orientation;
import org.jaspa.eternity2.tile.Tile;
import org.jaspa.eternity2.tile1.Tile1;
import org.jaspa.eternity2.util.Util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class is a container for the solving process
 */
public class SolverImpl implements Solver {

    private EternityDAO currentDAO;

    /**
     * Constructs the Solver
     */
    public SolverImpl() {
        // Load application context
        // context = new ClassPathXmlApplicationContext("spring-context.xml");
    }

    public EternityDAO getNewGenDAO() {
        return currentDAO;
    }

    public EternityDAO getOldGenDAO() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.Solver#solve()
     */
    public void solve() {
        long start = System.currentTimeMillis();
        // solve all possible 1 tile combinations
        System.out.println("Solving all 1 tile problems.");
        Solver solver = new Tile1Solver();

        // record the current EternityDAO in use
        currentDAO = solver.getNewGenDAO();
        solver.solve();
        currentDAO.commit();
        System.out.println("Solved all 1 tile problems");

        // generate all possible 4 tile combinations
        System.out.println("Solving all 4 tile problems.");
        solver = new Tile4Solver();
        currentDAO = solver.getNewGenDAO();
        solver.solve();
        currentDAO.commit();
        System.out.println("Solved all 4 tile problems");

        // generate all possible 16 tile combinations
        System.out.println("Solving all 16 tile problems.");
        solver = new Tile16Solver();
        currentDAO = solver.getNewGenDAO();
        solver.solve();
        currentDAO.commit();
        System.out.println("Solved all 16 tile problems");

        // generate all possible 64 tile combinations
        // System.out.println("Solving all 64 tile problems.");
        // solver = new Tile64Solver();
        // solver.solve();
        // dao.commit();
        // System.out.println("Solved all 64 tile problems");

        // generate all possible 256 tile combinations
        // System.out.println("Solving all 256 tile problems.");
        // solver = new Tile256Solver();
        // solver.solve();
        // System.out.println("Solved all 256 tile problems");
        System.out.println("Entity2 puzzle solved in: "
                + (System.currentTimeMillis() - start));

        ObjectSet<Tile> tiles = currentDAO.findTiles(Tile1.class,
                Orientation.DNM);
        System.out.println("Got TileIds:" + tiles.size());

        printSqlInsert(tiles);

        // printGrids(tiles);
        System.out.println("Finished");

    }

    private void printSqlInsert(ObjectSet<Tile> tiles) {

        BufferedWriter sqlFile = null;
        try {
            try {
                sqlFile = new BufferedWriter(new FileWriter("tile16.sql"));

                long[] tileIDs = tiles.ext().getIDs();
                // get rid of the object set
                tiles = null;

                long count = 0;
                sqlFile.write("BEGIN;\n");
                for (long tileId : tileIDs) {
                    count++;
                    Tile tile = currentDAO.getTileById(tileId);
                    String sql = Util.getTileAsSQLInsert(tile);
                    sqlFile.write(sql);
                    // get rid of the tile
                    currentDAO.purge(tile);
                    if ((count % 10000) == 0) {
                        sqlFile.write("COMMIT;\n");
                        sqlFile.write("BEGIN;\n");
                        System.out.println("Processed: " + count);
                    }
                }
                sqlFile.write("COMMIT;\n");

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } finally {
            if (sqlFile != null)
                try {
                    sqlFile.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    private void printGrids(ObjectSet<Tile> tiles) {

        BufferedWriter gridFile = null;
        try {
            try {
                gridFile = new BufferedWriter(new FileWriter("grid.fil"));

                long[] tileIDs = tiles.ext().getIDs();
                // get rid of the object set
                tiles = null;

                long count = 0;
                for (int i = 0; i < 10; i++) {
                    count++;
                    Tile tile = currentDAO.getTileById(tileIDs[i]);
                    String grid = Util.getTileAsGridString(tile);
                    gridFile.write(grid);
                    // get rid of the tile
                    currentDAO.purge(tile);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } finally {
            if (gridFile != null)
                try {
                    gridFile.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

}
