/**
 * Copyright 2009 Jason Hewitt
 */

package org.jaspa.eternity2.dao;

import org.jaspa.eternity2.definition.TileDef;
import org.jaspa.eternity2.definition.TileFactory;
import org.jaspa.eternity2.solver.Solver;
import org.jaspa.eternity2.solver.Tile1Solver;
import org.jaspa.eternity2.tile.Tile;
import org.jaspa.eternity2.tile1.Tile1;
import org.jaspa.eternity2.util.Util;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class EternityDAOTest {

    private EternityDAO dao;

    /**
     * get the instance of the EternityDAO to use for the tests
     * Get the list of Tiles and put them into the Object Store
     */
    @Before
    public void setUp() throws Exception {
        dao = EternityDaoFactory.createTile1EternityDAO();
        // just do the tile one solving to put the required tiles in the
        // ObjectStore
        Solver solver = new Tile1Solver();
        solver.solve();
    }

    /**
     * Commit any changes to the ObjectStore and close it after the test
     */
    @After
    public void tearDown() throws Exception {
        dao.commit();
        dao.close(true);
    }

    /**
     * Performance test on finding rotated tiles
     */
    @Test
    public void testFindMatchingTile() {
        // get the list of all possible tile definitions
        TileFactory factory = new TileFactory();
        List<? extends TileDef> tileDefs = factory.getTileDefList();

        for (TileDef tileDef : tileDefs) {
            Tile tile = tileDef.createTile();
            int nameKey = ((Tile1) tile).getNameKey();
            dao.findTile1ByNameKey(nameKey);
        }
    }

    /**
     * Print tile defs as insert sql statements
     */
    @Test
    public void testPrintSQLTileDefs() {
        // get the list of all possible tile definitions
        TileFactory factory = new TileFactory();
        List<? extends TileDef> tileDefs = factory.getTileDefList();

        for (TileDef tileDef : tileDefs) {
            Tile tile = tileDef.createTile();
            System.out.print(Util.getTileAsSQLInsert(tile));
        }
    }

}
