/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.dao;

import com.db4o.Db4oEmbedded;
import com.db4o.config.EmbeddedConfiguration;
import org.jaspa.eternity2.dao.db4o.Db4oEternityDataStore;
import org.jaspa.eternity2.tile1.MidTile1;
import org.jaspa.eternity2.tile1.SideTile1;
import org.jaspa.eternity2.tile1.Tile1;
import org.jaspa.eternity2.tile16.MidTile16;
import org.jaspa.eternity2.tile16.SideTile16;
import org.jaspa.eternity2.tile4.MidTile4;
import org.jaspa.eternity2.tile4.SideTile4;
import org.jaspa.eternity2.tile64.CornerTile64;

/**
 * Class acts as a factory for creating the required EternityDAO
 */
public class EternityDaoFactory {

    private static final String TILE1DATASTORE = "Eternity2Tile1DS.odb";
    private static final String TILE4DATASTORE = "Eternity2Tile4DS.odb";
    private static final String TILE16DATASTORE = "Eternity2Tile16DS.odb";
    private static final String TILE64DATASTORE = "Eternity2Tile64DS.odb";
    private static final String TILE256DATASTORE = "Eternity2Tile256DS.odb";

    private static final int MESSAGE_LEVEL = 1;

    private static EternityDAO tile1EternityDAO = null;
    private static EternityDAO tile4EternityDAO = null;
    private static EternityDAO tile16EternityDAO = null;
    private static EternityDAO tile64EternityDAO = null;
    private static EternityDAO tile256EternityDAO = null;

    /**
     * @return The constructed EternityDAO for the Tile1 Datastore
     */
    static public EternityDAO createTile1EternityDAO() {
        if (tile1EternityDAO == null || tile1EternityDAO.getState() == EternityDAOState.CLOSED) {
            EmbeddedConfiguration config = createDefaultConfig();

            // add the indexes to the configuration
            config.common().objectClass(Tile1.class).objectField("nameKey").indexed(true);
            // config.common().objectClass(Tile1.class).objectField("bottomKey").indexed(true);
            // config.common().objectClass(Tile1.class).objectField("leftKey").indexed(true);
            // config.common().objectClass(Tile1.class).objectField("leftBottomKey").indexed(true);

            config.common().objectClass(SideTile1.class).objectField("leftKey").indexed(true);

            config.common().objectClass(MidTile1.class).objectField("bottomKey").indexed(true);
            config.common().objectClass(MidTile1.class).objectField("leftKey").indexed(true);
            config.common().objectClass(MidTile1.class).objectField("leftBottomKey").indexed(true);

            EternityDataStore ds = new Db4oEternityDataStore(TILE1DATASTORE, config);
            tile1EternityDAO = new EternityDAO(ds);
        }
        return tile1EternityDAO;
    }

    /**
     * @return The constructed EternityDAO for the Tile4 Datastore
     */
    public static EternityDAO createTile4EternityDAO() {
        if (tile4EternityDAO == null || tile4EternityDAO.getState() == EternityDAOState.CLOSED) {
            EmbeddedConfiguration config = createDefaultConfig();

            // add the indexes to the configuration
            config.common().objectClass(SideTile4.class).objectField("leftKey").indexed(true);

            config.common().objectClass(MidTile4.class).objectField("bottomKey").indexed(true);
            config.common().objectClass(MidTile4.class).objectField("leftKey").indexed(true);
            config.common().objectClass(MidTile4.class).objectField("leftBottomKey").indexed(true);

            EternityDataStore ds = new Db4oEternityDataStore(TILE4DATASTORE, config);
            tile4EternityDAO = new EternityDAO(ds);
        }
        return tile4EternityDAO;
    }

    /**
     * @return The constructed EternityDAO for the Tile16 Datastore
     */
    public static EternityDAO createTile16EternityDAO() {
        if (tile16EternityDAO == null || tile16EternityDAO.getState() == EternityDAOState.CLOSED) {
            EmbeddedConfiguration config = createDefaultConfig();
            // add the indexes to the configuration
            config.common().objectClass(SideTile16.class).objectField("leftKey").indexed(true);

            config.common().objectClass(MidTile16.class).objectField("bottomKey").indexed(true);
            config.common().objectClass(MidTile16.class).objectField("leftKey").indexed(true);
            config.common().objectClass(MidTile16.class).objectField("leftBottomKey").indexed(true);

            EternityDataStore ds = new Db4oEternityDataStore(TILE16DATASTORE, config);
            tile16EternityDAO = new EternityDAO(ds);
        }
        return tile16EternityDAO;
    }

    public static EternityDAO createTile64EternityDAO() {
        if (tile64EternityDAO == null || tile64EternityDAO.getState() == EternityDAOState.CLOSED) {
            EmbeddedConfiguration config = createDefaultConfig();

            // different indexes because at this point its only corner tiles
            // that will be produced
            config.common().objectClass(CornerTile64.class).objectField("bottomKey").indexed(true);
            config.common().objectClass(CornerTile64.class).objectField("leftKey").indexed(true);
            config.common().objectClass(CornerTile64.class).objectField("leftBottomKey").indexed(true);

            EternityDataStore ds = new Db4oEternityDataStore(TILE64DATASTORE, config);
            tile64EternityDAO = new EternityDAO(ds);
        }
        return tile64EternityDAO;
    }

    public static EternityDAO createTile256EternityDAO() {
        if (tile1EternityDAO == null || tile1EternityDAO.getState() == EternityDAOState.CLOSED) {
            EmbeddedConfiguration config = createDefaultConfig();
            EternityDataStore ds = new Db4oEternityDataStore(TILE256DATASTORE, config);
            tile256EternityDAO = new EternityDAO(ds);
        }
        return tile256EternityDAO;
    }

    static private EmbeddedConfiguration createDefaultConfig() {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();

        config.common().messageLevel(MESSAGE_LEVEL);

        config.common().callbacks(false);
        config.common().callConstructors(true);
        config.common().weakReferences(false);
        // do not use string encoding as this causes a problem opening the data
        // store in OME
        // config.common().stringEncoding(StringEncodings.latin());

        config.file().blockSize(8);

        return config;

    }
}
