/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.dao;

import com.db4o.ObjectSet;
import org.jaspa.eternity2.tile.Orientation;
import org.jaspa.eternity2.tile.Tile;

import java.util.Collection;

/**
 * Singleton encapsulates a connection to the object store and the data access
 * operations that may be performed on it.
 *
 * @author jhewitt
 */
public class EternityDAO {

    // the objectStore
    private EternityDataStore ds;
    // the state of the EternityDAO
    private EternityDAOState state = EternityDAOState.CLOSED;

    /**
     * Construct a EternityDAO with the given EternityDataStore
     */
    public EternityDAO(EternityDataStore ds) {
        this.ds = ds;
        state = EternityDAOState.OPEN;

    }

    /**
     * @return the state
     */
    public EternityDAOState getState() {
        return state;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.dao.EternityDataStore#commit()
     */
    public void commit() {
        ds.commit();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.dao.EternityDataStore#close()
     */
    public boolean close(boolean delete) {
        boolean result = ds.close();
        if (result) {
            if (delete) {
                ds.delete();
            }
            ds = null;
            state = EternityDAOState.CLOSED;
        }
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.jaspa.eternity2.dao.EternityDataStore#store(org.jaspa.eternity2.tile
     * .Tile)
     */
    public void storeAll(Collection<Tile> tiles) {
        ds.storeAll(tiles);
    }


    /*
     * (non-Javadoc)
     *
     * @see
     * org.jaspa.eternity2.dao.EternityDataStore#store(org.jaspa.eternity2.tile
     * .Tile)
     */
    public void store(Tile newTile) {
        ds.store(newTile);
    }


    /**
     * Return the matching tiles of the given class with the default north
     * orientation
     *
     * @param clazz The class of the tile to find
     * @return The found tiles
     */
    public ObjectSet<Tile> findTiles(Class<? extends Tile> clazz) {
        return findTiles(clazz, Orientation.NORTH);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.dao.EternityDataStore#findTiles(java.lang.Class)
     */
    public ObjectSet<Tile> findTiles(Class<? extends Tile> clazz, Orientation orientation) {
        return ds.findTiles(clazz, orientation);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     *
     *
     * org.jaspa.eternity2.dao.EternityDataStore#getTopLeftTiles(org.jaspa.eternity2
     * .tile.Tile)
     */
    public ObjectSet<Tile> getTopLeftTiles(Tile tile) {
        return ds.getTopLeftTiles(tile);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.jaspa.eternity2.dao.EternityDataStore#getBottomRightTiles(org.jaspa
     * .eternity2.tile.Tile)
     */
    public ObjectSet<Tile> getBottomRightTiles(Tile tile) {
        return ds.getBottomRightTiles(tile);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.jaspa.eternity2.dao.EternityDataStore#getTopRightTiles(org.jaspa.
     * eternity2.tile.Tile, org.jaspa.eternity2.tile.Tile,
     * org.jaspa.eternity2.tile.Tile)
     */
    public ObjectSet<Tile> getTopRightTiles(Tile bottomLeft, Tile topLeft, Tile bottomRight) {
        return ds.getTopRightTiles(bottomLeft, topLeft, bottomRight);
    }

    /**
     * @param id The identifier of the tile to find
     * @return the the found tile
     */
    public Tile getTileById(long id) {
        return ds.getTileById(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.dao.EternityDataStore#findTile1ByNameKey(int)
     */
    public Tile findTile1ByNameKey(int nameKey) {
        return ds.findTile1ByNameKey(nameKey);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.dao.EternityDataStore#findSolutionTiles()
     */
    public ObjectSet<Tile> findSolutionTiles() {
        return ds.findSolutionTiles();
    }

    /**
     * Purges the list of tiles from any cache
     *
     * @param tiles The list of tiles to purge
     */
    public void purge(ObjectSet<Tile> tiles) {
        ds.purge(tiles);
    }

    /**
     * Purges the tile from any cache
     *
     * @param tile The tile to purge
     */
    public void purge(Tile tile) {
        ds.purge(tile);
    }
}
