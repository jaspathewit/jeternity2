/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.tile;

/**
 * class gives the unique identifier for the tile.
 * Basically this class allows each tile to be represented a a bit in a 256 bit
 * number This allows for efficient searching if there is shared tiles between
 * composite tiles
 */
public class TileId {
    private final long[] data = new long[4];

    /**
     * Construct the TileId for a given value
     *
     * @param value
     */
    TileId(int value) {
        // get a byte array containing 255 zeros
        byte[] bytes = new byte[256];
        for (int i = 0; i < 256; i++) {
            bytes[i] = (byte) '0';
        }

        // set the required bit to 1
        bytes[255 - (value - 1)] = (byte) '1';
        String binary = new String(bytes);

        // chop up the string convert the binary to the four required longs
        data[0] = Long.parseLong(binary.substring(0, 63), 2);
        System.out.println("data0: " + data[0]);
        data[1] = Long.parseLong(binary.substring(64, 127), 2);

        System.out.println(" data1: " + data[1]);
        data[2] = Long.parseLong(binary.substring(128, 191), 2);
        System.out.println(" data2: " + data[2]);
        data[3] = Long.parseLong(binary.substring(192, 255), 2);
        System.out.println(" data3: " + data[3]);
    }
}
