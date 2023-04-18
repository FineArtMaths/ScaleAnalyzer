package com.calnrich.slonimsky;

import java.util.ArrayList;

public class LayoutGenerator {

	private static final String XY_OPEN = "\\[\n";
	private static final String XY_START = "\\xy\n";
	private static final String XY_END = "\\endxy\n";
	private static final String XY_CLOSE = "\\]\n";

	private static final String LAYOUT_FRAME = " (0,2);(28,2) **\\dir{-};\n"
											 + "(0,0);(0,4) **\\dir{-};\n" 
											 + "(4,0);(4,4) **\\dir{-};\n" 
											 + "(8,0);(8,4) **\\dir{-};\n" 
											 + "(12,0);(12,4) **\\dir{-};\n" 
											 + "(16,0);(16,4) **\\dir{-};\n" 
											 + "(20,0);(20,4) **\\dir{-};\n" 
											 + "(24,0);(24,4) **\\dir{-};\n" 
											 + "(28,0);(28,4) **\\dir{-};\n"; 

	private static final String NOTE_1 = "(2.0, 2.0)*+[o][F-]{};\n";
	private static final String NOTE_2 = "(6.0, 2.0)*+[o][F-]{};\n";
	private static final String NOTE_3 = "(10.0, 2.0)*+[o][F-]{};\n";
	private static final String NOTE_4 = "(14.0, 2.0)*+[o][F-]{};\n";
	private static final String NOTE_5 = "(18.0, 2.0)*+[o][F-]{};\n";
	private static final String NOTE_6 = "(22.0, 2.0)*+[o][F-]{};\n";
	private static final String NOTE_7 = "(26.0, 2.0)*+[o][F-]{};\n";
	
	private ArrayList<ArrayList<String>> m_layouts = new ArrayList<ArrayList<String>>(); 
	
	public static void main(String[] args) {
		LayoutGenerator lg = new LayoutGenerator();
		//lg.printAllLayouts();
		lg.printLayoutsByType();
	}
	
	public LayoutGenerator(){
		generateLayouts();		
	}
	
	public ArrayList<String> getLayouts(int numNotes){
		return m_layouts.get(numNotes);
	}
	
	public int getNumNoteSizes(){
		return m_layouts.size();
	}

	public boolean isStretchLayout(String layout){
		return !layout.substring(5, 7).equals("00");
	}
	
	private void generateLayouts(){
		String str = "";
		initLayoutArray();
		for (int i = 0; i < 64; i++){
			str = "1" + pad(Integer.toBinaryString(i), 6);
			if(getNumNotes(str) < 5 && getConsecSemis(str) < 2){
				m_layouts.get(getNumNotes(str)).add(str);
			}
		}
	}

	public void printAllLayouts(){
		int count = 0;
		System.out.println("\\[\n\\begin{array}{ccc}");
		for(int i = 0; i< 5; i++){
			for(String layout : m_layouts.get(i)){
				System.out.println(printLayout(layout, false));
				if(count % 3 == 2){
					System.out.println("\\\\\n");
				} else {
					System.out.println("&\n");
				}
				count++;
			}
		}
		System.out.println("\\end{array}\n\\]");
		System.out.println("COUNT: " + count);
	}
	
	public void printLayoutsByType(){
		int count = 0;
		
		// First print standard layouts
		System.out.println("\\subsubsection{Standard Layouts}");
		System.out.println("\\[\n\\begin{array}{ccc}");
		for(int i = 0; i< 5; i++){
			for(String layout : m_layouts.get(i)){
				if(layout.substring(5, 7).equals("00")){
					System.out.println(printLayout(layout, false));
					if(count % 3 == 2){
						System.out.println("\\\\\n");
					} else {
						System.out.println("&\n");
					}
					count++;
				}
			}
		}
		System.out.println("\\end{array}\n\\]");
		
		//Now print stretch layouts
		System.out.println("\\subsubsection{Stretch Layouts}");
		System.out.println("\\[\n\\begin{array}{ccc}");
		for(int i = 0; i< 5; i++){
			for(String layout : m_layouts.get(i)){
				if(!layout.substring(5, 7).equals("00")){
					System.out.println(printLayout(layout, false));
					if(count % 3 == 2){
						System.out.println("\\\\\n");
					} else {
						System.out.println("&\n");
					}
					count++;
				}
			}
		}
		System.out.println("\\end{array}\n\\]");
		
	}
	
	private void initLayoutArray(){
		for(int i = 0; i< 5; i++){
			m_layouts.add(new ArrayList<String>());
		}
	}
	
	private String pad(String bin, int places){
		while(bin.length() < places){
			bin = "0" + bin;
		}
		return bin;
	}
	
	private int getNumNotes(String bin){
		int num = 0;
		for(int i = 0; i < bin.length(); i++){
			if(bin.substring(i, i+1).equals("1")){
				num++;
			}
		}
		return num;
	}
	
	private int getConsecSemis(String bin){
		int num = 0;
		int max = 0;
		for(int i = 0; i < bin.length(); i++){
			if(bin.substring(i, i+1).equals("1")){
				num++;
			} else {
				if(num > max){
					max = num;
				}
				num = 0;
			}
		}
		return max;
	}
	
	public static String printLayout(String layoutCode, boolean display){
		String retVal = "";
		if(display){ 
			retVal += XY_OPEN;
		}
		retVal += XY_START;
		retVal += LAYOUT_FRAME;
		retVal += placeNotes(layoutCode);
		retVal += XY_END;
		if(display){ 
			retVal += XY_CLOSE;
		}
		return retVal;
	}
	
	private static String placeNotes(String layoutCode){
		if(null == layoutCode){
			return "";
		}
		String retVal = "";
		if(layoutCode.substring(0, 1).equals("1")){
			retVal += NOTE_1;
		}
		if(layoutCode.substring(1, 2).equals("1")){
			retVal += NOTE_2;
		}
		if(layoutCode.substring(2, 3).equals("1")){
			retVal += NOTE_3;
		}
		if(layoutCode.substring(3, 4).equals("1")){
			retVal += NOTE_4;
		}
		if(layoutCode.substring(4, 5).equals("1")){
			retVal += NOTE_5;
		}
		if(layoutCode.substring(5, 6).equals("1")){
			retVal += NOTE_6;
		}
		if(layoutCode.substring(6, 7).equals("1")){
			retVal += NOTE_7;
		}
		return retVal;
	}

	public static String printRefNum(SlonimskyPattern pattern) {
		String retVal = pattern.getBase().getName().substring(0, 4);
		retVal += "-";
		retVal += pattern.getLayout();
		retVal += "-";
		retVal += pattern.getSequence();
		return retVal;
	}

	
	
	
}
