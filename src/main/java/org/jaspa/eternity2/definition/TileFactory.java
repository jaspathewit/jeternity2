/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.definition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jaspa.eternity2.tile.Colour;
import org.jaspa.eternity2.tile.Pattern;
import org.jaspa.eternity2.tile.Side;
import org.jaspa.eternity2.tile.TileIdConst;
import org.jaspa.eternity2.tile.TileIdSet;

/**
*  Class contains the list of Tiles that exist
*/
public class TileFactory {

	private List<TileDef> tileList;
	private static final String TILESDEF = "Tiles256.def";

	/**
	 * @return the tileDefList
	 */
	public List<? extends TileDef> getTileDefList() {
		if (tileList == null) {
			tileList = new ArrayList<TileDef>();

			InputStream is = ClassLoader.getSystemResourceAsStream(TILESDEF);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));

			try {
				String line = reader.readLine();
				while (line != null) {
					// check that the line is not a comment line
					if (!line.startsWith("#") && !"".equals(line)) {
						TileDef tileDef = buildTileDef(line);
						tileList.add(tileDef);
					}
					line = reader.readLine();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// add all corner tiles
			// tileList.addAll(getCornerTileDefList());
			// add all side tiles
			// tileList.addAll(getSideTileDefList());
			// add all the middle Tiles
			// tileList.addAll(getMidTileDefList());

			assert tileList.size() <= 256 : "256 tiles in the puzzle";
		}
		return tileList;
	}

	/**
	 * Construct a tile from the string description of that Tile.
	 * String description of a tile follows the following format
	 * TileDef Type,Tile Number, Top Side Description, Right Side Description,
	 * Bottom Side Description, Left Side Description.
	 * Where
	 * TileDef Type = [C|S|M]
	 * Tile Number = 999
	 * SideDiscription = BackGround colour code Foreground colour code pattern
	 * code
	 * where colour code = 99 and pattern code = 99
	 * 
	 * @param string
	 *            The string description of a tile
	 * @return The constructed tile
	 */
	private TileDef buildTileDef(String tile) {
		// get the various parts of the definition from the string
		String[] parts = java.util.regex.Pattern.compile(",").split(tile);

		// get the type
		char type = parts[0].charAt(0);

		String name = parts[1];
		TileIdSet tileId = getTileIdSet(name);
		Side top = createSide(parts[2]);
		Side right = createSide(parts[3]);
		Side bottom = createSide(parts[4]);
		Side left = createSide(parts[5]);
		TileDef result = createTileDef(type, name, tileId, top, right, bottom,
				left);
		return result;
	}

	/**
	 * Create a tileDef
	 * 
	 * @param type
	 *            The type of TileDef to construct
	 * @param tileNumber
	 *            The tile numeber
	 * @param tileIdSet
	 *            Tdhe TileIdSet
	 * @param top
	 *            The top Side
	 * @param right
	 *            The right side
	 * @param bottom
	 *            The bottom side
	 * @param left
	 *            Teh left Side
	 * @return The constructed TileDef
	 */
	private TileDef createTileDef(char type, String name, TileIdSet tileId,
			Side top, Side right, Side bottom, Side left) {
		TileDef result = null;
		switch (type) {
		case 'C': {
			result = new CornerTileDef(name, tileId, top, right, bottom, left);
			break;
		}
		case 'S': {
			result = new SideTileDef(name, tileId, top, right, bottom, left);
			break;
		}
		case 'M': {
			result = new MidTileDef(name, tileId, top, right, bottom, left);
			break;
		}
		}
		return result;
	}

	/**
	 * Gets the TileId from the given tileNumber
	 * 
	 * @param tileNumber
	 * @return
	 */
	private TileIdSet getTileIdSet(String tileNumber) {
		TileIdSet result = TileIdConst.getTileIdSetFromName(tileNumber);
		return result;
	}

	/**
	 * Create a side from the given string side definition
	 * 
	 * @param string
	 * @return
	 */
	public static Side createSide(String side) {
		String code = side.substring(0, 2);
		Colour background = Colour.getFromCode(code);
		code = side.substring(2, 4);
		Colour foreground = Colour.getFromCode(code);
		code = side.substring(4, 6);
		Pattern pattern = Pattern.getFromCode(code);
		Side result = new Side(background, foreground, pattern);
		return result;
	}

}
