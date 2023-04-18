package com.calnrich.hypermodes;

import java.util.ArrayList;
import java.util.HashMap;
import com.calnrich.fortefy.core.note.ModularParameter;

public class XYConstants {

	public static final int GRID_SIZE = 4;

	private static final Integer E_SHAPE = new Integer(0);
	private static final Integer A_SHAPE = new Integer(1);
	private static final Integer G_SHAPE = new Integer(2);
	private static final Integer C_SHAPE = new Integer(3);
	private static final Integer D_SHAPE = new Integer(4);

	private static final HashMap shapeMap = new HashMap();
	static{
		shapeMap.put("~E", E_SHAPE);
		shapeMap.put("~A", A_SHAPE);
		shapeMap.put("~G", G_SHAPE);
		shapeMap.put("~C", C_SHAPE);
		shapeMap.put("~D", D_SHAPE);
	}

	public static Integer getShape(String shape){
		return (Integer)(shapeMap.get(shape));
	}

	public static final String DisplayMathStart = "\\[\n ";
	public static final String DisplayMathEnd = "\\]\n ";
	public static final String XYStart = "\\xy \n ";
	public static final String XYEnd = "\n\\endxy\n ";

	public static final String rootTone = "*+[o][F]{\\bullet};";
	public static final String chordTone = "*+[o]{\\bullet};";
	public static final String scaleTone = "*+[o][F-]{};";
	public static final String crossTone = "*+[o]{\\times};";


	//	0	1	2	3	4	5	6	7	8	9	10	11
	//	1	b2	2	b3	3	4	b5	5	b6	6	b7	7

	private static ModularParameter getBasePitchClass(Integer shape, int currString){
		if (shape.equals(E_SHAPE)){
			return crossStrings(0, 0, currString);
		} else if (shape.equals(A_SHAPE)){
			return crossStrings(6, 0, currString);
		} else if (shape.equals(G_SHAPE)){
			return crossStrings(9, 0, currString);
		} else if (shape.equals(C_SHAPE)){
			return crossStrings(4, 0, currString);
		} else if (shape.equals(D_SHAPE)){
			return crossStrings(2, 0, currString);
		} else {
			System.out.println("getBasePitchClass was passed " + shape + " which is not a valid shape");
			return null;
		}
	}

	private static final int[] stringDistances = {5, 5, 5, 4, 5};

	public static ModularParameter crossStrings(int pitchClass, int fromString, int toString){
		//System.out.println("CROSSING: " + pitchClass + ", " + fromString + ", " + toString);
		int diff = 0;
		if (fromString < toString){
			for(int i = 0; i < toString - fromString; i++){
				diff += stringDistances[fromString + i];
			}
		} else {
			for(int i = 0; i < fromString - toString; i++){
				diff += 12 - stringDistances[toString + i];
			}
		}
		return new ModularParameter(pitchClass + diff);
	}

	public static ArrayList getPitchClassPositions(Integer shape, ModularParameter pitchClass, int fretCount){
		ArrayList positions = new ArrayList ();
		for (int currString = 0; currString < 6; currString++){
			ModularParameter currPitch = getBasePitchClass(shape, currString);
			for (int currFret = fretCount; currFret > 0; currFret--){
				if (currPitch.GetValue() == pitchClass.GetValue()){
					//System.out.println("Value=[" + currPitch.GetValue() + ", new position at (" + currString + ", " + currFret + ")");
					positions.add(new FretboardPosition(currString, currFret));
				}
				currPitch.Add(1);
			}
		}
		return positions;
	}

}
