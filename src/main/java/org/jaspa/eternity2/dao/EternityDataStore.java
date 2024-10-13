/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.dao;

import com.db4o.ObjectSet;
import org.jaspa.eternity2.tile.Orientation;
import org.jaspa.eternity2.tile.Tile;

import java.util.Collection;

/**
 * Interface defines the methods that must be supported by the
 * EnernityDataStore. This permits the development of different implementations
 * of the datastore.
 *
 * @author JHewitt
 */
public interface EternityDataStore {

    /**
     * Commit the current changes to the object store
     */
    void commit();

    /**
     * Close the underlying ObjectStore
     *
     * @return true if the database was closed
     */
    boolean close();

    /**
     * Delete the file underlaying the  ObjectStore
     *
     * @return true if the store was deleted
     */
    boolean delete();

    /**
     * Store a Tile in the ObjectStore
     *
     * @param object The object to store
     */
    void store(Tile object);

    /**
     * Store all tiles in a collection in the ObjectStore
     *
     * @param objects The collection of objects to store
     */
    void storeAll(Collection<Tile> objects);

    /**
     * get Tile with given TileId
     *
     * @param id The identifier of the tile to retrieve
     * @return the retrieved tile
     */
    Tile getTileById(long id);

    /**
     * Find all the tiles of a given class type
     * <p>
     * For Corner Tile types
     * <p>
     * *___
     * *...|
     * *.A.|
     * *...|
     * ******
     * <p>
     * For Side Tile types
     * <p>
     * *___
     * *...|
     * *.A.|
     * *...|
     * *___|
     * <p>
     * For Mid Tile types
     * <p>
     * _|___|_
     * .|...|
     * .|.A.|
     * .|...|
     * _|___|_
     * .|...|
     * <p>
     * Ie finds all A's
     *
     * @param clazz       The class type of the tiles to find with the given orientation
     * @param orientation The orientation of the required tiles
     * @return ObjectSet containing the tiles
     */
    ObjectSet<Tile> findTiles(Class<? extends Tile> clazz, Orientation orientaion);

    /**
     * Get the list of tiles that match the top edge of the given tile
     * <p>
     * The type of tile found is determined by the type of the tile given
     * <p>
     * For Corner Tiles
     * ___
     * *...|
     * *.B.|
     * *___|
     * *...|
     * *.A.|
     * *****
     * <p>
     * For Side Tiles
     * *___|_
     * *...|
     * *.B.|
     * *___|_
     * *...|
     * *.A.|
     * *___|_
     * <p>
     * Ie given A finds all possible B's
     *
     * @param tile The tile to match the top edge
     * @return The ObjectSet of the matching tiles
     */
    ObjectSet<Tile> getTopLeftTiles(Tile tile);

    /**
     * Get the list of side tiles that match the right edge of the given tile
     * <p>
     * The type of tile found is determined by the type of the tile given
     * <p>
     * For Corner Tiles
     * <p>
     * *___|___|
     * *...|...|
     * *.A.|.C.|
     * *********
     * <p>
     * For Side Tiles
     * *
     * *___|___|_
     * *...|...|
     * *.A.|.C.|
     * *___|___|_
     * *...|...|
     * <p>
     * For Mid Tiles
     * <p>
     * |___|___|_
     * |...|...|
     * |.A.|.C.|
     * |___|___|_
     * |...|...|
     * <p>
     * <p>
     * IE given A finds all possible C
     *
     * @param tile The tile to match the right edge
     * @return The ObjectSet of the matching tiles
     */
    ObjectSet<Tile> getBottomRightTiles(Tile tile);

    /**
     * Get all middle tiles that match the edge formed by the given top and
     * right tiles
     * <p>
     * For Corner Tiles
     * *___|___|_
     * *...|...|
     * *.B.|.D.|
     * *___|___|_
     * *...|...|
     * *.A.|.C.|
     * **********
     * <p>
     * For Side Tiles
     * <p>
     * *___|___|_
     * *...|...|
     * *.B.|.D.|
     * *___|___|_
     * *...|...|
     * *.A.|.C.|
     * *___|___|_
     * *...|...|
     * <p>
     * For Mid Tiles
     * _|___|___|_
     * .|...|...|
     * .|.B.|.D.|
     * _|___|___|_
     * .|...|...|
     * .|.A.|.C.|
     * _|___|___|_
     * .|...|...|
     * <p>
     * Ie given B, and C finds all possible D's
     *
     * @param topLeft     The top tile to match
     * @param bottomRight The right tile to match
     * @return The list of matching tiles
     */
    ObjectSet<Tile> getTopRightTiles(Tile bottomLeft, Tile topLeft, Tile bottomRight);

    /**
     * Finds a Tile1 object with the given NameKey
     *
     * @param nameKey The name key of the tile to find
     * @return the Tile1 object with the matching name key
     */
    Tile findTile1ByNameKey(int nameKey);

    /**
     * @return The list of solution tiles
     */
    ObjectSet<Tile> findSolutionTiles();

    /**
     * Remove the tiles contained in the list from any cache
     *
     * @param tiles
     */
    void purge(ObjectSet<Tile> tiles);

    /**
     * Remove the tile cache
     *
     * @param tile the tile to remove
     */
    void purge(Tile tile);
}