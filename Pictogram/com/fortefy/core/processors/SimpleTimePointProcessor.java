package com.calnrich.fortefy.core.processors;

import com.calnrich.fortefy.core.note.NoteEventSet;
import com.calnrich.fortefy.core.note.NoteEvent;

public final class SimpleTimePointProcessor extends AbstractNoteEventProcessor {

	public SimpleTimePointProcessor() {
		super();
		m_iQuantum = 64;
	}

	public SimpleTimePointProcessor(NoteEventSet objFirst) {
		super(objFirst);
		m_iQuantum = 64;
	}

	public SimpleTimePointProcessor(NoteEventSet objFirst, NoteEventSet objSecond) {
		super(objFirst, objSecond);
		m_iQuantum = 64;
	}

	public void SetQuantumDuration(int iQuantum){
		m_iQuantum = iQuantum;
	}

	public NoteEventSet Process() {

		NoteEventSet objTimePoints = new NoteEventSet(m_objFirst);
		int iDuration = 0;
		int iCurrentPC = -1;
		int iNextPC = 0;
		int iNextTimePoint = 0;
		int iTimePoint = 0;
		int iTPRegister = 0;
		for (int i = 0; i < objTimePoints.size() - 1; i++){

			if (iCurrentPC == -1){
				iCurrentPC = objTimePoints.GetNoteEvent(i).GetPitchClass();
			}
			iNextPC = objTimePoints.GetNoteEvent(i + 1).GetPitchClass();

			if (iNextPC < iCurrentPC){
				++iTPRegister;
			}


			iNextTimePoint = (12 * iTPRegister) + iNextPC;
			iDuration = (iNextTimePoint - iTimePoint) * m_iQuantum;

//System.out.println("iDuration = " + iDuration + ", iNextTimePoint = " + iNextTimePoint + ", iNextPC = " + iNextPC + ", iCurrentPC = " + iCurrentPC + ", iTPRegister = "+ iTPRegister);

			objTimePoints.GetNoteEvent(i).SetDuration(iDuration);
			iCurrentPC = iNextPC;
			iTimePoint = iNextTimePoint;
		}

		return objTimePoints;
	}

	public String GetFirstName(){
		return "Source";
	}

	public String GetFirstDescription(){
		return "The pitch classes will be used to produce a series of durations in which the time interval between adjacent notes is proportional to the pitch interval between them.";
	}

	public String GetSecondName(){
		return "Not used";
	}

	public String GetSecondDescription(){
		return "";
	}

	private int m_iQuantum;

}