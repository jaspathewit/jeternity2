/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.tile;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Class tests the TileIdSet class
 */
public class TileIdSetTest extends TestCase {

    /**
     * Test method for {@link org.jaspa.eternity2.tile.TileIdSet#TileId(int)}.
     */
    @Test
    public void testTileIdSet() {
        // test creation
        TileIdSet tileId = new TileIdSet(0);
        assertTrue(tileId.isEmptySet());

        tileId = new TileIdSet(256);
        assertFalse(tileId.isEmptySet());

        for (int i = 1; i < 257; i++) {
            tileId = new TileIdSet(i);
            assertFalse(tileId.isEmptySet());
        }
    }

    /**
     * Test method for {@link org.jaspa.eternity2.tile.TileIdSet#TileId(int)}.
     */
    @Test
    public void testTileIdSetIntersection() {
        // test creation
        TileIdSet empty = new TileIdSet(0);
        TileIdSet test1 = new TileIdSet(200);
        TileIdSet test2 = new TileIdSet(100);

        TileIdSet inter = test1.intersection(empty);
        assertTrue(inter.isEmptySet());

        inter = test1.intersection(test1);
        assertFalse(inter.isEmptySet());

        inter = test1.intersection(test2);
        assertTrue(inter.isEmptySet());

    }

    /**
     * Test method for {@link org.jaspa.eternity2.tile.TileIdSet#TileId(int)}.
     */
    @Test
    public void testTileIdSetUnion() {
        // test creation
        TileIdSet empty = new TileIdSet(0);
        TileIdSet test1 = new TileIdSet(200);
        TileIdSet test2 = new TileIdSet(100);

        TileIdSet union = test1.union(empty);
        assertFalse(union.isEmptySet());

        union = test1.union(test1);
        assertFalse(union.isEmptySet());

        union = test1.union(test2);
        assertFalse(union.isEmptySet());

    }

}
