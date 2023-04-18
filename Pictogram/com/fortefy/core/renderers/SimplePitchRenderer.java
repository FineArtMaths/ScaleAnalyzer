package com.calnrich.fortefy.core.renderers;

import com.calnrich.fortefy.core.note.NoteEvent;
import com.calnrich.fortefy.core.note.NoteEventSet;

import java.util.Vector;
import java.util.Enumeration;
import java.util.Collections;
import java.util.Comparator;

public class SimplePitchRenderer extends SimplePitchClassRenderer {

	public SimplePitchRenderer(){
		super();
	}

	public SimplePitchRenderer(NoteEventSet objNoteEventSet){
		super(objNoteEventSet);
	}

	public String Render(){
		StringBuffer strBuffer = new StringBuffer();
		NoteEvent objNoteEvent = null;
		for (int i = 0; i < m_objNoteEventSet.size(); i++){
			objNoteEvent = m_objNoteEventSet.GetNoteEvent(i);
			strBuffer.append( objNoteEvent.GetPitchClassName(true) );
			strBuffer.append( " " );
		}
		return strBuffer.toString();
	}

}

