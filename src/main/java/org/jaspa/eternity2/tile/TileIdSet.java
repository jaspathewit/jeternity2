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
public class TileIdSet {
    private static final int DATASIZE = 5;
    private static final String zeros = new String(getEmptyByteArray());
    private long[] data = new long[DATASIZE];

    /**
     * Construct the TileId for a given value
     *
     * @param value
     */
    public TileIdSet(int value) {
        byte[] bytes = getEmptyByteArray();

        if (value != 0) {
            // set the required bit to 1
            bytes[255 - (value - 1)] = (byte) '1';
            String binary = new String(bytes);

            // chop up the string convert the binary to the four required longs
            data[0] = Long.parseLong(binary.substring(0, 4), 2);
            data[1] = Long.parseLong(binary.substring(4, 67), 2);
            data[2] = Long.parseLong(binary.substring(67, 130), 2);
            data[3] = Long.parseLong(binary.substring(130, 193), 2);
            data[4] = Long.parseLong(binary.substring(193, 256), 2);
        }
    }

    /**
     * Construct the TileId with a given array of longs
     *
     * @param value
     */
    private TileIdSet(long[] data) {
        assert data.length == DATASIZE : "Data array is four elements in lenght";
        // copy the given data array
        long[] newData = new long[DATASIZE];
        System.arraycopy(data, 0, newData, 0, 5);
        this.data = newData;
    }

    /**
     * @return
     */
    private static byte[] getEmptyByteArray() {
        // get a byte array containing 255 zeros
        byte[] bytes = new byte[256];
        for (int i = 0; i < 256; i++) {
            bytes[i] = (byte) '0';
        }
        return bytes;
    }

    /**
     * Calculates the intersection between This TileId and the given TileId
     *
     * @param tileId
     * @return The TileID containing the Intersection.
     */
    public TileIdSet intersection(TileIdSet tileId) {
        // calculate a data array containing the intersection
        long[] inter = new long[DATASIZE];
        long[] set1 = this.data;
        long[] set2 = tileId.data;
        for (int i = 0; i < DATASIZE; i++) {
            inter[i] = (set1[i] & set2[i]);
        }
        TileIdSet result = new TileIdSet(inter);
        return result;
    }

    /**
     * @return true if this TileIdSet is empty
     */
    public boolean isEmptySet() {
        for (long value : data) {
            if (value != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param tileId
     * @return the union between the tileIDSet and the given TileIdSet
     */
    public TileIdSet union(TileIdSet tileId) {
        // calculate a data array containing the union
        long[] union = new long[DATASIZE];
        long[] set1 = this.data;
        long[] set2 = tileId.data;
        for (int i = 0; i < DATASIZE; i++) {
            union[i] = (set1[i] | set2[i]);
        }
        TileIdSet result = new TileIdSet(union);
        return result;
    }

    /**
     * @return the TileIdSet as a string
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < DATASIZE; i++) {
            String bin = Long.toBinaryString(data[i]);
            builder.append(zeros.substring(0, zeros.length() - bin.length())
                    + Long.toBinaryString(data[i]));
        }
        return builder.toString();
    }
}
