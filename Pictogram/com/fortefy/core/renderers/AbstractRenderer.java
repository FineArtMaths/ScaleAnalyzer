package com.calnrich.fortefy.core.renderers;

import com.calnrich.fortefy.core.note.NoteEvent;
import com.calnrich.fortefy.core.note.NoteEventSet;

import java.util.Vector;
import java.util.Enumeration;
import java.util.Collections;
import java.util.Comparator;

public abstract class AbstractRenderer {

	public AbstractRenderer(){
		m_objNoteEventSet = new NoteEventSet();
	}

	public AbstractRenderer(NoteEventSet objNoteEventSet){
		m_objNoteEventSet = objNoteEventSet;
	}

// Accessors

	public void SetNoteEventSet(NoteEventSet objNoteEventSet){
		m_objNoteEventSet = new NoteEventSet();
		m_objNoteEventSet.Append(objNoteEventSet);
	}

	public abstract String Render();

	protected NoteEventSet m_objNoteEventSet;

}

