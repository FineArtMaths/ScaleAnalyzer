package com.calnrich.fortefy.core.renderers;

import com.calnrich.fortefy.core.note.NoteEvent;
import com.calnrich.fortefy.core.note.NoteEventSet;

import java.text.NumberFormat;
import java.util.Vector;
import java.util.Enumeration;
import java.util.Collections;
import java.util.Comparator;

/*
 * Test this using TestSeries.java
 * 
 * It currently produces valid MusicXML with the following bugs:
 *   Ties between notes don't work yet
 *   Time signatures only need to be set when they change
 *   Barlines aren't shown in Sibelius
 * 
 */

public final class MusicXMLRenderer extends AbstractRenderer {

	public MusicXMLRenderer(){
		super();
	}

	public MusicXMLRenderer(NoteEventSet objNoteEventSet){
		super(objNoteEventSet);
	}

	public String Render(){

		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(WriteHeader());

		int iCurrentBar = 1;
		int iCurrentPitch = 0;
		NoteEvent objNoteEvent = null;
		int iNumBarsRemaining = 100; // obviously making this larger gives a longer score
		
		while(iNumBarsRemaining > 0){
			
			// Create a new measure. [in future: randomly change these values sometimes]
			int iTimeSigTop = 4;
			int iTimeSigBottom = 4;
			int iTickLength = 8;
			int iNumTicksInBar = iTickLength * iTimeSigTop / (4 / iTimeSigBottom); // the 4 here refers to the standard quarter note
			strBuffer.append(StartMeasure(iCurrentBar, iTimeSigTop, iTimeSigBottom, iTickLength));
			
			while(iNumTicksInBar > 0){
				// This will tend to produce longer notes at the starts of bars and shorter ones at the end... 
				// we may need to put these in an array and shuffle them to create more variety.
				int iNumTicksInNote = Math.round((float)(Math.random() * (iNumTicksInBar - 1))) + 1;
				iNumTicksInBar -= iNumTicksInNote;
				objNoteEvent = m_objNoteEventSet.GetNoteEvent(iCurrentPitch);
				// print it
				strBuffer.append(WriteNote(objNoteEvent, iNumTicksInNote, iTimeSigBottom, 0));
				
				iCurrentPitch++;
				if (iCurrentPitch >= m_objNoteEventSet.size()){
					iCurrentPitch = 0;
				}
			}

			strBuffer.append(EndMeasure());
			iCurrentBar++;
			iNumBarsRemaining--;
		}
		

		strBuffer.append(WriteFooter());

		return strBuffer.toString();

	}

	private String WriteHeader(){
		StringBuffer objOut = new StringBuffer();
		objOut.append("<?xml version='1.0' encoding='UTF-8' standalone='no'?>\n");
		objOut.append("<!DOCTYPE score-partwise PUBLIC\n");
		objOut.append("    '-//Recordare//DTD MusicXML 3.0 Partwise//EN'\n");
		objOut.append("    'http://www.musicxml.org/dtds/partwise.dtd'>\n");
		objOut.append("<score-partwise version='3.0'>\n");
		objOut.append("  <part-list>\n");
		objOut.append("      <score-part id='P1'>\n");
		objOut.append("      <part-name>Music</part-name>\n");
		objOut.append("    </score-part>\n");
		objOut.append("  </part-list>\n");
		objOut.append("  <part id='P1'>\n");
		return objOut.toString();
	}

	private String WriteFooter(){
		StringBuffer objOut = new StringBuffer();
		objOut.append("    </part>");
		objOut.append("  </score-partwise>");
		return objOut.toString();
	}
	
	private String StartMeasure(int iMeasureNum, int iTimeSigTop, int iTimeSigBottom, int iMXMLDivision){
		StringBuffer objOut = new StringBuffer();
		objOut.append("    <measure number='" + iMeasureNum + "'>\n");
		objOut.append("    <attributes>\n");
		objOut.append("      <divisions>" + iMXMLDivision + "</divisions>\n");
		objOut.append("      <key>\n");
		objOut.append("        <fifths>0</fifths>\n");
		objOut.append("      </key>\n");
		objOut.append("      <time>\n");
		objOut.append("        <beats>" + iTimeSigTop + "</beats>\n");
		objOut.append("        <beat-type>" + iTimeSigBottom + "</beat-type>\n");
		objOut.append("      </time>\n");
		objOut.append("      <clef>\n");
		objOut.append("        <sign>G</sign>\n");
		objOut.append("        <line>2</line>\n");
		objOut.append("      </clef>\n");
		objOut.append("    </attributes>\n");
		return objOut.toString();
	}
	
	private String EndMeasure(){
		return "  </measure>\n";
	}
	
	private String WriteNote(NoteEvent objNoteEvent, int iMxmlDuration, int iTimeSigBottom, int iTieType){
		
		// If the duration isn't a standard one, create tied notes as necessary.
		// This is quite a tedious thing to have to do, but I think it's needed for Sibelius
		if(iMxmlDuration != 1
			&& iMxmlDuration != 2
			&& iMxmlDuration != 4
			&& iMxmlDuration != 8
			&& iMxmlDuration != 16
			&& iMxmlDuration != 32
				){
			int i = 0;
			while(Math.pow(2, i) < iMxmlDuration){
				i++;
			}
			i--;
			return WriteNote(objNoteEvent, (int)Math.round(Math.pow(2, i)), iTimeSigBottom, 1) + 
					WriteNote(objNoteEvent, iMxmlDuration - (int)Math.round(Math.pow(2, i)), iTimeSigBottom, 2);
		}
			
		
		
		// It's a standard duration, so write it.
		
		StringBuffer objOut = new StringBuffer();
		objOut.append("        <note>\n");
		objOut.append("          <pitch>\n");
		objOut.append("            <step>" + objNoteEvent.GetMXMLName() + "</step>\n");
		if(!"".equals(objNoteEvent.GetMXMLAlterElement())){
			objOut.append("            <alter>" + objNoteEvent.GetMXMLAlterElement() + "</alter>\n");
		}
		objOut.append("            <octave>" + objNoteEvent.GetRegister() + "</octave>\n");
		objOut.append("          </pitch>\n");
		objOut.append("          <duration>" + iMxmlDuration +  "</duration>\n");
		if(1 == iTieType){
			objOut.append("          <tie type='stop'/>\n");
			objOut.append("          <tie type='start'/>\n");
		} else if(2 == iTieType){
			objOut.append("          <tie type='stop'/>\n");
		}

		objOut.append("          <type>" + getDurationType(iMxmlDuration, iTimeSigBottom)  +  "</type>\n");
		objOut.append("        </note>\n");
		return objOut.toString();
	}

	private String getDurationType(int iMxmlDuration, int iTimeSigBottom) {
		return m_strDurationTypes[iMxmlDuration];
	}
	
	private static String[] m_strDurationTypes  = {"", "32nd", "16th", "16th", "eighth", "eighth", "eighth", "eighth", 
		"quarter", "quarter", "quarter", "quarter", "quarter", "quarter", "quarter", "quarter", 
		"half", "half", "half", "half", "half", "half", "half", "half", "half", "half", "half", "half", "half", "half", "half", "half", "whole"};


}

