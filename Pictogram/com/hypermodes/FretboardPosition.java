package com.calnrich.hypermodes;

public class FretboardPosition {

	private int m_string = -1;
	private int m_fret = -1;

	public FretboardPosition(int currString, int currFret){
		m_string = currString;
		m_fret = currFret;
	}

	public int getString(){
		return m_string;
	}

	public int getFret(){
		return m_fret;
	}

}
