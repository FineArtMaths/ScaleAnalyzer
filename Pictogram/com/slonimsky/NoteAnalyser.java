package com.calnrich.slonimsky;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class NoteAnalyser {
	
	private static final String[] m_strPCNames = {"1", "\\flat2", "2", "\\flat3", "3", "4", "\\flat5", "5", "\\flat6", "6", "\\flat7", "7"};
	private static final String C_WTS = "Whole Tone Scale";
	private static final String C_AA = "Augmented Arpeggio";
	private static final String C_DSA = "Diminished Seventh Arpeggio";
	private static final String C_DSb5A = "Dominant 7 $\\flat$5 arpeggio";
	private static final String C_DSb5s11A = "Dominant 7 $\\flat9$ $\\sharp11$ arpeggio";
	private static final String C_TI = "Tritone interval";
	private static final String C_HWD = "Half-Whole Diminished Scale Group";
	private static final String C_SYM_1 = "$1^{\\text{aug}}$ + $1^{\\varnothing}$ + $2^{\\text{maj}}$ scale";
	private static final String C_SYM_2 = "$1^{\\text{aug}}$ + $\\flat7^{\\text{maj}}$ + $7^{\\text{min}}$ Scale Group";
	private static final String C_SYM_3 = "$4^{\\text{min}}$ + $7^{\\text{min}}$ Scale Group";
	private static final String C_SYM_4 = "$2^{\\text{maj}}$ + $\\sharp5^{\\text{maj}}$ Scale Group";
	private static final String C_SYM_5 = "$1^{\\text{dom}}$ + $\\flat6^{\\text{maj}}$ Scale Group";
	private static final String C_DOUB = "Double Chromatic Scale Group";
	private static final String C_TRIP = "Triple Chromatic Scale Group";
	private static final String C_TRIT = "Double Chromatic Scale Group";

	private static final HashMap<String, String> m_analyses = new HashMap<String, String>();
	static{
		m_analyses.put("1, 2, 3, \\flat5, \\flat6, \\flat7", C_WTS);
		
		m_analyses.put("1, 3, \\flat6", C_AA);
		m_analyses.put("1, \\flat3, \\flat5, 6", C_DSA);
		m_analyses.put("1, 3, \\flat5, \\flat7", C_DSb5A);
		m_analyses.put("1, \\flat2, 3, \\flat5, 5, \\flat7", C_DSb5s11A);
		m_analyses.put("1, \\flat5", C_TI);

		
		m_analyses.put("1, \\flat2, \\flat3, 3, \\flat5, 5, 6, \\flat7", C_HWD);
		// m_analyses.put("1, 2, \\flat3, 4, \\flat5, \\flat6, 6, 7", "Whole-Half diminished scale");
		m_analyses.put("1, 2, \\flat3, 4, \\flat5, \\flat6, 6, 7", C_HWD);
		
		//m_analyses.put("1, 2, 3, 4, \\flat5, \\flat6, \\flat7, 7", "$1^{\\text{aug}}$ + $\\flat7^{\\text{maj}}$ + $7^{\\text{min}}$ scale");
		//m_analyses.put("1, \\flat2, 2, 3, \\flat5, 5, \\flat6, \\flat7", "$1^{\\text{maj}}$ + $\\flat2^{\\text{min}}$ + $2^{\\text{aug}}$ scale");
		//m_analyses.put("1, \\flat2, \\flat3, 4, \\flat5, 5, 6, 7", "$1^{\\text{min}}$ + $\\flat2^{\\text{aug}}$ + $7^{\\text{maj}}$ scale");
		m_analyses.put("1, 2, \\flat3, 3, \\flat5, \\flat6, 6, \\flat7", C_SYM_1);
				
		m_analyses.put("1, 2, 3, 4, \\flat5, \\flat6, \\flat7, 7", C_SYM_2);
		m_analyses.put("1, \\flat2, 2, 3, \\flat5, 5, \\flat6, \\flat7", C_SYM_2);
		m_analyses.put("1, \\flat2, \\flat3, 4, \\flat5, 5, 6, 7", C_SYM_2);
		m_analyses.put("1, 2, \\flat3, 3, \\flat5, \\flat6, 6, \\flat7", C_SYM_2);

		//m_analyses.put("1, 2, 4, \\flat5, \\flat6, 7", "$4^{\\text{min}}$ + $7^{\\text{min}}$ scale");
		//m_analyses.put("1, \\flat2, \\flat3, \\flat5, 5, 6", "$1^{\\text{min}}$ + $\\flat5^{\\text{min}}$ scale");
		//m_analyses.put("1, \\flat3, 3, \\flat5, 6, \\flat7", "$\\flat3^{\\text{min}}$ + $6^{\\text{min}}$ scale");
		//m_analyses.put("1, \\flat2, \\flat5, 5, \\flat6, 6, \flat7, 7", "$1^{\\text{min}}$ + $\\flat5^{\\text{min}}$ scale");

		m_analyses.put("1, 2, 4, \\flat5, \\flat6, 7", C_SYM_3);
		m_analyses.put("1, \\flat2, \\flat3, \\flat5, 5, 6", C_SYM_3);
		m_analyses.put("1, \\flat3, 3, \\flat5, 6, \\flat7", C_SYM_3);
		

		//m_analyses.put("1, 2, \\flat3, \\flat5, \\flat6, 6", "$2^{\\text{maj}}$ + $\\sharp5^{\\text{maj}}$ scale");
		//m_analyses.put("1, \\flat3, 4, \\flat5, 6, 7", "$4^{\\text{maj}}$ + $7^{\\text{maj}}$ scale");		
	
		m_analyses.put("1, 2, \\flat3, \\flat5, \\flat6, 6", C_SYM_4);
		m_analyses.put("1, \\flat3, 4, \\flat5, 6, 7", C_SYM_4);		
	
		//m_analyses.put("1, \\flat3, 3, 5, \\flat6, 7", "$1^{\\text{dom}}$ + $\\flat6^{\\text{maj}}$ scale");
		//m_analyses.put("1, \\flat2, 3, 4, \\flat6, 6", "$1^{\\text{aug}}$ + $\\flat2^{\\text{aug}}$");

		m_analyses.put("1, \\flat3, 3, 5, \\flat6, 7", C_SYM_5);
		m_analyses.put("1, \\flat2, 3, 4, \\flat6, 6", C_SYM_5);
		
		
				//m_analyses.put("1, \\flat3, 3, 4, \\flat5, 6, \\flat7, 7", "Double Chromatic I scale");
		//m_analyses.put("1, \\flat2, 2, \\flat3, \\flat5, 5, \\flat6, 6", "Double Chromatic II scale");
		//m_analyses.put("1, \\flat2, 2, 4, \\flat5, 5, \\flat6, 7", "Double Chromatic III scale");
		//m_analyses.put("1, \\flat2, 3, 4, \\flat5, 5, \\flat7, 7", "Double Chromatic IV scale");

		m_analyses.put("1, \\flat3, 3, 4, \\flat5, 6, \\flat7, 7", C_DOUB);
		m_analyses.put("1, \\flat2, 2, \\flat3, \\flat5, 5, \\flat6, 6", C_DOUB);
		m_analyses.put("1, \\flat2, 2, 4, \\flat5, 5, \\flat6, 7", C_DOUB);
		m_analyses.put("1, \\flat2, 3, 4, \\flat5, 5, \\flat7, 7", C_DOUB);

		//m_analyses.put("1, \\flat2, 2, 3, 4, \\flat5, \\flat6, 6, \\flat7", "Triple Chromatic I scale");
		//m_analyses.put("1, \\flat2, \\flat3, 3, 4, 5, \\flat6, 6, 7", "Triple Chromatic II scale");
		//m_analyses.put("1, 2, \\flat3, 3, \\flat5, 5, \\flat6, \\flat7, 7", "$1^{\\text{aug}}$ + $1^{\\varnothing}$ + $2^{\\text{maj}}$ scale");

		m_analyses.put("1, \\flat2, 2, 3, 4, \\flat5, \\flat6, 6, \\flat7", C_TRIP);
		m_analyses.put("1, \\flat2, \\flat3, 3, 4, 5, \\flat6, 6, 7", C_TRIP);
		m_analyses.put("1, 2, \\flat3, 3, \\flat5, 5, \\flat6, \\flat7, 7", C_TRIP);
		

		//m_analyses.put("1, \\flat2, 2, \\flat5, 5, \\flat6", "Tritone Chromatic I scale");
		//m_analyses.put("1, \\flat2, 4, \\flat5, 5, 7", "Tritone Chromatic II scale");
		//m_analyses.put("1, 3, 4, \\flat5, \\flat7, 7", "Tritone Chromatic III scale");

		m_analyses.put("1, \\flat2, 2, \\flat5, 5, \\flat6", C_TRIT);
		m_analyses.put("1, \\flat2, 4, \\flat5, 5, 7", C_TRIT);
		m_analyses.put("1, 3, 4, \\flat5, \\flat7, 7", C_TRIT);


		m_analyses.put("1, 2, \\flat5, \\flat6", "Whole-Half diminished scale subset");
		m_analyses.put("1, 4, \\flat5, 7", "Whole-Half diminished scale subset");
		m_analyses.put("1, \\flat2, \\flat5, 5", "Half-Whole diminished scale subset");

		m_analyses.put("1, \\flat2, 2, \\flat3, 3, \\flat5, 5, \\flat6, 6, \\flat7, 7", "Chromatic scale with 4 omitted");
		m_analyses.put("1, \\flat2, 2, \\flat3, \\flat5, 5, \\flat6, 6, \\flat7, 7", "Chromatic scale with 3 and 4 omitted");
		m_analyses.put("1, \\flat2, 2, \\flat5, 5, \\flat6, 6, \\flat7, 7", "Chromatic scale with $\\flat$3, 3 and 4 omitted");
		m_analyses.put("1, \\flat2, \\flat5, 5, \\flat6, 6, \\flat7, 7", "Chromatic scale with 2, $\\flat$3, 3 and 4 omitted");
		m_analyses.put("1, \\flat2, \\flat5, 5, \\flat6, 6, \\flat7, 7", "Chromatic scale with 2, $\\flat$3, 3, $\\flat$4 and 4 omitted");


}
	
	public static String printAnalysis(SlonimskyPattern pattern) {
		return "\\text{" + getAnalysisString(pattern) + "}";
	}
	
	public static String getAnalysisString(SlonimskyPattern pattern) {
		String analysis = "";
		ArrayList<Integer> pcs = getDiscretePitchClasses(pattern);
		if(pcs.size() == 12){
			analysis = "Total Chromatic";
		} else {
			for(Integer pc : pcs){
				analysis += "$" + m_strPCNames[pc] + "$, ";
			}
			if(null == analysis || analysis.equals("")){
				System.out.println("UNKNOWN: " + analysis);
				analysis = "UNKNOWN";
			} else {
				analysis = analysis.substring(0, analysis.length() - 2);
			}
		}
		
		if(m_analyses.containsKey(analysis)){
			return m_analyses.get(analysis);
		}
		
		return analysis;
	}
	
	public static ArrayList<Integer> getDiscretePitchClasses(SlonimskyPattern pattern){
		ArrayList<Integer> returnValue = new ArrayList<Integer>();
		for(PatternNote note : pattern.getNotes()){
			if(!returnValue.contains(note.getPitchClass())){
				returnValue.add(note.getPitchClass());
			}
		}
		Collections.sort(returnValue);
		return returnValue;
	}

}
