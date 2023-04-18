package com.calnrich.slonimsky;

public class TabConstants {

	public static final String TAB_START = 
		"\\begin{lilypond}\n" +
		"\\paper {\n" + 
		"     line-width = 180\\mm - 2.0 * 0.4\\in\n" +
		"}\n" + 
		"<<\n";
	
	public static final String NOTATIONSTAFF_START = 
		"\\new Staff {\n" +
		"\\override Staff.TimeSignature #'stencil = ##f\n";
	
	public static final String NOTATIONNOTES_START = "<< {\n";
	public static final String NOTATIONNOTES_END = "} >>\n";
	public static final String NOTATION_END = "}\n";

	public static final String TABSTAFF_START = 
		"\\new TabStaff {\n" +
		"\\override TabStaff.Stem #'transparent = ##t\n" +
		"\\override TabStaff.Beam #'transparent = ##t\n" +
		"\\override Staff.TimeSignature #'stencil = ##f\n";
	
	public static final String TIME_SIG = "\\time";

	public static final String TABNOTES_START = "{\n";
	public static final String TABNOTES_END = "}\n";
	public static final String TAB_END = "}\n>>\n\\end{lilypond}\n";
	
	public static final int BASE_DURATION = 1;
	
	public static final int POSITION_SIZE_IN_FRETS = 5;

	
}
