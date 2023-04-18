package com.calnrich.fortefy.core.processors;

import com.calnrich.fortefy.core.note.NoteEventSet;

public abstract class AbstractNoteEventProcessor implements Processor{

	AbstractNoteEventProcessor(){
		m_objFirst = new NoteEventSet();
		m_objSecond = new NoteEventSet();
	}

	AbstractNoteEventProcessor(NoteEventSet objFirst){
		m_objFirst = objFirst;
		m_objSecond = new NoteEventSet();
	}

	AbstractNoteEventProcessor(NoteEventSet objFirst, NoteEventSet objSecond){
		m_objFirst = objFirst;
		m_objSecond = objSecond;
	}

	public void SetFirst(NoteEventSet objFirst){
		m_objFirst = objFirst;
	}

	public void SetSecond(NoteEventSet objSecond){
		m_objSecond = objSecond;
	}

	public abstract String GetFirstName();
	public abstract String GetFirstDescription();
	public abstract String GetSecondName();
	public abstract String GetSecondDescription();

	public abstract NoteEventSet Process();

	protected NoteEventSet m_objFirst;
	protected NoteEventSet m_objSecond;
}