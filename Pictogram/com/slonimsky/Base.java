package com.calnrich.slonimsky;

import com.calnrich.fortefy.core.note.ModularParameter;
import java.util.ArrayList;

public final class Base {

	public static final SlonimskyBase TRITONE = new SlonimskyBase();
	static{
		TRITONE.setName("Tritone");
		TRITONE.getNotes().add(new ModularParameter(0));
		TRITONE.getNotes().add(new ModularParameter(6));
		TRITONE.setNumOctavesToPrint(3);
	}

	public static final SlonimskyBase SESQUITONE = new SlonimskyBase();
	static{
		SESQUITONE.setName("Sesquitone");
		SESQUITONE.getNotes().add(new ModularParameter(0));
		SESQUITONE.getNotes().add(new ModularParameter(3));
		SESQUITONE.getNotes().add(new ModularParameter(6));
		SESQUITONE.getNotes().add(new ModularParameter(9));
		SESQUITONE.setNumOctavesToPrint(2);
	}
	
	public static final SlonimskyBase SESQUIQUADRITONE = new SlonimskyBase();
	static{
		SESQUIQUADRITONE.setName("Sesquiquadritone");
		SESQUIQUADRITONE.getNotes().add(new ModularParameter(0));
		SESQUIQUADRITONE.getNotes().add(new ModularParameter(9));
		SESQUIQUADRITONE.getNotes().add(new ModularParameter(6));
		SESQUIQUADRITONE.getNotes().add(new ModularParameter(3));
		SESQUIQUADRITONE.setNumOctavesToPrint(1);
	}
	
	public static final SlonimskyBase DITONE = new SlonimskyBase();
	static{
		DITONE.setName("Ditone");
		DITONE.getNotes().add(new ModularParameter(0));
		DITONE.getNotes().add(new ModularParameter(4));
		DITONE.getNotes().add(new ModularParameter(8));
		DITONE.setNumOctavesToPrint(2);
	}
	
	public static final SlonimskyBase QUADRITONE = new SlonimskyBase();
	static{
		QUADRITONE.setName("Quadritone");
		QUADRITONE.getNotes().add(new ModularParameter(0));
		QUADRITONE.getNotes().add(new ModularParameter(8));
		QUADRITONE.getNotes().add(new ModularParameter(4));
		QUADRITONE.setNumOctavesToPrint(1);
	}
	
	public static final SlonimskyBase WHOLETONE = new SlonimskyBase();
	static{
		WHOLETONE.setName("Whole Tone");
		WHOLETONE.getNotes().add(new ModularParameter(0));
		WHOLETONE.getNotes().add(new ModularParameter(2));
		WHOLETONE.getNotes().add(new ModularParameter(4));
		WHOLETONE.getNotes().add(new ModularParameter(6));
		WHOLETONE.getNotes().add(new ModularParameter(8));
		WHOLETONE.getNotes().add(new ModularParameter(10));
		WHOLETONE.setNumOctavesToPrint(2);
	}

	public static final SlonimskyBase QUINQUETONE = new SlonimskyBase();
	static{
		QUINQUETONE.setName("Quinquetone");
		QUINQUETONE.getNotes().add(new ModularParameter(0));
		QUINQUETONE.getNotes().add(new ModularParameter(10));
		QUINQUETONE.getNotes().add(new ModularParameter(8));
		QUINQUETONE.getNotes().add(new ModularParameter(6));
		QUINQUETONE.getNotes().add(new ModularParameter(4));
		QUINQUETONE.getNotes().add(new ModularParameter(2));
		QUINQUETONE.setNumOctavesToPrint(1);
	}

	
	/*
	public static final SlonimskyBase SEPTITONE = new SlonimskyBase();
	static{
		SEPTITONE.setName("Septitone");
		SEPTITONE.getNotes().add(new ModularParameter(0));
		SEPTITONE.getNotes().add(new ModularParameter(10));
		SEPTITONE.getNotes().add(new ModularParameter(8));
		SEPTITONE.getNotes().add(new ModularParameter(6));
		SEPTITONE.getNotes().add(new ModularParameter(4));
		SEPTITONE.getNotes().add(new ModularParameter(2));
		SEPTITONE.setNumOctavesToPrint(1);
	}
*/

	public static final SlonimskyBase SEMITONE = new SlonimskyBase();
	static{
		SEMITONE.setName("Semitone");
		SEMITONE.getNotes().add(new ModularParameter(0));
		SEMITONE.getNotes().add(new ModularParameter(1));
		SEMITONE.getNotes().add(new ModularParameter(2));
		SEMITONE.getNotes().add(new ModularParameter(3));
		SEMITONE.getNotes().add(new ModularParameter(4));
		SEMITONE.getNotes().add(new ModularParameter(5));
		SEMITONE.getNotes().add(new ModularParameter(6));
		SEMITONE.getNotes().add(new ModularParameter(7));
		SEMITONE.getNotes().add(new ModularParameter(8));
		SEMITONE.getNotes().add(new ModularParameter(9));
		SEMITONE.getNotes().add(new ModularParameter(10));
		SEMITONE.getNotes().add(new ModularParameter(11));
		SEMITONE.setNumOctavesToPrint(2);
	}

	public static final SlonimskyBase DIAPENTE = new SlonimskyBase();
	static{
		DIAPENTE.setName("Diapente");
		DIAPENTE.getNotes().add(new ModularParameter(0));
		DIAPENTE.getNotes().add(new ModularParameter(7));//G
		DIAPENTE.getNotes().add(new ModularParameter(2));//D
		DIAPENTE.getNotes().add(new ModularParameter(9));//A
		DIAPENTE.getNotes().add(new ModularParameter(4));//E
		DIAPENTE.getNotes().add(new ModularParameter(11));//B
		DIAPENTE.getNotes().add(new ModularParameter(6));
		DIAPENTE.getNotes().add(new ModularParameter(1));
		DIAPENTE.getNotes().add(new ModularParameter(8));
		DIAPENTE.getNotes().add(new ModularParameter(3));
		DIAPENTE.getNotes().add(new ModularParameter(10));
		DIAPENTE.getNotes().add(new ModularParameter(5));//F
		DIAPENTE.setNumOctavesToPrint(1);
	}

	public static final SlonimskyBase DIATESSARON = new SlonimskyBase();
	static{
		DIATESSARON.setName("Diatessaron");
		DIATESSARON.getNotes().add(new ModularParameter(0));
		DIATESSARON.getNotes().add(new ModularParameter(5));//F
		DIATESSARON.getNotes().add(new ModularParameter(10));
		DIATESSARON.getNotes().add(new ModularParameter(3));
		DIATESSARON.getNotes().add(new ModularParameter(8));
		DIATESSARON.getNotes().add(new ModularParameter(1));
		DIATESSARON.getNotes().add(new ModularParameter(6));
		DIATESSARON.getNotes().add(new ModularParameter(11));//B
		DIATESSARON.getNotes().add(new ModularParameter(4));//E
		DIATESSARON.getNotes().add(new ModularParameter(9));//A
		DIATESSARON.getNotes().add(new ModularParameter(2));//D
		DIATESSARON.getNotes().add(new ModularParameter(7));//G
		DIATESSARON.setNumOctavesToPrint(1);
	}

	public static final SlonimskyBase SESQUIQUINQUETONE = new SlonimskyBase();
	static{
		SESQUIQUINQUETONE.setName("Sesquiquinquetone");
		SESQUIQUINQUETONE.getNotes().add(new ModularParameter(0));
		SESQUIQUINQUETONE.getNotes().add(new ModularParameter(11));
		SESQUIQUINQUETONE.getNotes().add(new ModularParameter(10));
		SESQUIQUINQUETONE.getNotes().add(new ModularParameter(9));
		SESQUIQUINQUETONE.getNotes().add(new ModularParameter(8));
		SESQUIQUINQUETONE.getNotes().add(new ModularParameter(7));
		SESQUIQUINQUETONE.getNotes().add(new ModularParameter(6));
		SESQUIQUINQUETONE.setNumOctavesToPrint(1);
	}

	// To facilitate iterating through all bases
	public static final ArrayList<SlonimskyBase> BASE_ARRAY = new ArrayList<SlonimskyBase>();
	static{
		BASE_ARRAY.add(TRITONE);
		BASE_ARRAY.add(SESQUITONE);
		BASE_ARRAY.add(SESQUIQUADRITONE);

		BASE_ARRAY.add(DITONE);
		BASE_ARRAY.add(QUADRITONE);
		
		BASE_ARRAY.add(WHOLETONE);
		BASE_ARRAY.add(QUINQUETONE);
		
		BASE_ARRAY.add(SEMITONE);
		BASE_ARRAY.add(DIAPENTE);
		BASE_ARRAY.add(DIATESSARON);
		BASE_ARRAY.add(SESQUIQUINQUETONE);
}
	
	/*
	 * 
	 * NON-SYMMETRICAL ARPEGGIO BASES
	 * 
	 */
	
	public static final SlonimskyBase NSA_MAJOR_TRIAD = new SlonimskyBase();
	static{
		NSA_MAJOR_TRIAD.setName("Major Triad");
		NSA_MAJOR_TRIAD.getNotes().add(new ModularParameter(0));
		NSA_MAJOR_TRIAD.getNotes().add(new ModularParameter(4));
		NSA_MAJOR_TRIAD.getNotes().add(new ModularParameter(7));
		NSA_MAJOR_TRIAD.setNumOctavesToPrint(2);
	}

	public static final SlonimskyBase NSA_MINOR_TRIAD = new SlonimskyBase();
	static{
		NSA_MINOR_TRIAD.setName("Minor Triad");
		NSA_MINOR_TRIAD.getNotes().add(new ModularParameter(0));
		NSA_MINOR_TRIAD.getNotes().add(new ModularParameter(3));
		NSA_MINOR_TRIAD.getNotes().add(new ModularParameter(7));
		NSA_MINOR_TRIAD.setNumOctavesToPrint(2);
	}

	public static final SlonimskyBase NSA_MAJOR_SEVENTH = new SlonimskyBase();
	static{
		NSA_MAJOR_SEVENTH.setName("Major Seventh");
		NSA_MAJOR_SEVENTH.getNotes().add(new ModularParameter(0));
		NSA_MAJOR_SEVENTH.getNotes().add(new ModularParameter(4));
		NSA_MAJOR_SEVENTH.getNotes().add(new ModularParameter(7));
		NSA_MAJOR_SEVENTH.getNotes().add(new ModularParameter(11));
		NSA_MAJOR_SEVENTH.setNumOctavesToPrint(2);
	}

	public static final SlonimskyBase NSA_MINOR_SEVENTH = new SlonimskyBase();
	static{
		NSA_MINOR_SEVENTH.setName("Minor Seventh");
		NSA_MINOR_SEVENTH.getNotes().add(new ModularParameter(0));
		NSA_MINOR_SEVENTH.getNotes().add(new ModularParameter(3));
		NSA_MINOR_SEVENTH.getNotes().add(new ModularParameter(7));
		NSA_MINOR_SEVENTH.setNumOctavesToPrint(2);
	}

	public static final SlonimskyBase NSA_HALF_DIMINISHED = new SlonimskyBase();
	static{
		NSA_HALF_DIMINISHED.setName("Half Diminished Arpeggio");
		NSA_HALF_DIMINISHED.getNotes().add(new ModularParameter(0));
		NSA_HALF_DIMINISHED.getNotes().add(new ModularParameter(3));
		NSA_HALF_DIMINISHED.getNotes().add(new ModularParameter(6));
		NSA_HALF_DIMINISHED.getNotes().add(new ModularParameter(10));
		NSA_HALF_DIMINISHED.setNumOctavesToPrint(2);
	}

	public static final SlonimskyBase NSA_COMMON_PENTATONIC = new SlonimskyBase();
	static{
		NSA_COMMON_PENTATONIC.setName("Common Pentatonic");
		NSA_COMMON_PENTATONIC.getNotes().add(new ModularParameter(0));
		NSA_COMMON_PENTATONIC.getNotes().add(new ModularParameter(2));
		NSA_COMMON_PENTATONIC.getNotes().add(new ModularParameter(4));
		NSA_COMMON_PENTATONIC.getNotes().add(new ModularParameter(7));
		NSA_COMMON_PENTATONIC.getNotes().add(new ModularParameter(9));
		NSA_COMMON_PENTATONIC.setNumOctavesToPrint(2);
	}

	public static final SlonimskyBase NSA_MAJOR_SCALE = new SlonimskyBase();
	static{
		NSA_MAJOR_SCALE.setName("Major Scale");
		NSA_MAJOR_SCALE.getNotes().add(new ModularParameter(0));
		NSA_MAJOR_SCALE.getNotes().add(new ModularParameter(2));
		NSA_MAJOR_SCALE.getNotes().add(new ModularParameter(4));
		NSA_MAJOR_SCALE.getNotes().add(new ModularParameter(5));
		NSA_MAJOR_SCALE.getNotes().add(new ModularParameter(7));
		NSA_MAJOR_SCALE.getNotes().add(new ModularParameter(9));
		NSA_MAJOR_SCALE.getNotes().add(new ModularParameter(11));
		NSA_MAJOR_SCALE.setNumOctavesToPrint(2);
	}

	public static final SlonimskyBase NSA_1MAJ_b2AUG_SCALE = new SlonimskyBase();
	static{
		NSA_1MAJ_b2AUG_SCALE.setName("$1^{\\text{maj}}$ + $\flat2^{\\text{aug}}$ Scale");
		NSA_1MAJ_b2AUG_SCALE.getNotes().add(new ModularParameter(0));
		NSA_1MAJ_b2AUG_SCALE.getNotes().add(new ModularParameter(1));
		NSA_1MAJ_b2AUG_SCALE.getNotes().add(new ModularParameter(4));
		NSA_1MAJ_b2AUG_SCALE.getNotes().add(new ModularParameter(5));
		NSA_1MAJ_b2AUG_SCALE.getNotes().add(new ModularParameter(8));
		NSA_1MAJ_b2AUG_SCALE.getNotes().add(new ModularParameter(9));
		NSA_1MAJ_b2AUG_SCALE.setNumOctavesToPrint(2);
	}

	public static final SlonimskyBase NSA_1MAJ_b2AUG_SPREAD_SCALE = new SlonimskyBase();
	static{
		NSA_1MAJ_b2AUG_SPREAD_SCALE.setName("$1^{\\text{maj}}$ + $\flat2^{\\text{aug}}$ Scale (Spread)");
		NSA_1MAJ_b2AUG_SPREAD_SCALE.getNotes().add(new ModularParameter(0));
		NSA_1MAJ_b2AUG_SPREAD_SCALE.getNotes().add(new ModularParameter(9));
		NSA_1MAJ_b2AUG_SPREAD_SCALE.getNotes().add(new ModularParameter(8));
		NSA_1MAJ_b2AUG_SPREAD_SCALE.getNotes().add(new ModularParameter(5));
		NSA_1MAJ_b2AUG_SPREAD_SCALE.getNotes().add(new ModularParameter(4));
		NSA_1MAJ_b2AUG_SPREAD_SCALE.getNotes().add(new ModularParameter(1));
		NSA_1MAJ_b2AUG_SPREAD_SCALE.setNumOctavesToPrint(2);
	}

	public static final SlonimskyBase NSA_4MAJ_7MAJ_SCALE = new SlonimskyBase();
	static{
		NSA_4MAJ_7MAJ_SCALE.setName("$4^{\\text{maj}}$ + $7^{\\text{maj}}$ Scale");
		NSA_4MAJ_7MAJ_SCALE.getNotes().add(new ModularParameter(0));
		NSA_4MAJ_7MAJ_SCALE.getNotes().add(new ModularParameter(3));
		NSA_4MAJ_7MAJ_SCALE.getNotes().add(new ModularParameter(5));
		NSA_4MAJ_7MAJ_SCALE.getNotes().add(new ModularParameter(6));
		NSA_4MAJ_7MAJ_SCALE.getNotes().add(new ModularParameter(9));
		NSA_4MAJ_7MAJ_SCALE.getNotes().add(new ModularParameter(11));
		NSA_4MAJ_7MAJ_SCALE.setNumOctavesToPrint(2);
	}

	public static final SlonimskyBase NSA_4MAJ_7MAJ_SCALE_SPREAD = new SlonimskyBase();
	static{
		NSA_4MAJ_7MAJ_SCALE_SPREAD.setName("$4^{\\text{maj}}$ + $7^{\\text{maj}}$ Scale (Spread)");
		NSA_4MAJ_7MAJ_SCALE_SPREAD.getNotes().add(new ModularParameter(0));
		NSA_4MAJ_7MAJ_SCALE_SPREAD.getNotes().add(new ModularParameter(5));
		NSA_4MAJ_7MAJ_SCALE_SPREAD.getNotes().add(new ModularParameter(9));
		NSA_4MAJ_7MAJ_SCALE_SPREAD.getNotes().add(new ModularParameter(3));
		NSA_4MAJ_7MAJ_SCALE_SPREAD.getNotes().add(new ModularParameter(6));
		NSA_4MAJ_7MAJ_SCALE_SPREAD.getNotes().add(new ModularParameter(11));
		NSA_4MAJ_7MAJ_SCALE_SPREAD.setNumOctavesToPrint(2);
	}

	public static final SlonimskyBase NSA_1MIN_b5MIN_SCALE = new SlonimskyBase();
	static{
		NSA_1MIN_b5MIN_SCALE.setName("$1^{\\text{min}}$ + $\\flat5^{\\text{min}}$ Scale");
		NSA_1MIN_b5MIN_SCALE.getNotes().add(new ModularParameter(0));
		NSA_1MIN_b5MIN_SCALE.getNotes().add(new ModularParameter(1));
		NSA_1MIN_b5MIN_SCALE.getNotes().add(new ModularParameter(3));
		NSA_1MIN_b5MIN_SCALE.getNotes().add(new ModularParameter(6));
		NSA_1MIN_b5MIN_SCALE.getNotes().add(new ModularParameter(7));
		NSA_1MIN_b5MIN_SCALE.getNotes().add(new ModularParameter(9));
		NSA_1MIN_b5MIN_SCALE.setNumOctavesToPrint(2);
	}

	public static final SlonimskyBase NSA_1MIN_b5MIN_SCALE_SPREAD = new SlonimskyBase();
	static{
		NSA_1MIN_b5MIN_SCALE_SPREAD.setName("$1^{\\text{min}}$ + $\\flat5^{\\text{min}}$ Scale (Spread)");
		NSA_1MIN_b5MIN_SCALE_SPREAD.getNotes().add(new ModularParameter(0));
		NSA_1MIN_b5MIN_SCALE_SPREAD.getNotes().add(new ModularParameter(3));
		NSA_1MIN_b5MIN_SCALE_SPREAD.getNotes().add(new ModularParameter(1));
		NSA_1MIN_b5MIN_SCALE_SPREAD.getNotes().add(new ModularParameter(6));
		NSA_1MIN_b5MIN_SCALE_SPREAD.getNotes().add(new ModularParameter(9));
		NSA_1MIN_b5MIN_SCALE_SPREAD.getNotes().add(new ModularParameter(7));
		NSA_1MIN_b5MIN_SCALE_SPREAD.setNumOctavesToPrint(2);
	}

	public static final SlonimskyBase NSA_7b5_ARPEGGIO = new SlonimskyBase();
	static{
		NSA_7b5_ARPEGGIO.setName("Dominant 7 $\\flat$5 Arpeggio");
		NSA_7b5_ARPEGGIO.getNotes().add(new ModularParameter(0));
		NSA_7b5_ARPEGGIO.getNotes().add(new ModularParameter(4));
		NSA_7b5_ARPEGGIO.getNotes().add(new ModularParameter(6));
		NSA_7b5_ARPEGGIO.getNotes().add(new ModularParameter(10));
		NSA_7b5_ARPEGGIO.setNumOctavesToPrint(2);
	}

	// To facilitate iterating through all bases
	public static final ArrayList<SlonimskyBase> NSA_BASE_ARRAY = new ArrayList<SlonimskyBase>();
	static{
		NSA_BASE_ARRAY.add(NSA_MAJOR_TRIAD);
		NSA_BASE_ARRAY.add(NSA_MINOR_TRIAD);
		NSA_BASE_ARRAY.add(NSA_MINOR_SEVENTH);
		NSA_BASE_ARRAY.add(NSA_MAJOR_SEVENTH);
		NSA_BASE_ARRAY.add(NSA_HALF_DIMINISHED);
		NSA_BASE_ARRAY.add(NSA_COMMON_PENTATONIC);
		NSA_BASE_ARRAY.add(NSA_MAJOR_SCALE);
/*		NSA_BASE_ARRAY.add(NSA_1MAJ_b2AUG_SCALE);
		NSA_BASE_ARRAY.add(NSA_1MAJ_b2AUG_SPREAD_SCALE);
		NSA_BASE_ARRAY.add(NSA_4MAJ_7MAJ_SCALE);
		NSA_BASE_ARRAY.add(NSA_4MAJ_7MAJ_SCALE_SPREAD);
		NSA_BASE_ARRAY.add(NSA_1MIN_b5MIN_SCALE);
		NSA_BASE_ARRAY.add(NSA_1MAJ_b2AUG_SCALE);
		NSA_BASE_ARRAY.add(NSA_1MIN_b5MIN_SCALE_SPREAD);
		NSA_BASE_ARRAY.add(NSA_7b5_ARPEGGIO);*/
}
	
}
