package com.calnrich.hypermodes;

import java.io.FileReader;
import java.io.Reader;
import java.io.Writer;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList;

import com.calnrich.fortefy.core.note.ModularParameter;

public class Pictogram {

    private static final int DELIMITER = '%';
    private static final int INDICATOR = '~';
	private static DecimalFormat m_df = new DecimalFormat("#");

	private static int m_scaleCount[] = new int[12];

    public static void main(String[] args) throws Exception{
        Reader rdr = new FileReader(args[0]);
        Writer wtr = new FileWriter(args[0] + ".tex");
        int readBuffer;
        boolean preprocessing = false;
        String preprocessingTask = "";
        readBuffer = rdr.read();
        while (readBuffer != -1){
            if (preprocessing){
                if (readBuffer == DELIMITER){
					System.out.println(preprocessingTask);
                    wtr.write(preprocess(preprocessingTask));
                    preprocessingTask = "";
                } else {
                    preprocessingTask = preprocessingTask + (char)readBuffer;
                }
            } else {
                if (readBuffer != DELIMITER){
                    wtr.write(readBuffer);
                }
            }
            if (readBuffer == DELIMITER){
                preprocessing = !preprocessing;
            }
            readBuffer = rdr.read();
        }
        wtr.flush();
        wtr.close();
        System.out.println("========================================================");
        System.out.println("Pictogram has finished parsing the input file");
        int cnt = 0;
        for (int i = 0; i < 12; i++){
        	System.out.println("[" + m_scaleCount[i] + "] " + i + "-note scales were printed");
        	cnt += m_scaleCount[i];
		}
        System.out.println("A total of " + cnt + " scales were printed");
        System.out.println("========================================================");
    }

    private static String preprocess(String preprocessingTask){
        if (preprocessingTask.charAt(0) != INDICATOR){
            System.out.println("Ignored [" + preprocessingTask + "], which is not a Pictogram instruction");
            return "" + (char)DELIMITER + preprocessingTask + (char)DELIMITER;
        }
        String[] mainParts = preprocessingTask.split("\\|");
        if (mainParts.length < 2){
            System.out.println("Ignored [" + preprocessingTask + "], which is not a valid Pictogram instruction, because it has only " + mainParts.length + " fields");
            return "" + (char)DELIMITER + preprocessingTask +(char)DELIMITER;
        }
        String[] smallParts = null;
        if (mainParts.length > 2){
			smallParts = mainParts[2].split("\\,");
		} else {
			smallParts = new String[0];
		}
		String[] modeNames = null;
        if (mainParts.length > 3){
        	modeNames = mainParts[3].split("\\@");
		}
		int fretCount = -1;
		try{
			fretCount = m_df.parse(mainParts[1]).intValue();
		} catch(Exception e){
            System.out.println("Ignored [" + preprocessingTask + "] because fret count [" + mainParts[1] + "] could not be parsed");
            return "" + (char)DELIMITER + preprocessingTask +(char)DELIMITER;
		}
		String returnValue = "";
		for (int i = 0; i < smallParts.length; i++){
			smallParts[i] = smallParts[i].trim();
		}
		returnValue += processShape(mainParts[0], fretCount, smallParts, true, modeNames);
		return returnValue;
    }

	public static String getNotes(String shape, int fretCount, String[] notes, int startFret){
		String returnVal = "";//NotePlacer.printArpeggio(shape, fretCount, startFret);
		ModularParameter mp;
		String noteType =  "";
		String note = "";
		for (int i = 0; i < notes.length; i++){
			if (notes[i].equals("1")){
				noteType = XYConstants.crossTone;
				note = notes[i];
			} else {
				noteType = XYConstants.scaleTone;
				note = notes[i];
			}
			//System.out.println("notes[i] = [" + notes[i] + "], note = [" + note + "]");
			returnVal += NotePlacer.printNote(XYConstants.getShape(shape.substring(0,2)), note, fretCount, noteType, startFret);
		}
		return returnVal;
	}

	private static String processShape(String shape,
									   int fretCount,
									   String[] notes,
									   boolean display,
									   String[] modeNames){
		String returnValue = "";
		String displayInstruction = "";
		int startFret = 0;
		if (shape.split("\\,").length > 1){
			displayInstruction = shape.split("\\,")[1];
			if (shape.split("\\,").length > 2){
				try{
					startFret = m_df.parse(shape.split("\\,")[2]).intValue();
				} catch(Exception e){
					System.out.println("Error: start fret [" + shape.split("\\,")[2] + "] could not be parsed. Try not specifying the start fret.");
				}
			}
			shape = shape.split("\\,")[0];
		}
		if (displayInstruction.equals("NoDisplay")){
			display = false;
		}
		//System.out.println("shape = [" + shape.substring(0,2) + "]");
		if (XYConstants.getShape(shape.substring(0,2)) != null){
			if (display){
				returnValue += XYConstants.DisplayMathStart;
			}
			returnValue += XYConstants.XYStart;
			returnValue += FretboardBuilder.buildFretboard(fretCount + startFret);
			returnValue += getNotes(shape, fretCount, notes, startFret);
			returnValue += XYConstants.XYEnd;
			if(display){
				returnValue += XYConstants.DisplayMathEnd;
			}
		} else if (shape.substring(0,2).equals("~5")){
			if (display){
				returnValue += XYConstants.DisplayMathStart;
			}
			returnValue += "\\begin{array}{rrrrr}\n";
			returnValue += processShape("~C" + getModifier(shape), fretCount, notes, false, null);
			returnValue += "\n&\n";
			returnValue += processShape("~A" + getModifier(shape), fretCount, notes, false, null);
			returnValue += "\n&\n";
			returnValue += processShape("~G" + getModifier(shape), fretCount, notes, false, null);
			returnValue += "\n&\n";
			returnValue += processShape("~E" + getModifier(shape), fretCount, notes, false, null);
			returnValue += "\n&\n";
			returnValue += processShape("~D" + getModifier(shape), fretCount, notes, false, null);
			returnValue += "\\end{array}\n";
			if (display){
				returnValue += XYConstants.DisplayMathEnd;
			}
 		} else if (shape.equals("~M")){
			returnValue += getAllModes(fretCount, notes, modeNames);
		} else if (shape.equals("~T")){
			returnValue += getModeTable(notes, modeNames);
		} else {
			System.out.println("[" + shape + "] is not a valid shape");
		}
		return returnValue;
	}

	private static String getAllModes(int fretCount, String[] notes, String[] modeNames){
		String returnValue = ""; //"\n\n";
		int modeNumber = 0;
		List modeList = new ArrayList();
		int offset = 11; 
		
		int[] binaryRep = new int[12];
		for(int i = 0; i < 12; i++){
			binaryRep[i] = 0;
			for(int j = 0; j < notes.length; j++){
				if (NotePlacer.getPitchFromNoteName(notes[j]) == i){
					binaryRep[i] = 1;
				}
			}
		}
		
		while (offset >= 0){
			if(binaryRep[offset] == 0){
				// it's a hypermode, so build the scale notes and print it.
				
				String[] hmNotes = new String[notes.length + 1];
				hmNotes[0] = "1";	// add the root
				int ioff = offset;
				int idx = 1;
				for(int i = 0; i < 12; i++){
					if(binaryRep[ioff] == 1){
						hmNotes[idx] = NotePlacer.getNoteNameFromPitch(i);
						idx++;
					}
					ioff++;
					if(ioff > 11){
						ioff = 0;
					}
				}
				
				String strOffset = NotePlacer.getNoteNameFromPitch(12 - offset);
				returnValue += "\\subsubsection*{The " + strOffset + " Hypermode \\hspace{1in} [" + printNotes(hmNotes, modeNumber) + "]}";
				returnValue += processShape("~5", fretCount, hmNotes, true, null);
			}
			offset--;
		}
		return returnValue;
	}

	private static String joinArray(String[] arr){
		String strReturn = "";
		boolean rootIsFirst = false;
		do{
			arr = rotate(arr);
			if (arr[0].equals("1")){
				rootIsFirst = true;
			}
		} while (!rootIsFirst);
		for (int i = 0; i < arr.length; i++){
			strReturn += arr[i] + "|";
		}
		return strReturn;
	}

	private static String[] rotate(String[] arr){
		String[] retval = new String[arr.length];
		String firstVal = arr[0];
		for (int i = 0; i < arr.length - 1; i++){
			retval[i] = arr[i+1];
		}
		retval[arr.length - 1] = firstVal;
		return retval;
	}

	private static String printNotes(String[] notes, int modeNumber){
		String returnValue = "";
		for (int i=0; i<notes.length; i++){
			if(null != notes[modeNumber] && !notes[modeNumber].equals("1")){
				if(notes[modeNumber].substring(0, 1).equals("#")){
					if (notes[modeNumber].length() == 3){
						returnValue += "$\\sharp \\sharp " + notes[modeNumber].substring(2, 3) + (notes.length == 7 ? "^{" + getSeventhType(notes, modeNumber) + "}$ " : "$ ");
					} else {
						returnValue += "$\\sharp " + notes[modeNumber].substring(1, 2) + (notes.length == 7 ? "^{" + getSeventhType(notes, modeNumber) + "}$ " : "$ ");
					}
				} else if(notes[modeNumber].substring(0, 1).equals("b")){
					if (notes[modeNumber].length() == 3){
						returnValue += "$\\flat\\flat " + notes[modeNumber].substring(2, 3) + (notes.length == 7 ? "^{" + getSeventhType(notes, modeNumber) + "}$ " : "$ ");
					} else {
						returnValue += "$\\flat " + notes[modeNumber].substring(1, 2) + (notes.length == 7 ? "^{" + getSeventhType(notes, modeNumber) + "}$ " : "$ ");
					}
				} else {
					returnValue += "$" + notes[modeNumber] + (notes.length == 7 ? "^{" + getSeventhType(notes, modeNumber) + "}$ " : "$ ");
				}
			}
			modeNumber++;
			if (modeNumber >= notes.length){
				modeNumber = 0;
			}
		}
		return returnValue;
	}

	private static String printIntervals(String[] notes, int modeNumber){
		String returnValue = "";
		ModularParameter currentNote = new ModularParameter(0);
		ModularParameter nextNote = new ModularParameter(0);
		int nextNoteNumber = modeNumber;
		int interval = 0;
		for (int i=0; i<notes.length; i++){
			currentNote.SetValue(NotePlacer.getPitchFromNoteName(notes[modeNumber]));
			nextNoteNumber++;
			if (nextNoteNumber >= notes.length){
				nextNoteNumber = 0;
			}
			nextNote.SetValue(NotePlacer.getPitchFromNoteName(notes[nextNoteNumber]));
			returnValue += NotePlacer.getIntervalName(nextNote.Subtract(currentNote.GetValue()));
			if (i < notes.length - 1){
				returnValue += ", ";
			}
			modeNumber++;
			if (modeNumber >= notes.length){
				modeNumber = 0;
			}
		}
		return returnValue;
	}

	private static String[] getNextMode(String[] notes){
		ModularParameter[] pitches = new ModularParameter[notes.length];
		for(int i = 0; i < notes.length; i++){
			pitches[i] = new ModularParameter(NotePlacer.getPitchFromNoteName(notes[i]));
		}
		int modeNumber = -1;
		do{
			for(int j = 0; j < pitches.length; j++){
				pitches[j].Subtract(1);
				if (pitches[j].GetValue() == 0){
					modeNumber = j;
				}
			}

		}while(modeNumber == -1);
		String[] returnValue = new String[notes.length];
		int functionalPitch = notes.length - modeNumber + 1;
		//System.out.println("START: functionalPitch = " + functionalPitch+", modeNumber = "+ modeNumber);
		for(int k = 0; k < notes.length; k++){
			//System.out.println("	functionalPitch = " + functionalPitch + ", pitch=" + pitches[k].GetValue());
			if (notes.length == 7){
				returnValue[k] = NotePlacer.getNoteNameFromPitch(pitches[k].GetValue(), functionalPitch);
			} else {
				returnValue[k] = NotePlacer.getPrefNoteNameFromPitch(pitches[k].GetValue(), pitches);
			}
			functionalPitch++;
			if (functionalPitch > 7){
				functionalPitch = 1;
			}
		}
		return returnValue;
	}

	private static String getSeventhType(String[] notes, int note){
		// Assemble the pitches for the 7th chord
		int root = note;
		ModularParameter mpRoot = new ModularParameter(NotePlacer.getPitchFromNoteName(notes[root]));
		int third = root + 2;
		if (third >= notes.length){ third = third - notes.length ; }
		ModularParameter mpThird = new ModularParameter(NotePlacer.getPitchFromNoteName(notes[third]));
		mpThird = new ModularParameter(mpThird.Subtract(mpRoot.GetValue()));
		if (mpThird.GetValue() != 3 && mpThird.GetValue() != 4){
			// The third isn't where we expect -- go hunting
			int thirdBelow = third - 1;
			if (thirdBelow < 0){ thirdBelow = notes.length + thirdBelow; }
			ModularParameter mpThirdBelow = new ModularParameter(NotePlacer.getPitchFromNoteName(notes[thirdBelow]));
			mpThirdBelow = new ModularParameter(mpThirdBelow.Subtract(mpRoot.GetValue()));

			if (mpThirdBelow.GetValue() == 3 || mpThirdBelow.GetValue() == 4){
				mpThird = new ModularParameter(mpThirdBelow.GetValue());
			} else {
				int thirdAbove = third + 1;
				if (thirdAbove >= notes.length){ thirdAbove = thirdAbove - notes.length; }
				ModularParameter mpThirdAbove = new ModularParameter(NotePlacer.getPitchFromNoteName(notes[thirdAbove]));
				mpThirdAbove = new ModularParameter(mpThirdAbove.Subtract(mpRoot.GetValue()));
				if (mpThirdAbove.GetValue() == 3 || mpThirdAbove.GetValue() == 4){
					mpThird = new ModularParameter(mpThirdAbove.GetValue());
				}
			}
		}


		int fifth = third + 2;
		if (fifth >= notes.length){ fifth = fifth - notes.length; }
		ModularParameter mpFifth = new ModularParameter(NotePlacer.getPitchFromNoteName(notes[fifth]));
		mpFifth = new ModularParameter(mpFifth.Subtract(mpRoot.GetValue()));
		if ((mpFifth.GetValue() == 6 && mpThird.GetValue() != 3) || (mpFifth.GetValue() == 8 && mpThird.GetValue() != 4)){
			// The fifth doesn't match the third -- go hunting for a better one
			int fifthBelow = third - 1;
			if (fifthBelow < 0){ fifthBelow = notes.length + fifthBelow; }
			ModularParameter mpFifthBelow = new ModularParameter(NotePlacer.getPitchFromNoteName(notes[fifthBelow]));
			mpFifthBelow = new ModularParameter(mpFifthBelow.Subtract(mpRoot.GetValue()));

			if (mpFifthBelow.GetValue() == 7 || (mpFifthBelow.GetValue() == 6 && mpThird.GetValue() == 3) || (mpFifthBelow.GetValue() == 8 && mpThird.GetValue() == 4)){
				mpFifth = new ModularParameter(mpFifthBelow.GetValue());
			} else {
				int fifthAbove = fifth + 1;
				if (fifthAbove >= notes.length){ fifthAbove = fifthAbove - notes.length; }
				ModularParameter mpFifthAbove = new ModularParameter(NotePlacer.getPitchFromNoteName(notes[fifthAbove]));
				mpFifthAbove = new ModularParameter(mpFifthAbove.Subtract(mpRoot.GetValue()));
				if (mpFifthAbove.GetValue() == 7 || (mpFifthAbove.GetValue() == 6 && mpThird.GetValue() == 3) || (mpFifthAbove.GetValue() == 8 && mpThird.GetValue() == 4)){
					mpFifth = new ModularParameter(mpFifthAbove.GetValue());
				} else {
					int fifthAboveAgain = fifth + 2;
					if (fifthAboveAgain >= notes.length){ fifthAboveAgain = fifthAboveAgain - notes.length; }
					ModularParameter mpFifthAboveAgain = new ModularParameter(NotePlacer.getPitchFromNoteName(notes[fifthAboveAgain]));
					mpFifthAboveAgain = new ModularParameter(mpFifthAboveAgain.Subtract(mpRoot.GetValue()));
					if (mpFifthAboveAgain.GetValue() == 7 || (mpFifthAboveAgain.GetValue() == 6 && mpThird.GetValue() == 3) || (mpFifthAboveAgain.GetValue() == 8 && mpThird.GetValue() == 4)){
						mpFifth = new ModularParameter(mpFifthAboveAgain.GetValue());
					}
				}

			}
		}

		int seventh = fifth + 2;
		if (seventh >= notes.length){ seventh = seventh - notes.length; }
		ModularParameter mpSeventh = new ModularParameter(NotePlacer.getPitchFromNoteName(notes[seventh]));
		mpSeventh = new ModularParameter(mpSeventh.Subtract(mpRoot.GetValue()));

		// Decide what type it is
		String strThird = NotePlacer.getNoteNameFromPitch(mpThird.GetValue(), 3);
		String strFifth = NotePlacer.getNoteNameFromPitch(mpFifth.GetValue(), 5);
		String strSeventh = NotePlacer.getNoteNameFromPitch(mpSeventh.GetValue(), 7);
		if (strThird.equals("3")){
			if (strFifth.equals("5")){
				if (strSeventh.equals("b7")){
					return "d";
				} else if (strSeventh.equals("7")){
					return "\\Delta";
				} else {
					return "[d/\\Delta]";
				}
			} else if (strFifth.equals("#5")){
				return "+";
			} else if (strFifth.equals("b5")){
				if (strSeventh.equals("b7")){
					return "d\\flat 5";
				} else if (strSeventh.equals("7")){
					return "\\Delta\\flat 5";
				} else {
					return "[d\\flat 5/\\Delta\\flat 5]";
				}
			} else {	// no valid 5th
				if (strSeventh.equals("b7")){
					return "[d]";
				} else if (strSeventh.equals("7")){
					return "[\\Delta]";
				} else {
					return "[d/\\Delta]";
				}
			}
		} else if (strThird.equals("b3")){
			if (strFifth.equals("5")){
				if (strSeventh.equals("7")){
					return "m\\Delta";
				} else if (strSeventh.equals("b7")){
					return "m";
				} else {
					return "[m]";
				}
			} else 	if (strFifth.equals("b5")){
				if (strSeventh.equals("b7")){
					return "\\varnothing";
				} else if (strSeventh.equals("bb7") || strSeventh.equals("6")){
					return "\\circ";
				} else {
					return "[\\varnothing/\\circ]";
				}
			} else {	// no valid 5th
				if (strSeventh.equals("7")){
					return "[m\\Delta]";
				} else {
					return "[m]";
				}
			}

		} else {	// There's no valid third
			if (strFifth.equals("5")){
				if (strSeventh.equals("b7")){
					return "[m/d]";
				} else if (strSeventh.equals("7")){
					return "[\\Delta]";
				} else {
					return "[m/d/\\Delta]";
				}
			} else 	if (strFifth.equals("b5")){
				if (strSeventh.equals("b7")){
					return "[\\varnothing]";
				} else if (strSeventh.equals("bb7") || strSeventh.equals("6")){
					return "[\\circ]";
				} else {
					return "[\\varnothing/\\circ]";
				}
			} else 	if (strFifth.equals("#5")){
				return "[+]";
			}

		}

		//System.out.println("Chord of 1 " + strThird + " " + strFifth + " " + strSeventh + " not recognised");
		return "";
	}

	private static String getModifier(String shape){
		if (shape.length() > 2){
			return shape.substring(2,3);
		} else {
			return "";
		}
	}

	private static String getModifier(String[] notes){
		boolean containsMajor3 = false;
		boolean containsMinor3 = false;
		boolean containsNatural5 = false;
		boolean containsDiminished5 = false;
		boolean containsAugmented5 = false;
		for (int i = 0; i < notes.length; i++){
			if (notes[i].equals("b3")){ containsMinor3 = true; }
			if (notes[i].equals("#2")){ containsMinor3 = true; }
			if (notes[i].equals("3")){ containsMajor3 = true; }
			if (notes[i].equals("b4")){ containsMajor3 = true; }
			if (notes[i].equals("#4")){ containsDiminished5 = true; }
			if (notes[i].equals("b5")){ containsDiminished5 = true; }
			if (notes[i].equals("5")){ containsNatural5 = true; }
			if (notes[i].equals("#5")){ containsAugmented5 = true; }
			if (notes[i].equals("b6")){ containsAugmented5 = true; }
		}
		if (containsMajor3 && containsNatural5){
			return ""; // major
		} else if (containsMinor3 && containsNatural5){
			return "m"; // minor
		} else if (containsMinor3 && containsDiminished5){
			return "d"; // dim
		} else if (containsMajor3 && containsAugmented5){
			return "a"; // aug
		} else {
			return "0";
		}
	}

	// TO DO: this needs to be made more sophisticated. Looks like as it stands
	// it's breaking the the Augmented ##3 ##4 #6 Scale
	private static String[] removeChordNotes(String[] noteArray, String modifier){
		String[] notes = new String[noteArray.length];
		boolean isMaj = isMajor(modifier);
		boolean isMin = isMinor(modifier);
		boolean isDim = isDiminished(modifier);
		boolean isAug = isAugmented(modifier);
		boolean isNatFifth = isNatFifth(modifier);
		for (int i = 0; i < noteArray.length; i++){
			if (noteArray[i].equals("1")){ notes[i] = ""; } else
			if (noteArray[i].equals("b3") && isMin){ notes[i] = ""; } else
			if (noteArray[i].equals("#2") && isMin){ notes[i] = ""; } else
			if (noteArray[i].equals("3") && isMaj){ notes[i] = ""; } else
			if (noteArray[i].equals("b4") && isMaj){ notes[i] = ""; } else
			if (noteArray[i].equals("#4") && isDim){ notes[i] = ""; } else
			if (noteArray[i].equals("b5") && isDim){ notes[i] = ""; } else
			if (noteArray[i].equals("5") && isNatFifth){ notes[i] = ""; } else
			if (noteArray[i].equals("#5") && isAug){ notes[i] = ""; } else
			if (noteArray[i].equals("b6") && isAug){ notes[i] = ""; }
			else {notes[i] = noteArray[i];}
		}
		return notes;
	}

	private static boolean isMajor(String modifier){
		return modifier.equals("") || modifier.equals("a");
	}

	private static boolean isNatFifth(String modifier){
		return modifier.equals("") || modifier.equals("m");
	}

	private static boolean isMinor(String modifier){
		return modifier.equals("m") || modifier.equals("d");
	}

	private static boolean isDiminished(String modifier){
		return modifier.equals("d");
	}

	private static boolean isAugmented(String modifier){
		return modifier.equals("a");
	}

	private static String getModeTable(String[] notes, String[] modeNames){
		String returnValue = "\\begin{tabular}{";
		for (int k = 0; k < notes.length; k++){
			returnValue += "l";
		}
		returnValue += "}\n";
		ModularParameter mp = null;
		ModularParameter[] mpArr = new ModularParameter[notes.length];
		for (int j = 0; j < notes.length; j++){
			mpArr[j] = new ModularParameter(NotePlacer.getPitchFromNoteName(notes[j]));
		}
		for (int i = 0; i < 12; i++){
			boolean isMode = NotePlacer.contains(mpArr, 0);
			for (int j = 0; j < notes.length; j++){
				if(isMode){
					returnValue += "\\begin{tabular}{|c|}\\hline";
					returnValue += NotePlacer.getTeXNoteNameFromPitch(mpArr[j].GetValue());
					returnValue += "\\\\ \\hline\\end{tabular}";
				} else {
					returnValue += NotePlacer.getTeXNoteNameFromPitch(mpArr[j].GetValue());
				}
				if (j < notes.length - 1){
					returnValue += "&";
				}
				mpArr[j].Add(1);
			}
			if (i < 11){
				returnValue += "\\\\";
			}
		}
		returnValue += "\\end{tabular}";
		m_scaleCount[notes.length] += 12;
		return returnValue;
	}

}