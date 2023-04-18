package com.calnrich.fortefy.core.note;
import com.calnrich.fortefy.core.Matheme;

public final class LinearParameter extends NoteEventParameter {

	public LinearParameter(){
		super();
	}

	public LinearParameter (int iNumber){
		super();
		m_iValue = iNumber;
	}

	public void SetValue(int iNumber) {
		m_iValue = iNumber;
	}

	public int Add(int iNumber){
		m_iValue += iNumber;
		return m_iValue;
	}

	public int Subtract(int iNumber){
		m_iValue -= iNumber;
		return m_iValue;
	}

	public int Multiply(int iNumber){
		m_iValue = m_iValue * iNumber;
		return m_iValue;
	}

	public int DivideBy(int iNumber){
		double dNumber = m_iValue / iNumber;
		m_iValue = (int)( dNumber );
		return m_iValue;
	}

}