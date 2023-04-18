package com.calnrich.fortefy.core.processors;

import com.calnrich.fortefy.core.note.NoteEventSet;
import com.calnrich.fortefy.core.note.NoteEvent;
import java.util.Vector;

public final class SequenceProcessor extends AbstractNoteEventProcessor {

	public SequenceProcessor(){
		super();
		m_iSequenceJump = 1;
		m_iSequenceCount = -1;
		boolSequenceCountGrows = true;
	}

	public SequenceProcessor(NoteEventSet objFirst){
		super(objFirst);
		m_iSequenceJump = 1;
		m_iSequenceCount = m_objFirst.size();
		boolSequenceCountGrows = false;
	}

	public SequenceProcessor(NoteEventSet objFirst, NoteEventSet objSecond){
		super(objFirst, objSecond);
		m_iSequenceJump = 1;
		m_iSequenceCount = m_objFirst.size();
		boolSequenceCountGrows = false;
	}

	public void SetSequenceCount(int iSequenceCount){
		m_iSequenceCount = iSequenceCount;
		boolSequenceCountGrows = false;
	}

	public void SetSequenceJump(int iSequenceJump){
		m_iSequenceJump = iSequenceJump;
	}

	public NoteEventSet Process() {

		NoteEventSet objSequence = new NoteEventSet();
		m_iaCellIndices = new int[m_objSecond.size()];

		// First map the cell NoteEvents to those in the template:
		for (int i = 0; i < m_objSecond.size(); i++){
			for (int j = 0; j < m_objFirst.size(); j++){
				if (m_objFirst.GetNoteEvent(j).GetPitchClass() == m_objSecond.GetNoteEvent(i).GetPitchClass()){
					m_iaCellIndices[i] = j;
				}
			}
		}

		int iIndex;
		int iRegister;

		objSequence.Append(m_objSecond);

		for (int k = 0; k < m_iSequenceCount; k++){
			for (int l = 0; l < m_objSecond.size(); l++){
				iIndex = m_iaCellIndices[l];
				iIndex += m_iSequenceJump;
				if (iIndex >= (m_objFirst.size())) {
					iIndex = iIndex % (m_objFirst.size());
					m_objSecond.GetNoteEvent(l).SetRegister(m_objSecond.GetNoteEvent(l).GetRegister() + 1);
				}
				if (iIndex < 0) {
					iIndex = (12 + iIndex) % (m_objFirst.size());
					m_objSecond.GetNoteEvent(l).SetRegister(m_objSecond.GetNoteEvent(l).GetRegister() - 1);
				}
				iRegister = m_objSecond.GetNoteEvent(l).GetRegister();
				m_iaCellIndices[l] = iIndex;
				objSequence.Append(new NoteEvent(m_objFirst.GetNoteEvent(iIndex).GetPitchClass(), iRegister));
			}
		}

		return objSequence;
	}

	public String GetFirstName(){
		return "Template";
	}

	public String GetFirstDescription(){
		return "The set of NoteEvents to be used in this sequence";
	}

	public String GetSecondName(){
		return "Cell";
	}

	public String GetSecondDescription(){
		return "The cell to be sequenced";
	}

	private int[] m_iaCellIndices;
	private int m_iSequenceCount;
	private int m_iSequenceJump;
	private boolean boolSequenceCountGrows;

}