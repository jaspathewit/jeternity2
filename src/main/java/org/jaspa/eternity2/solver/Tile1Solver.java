/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.solver;

import org.jaspa.eternity2.dao.EternityDAO;
import org.jaspa.eternity2.dao.EternityDaoFactory;
import org.jaspa.eternity2.definition.TileDef;
import org.jaspa.eternity2.definition.TileFactory;
import org.jaspa.eternity2.tile.Tile;

import java.util.List;

/**
 * Class encapsulates the solve process for Tile1
 */
public class Tile1Solver implements Solver {

    private final EternityDAO dao;

    /**
     * Construct the tile 1 solver
     */
    public Tile1Solver() {
        super();
        // get the DAO to be used
        this.dao = EternityDaoFactory.createTile1EternityDAO();
    }

    public EternityDAO getNewGenDAO() {
        return dao;
    }

    public EternityDAO getOldGenDAO() {
        throw new UnsupportedOperationException();
    }

    /**
     * Method solves 1 tiles Essentially this creates the tiles from the
     * TileDefs from
     * the tile factory and puts them into the object store together with the
     * top, right, bottom, and left edge codess as calculated by rotating the
     * tile four times
     */
    public void solve() {
        TileFactory tileFactory = new TileFactory();

        List<? extends TileDef> tileDefs = tileFactory.getTileDefList();

        printTileDefs(tileDefs);

        for (TileDef tileDef : tileDefs) {

            // construct a tile from the definition
            Tile tile = tileDef.createTile();

            rotateAndStore(tile);
        }
        dao.commit();
    }

    private void printTileDefs(List<? extends TileDef> tileDefs) {
        for (TileDef tileDef : tileDefs) {
            System.out.println(tileDef);
        }

    }

    /**
     * Stores the 4 versions of the tile by rotating the tile four times and
     * storing it to the ObjectStore
     *
     * @param tile
     * @param odb
     * @throws CloneNotSupportedException
     */
    private void rotateAndStore(Tile tile) {
        try {
            // must work on a copy of the tile
            Tile newTile = tile.clone();

            for (int i = 0; i < 4; i++) {
                // put the clone into the object store
                dao.store(newTile);
                // System.out.println(Util.getTileAsGridString(newTile));
                // clone the tile
                newTile = newTile.clone();
                // rotate the tile
                newTile.rotate();
            }
        } catch (CloneNotSupportedException e) {
            assert false : "Cannot Happen as clone is supported by all tiles";
        }
    }

}
