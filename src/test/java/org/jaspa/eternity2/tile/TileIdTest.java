/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.tile;

import org.junit.Test;

/**
 * Class tests the TileId class
 */
public class TileIdTest {

    /**
     * Test method for {@link org.jaspa.eternity2.tile.TileId#TileId(int)}.
     */
    @Test
    public void testTileId() {
        for (int i = 100; i < 257; i++) {
            // TileId tileId = new TileId(i);

            System.out.println("public static final TileId T" + i
                    + " = new TileId(" + i + ");");
        }

    }

}
