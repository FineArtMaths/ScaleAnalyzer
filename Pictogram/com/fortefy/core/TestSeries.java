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

public final class TestSeries {

	public static void main(String[] args){

		NoteEventSet objBaseSeries = new NoteEventSet();
		objBaseSeries.AddPitchClass(0);
		objBaseSeries.AddPitchClass(2);
		objBaseSeries.AddPitchClass(6);
		objBaseSeries.AddPitchClass(1);
		objBaseSeries.AddPitchClass(8);
		objBaseSeries.AddPitchClass(7);
		objBaseSeries.AddPitchClass(11);
		objBaseSeries.AddPitchClass(10);
		objBaseSeries.AddPitchClass(3);
		objBaseSeries.AddPitchClass(5);
		objBaseSeries.AddPitchClass(9);
		objBaseSeries.AddPitchClass(4);

		NoteEventSet objBaseSeriesInverted = new NoteEventSet(objBaseSeries);
		objBaseSeriesInverted.Invert();

		NoteEventSet objBaseSeriesRetrograde = new NoteEventSet(objBaseSeries);
		objBaseSeriesInverted.Retrograde();

		NoteEventSet objBaseSeriesInvertedRetrograde = new NoteEventSet(objBaseSeriesInverted);
		objBaseSeriesInvertedRetrograde.Retrograde();

		NoteEventSet objFullSeries = new NoteEventSet();
		objFullSeries.Append(objBaseSeries);
		objFullSeries.Append(objBaseSeriesInverted);
		objFullSeries.Append(objBaseSeriesRetrograde);
		objFullSeries.Append(objBaseSeriesInvertedRetrograde);

		NoteEventSet objTranspositions = new NoteEventSet();
		objTranspositions.AddPitch(0, 0);
		objTranspositions.AddPitch(11, -1);
		objTranspositions.AddPitch(0, 0);
		objTranspositions.AddPitch(3, 0);
		objTranspositions.AddPitch(6, 0);

		SlonimskyProcessor objSP = new SlonimskyProcessor(objTranspositions, objFullSeries);
		NoteEventSet objSlResult = objSP.Process();

		SimpleTimePointProcessor objTPP = new SimpleTimePointProcessor(objFullSeries);
		NoteEventSet objNewTP = objTPP.Process();

		MusicXMLRenderer objMR = new MusicXMLRenderer(objNewTP);

		try {
			FileWriter fosFile = new FileWriter("C:\\java\\src\\com\\calnrich\\fortefy\\output\\testSeries.xml");
			fosFile.write(objMR.Render());
			fosFile.close();
		} catch (Exception e){
			e.printStackTrace();
		}

	}

}

