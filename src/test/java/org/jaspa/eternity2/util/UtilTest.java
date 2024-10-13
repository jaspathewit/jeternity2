/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.util;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Class tests the utility methods
 */
public class UtilTest extends TestCase {
    /**
     * Test method for {@link org.jaspa.eternity2.tile.TileIdSet#TileId(int)}.
     */
    @Test
    public void testReverseKeySense() {
        String test = "abcdefghijkl";

        String result = Util.reverseKeySense(test);

        assertEquals("ghijklabcdef", result);

    }

}
