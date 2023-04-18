package com.calnrich.fortefy.core.renderers;

import com.calnrich.fortefy.core.note.NoteEvent;
import com.calnrich.fortefy.core.note.NoteEventSet;

import java.util.Vector;
import java.util.Enumeration;
import java.util.Collections;
import java.util.Comparator;

public final class MIDIRenderer extends AbstractRenderer {

	public MIDIRenderer(){
		super();
		m_iTicksPerBar = 256;
	}

	public MIDIRenderer(NoteEventSet objNoteEventSet){
		super(objNoteEventSet);
		m_iTicksPerBar = 256;
	}

	public void SetTicksPerBar(int iTicks){
		m_iTicksPerBar = iTicks;
	}

	public String Render(){

		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(WriteHeader());

		int iCurrentBar = 1;
		int iCurrentBeat = 1;
		int iCurrentTimePoint = 0;
		String strPCName = "";
		NoteEvent objNoteEvent = null;

		for (int i = 0; i < m_objNoteEventSet.size(); i++){
			objNoteEvent = m_objNoteEventSet.GetNoteEvent(i);
			strPCName = objNoteEvent.GetPitchClassName(true);
			strBuffer.append("   " + iCurrentBar + ": " + iCurrentBeat + ":" + iCurrentTimePoint + " |On Note	 | chan= 1   | pitch="  + strPCName + "	 | vol=64 \n");
			iCurrentTimePoint += objNoteEvent.GetDuration();
			if (iCurrentTimePoint > m_iTicksPerBar){
				System.out.println("iCurrentTimePoint > m_iTicksPerBar (" + iCurrentTimePoint + " > " + m_iTicksPerBar + "), so:");
				iCurrentBeat += Math.floor(iCurrentTimePoint / m_iTicksPerBar);
				iCurrentTimePoint = iCurrentTimePoint % m_iTicksPerBar;
				System.out.println("iCurrentBeat now = " + iCurrentBeat + ", iCurrentTimePoint=" + iCurrentTimePoint);
				if (iCurrentBeat > 4){
					iCurrentBar += Math.floor(iCurrentBeat / 4);
					iCurrentBeat = iCurrentBeat % 4;
				}
			}
			strBuffer.append("   " + iCurrentBar + ": " + iCurrentBeat + ":" + iCurrentTimePoint + " |(Off) Note  | chan= 1   | pitch="  + strPCName + "\n");
		}

		++iCurrentBar;
		strBuffer.append(iCurrentBar + ": " + iCurrentBeat + ": 1 |End of track|\n");

		return strBuffer.toString();

	}

	private String WriteHeader(){
		StringBuffer objOut = new StringBuffer();
		objOut.append("MThd | Format=1 | # of Tracks=2 | Division=256\n");
		objOut.append("\n");
		objOut.append("Track #0 ******************************************\n");
		objOut.append("Time Event\n");
		objOut.append("1: 1: 0 |Time Sig | 4/4 | MIDI-clocks\\click=24 | 32nds\\quarter=8\n");
		objOut.append("|Key Sig | C Major |\n");
		objOut.append("1 |End of track| \n");
		objOut.append("\n");
		objOut.append("Track #1 ******************************************\n");
		objOut.append("Time Event\n");
		objOut.append("1: 1: 0 |Controller | chan= 1 | contr=ContlOff | value= 0\n");
		objOut.append("|Controller | chan= 1 | contr=Effects | value= 60\n");
		objOut.append("|Controller | chan= 1 | contr=Pan H | value= 51\n");
		objOut.append("|Controller | chan= 1 | contr=Volume H | value=100\n");
		objOut.append("|Controller | chan= 1 | contr=ContlOff | value= 0\n");
		objOut.append("|Controller | chan= 1 | contr=Effects | value= 60\n");
		objOut.append("|Controller | chan= 1 | contr=Pan H | value= 51\n");
		objOut.append("|Controller | chan= 1 | contr=Volume H | value=100\n");
		objOut.append("|Track Name | len=5 |\n");
		objOut.append("0x50 0x69 0x61 0x6E 0x6F \n");
		objOut.append("|Program | chan= 1 | pgm #= 1 Grand Piano \n");
		return objOut.toString();
	}

	private int m_iTicksPerBar;

}

