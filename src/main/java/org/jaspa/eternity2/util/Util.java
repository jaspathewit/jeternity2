/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.util;

import org.jaspa.eternity2.dao.EternityDAO;
import org.jaspa.eternity2.dao.EternityDaoFactory;
import org.jaspa.eternity2.definition.TileFactory;
import org.jaspa.eternity2.tile.Orientation;
import org.jaspa.eternity2.tile.Side;
import org.jaspa.eternity2.tile.Tile;
import org.jaspa.eternity2.tile.TileIdSet;

/**
 * Provides utility methods
 */
public class Util {

    private static final int SIDEKEYLENGTH = 6;

    /**
     * Calculate the union of all the TileIds from the given tiles
     *
     * @param bottomLeft
     * @param topLeft
     * @param topRight
     * @param bottomRight
     * @return The TileIdSet containing the union of all TileIds in the given
     * tiles
     */
    public static TileIdSet getTileIdUnion(Tile bottomLeft, Tile topLeft, Tile topRight, Tile bottomRight) {
        // create the empty TileSet
        TileIdSet result = new TileIdSet(0);
        result = result.union(bottomLeft.getTileId());
        result = result.union(topLeft.getTileId());
        result = result.union(topRight.getTileId());
        result = result.union(bottomRight.getTileId());

        return result;
    }

    /**
     * Appends the orientation to the given name to create the nameKey for a
     * tile
     *
     * @param name
     * @param orientation
     * @return
     */
    public static int getNameKey(String name, Orientation orientation) {
        int result = 10000;
        // add the tile name
        result += Integer.parseInt(name) * 10;
        // add the orientation
        result += orientation.code();
        return result;
    }

    /**
     * Takes a given name key and "rotates" it
     *
     * @param nameKey The name key to rotate
     * @return
     */
    public static int rotateNameKey(int nameKey) {

        String nameKeyTxt = Integer.toString(nameKey);
        int result = nameKey;

        // get the current orientation
        char digit = nameKeyTxt.charAt(nameKeyTxt.length() - 1);
        Orientation orientation = Orientation.fromChar(digit);
        result -= orientation.code();
        // rotate the orientation
        orientation = Orientation.rotate(orientation);
        result += orientation.code();
        return result;
    }

    /**
     * prints the tile as a tile grid
     *
     * @param tile the tile to print
     */
    static public String getTileAsGridString(Tile tile) {

        Tile[][] grid = getGridOfSubTiles(tile);

        StringBuffer sb = new StringBuffer(255);

        sb.append("Solution: ");
        sb.append(tile.getName());
        sb.append(tile.getOrientation());
        sb.append("\n");

        sb.append("        ");
        sb.append(tile.getTopKey());
        sb.append("        \n");

        sb.append(tile.getLeftKey());
        sb.append("        ");
        sb.append(tile.getRightKey());
        sb.append("\n");

        sb.append("        ");
        sb.append(tile.getBottomKey());
        sb.append("        \n");

        int side = grid.length;

        for (int i = 0; i < side; i++) {
            sb.append("|");
            for (int k = 0; k < side; k++) {
                Tile subTile = grid[i][k];
                sb.append("      |");
                if (tile == null)
                    sb.append("      |");
                else {
                    sb.append(subTile.getTopKey());
                    sb.append("|");
                }
                sb.append("      |");
            }
            sb.append("\n");

            sb.append("|");
            for (int k = 0; k < side; k++) {
                Tile subTile = grid[i][k];
                if (tile == null) {
                    sb.append("      |       |       |");
                } else {
                    sb.append(subTile.getLeftKey());
                    sb.append("| ");
                    sb.append(subTile.getName());
                    sb.append(subTile.getOrientation().toString().charAt(0));
                    sb.append(" |");
                    sb.append(subTile.getRightKey());
                    sb.append("|");
                }
            }
            sb.append("\n");
            sb.append("|");
            for (int k = 0; k < side; k++) {
                Tile subTile = grid[i][k];
                sb.append("      |");
                if (subTile == null)
                    sb.append("      |");
                else {
                    sb.append(subTile.getBottomKey());
                    sb.append("|");
                }
                sb.append("      |");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Return the
     *
     * @param tile
     * @return
     */
    private static Tile[][] getGridOfSubTiles(Tile tile) {
        int[][] nameKeys = tile.getNameKeys();
        int side = nameKeys.length;

        Tile[][] result = new Tile[side][side];
        EternityDAO dao = EternityDaoFactory.createTile1EternityDAO();

        // loop through the rows
        for (int i = 0; i < side; i++) {

            // loop through the cols
            for (int j = 0; j < side; j++) {
                // retrieve the tile from the object store
                Tile singleTile = dao.findTile1ByNameKey(nameKeys[i][j]);

                result[i][j] = singleTile;
            }
        }
        return result;
    }

    /**
     * @param nameKeys The Name Keys array to clone
     * @return
     */
    public static int[][] cloneNameKeys(int[][] nameKeys) {
        int size = nameKeys.length;

        int[][] result = new int[size][size];

        for (int i = 0; i < size; i++) {
            System.arraycopy(nameKeys[i], 0, result[i], 0, size);
        }
        return result;
    }

    public static int[][] cloneNameKeys(int[][] topLeft, int[][] topRight, int[][] bottomRight, int[][] bottomLeft) {

        int[][] result = new int[topLeft.length * 2][topLeft.length * 2];

        // copy the topLeft NameKeys
        insert2dArray(result, 0, 0, topLeft);
        insert2dArray(result, 0, topLeft.length, topRight);
        insert2dArray(result, topLeft.length, 0, bottomLeft);
        insert2dArray(result, topLeft.length, topLeft.length, bottomRight);

        return result;
    }

    /**
     * Inserts a 2d array into another 2d array
     *
     * @param result
     * @param i
     * @param j
     * @param topLeft
     */
    private static void insert2dArray(int[][] dest, int row, int col, int[][] src) {

        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dest[i + row], col, src.length);
        }
    }

    /**
     * Rotates the specified nameKeys 90 degrees clockwise.
     *
     * @param nameKeys The matrix to rotate.
     * @return A new matrix containing the rotated matrix.
     */
    public static int[][] rotate(int[][] nameKeys) {
        int numRows = nameKeys.length;
        int numColumns = nameKeys[0].length;
        int i = numRows - 1;
        // create the array for the new nameKeys
        int[][] newNameKeys = new int[numColumns][numRows];
        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numColumns; x++) {
                // change the position of the nameKey and rotate it
                newNameKeys[x][i] = Util.rotateNameKey(nameKeys[y][x]);
            }
            --i;
        }
        return newNameKeys;
    }

    /**
     * Calculates the name of the given tile
     *
     * @return
     */
    public static String getName(Tile tile) {
        int[][] nameKeys = tile.getNameKeys();

        int size = nameKeys.length;

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                String txt = Integer.toString(nameKeys[i][k]);
                builder.append(txt, 1, txt.length() - 1);
            }
        }
        return builder.toString();
    }

    /**
     * Reverses the sense of a key ie 12345678 -> 56781234
     *
     * @param the key to reverse
     */
    public static String reverseKeySense(String key) {
        assert key.length() % SIDEKEYLENGTH == 0 : "key length must be an exact multiple of SIDEKEYLENGTH";
        StringBuffer result = new StringBuffer(key);
        result.reverse();

        // loop through the characters in the string buffer
        int pos = 0;
        while (pos < result.length()) {
            String temp = result.substring(pos, (pos + SIDEKEYLENGTH));
            StringBuffer buf = new StringBuffer(temp);
            buf.reverse();
            result.replace(pos, (pos + SIDEKEYLENGTH), buf.toString());
            pos += SIDEKEYLENGTH;
        }

        return result.toString();

    }

    /**
     * gets the tile as SQL insert statement representation.
     * Useful for exporting tiles to a RDB for analysis
     *
     * @param tile The tile to print as a insert statement
     */
    public static String getTileAsSQLInsert(Tile tile) {
        String type = tile.getClass().getSimpleName();
        String sb = "insert into tiledef (ttype, tileid, orientation, sedge, stop, sright, sbottom, sleft) values ('" +
                type +
                "','" +
                tile.getName() +
                "','" +
                tile.getOrientation() +
                "','" +
                keyToSideString(tile.getTopKey()) +
                "|" +
                keyToSideString(tile.getRightKey()) +
                "|" +
                keyToSideString(tile.getBottomKey()) +
                "|" +
                keyToSideString(tile.getLeftKey()) +
                "','" +
                keyToSideString(tile.getTopKey()) +
                "','" +
                keyToSideString(tile.getRightKey()) +
                "','" +
                keyToSideString(tile.getBottomKey()) +
                "','" +
                keyToSideString(tile.getLeftKey()) + "');\n";

        return sb;
    }

    /**
     * Creates a human readable string from the given side Key
     *
     * @param key
     * @return
     */
    private static String keyToSideString(String key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < key.length(); i += 6) {
            String subKey = key.substring(i);
            Side side = TileFactory.createSide(subKey);

            result.append(side);
            result.append(".");
        }
        result.setLength(result.length() - 1);
        return result.toString();
    }
}
