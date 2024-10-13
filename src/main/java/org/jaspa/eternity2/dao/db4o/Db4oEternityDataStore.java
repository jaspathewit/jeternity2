/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.dao.db4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ext.ExtObjectContainer;
import com.db4o.query.Constraint;
import com.db4o.query.Query;
import org.jaspa.eternity2.dao.EternityDataStore;
import org.jaspa.eternity2.tile.Orientation;
import org.jaspa.eternity2.tile.Tile;
import org.jaspa.eternity2.tile1.Tile1;
import org.jaspa.eternity2.tile16.Tile16;

import java.io.File;
import java.util.Collection;

/**
 * Singleton encapsulates a connection to the object store and the data access
 * operations that may be performed on it.
 *
 * @author jhewitt
 */
public class Db4oEternityDataStore implements EternityDataStore {

    private final String filename;
    // the objectStore
    private ObjectContainer odb;

    /**
     * constructor
     */
    public Db4oEternityDataStore(String filename, EmbeddedConfiguration config) {
        this.odb = Db4oEmbedded.openFile(config, filename);
        this.filename = filename;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.dao.EternityDataStore#commit()
     */
    public void commit() {
        odb.commit();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.dao.EternityDataStore#close()
     */
    public boolean close() {
        boolean result = odb.close();
        if (result) {
            odb = null;
        }
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.dao.EternityDataStore#delete()
     */
    public boolean delete() {
        File file = new File(this.filename);
        return file.delete();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.dao.EternityDataStore#store(java.lang.Object)
     *
     * Clients must handle purging the stored object
     */
    public void storeAll(Collection<Tile> tiles) {
        for (Tile tile : tiles) {
            store(tile);
        }
    }


    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.dao.EternityDataStore#store(java.lang.Object)
     *
     * Clients must handle purging the stored object
     */
    public void store(Tile tile) {
        odb.store(tile);
        purge(tile);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.dao.EternityDataStore#getTileById(long)
     */
    public Tile getTileById(long id) {
        Object obj = odb.ext().getByID(id);
        odb.activate(obj, 2);
        return (Tile) obj;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.dao.EternityDataStore#findTiles(java.lang.Class)
     */
    public ObjectSet<Tile> findTiles(Class<? extends Tile> clazz, Orientation orientation) {
        Query query = odb.query();
        query.constrain(clazz);
        if (orientation != Orientation.DNM) {
            query.descend("orientationCode").constrain(orientation.code()).equal();
        }

        ObjectSet<Tile> tiles = query.execute();
        return tiles;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     *
     *
     *
     *
     *
     *
     *
     *
     *
     * org.jaspa.eternity2.dao.EternityDataStore#getTopLeftTiles(org.jaspa.eternity2
     * .tile.Tile)
     */
    public ObjectSet<Tile> getTopLeftTiles(Tile tile) {
        Query query = odb.query();
        query.constrain(tile.getTopLeftCriteria().getTileClass());
        Orientation orientation = tile.getTopLeftCriteria().getOrientation();
        Constraint constraint = query.descend("bottomKey").constrain(tile.getTopKey()).equal();
        if (orientation != Orientation.DNM) {
            constraint.and(query.descend("orientationCode").constrain(orientation.code()).equal());

        }
        ObjectSet<Tile> tiles = query.execute();
        return tiles;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.jaspa.eternity2.dao.EternityDataStore#getBottomRightTiles(org.jaspa
     * .eternity2.tile.Tile)
     */
    public ObjectSet<Tile> getBottomRightTiles(Tile tile) {
        Query query = odb.query();
        query.constrain(tile.getBottomRightCriteria().getTileClass());
        Constraint constraint = query.descend("leftKey").constrain(tile.getRightKey()).equal();
        Orientation orientation = tile.getBottomRightCriteria().getOrientation();
        if (orientation != Orientation.DNM) {
            constraint.and(query.descend("orientationCode").constrain(orientation.code()).equal());
        }
        ObjectSet<Tile> tiles = query.execute();
        return tiles;
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
        Query query = odb.query();
        query.constrain(bottomLeft.getTopRightCriteria().getTileClass());
        String leftBottomKey = topLeft.getRightKey() + bottomRight.getTopKey();
        query.descend("leftBottomKey").constrain(leftBottomKey).equal();
        ObjectSet<Tile> tiles = query.execute();
        return tiles;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.dao.EternityDataStore#findTile1ByNameKey(int)
     */
    public Tile findTile1ByNameKey(int nameKey) {
        Query query = odb.query();
        query.constrain(Tile1.class);
        query.descend("nameKey").constrain(nameKey).equal();
        ObjectSet<Tile> tiles = query.execute();
        assert tiles.size() == 1 : "There must be 1 matching tile";
        return tiles.get(0);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.dao.EternityDataStore#findSolutionTiles()
     */
    public ObjectSet<Tile> findSolutionTiles() {
        Query query = odb.query();
        query.constrain(Tile16.class);
        ObjectSet<Tile> tiles = query.execute();
        return tiles;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.dao.EternityDataStore#purge(com.db4o.ObjectSet)
     */
    public void purge(ObjectSet<Tile> tiles) {
        for (Tile tile : tiles) {
            purge(tile);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jaspa.eternity2.dao.EternityDataStore#purge(com.db4o.ObjectSet)
     */
    public void purge(Tile tile) {
        ExtObjectContainer ext = odb.ext();
        // purge the TileIdSet
        ext.purge(tile.getTileId());
        // purge the tile
        ext.purge(tile);
    }
}
