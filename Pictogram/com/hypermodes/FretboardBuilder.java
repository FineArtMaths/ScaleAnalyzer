package com.calnrich.hypermodes;

public class FretboardBuilder {

	public static String buildFretboard(int fretCount){
		String fretboard = "";
		// Frets
		for (int i = 0; i <= fretCount; i++){
			fretboard += "(0," + (i * XYConstants.GRID_SIZE) + ");(" + (5*XYConstants.GRID_SIZE) + "," + (i * XYConstants.GRID_SIZE) + ") **\\dir{-}; \n ";
		}
		// Strings
		for (int j = 0; j < 6; j++){
			fretboard += "(" + (j* XYConstants.GRID_SIZE) + ",0);(" + (j* XYConstants.GRID_SIZE) + "," + (fretCount*XYConstants.GRID_SIZE) + ") **\\dir{-}; \n ";
		}
		return fretboard;
	}

}
