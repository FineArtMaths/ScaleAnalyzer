package com.calnrich.fortefy.core;

import com.calnrich.fortefy.core.note.NoteEventSet;
import com.calnrich.fortefy.core.processors.*;
import com.calnrich.fortefy.core.renderers.*;

import java.util.Vector;
import java.util.Enumeration;

import java.io.FileWriter;

/*
Create CSFactory objects which take eg names, Forte numbers etc
GUI: Get/create NoteEventSets
	 Chain processors & feed in NoteEventSets
	 View output in different formats (incl graphical?)
*/

public final class Testharness {

	public static void main(String[] args){

		NoteEventSet objTestNoteEventSet = new NoteEventSet();
		objTestNoteEventSet.AddPitchClass(0);
		objTestNoteEventSet.AddPitchClass(2);
		objTestNoteEventSet.AddPitchClass(4);
		objTestNoteEventSet.AddPitchClass(5);
		objTestNoteEventSet.AddPitchClass(7);
		objTestNoteEventSet.AddPitchClass(9);
		objTestNoteEventSet.AddPitchClass(11);

		System.out.println("PCString:");
		System.out.println(objTestNoteEventSet.GetPCString(true));

		System.out.println("CTString:");
		System.out.println(objTestNoteEventSet.GetCTString());

		System.out.println("Shifting by 13:");
		objTestNoteEventSet.ShiftUp(13);
		System.out.println(objTestNoteEventSet.GetPCString(true));

		System.out.println("Shifting by -13:");
		objTestNoteEventSet.ShiftDown(13);
		System.out.println(objTestNoteEventSet.GetPCString(true));

		System.out.println("Testing rotate:");
		objTestNoteEventSet.RotateForwards();
		System.out.println(objTestNoteEventSet.GetPCString(true));

		System.out.println("Testing rotate again:");
		objTestNoteEventSet.RotateForwards();
		System.out.println(objTestNoteEventSet.GetPCString(true));

		System.out.println("Testing RotateBackwards:");
		objTestNoteEventSet.RotateBackwards();
		System.out.println(objTestNoteEventSet.GetPCString(true));

		System.out.println("Testing RotateBackwards again:");
		objTestNoteEventSet.RotateBackwards();
		System.out.println(objTestNoteEventSet.GetPCString(true));

		System.out.println("Testing Invert:");
		objTestNoteEventSet.Invert();
		System.out.println(objTestNoteEventSet.GetPCString(true));

		System.out.println("Testing Invert again:");
		objTestNoteEventSet.Invert();
		System.out.println(objTestNoteEventSet.GetPCString(true));

		System.out.println("Testing Retrograde:");
		objTestNoteEventSet.Retrograde();
		System.out.println(objTestNoteEventSet.GetPCString(true));

		System.out.println("Testing Retrograde again:");
		objTestNoteEventSet.Retrograde();
		System.out.println(objTestNoteEventSet.GetPCString(true));

		System.out.println("Testing a Slonimsky process:");

		NoteEventSet objCell = new NoteEventSet();
		objCell.AddPitch(0, 0);
		objCell.AddPitch(11, -1);
		objCell.AddPitch(0, 0);
		objCell.AddPitch(3, 0);
		objCell.AddPitch(6, 0);

		NoteEventSet objPoints = new NoteEventSet();
		objPoints.AddPitch(0, 4);
		for (int i=0; i<5; i++){
			System.out.println("*** objPoints = " + objPoints.GetPCString(true));
			objPoints.AddIntervalAbove(4);
		}

		//System.out.println("*** objCell = " + objCell.GetPCString(true));
		System.out.println("*** objPoints = " + objPoints.GetPCString(true));
		SlonimskyProcessor objSP = new SlonimskyProcessor(objPoints, objCell);
		NoteEventSet objSlResult = objSP.Process();

		System.out.println("*** " + objSlResult.GetPCString(true));

		System.out.println("");
		System.out.println("");

		System.out.println("Testing Sort:");
		objSlResult.SortByPCAndScopeAscending();
		System.out.println(objSlResult.GetPCString(true));

		System.out.println("Removing duplicates:");
		objSlResult.RemConsecPCScopeDups();
		System.out.println(objSlResult.GetPCString(true));

		System.out.println("Testing Sort without scope:");
		objSlResult.SortByPCAscending();
		System.out.println(objSlResult.GetPCString(true));

		System.out.println("Removing duplicates agin:");
		objSlResult.RemConsecPCDups();
		System.out.println(objSlResult.GetPCString(true));

		System.out.println("Quantizing the old class to this new one:");
		SerialQuantProcessor objSQProcess = new SerialQuantProcessor(objTestNoteEventSet, objSlResult);
		NoteEventSet objSQResult = objSQProcess.Process();
		System.out.println(objSQResult.GetPCString(true));


		System.out.println("Testing a SequenceProcess:");

		NoteEventSet objNewCell = new NoteEventSet();
		objNewCell.AddPitch(0, 1);
		objNewCell.AddPitch(11, 0);
		objNewCell.AddPitch(0, 1);
		objNewCell.AddPitch(2, 1);
		objNewCell.AddPitch(4, 1);
		System.out.println("Cell: " + objNewCell.GetPCString(true));

		NoteEventSet objNewPoints = new NoteEventSet();
		objNewPoints.AddPitch(0, 0);
		objNewPoints.AddPitch(2, 0);
		objNewPoints.AddPitch(4, 0);
		objNewPoints.AddPitch(5, 0);
		objNewPoints.AddPitch(7, 0);
		objNewPoints.AddPitch(9, 0);
		objNewPoints.AddPitch(11, 0);
		System.out.println("Points: " + objNewPoints.GetPCString(true));

		SequenceProcessor objSeqProc = new SequenceProcessor(objNewPoints, objNewCell);
		NoteEventSet objSPResult = objSeqProc.Process();
		System.out.println(objSPResult.GetPCString(true));

		objNewCell.AddPitch(3, 1);
		objNewCell.AddPitch(8, 0);
		objNewCell.AddPitch(9, 1);
		objNewCell.AddPitch(10, 0);
		objNewCell.AddPitch(1, 1);

		SimpleTimePointProcessor objTPP = new SimpleTimePointProcessor(objSPResult);
		NoteEventSet objNewTP = objTPP.Process();
		System.out.println("Time-Point Processed: \n" + objNewTP.GetPCString(true, true));

		MIDIRenderer objMR = new MIDIRenderer(objNewTP);
		//System.out.println();
		try {
			FileWriter fosFile = new FileWriter("C:\\java\\src\\com\\calnrich\\fortefy\\output\\testHarness.txt");
			fosFile.write(objMR.Render());
			fosFile.close();
		} catch (Exception e){
			e.printStackTrace();
		}

	}

}

