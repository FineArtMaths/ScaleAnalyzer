package com.calnrich.hypermodes;

import java.io.FileReader;
import java.io.Reader;
import java.io.Writer;
import java.io.FileWriter;
import java.text.DecimalFormat;

import com.calnrich.fortefy.core.note.ModularParameter;

public class Analyst {

    private static final int DELIMITER = '%';
    private static final int INDICATOR = '~';
	private static DecimalFormat m_df = new DecimalFormat("#");

	private static int m_scaleCountPenta = 0;
	private static int m_scaleCountHepta = 0;

	private static String[] scaleNames = new String[4096];

	private static String reportDupes = "";

    public static void main(String[] args) throws Exception{
        Reader rdr = new FileReader(args[0]);
        Writer wtr = new FileWriter(args[0] + ".analysis");
        int readBuffer;
        boolean preprocessing = false;
        String preprocessingTask = "";
        readBuffer = rdr.read();
        while (readBuffer != -1){
            if (preprocessing){
                if (readBuffer == DELIMITER){
                    preprocess(preprocessingTask);
                    preprocessingTask = "";
                } else {
                    preprocessingTask = preprocessingTask + (char)readBuffer;
                }
            }
            if (readBuffer == DELIMITER){
                preprocessing = !preprocessing;
            }
            readBuffer = rdr.read();
        }

        wtr.write("\n========================================================\n");
		wtr.write("D U P L I C A T E   S C A L E S\n");
        wtr.write("========================================================\n");
		wtr.write(reportDupes);

        wtr.write("\n\n========================================================\n");
		wtr.write("S C A L E S   N O T   C O V E R E D\n");
        wtr.write("========================================================\n");

		for (int i = 0; i< scaleNames.length; i++){
			if (scaleNames[i] == null){
				wtr.write(getBinaryForm(i));
			}
		}

        wtr.write("\n\n========================================================\n");
		wtr.write("D E B U G   O U T P U T\n");
        wtr.write("========================================================\n");

		for (int i = 0; i< scaleNames.length; i++){
			wtr.write(i + ": " + scaleNames[i] + "\n");
		}

        wtr.flush();
        wtr.close();
        System.out.println("========================================================");
        System.out.println("Analyst has finished parsing the input file");
        System.out.println("[" + m_scaleCountPenta + "] pentatonics were analysed");
        System.out.println("[" + m_scaleCountHepta + "] heptatonics were analysed");
        System.out.println("NOTE: Analyst is only looking at heptatonics!");
        System.out.println("========================================================");
    }

    private static String getBinaryForm(int num){
		String returnValue = "";

		// get binary representation
		int tempNum = num;
		for (int j = 11; j >= 0; j--){
			if (tempNum >= powerTwo(j)){
				returnValue += "1";
				tempNum -= powerTwo(j);
			} else {
				returnValue += "0";
			}
		}

		// bit rotate and remove modes from the list
		String rotatedValue = returnValue;
		for (int k = 0; k < 12; k++){
			rotatedValue = bitRotate(rotatedValue);
			if (scaleNames[getDecimalForm(rotatedValue)] == null){
				scaleNames[getDecimalForm(rotatedValue)] = "DONE";
			}
		}

		String analysis = analyseForm(returnValue, num);
		if (analysis.substring(0,1).equals("Y")){
			return "" + num + " = " + returnValue + "     " + analysis + "\n";
		} else {
			return "";
		}
	}

	private static String bitRotate(String form){
		return form.substring(1, 12) + form.substring(0, 1);
	}

	private static int getDecimalForm(String binstr){
		int retVal = 0;
		for (int j = 0; j < 12; j++){
			if (binstr.substring(j, j + 1).equals("1")){
				retVal += powerTwo(11 - j);
			}
		}
		return retVal;
	}

	private static int countNotes(String binstr){
		int retVal = 0;
		for (int j = 0; j < 12; j++){
			if (binstr.substring(j, j + 1).equals("1")){
				retVal++;
			}
		}
		return retVal;
	}

	private static int getLargestConsecutiveSemitoneCount(String binstr){
		int maxCount = 0;
		int currCount = 0;
		for (int j = 0; j < 12; j++){
			if (binstr.substring(j, j + 1).equals("1")){
				currCount++;
			} else {
				if (currCount > maxCount){
					maxCount = currCount;
				}
				currCount = 0;
			}
		}
		if (maxCount < currCount){
			maxCount = currCount;
		}
		return maxCount;
	}

	private static int getLargestInterval(String binstr){
		int maxCount = 0;
		int currCount = 0;
		for (int j = 0; j < 12; j++){
			if (binstr.substring(j, j + 1).equals("0")){
				currCount++;
			} else {
				if (currCount > maxCount){
					maxCount = currCount;
				}
				currCount = 0;
			}
		}
		if (maxCount < currCount){
			maxCount = currCount;
		}
		return maxCount;
	}

	private static String analyseForm(String form, int counter){
		String returnValue = "";
		int numNotes = countNotes(form);
		String indicator = "Y";
		if (numNotes != 7){
			indicator = "N";
		}
		int conSems = getLargestConsecutiveSemitoneCount(form);
		if (conSems > 3){
			indicator = "N";
		}
		String autoTeX = "";
		String rotatedForm = form;
		int count = 0;
		while(rotatedForm.substring(0, 1).equals("0") && count < 12){
			count++;
			rotatedForm = bitRotate(rotatedForm);
		}
		autoTeX = "\\subsection{The NoName" + counter + " Scale} %~M|5|" + getNoteNames(rotatedForm) + "|NoName" + counter + "@NoName@NoName@NoName@NoName@NoName@NoName@NoName%";
		return indicator + "(" + numNotes + " notes), (" + conSems + " consecutive sems), (" + getLargestInterval(rotatedForm) + " largest interval)       " + autoTeX;
	}

	private static String getNoteNames(String form){
		String returnValue = "";
		for (int i = 0; i < 12; i++){
			if (form.substring(i, i + 1).equals("1")){
				returnValue += NotePlacer.getNoteNameFromPitch(i) + ",";
			}
		}
		if (returnValue.length() > 1){
			return returnValue.substring(0, returnValue.length() - 1);
		} else {
			return "Invalie autoTeX";
		}
	}

	private static int powerTwo(int i){
		int retVal = 1;
		for (int p = 0; p < i; p++){
			retVal *= 2;
		}
		return retVal;
	}

    private static String preprocess(String preprocessingTask){
        if (preprocessingTask.charAt(0) != INDICATOR){
            return "" + (char)DELIMITER + preprocessingTask + (char)DELIMITER;
        }
        String[] mainParts = preprocessingTask.split("\\|");
        if (mainParts.length < 2){
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
            return "" + (char)DELIMITER + preprocessingTask +(char)DELIMITER;
		}
		String returnValue = "";
		for (int i = 0; i < smallParts.length; i++){
			smallParts[i] = smallParts[i].trim();
		}
		returnValue += processShape(mainParts[0], fretCount, smallParts, true, modeNames);
		return returnValue;
    }

	public static String getNotes(String shape, String[] notes){
		String returnVal = "";
		ModularParameter mp;
		String note = "";
		for (int i = 0; i < notes.length; i++){
			if (notes[i].length() > 1 && notes[i].substring(notes[i].length() - 1, notes[i].length()).equals("x")){
				note = notes[i].substring(0, notes[i].length() - 1);
			} else {
				note = notes[i];
			}
			returnVal += note;
		}
		return returnVal;
	}

	private static String processShape(String shape,
									   int fretCount,
									   String[] notes,
									   boolean display,
									   String[] modeNames){
		String returnValue = "";
		if (shape.equals("~M")){
			returnValue += getAllModes(fretCount, notes, modeNames);
		}
		return returnValue;
	}

	private static String getAllModes(int fretCount, String[] notes, String[] modeNames){
		String returnValue = "";
		int modeNumber = 0;
		do{
			int charNum = getCharacteristicNumber(notes, modeNumber);
			returnValue += "" + charNum + "\n";
			if (scaleNames[charNum] != null){
				reportDupes += modeNames[modeNumber] + " and " + scaleNames[charNum] + " share the characteristic number " + charNum + "\n";
			}
			scaleNames[charNum] = modeNames[modeNumber];
			modeNumber++;
			notes = getNextMode(notes);
			if (notes.length == 5){
				m_scaleCountPenta++;
			} else if (notes.length == 7){
				m_scaleCountHepta++;
			}
		} while (notes[0] != "1");
		return returnValue;
	}

	private static int getCharacteristicNumber(String[] notes, int modeNumber){
		int returnValue = 0;
		for (int i=0; i<notes.length; i++){
			int q = NotePlacer.getPitchFromNoteName(notes[modeNumber]);
			int q2 = 1;
			for (int p = 0; p < q; p++){
				q2 *= 2;
			}
			returnValue += q2;
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
		for(int k = 0; k < notes.length; k++){
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

}