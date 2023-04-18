package com.calnrich.slonimsky;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.calnrich.fortefy.core.note.ModularParameter;

public class TabPrinter {

	protected static String[] m_strPCNames = {"c", "cis", "d", "dis", "e", "f", "fis", "g", "gis", "a", "ais", "b"};
	//private LayoutGenerator m_lg = new LayoutGenerator();
	protected int m_currentString;
	protected int m_previousString;
	protected int m_startFret;
	protected int m_maxLilyPondOctave;
	protected PatternNote m_lastNote;
	protected PatternNote m_lastBaseNote;
	protected PatternNote m_startNote;
		
	public String printEntry(SlonimskyPattern pattern){
		String printableValue = "\\begin{samepage}\n";
		printableValue += printHeader(pattern);
		printableValue += printTab(pattern);
		printableValue += "\n\n\\end{samepage}\n\n";
		return(printableValue);
	}
	
	public String printTab(SlonimskyPattern pattern) {
		m_startNote = pattern.getNotes().get(0);
		setupLastNotes(pattern, 1, 2, false);		
		String returnValue = "";

		returnValue += TabConstants.TAB_START;

		returnValue += TabConstants.NOTATIONSTAFF_START;
		returnValue += TabConstants.NOTATIONNOTES_START;
		returnValue += getTimeSignature(4, 4);
		// notes go here
		int count = 0;
		for(PatternNote note : pattern.getNotes()){
			returnValue += getNotationPitchName(note, count, pattern.getSequence().size());
			returnValue += " ";
			count++;
			if(count == pattern.getSequence().size()){
				count = 0;
			}
			m_lastNote = note;
		}
		returnValue += printReversedNotes(pattern.getSequence().size());
		returnValue += TabConstants.NOTATIONNOTES_END;
		returnValue += TabConstants.NOTATION_END;

		returnValue += TabConstants.TABSTAFF_START;
		returnValue += TabConstants.TABNOTES_START;
		returnValue += getTimeSignature(pattern.getNotes().size(), getDuration());

		m_currentString = 6;
		m_previousString = 6;
		m_startFret = getStartFret(pattern);
		
		setupLastNotes(pattern, 0, 2, true);
		
		for(PatternNote note : pattern.getNotes()){
			// note.setLilyPondPitchOctave(note.getLilyPondPitchOctave() + 1);
			returnValue += getTabPitchName(note);
			returnValue += " ";
			m_lastNote = note;
		}
		returnValue += printReversedNotes(pattern.getSequence().size());
		returnValue += TabConstants.TABNOTES_END;
		returnValue += TabConstants.TAB_END;
		return returnValue;
	}

	protected String getNotationPitchName(PatternNote note, int count, int patternSize) {
		String pn = getPitchName(note, true) + getDuration() + getBeamingInstruction(count, patternSize);
		return pn;
	}
	protected String getTabPitchName(PatternNote note) {
		return getPitchName(note, true) + getDuration() + getString(note);
	}

	/**
	 * Used by subclass PatternTabPrinter
	 * @return
	 */
	protected String printReversedNotes(int sequenceSize) {
		return "";
	}

	protected int getStartFret(SlonimskyPattern pattern) {
		return 8;
	}

	protected void setupLastNotes(SlonimskyPattern pattern, int baseOctave, int range, boolean isTab) {
		m_lastBaseNote = pattern.getNotes().get(0);
		m_lastBaseNote.setLilyPondPitchOctave(baseOctave);
		m_lastNote = pattern.getNotes().get(0);
		m_lastNote.setLilyPondPitchOctave(baseOctave);
		m_maxLilyPondOctave = baseOctave + range;
	}

	protected int getDuration() {
		return 16;
	}

	protected String getTimeSignature(int numNotes, int division){
		numNotes = checkNumNotes(numNotes, division);
		return TabConstants.TIME_SIG + numNotes + "/" + division + "\n";
	}

	private static int checkNumNotes(int numNotes, int division) {
		if(numNotes <= division){
			return numNotes;
		} else{
			return checkNumNotes(numNotes / 2, division);
		}
	}
		
	protected String getPitchName(PatternNote note, boolean isTabNote) {
		note.setLilyPondPitchName(m_strPCNames[note.getPitchClass()]);
		if(note.isBaseNote()){
			// make sure this is immediately above the last base note we printed in pitch
			int lastBaseNotePitch = m_lastBaseNote.getPitchClass() + 12 * m_lastBaseNote.getLilyPondPitchOctave();
			note.setLilyPondPitchOctave(m_lastBaseNote.getLilyPondPitchOctave());
			int currentPitch = note.getPitchClass() + 12 * note.getLilyPondPitchOctave();
			int interval = currentPitch - lastBaseNotePitch ;
			if(interval < 0){
				if(note.getLilyPondPitchOctave() < m_maxLilyPondOctave - 1){ // don't let base notes go above the 12th fret
					note.setLilyPondPitchOctave(note.getLilyPondPitchOctave() + 1);
				}
			} else if( interval > 12){
				note.setLilyPondPitchOctave(note.getLilyPondPitchOctave() - 1);
			}
			// set the last base note to be this one
			m_lastBaseNote = note;
		} else {
			int lastNotePitch = m_lastNote.getPitchClass() + 12 * m_lastNote.getLilyPondPitchOctave();
			note.setLilyPondPitchOctave(m_lastNote.getLilyPondPitchOctave());
			int currentPitch = note.getPitchClass() + 12 * note.getLilyPondPitchOctave();
			int interval = currentPitch - lastNotePitch ;
			if(interval < 0){
				// we have gone down -- should we have?
				if(note.getSequenceStep() - m_lastNote.getSequenceStep() > 0){
					//no -- adjust
					if(note.getLilyPondPitchOctave() < m_maxLilyPondOctave){ // don't let sequence notes go above the 24th fret
						note.setLilyPondPitchOctave(note.getLilyPondPitchOctave() + 1);
					}
				}
			} else if( interval > 0){
				// we've gone up -- should we have?
				if(note.getSequenceStep() - m_lastNote.getSequenceStep() < 0){
					//no -- adjust
					note.setLilyPondPitchOctave(note.getLilyPondPitchOctave() - 1);
				}
			}
		}		
		if(isTabNote){
			//note.setLilyPondPitchOctave(note.getLilyPondPitchOctave() - 1);
		}
		return getAdjustedPitchName(note);
	}

	protected String getAdjustedPitchName(PatternNote note) {
		String returnValue = note.getLilyPondPitchName();
		String octaveModifier = "";
		if(note.getLilyPondPitchOctave() < 0){
			for(int i = note.getLilyPondPitchOctave(); i < 0; i++){
				octaveModifier += ",";
			}
		} else if (note.getLilyPondPitchOctave() > 0){
			for(int i = 0; i < note.getLilyPondPitchOctave(); i++){
				octaveModifier += "'";
			}			
		}
		return returnValue + octaveModifier;
	}

	protected String getBeamingInstruction(int count, int sequenceSize) {
		if(count == 0){
			return "[";
		}
		if(count == sequenceSize - 1){
			return "]";
		}
		return "";	// default
	}
 
	protected String getString(PatternNote note) {
		// If fret will be too high or low, change strings until it isn't
		// Warning: here be hardcoding
		if(m_currentString > 1 && 
				m_startFret - (5 * (6 - m_currentString)) + note.getPitchClass() + (note.getLilyPondPitchOctave() * 12) > getMaxFret()){
			m_currentString--;
			return getString(note);
		} else if(m_currentString < 6 && getFretNumberForNote(note) < getMinFret()){
			m_currentString++;			
			return getString(note);
		} else if(m_currentString < 6 && m_previousString == m_currentString && m_lastNote.getAbsolutePitch() - note.getAbsolutePitch() > 4){
			m_currentString++;
		}
		
		m_previousString = m_currentString;
		return "\\" + m_currentString;
	}

	protected int getMinFret() {
		return m_startFret;
	}

	protected int getMaxFret() {
		return getMinFret() + 4;
	}

	protected int getFretNumberForNote(PatternNote note) {
		return m_startFret + note.getAbsolutePitch() - getPitchClassAtPosition();
	}

	private int getPitchClassAtPosition() {
		return (TabConstants.POSITION_SIZE_IN_FRETS * (6 - m_currentString));
	}

	private String printHeader(SlonimskyPattern pattern) {
		String returnValue = "\\[\n\\begin{array}{cccc}";
		//returnValue += LayoutGenerator.printRefNum(pattern);
		returnValue += "\n&\n";
		returnValue += LayoutGenerator.printLayout(pattern.getLayout(), false);
		returnValue += "\n&\n";
		returnValue += pattern.getSequence();
		returnValue += "\n&\n";
		returnValue += NoteAnalyser.printAnalysis(pattern);
		returnValue += "\n";
		returnValue += "\\end{array}\n\\]";
		return returnValue;
	}

	public static void generateOne(String[] args) throws IOException {

		SlonimskyGenerator sg = new SlonimskyGenerator();
		sg.calculateAllPatterns();
		// Now we need to take each base in turn...
		//...apply each pattern to it in turn
		for(SlonimskyBase base : Base.BASE_ARRAY){
			sg.decorateBase(base);
		}

		TabPrinter tp = new TabPrinter();
		SlonimskyBase base = Base.DITONE;
		for(SlonimskyPattern pattern : sg.getDecoratedBases().get(base)){
			System.out.println(tp.printEntry(pattern));
		}
	}
	
	public static void main(String[] args) throws IOException {
		//generateAll(args);
		//generateOne(args);
		generateNonSymmetrical(args);
	}

	public static void generateAll(String[] args) throws IOException {
		SlonimskyGenerator sg = new SlonimskyGenerator();
		sg.calculateAllPatterns();
		// Now we need to take each base in turn...
		//...apply each pattern to it in turn
		for(SlonimskyBase base : Base.BASE_ARRAY){
			sg.decorateBase(base);
		}

		// ...and print the tab
		TabPrinter tp = new TabPrinter();
		int chapter = 1;
		for(SlonimskyBase base : sg.getDecoratedBases().keySet()){
			// Create a new file
			FileWriter fw = new FileWriter(TexFileConstants.TEX_BASE_DIRECTORY 
					+ TexFileConstants.TEX_DIR_DELIMITER 
					+ TexFileConstants.TEX_OUTPUT_DIRECTORY 
					+ TexFileConstants.TEX_DIR_DELIMITER 
					+ TexFileConstants.TEX_OUTPUT_FILENAME_BASE
					+ chapter + ".lytex");
			// Append the file header
			FileReader fr = new FileReader(TexFileConstants.TEX_BASE_DIRECTORY 
					+ TexFileConstants.TEX_DIR_DELIMITER 
					+ TexFileConstants.TEX_RESOURCE_DIRECTORY 
					+ TexFileConstants.TEX_DIR_DELIMITER 
					+ TexFileConstants.TEX_DOC_HEAD_FILENAME);
			BufferedReader br = new BufferedReader(fr);
			String s;
			while((s = br.readLine()) != null) {
				fw.append(s);
			}
			fr.close(); 

			// Print the chapter contents
			fw.append(tp.printChapterOpening(base));
			HashMap<String, ArrayList<SlonimskyPattern>> analysedPatterns = new HashMap<String, ArrayList<SlonimskyPattern>>(); 
			for(SlonimskyPattern pattern : sg.getDecoratedBases().get(base)){
				String key = NoteAnalyser.getAnalysisString(pattern);
				if(!analysedPatterns.containsKey(key)){
					analysedPatterns.put(key, new ArrayList<SlonimskyPattern>());
				}
				analysedPatterns.get(key).add(pattern);
			}
			for(String k : analysedPatterns.keySet()){
				fw.append("\\subsection{" + k + "}\n\n");
				for(SlonimskyPattern pattern : analysedPatterns.get(k)){
					fw.append(tp.printEntry(pattern));					
				}
			}
			// Append the footer
			FileReader frf = new FileReader(TexFileConstants.TEX_BASE_DIRECTORY 
					+ TexFileConstants.TEX_DIR_DELIMITER 
					+ TexFileConstants.TEX_RESOURCE_DIRECTORY 
					+ TexFileConstants.TEX_DIR_DELIMITER 
					+ TexFileConstants.TEX_DOC_TAIL_FILENAME);
			BufferedReader brf = new BufferedReader(frf);
			while((s = brf.readLine()) != null) {
				fw.append(s);
			}
			frf.close(); 

			// Close the file
			fw.close();
			chapter++;
		}
		
		System.out.println("RUN COMPLETE: now run resources/lytex-full to create out/chapter_*.tex files. After that run the FileCombiner class");
		
	}

	public static void generateNonSymmetrical(String[] args) throws IOException {
		SlonimskyGenerator sg = new SlonimskyGenerator();
		sg.calculateAllPatterns();
		for(SlonimskyBase base : Base.NSA_BASE_ARRAY){
			sg.decorateBase(base);
		}

		// ...and print the tab
		TabPrinter tp = new TabPrinter();
		int chapter = 1;
		for(SlonimskyBase base : sg.getDecoratedBases().keySet()){
			// Create a new file
			FileWriter fw = new FileWriter(TexFileConstants.TEX_BASE_DIRECTORY 
					+ TexFileConstants.TEX_DIR_DELIMITER 
					+ TexFileConstants.TEX_OUTPUT_DIRECTORY 
					+ TexFileConstants.TEX_DIR_DELIMITER 
					+ TexFileConstants.TEX_NSA_OUTPUT_FILENAME_BASE
					+ chapter + ".lytex");
			// Append the file header
			FileReader fr = new FileReader(TexFileConstants.TEX_BASE_DIRECTORY 
					+ TexFileConstants.TEX_DIR_DELIMITER 
					+ TexFileConstants.TEX_RESOURCE_DIRECTORY 
					+ TexFileConstants.TEX_DIR_DELIMITER 
					+ TexFileConstants.TEX_DOC_HEAD_FILENAME);
			BufferedReader br = new BufferedReader(fr);
			String s;
			while((s = br.readLine()) != null) {
				fw.append(s);
			}
			fr.close(); 

			// Print the chapter contents
			fw.append(tp.printChapterOpening(base));
			HashMap<String, ArrayList<SlonimskyPattern>> analysedPatterns = new HashMap<String, ArrayList<SlonimskyPattern>>(); 
			for(SlonimskyPattern pattern : sg.getDecoratedBases().get(base)){
				String key = NoteAnalyser.getAnalysisString(pattern);
				if(!analysedPatterns.containsKey(key)){
					analysedPatterns.put(key, new ArrayList<SlonimskyPattern>());
				}
				analysedPatterns.get(key).add(pattern);
			}
			for(String k : analysedPatterns.keySet()){
				//fw.append("\\subsection{" + k + "}\n\n");
				for(SlonimskyPattern pattern : analysedPatterns.get(k)){
					fw.append(tp.printEntry(pattern));					
				}
			}
			// Append the footer
			FileReader frf = new FileReader(TexFileConstants.TEX_BASE_DIRECTORY 
					+ TexFileConstants.TEX_DIR_DELIMITER 
					+ TexFileConstants.TEX_RESOURCE_DIRECTORY 
					+ TexFileConstants.TEX_DIR_DELIMITER 
					+ TexFileConstants.TEX_DOC_TAIL_FILENAME);
			BufferedReader brf = new BufferedReader(frf);
			while((s = brf.readLine()) != null) {
				fw.append(s);
			}
			frf.close(); 

			// Close the file
			fw.close();
			chapter++;
		}
		
		System.out.println("RUN COMPLETE: now run resources/lytex-full-nsa to create out/nsa-chapter_*.tex files. After that run the FileCombiner class");
		
	}

	public String printChapterOpening(SlonimskyBase base) {
		String returnValue = "";
		returnValue += "\n\n\\chapter{" + base.getName() + "}\n";
		returnValue += "\\section*{Base}\n";
		returnValue += printBase(base);
		returnValue += "\\section*{Patterns}\n";
		return returnValue ;
	}

	private String printBase(SlonimskyBase base) {
		String returnValue = "";
		returnValue += TabConstants.TAB_START;

		returnValue += TabConstants.NOTATIONSTAFF_START;
		returnValue += TabConstants.NOTATIONNOTES_START;
		returnValue += getTimeSignature(base.getNotes().size(), 1);

		m_lastBaseNote = new PatternNote(0, 0, 0, true);
		m_lastBaseNote.setLilyPondPitchOctave(1);
		m_maxLilyPondOctave = 3;
		for(ModularParameter note : base.getNotes()){
			PatternNote pn = new PatternNote(note.GetValue(), 0, 0, true);
			returnValue += getPitchName(pn, false)+ TabConstants.BASE_DURATION;
			returnValue += " ";
		}
		returnValue += TabConstants.NOTATIONNOTES_END;
		returnValue += TabConstants.TAB_END;
		
		return returnValue;

	}
	
}
