package com.calnrich.fortefy.core.renderers;

import com.calnrich.fortefy.core.note.NoteEvent;
import com.calnrich.fortefy.core.note.NoteEventSet;

import java.util.Vector;
import java.util.Enumeration;
import java.util.Collections;
import java.util.Comparator;

public abstract class SimplePitchClassRenderer extends AbstractRenderer {

	public SimplePitchClassRenderer(){
		super();
	}

	public SimplePitchClassRenderer(NoteEventSet objNoteEventSet){
		super(objNoteEventSet);
	}

	public String Render(){
		StringBuffer strBuffer = new StringBuffer();
		NoteEvent objNoteEvent = null;
		for (int i = 0; i < m_objNoteEventSet.size(); i++){
			objNoteEvent = m_objNoteEventSet.GetNoteEvent(i); // #TODO: replace with local function
			strBuffer.append( objNoteEvent.GetPitchClassName(false) );
			strBuffer.append( " " );
		}
		return strBuffer.toString();
	}

	protected static String[] m_strPCNames = {"C", "C#", "D", "Eb", "E", "F", "F#", "G", "G#", "A", "Bb", "B"};
}

