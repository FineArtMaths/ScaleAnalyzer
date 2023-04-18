package com.calnrich.fortefy.core.processors;

import com.calnrich.fortefy.core.note.NoteEventSet;
import com.calnrich.fortefy.core.note.NoteEvent;

public final class SerialQuantProcessor extends AbstractNoteEventProcessor {

	public SerialQuantProcessor() {
		super();
	}

	public SerialQuantProcessor(NoteEventSet objFirst) {
		super(objFirst);
	}

	public SerialQuantProcessor(NoteEventSet objFirst, NoteEventSet objSecond) {
		super(objFirst, objSecond);
	}

	public NoteEventSet Process() {

		NoteEventSet objQuant = new NoteEventSet();
		int iSeriesItem = 0;
		int iSeriesLength = m_objSecond.size() - 1;
		int iRegister = 0;
		int iCurrentPCNum = 0;
		int iNewPCNum = 0;

		NoteEvent objCurrentSeriesItem;
		for (int i = 0; i < m_objFirst.size(); i++){
			objCurrentSeriesItem = m_objSecond.GetNoteEvent(iSeriesItem);
			iRegister = m_objFirst.GetNoteEvent(i).GetRegister();
			iCurrentPCNum = m_objFirst.GetNoteEvent(i).GetPitchClass();
			iNewPCNum = objCurrentSeriesItem.GetPitchClass();
			if (iNewPCNum - iCurrentPCNum > 6){
				--iRegister;
			}
			if (iCurrentPCNum - iNewPCNum > 6){
				++iRegister;
			}
			objQuant.Append(new NoteEvent(iNewPCNum, iRegister));
			++iSeriesItem;
			if (iSeriesItem > iSeriesLength) {
				iSeriesItem = 0;
			}
		}

		return objQuant;
	}

	public String GetFirstName(){
		return "Source";
	}

	public String GetFirstDescription(){
		return "The source material to be quantised";
	}

	public String GetSecondName(){
		return "Series";
	}

	public String GetSecondDescription(){
		return "The series to which the source will be quantised";
	}

}