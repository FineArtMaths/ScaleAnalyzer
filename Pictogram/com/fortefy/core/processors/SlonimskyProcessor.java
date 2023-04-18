package com.calnrich.fortefy.core.processors;

import com.calnrich.fortefy.core.note.NoteEventSet;
import com.calnrich.fortefy.core.note.NoteEvent;

public final class SlonimskyProcessor extends AbstractNoteEventProcessor {

	public SlonimskyProcessor() {
		super();
	}

	public SlonimskyProcessor(NoteEventSet objFirst) {
		super(objFirst);
	}

	public SlonimskyProcessor(NoteEventSet objFirst, NoteEventSet objSecond) {
		super(objFirst, objSecond);
	}

	public NoteEventSet Process() {

		NoteEventSet objSlonimsky = new NoteEventSet();
		int iShift;

		for (int i = 0; i < m_objFirst.size(); i++){
			iShift = m_objFirst.GetNoteEvent(i).GetPitchClass();
			iShift += m_objFirst.GetNoteEvent(i).GetRegister() * 12;
			m_objSecond.ShiftUp( iShift );
			objSlonimsky.Append(m_objSecond);
			m_objSecond.ShiftDown( iShift );
		}

		return objSlonimsky;
	}

	public String GetFirstName(){
		return "Source";
	}

	public String GetFirstDescription(){
		return "Each element of this NoteEventSet will be used as a point of transposition for the cell";
	}

	public String GetSecondName(){
		return "Cell";
	}

	public String GetSecondDescription(){
		return "The cell to be processed";
	}

}