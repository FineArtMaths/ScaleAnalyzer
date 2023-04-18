package com.calnrich.hypermodes;

import com.calnrich.fortefy.core.note.ModularParameter;
import java.util.ArrayList;

public class NotePlacer {

	public static String notePos(int x, int y){
			return "(" + (x * XYConstants.GRID_SIZE) + ", " + ((y * XYConstants.GRID_SIZE)  - (0.5 * XYConstants.GRID_SIZE)) + ")";
	}

	public static String printArpeggio(String shape, int fretCount, int startFret){
		String retVal = "";
		retVal += printNote(XYConstants.getShape(shape.substring(0, 2)), "1", fretCount, XYConstants.rootTone, startFret);
		if(shape.length() > 2){
			if (shape.substring(2,3).equals("m")){
				retVal += printNote(XYConstants.getShape(shape.substring(0, 2)), "b3", fretCount, XYConstants.chordTone, startFret);
				retVal += printNote(XYConstants.getShape(shape.substring(0, 2)), "5", fretCount, XYConstants.chordTone, startFret);
			} else if (shape.substring(2,3).equals("d")){
				retVal += printNote(XYConstants.getShape(shape.substring(0, 2)), "b3", fretCount, XYConstants.chordTone, startFret);
				retVal += printNote(XYConstants.getShape(shape.substring(0, 2)), "b5", fretCount, XYConstants.chordTone, startFret);
			} else if (shape.substring(2,3).equals("a")){
				retVal += printNote(XYConstants.getShape(shape.substring(0, 2)), "3", fretCount, XYConstants.chordTone, startFret);
				retVal += printNote(XYConstants.getShape(shape.substring(0, 2)), "#5", fretCount, XYConstants.chordTone, startFret);
			} else if (shape.substring(2,3).equals("0")){
				// do nothing -- blank arpeggio
			} else if (shape.substring(2,3).equals("1")){
				retVal += printNote(XYConstants.getShape(shape.substring(0, 2)), "3", fretCount, XYConstants.chordTone, startFret);
				retVal += printNote(XYConstants.getShape(shape.substring(0, 2)), "b5", fretCount, XYConstants.chordTone, startFret);
			} else if (shape.substring(2,3).equals("2")){
				retVal += printNote(XYConstants.getShape(shape.substring(0, 2)), "2", fretCount, XYConstants.chordTone, startFret);
				retVal += printNote(XYConstants.getShape(shape.substring(0, 2)), "5", fretCount, XYConstants.chordTone, startFret);
			} else if (shape.substring(2,3).equals("4")){
				retVal += printNote(XYConstants.getShape(shape.substring(0, 2)), "4", fretCount, XYConstants.chordTone, startFret);
				retVal += printNote(XYConstants.getShape(shape.substring(0, 2)), "5", fretCount, XYConstants.chordTone, startFret);
			} else {
				System.out.println("[" + shape.substring(2,3) + "] isn't a valid shape modifier");
			}
		} else {
			retVal += printNote(XYConstants.getShape(shape.substring(0, 2)), "3", fretCount, XYConstants.chordTone, startFret);
			retVal += printNote(XYConstants.getShape(shape.substring(0, 2)), "5", fretCount, XYConstants.chordTone, startFret);
		}
		return retVal;
	}

	public static String getIntervalName(int interval){
		if(interval == 1 || interval == -1){ return "s"; } else
		if(interval == 2 || interval == -2){ return "t"; } else
		if(interval == 3 || interval == -3){ return "mT"; } else
		if(interval == 4 || interval == -4){ return "MT"; } else
		if(interval == 5 || interval == -5){ return "4"; } else
		if(interval == 6 || interval == -6){ return "$\\flat$5"; } else
		if(interval == 7 || interval == -7){ return "5"; } else
		if(interval == 8 || interval == -8){ return "$\\sharp$5"; } else
		if(interval == 9 || interval == -9){ return "6"; } else
		if(interval == 10 || interval == -10){ return "$\\flat$7"; } else
		if(interval == 11 || interval == -11){ return "7"; }
		return "??";
	}

	public static int getPitchFromNoteName(String noteName){
		if (noteName.equals("1")){ return 0; } else
		if (noteName.equals("b2")){ return 1; } else
		if (noteName.equals("2")){ return 2; } else
		if (noteName.equals("bb3")){ return 2; } else
		if (noteName.equals("b3")){ return 3; } else
		if (noteName.equals("#2")){ return 3; } else
		if (noteName.equals("3")){ return 4; } else
		if (noteName.equals("bb4")){ return 3; } else
		if (noteName.equals("b4")){ return 4; } else
		if (noteName.equals("4")){ return 5; } else
		if (noteName.equals("#3")){ return 5; } else
		if (noteName.equals("b5")){ return 6; } else
		if (noteName.equals("bb5")){ return 5; } else
		if (noteName.equals("##3")){ return 6; } else
		if (noteName.equals("#4")){ return 6; } else
		if (noteName.equals("##4")){ return 7; } else
		if (noteName.equals("5")){ return 7; } else
		if (noteName.equals("bb6")){ return 7; } else
		if (noteName.equals("#5")){ return 8; } else
		if (noteName.equals("b6")){ return 8; } else
		if (noteName.equals("6")){ return 9; } else
		if (noteName.equals("bb7")){ return 9; } else
		if (noteName.equals("#6")){ return 10; } else
		if (noteName.equals("b7")){ return 10; } else
		if (noteName.equals("7")){ return 11; } else
		{ return -1; }
	}

	public static String getTeXNoteNameFromPitch(int pitch){
		if (pitch == 0){ return "1"; } else
		if (pitch == 1){ return "$\\flat$2"; } else
		if (pitch == 2){ return "2"; } else
		if (pitch == 3){ return "$\\flat$3"; } else
		if (pitch == 4){ return "3"; } else
		if (pitch == 5){ return "4"; } else
		if (pitch == 6){ return "$\\flat$5"; } else
		if (pitch == 7){ return "5"; } else
		if (pitch == 8){ return "$\\flat$6"; } else
		if (pitch == 9){ return "6"; } else
		if (pitch == 10){ return "$\\flat$7"; } else
		if (pitch == 11){ return "7"; } else
		{
			System.out.println("Returning blank note name for pitch " + pitch);
			return "";
		}
	}

	public static String getNoteNameFromPitch(int pitch){
		if (pitch == 0){ return "1"; } else
		if (pitch == 1){ return "b2"; } else
		if (pitch == 2){ return "2"; } else
		if (pitch == 3){ return "b3"; } else
		if (pitch == 4){ return "3"; } else
		if (pitch == 5){ return "4"; } else
		if (pitch == 6){ return "b5"; } else
		if (pitch == 7){ return "5"; } else
		if (pitch == 8){ return "b6"; } else
		if (pitch == 9){ return "6"; } else
		if (pitch == 10){ return "b7"; } else
		if (pitch == 11){ return "7"; } else
		{
			System.out.println("Returning blank note name for pitch " + pitch);
			return "";
		}
	}

	public static String getNoteNameFromPitch(int pitch, int functionalPitch){
		if (pitch == 0){ return "1"; } else
		if (pitch == 1){ return "b2"; } else
		if (pitch == 2){ return "2"; } else
		if (pitch == 3 && functionalPitch == 2){ return "#2"; } else
		if (pitch == 3){ return "b3"; } else
		if (pitch == 4 && functionalPitch == 4){ return "b4"; } else
		if (pitch == 4){ return "3"; } else
		if (pitch == 5 && functionalPitch == 3){ return "#3"; } else
		if (pitch == 5){ return "4"; } else
		if (pitch == 6 && functionalPitch == 5){ return "b5"; } else
		if (pitch == 6 && functionalPitch == 3){ return "##3"; } else
		if (pitch == 6 /*&& functionalPitch == 4*/){ return "#4"; } else
		if (pitch == 7 && functionalPitch == 6){ return "bb6"; } else
		if (pitch == 7 && functionalPitch == 4){ return "##4"; } else
		if (pitch == 7){ return "5"; } else
		if (pitch == 8 && functionalPitch == 5){ return "#5"; } else
		if (pitch == 8 /*&& functionalPitch == 6*/){ return "b6"; } else
		if (pitch == 9 && functionalPitch == 7){ return "bb7"; } else
		if (pitch == 9){ return "6"; } else
		if (pitch == 10 && functionalPitch == 6){ return "#6"; } else
		if (pitch == 10){ return "b7"; } else
		if (pitch == 11){ return "7"; } else
		{
			System.out.println("Returning blank note name for pitch " + pitch + " and functionalPitch " + functionalPitch);
			return "";
		}
	}

	public static String getPrefNoteNameFromPitch(int pitch, ModularParameter[] pitches){
		if (pitch == 0){ return "1"; } else
		if (pitch == 1){ return "b2"; } else
		if (pitch == 2){ return "2"; } else
		if (pitch == 3 && contains(pitches, 4)){ return "#2"; } else
		if (pitch == 3){ return "b3"; } else
		if (pitch == 4){ return "3"; } else
		if (pitch == 5){ return "4"; } else
		if (pitch == 6 && contains(pitches, 7)){ return "#4"; } else
		if (pitch == 6 && contains(pitches, 4) && contains(pitches, 7)){ return "#4"; } else
		if (pitch == 6 && contains(pitches, 4) && contains(pitches, 8)){ return "#4"; } else
		if (pitch == 6){ return "b5"; } else
		if (pitch == 7){ return "5"; } else
		if (pitch == 8 && !(contains(pitches, 7)) &&  contains(pitches, 4)){ return "#5"; } else
		if (pitch == 8){ return "b6"; } else
		if (pitch == 9){ return "6"; } else
		if (pitch == 10){ return "b7"; } else
		if (pitch == 11){ return "7"; } else
		{
			System.out.println("Returning blank note name for pitch " + pitch);
			return "";
		}
	}

	public static boolean contains(ModularParameter[] pitches, int pitch){
		for (int i=0; i<pitches.length; i++){
			if (pitches[i].GetValue() == pitch){
				return true;
			}
		}
		return false;
	}

	public static String printNote(Integer shape, String noteName, int fretCount, String noteType, int startFret){
		String note = "";
		if (getPitchFromNoteName(noteName) != -1){
			ModularParameter pitchClass = new ModularParameter(getPitchFromNoteName(noteName));
			ArrayList positions = XYConstants.getPitchClassPositions(shape, pitchClass, fretCount);
			for (int i = 0; i < positions.size(); i++){
				//System.out.println(noteName + " at (" + ((FretboardPosition)positions.get(i)).getString() + ", " + ((FretboardPosition)positions.get(i)).getFret() + ")");
				note += NotePlacer.notePos(((FretboardPosition)positions.get(i)).getString(), ((FretboardPosition)positions.get(i)).getFret() + startFret) + noteType + "\n";
			}
		}
		return note;
	}

}
