/**
 * Copyright 2009 Jason Hewitt
 */

package org.jaspa.eternity2.dao;

import com.db4o.Db4oEmbedded;
import com.db4o.config.EmbeddedConfiguration;
import org.jaspa.eternity2.dao.db4o.Db4oEternityDataStore;
import org.jaspa.eternity2.tile.TileIdSet;
import org.jaspa.eternity2.tile1.MidTile1;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Db4oEternityDataStoreTest {

    private Db4oEternityDataStore dao;

    /**
     * get the instance of the EternityDAO to use for the tests
     * Get the list of Tiles and put them into the Object Store
     */
    @Before
    public void setUp() throws Exception {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().messageLevel(0);
        config.common().callbacks(false);
        config.common().callConstructors(true);
        config.common().weakReferences(false);
        dao = new Db4oEternityDataStore("Test.odb", config);
    }

    /**
     * Commit any changes to the ObjectStore and close it after the test
     */
    @After
    public void tearDown() throws Exception {
        dao.close();
        dao.delete();
    }

    /**
     * Performance test on finding rotated tiles
     */
    @Test
    public void testSaveObject() {
        TileIdSet tileId = new TileIdSet(1);
        MidTile1 object = new MidTile1("1", tileId, "topKey", "rightKey", "bottomKey", "leftKey");

        int[][] nameKeys = new int[2][2];
        int count = 10;
        for (int i = 0; i < nameKeys.length; i++) {
            for (int j = 0; j < nameKeys.length; j++) {
                nameKeys[i][j] = count;
                count++;
            }
        }
        dao.store(object);
    }
}
