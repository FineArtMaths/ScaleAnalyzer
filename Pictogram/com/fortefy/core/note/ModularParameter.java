package com.calnrich.fortefy.core.note;


public final class ModularParameter extends NoteEventParameter implements Comparable {

	public ModularParameter (){
		super();
	}

	public ModularParameter (int iNumber){
		super();
		m_iValue = iNumber % 12;
	}

	public void SetValue(int iNumber) {
		m_iValue = iNumber % 12;
	}

	public int Add(int iNumber){
		m_iValue = (m_iValue + iNumber) % 12;
		return m_iValue;
	}

	public int Subtract(int iNumber){
		m_iValue = ((m_iValue + 12) - (iNumber % 12) ) % 12;
		return m_iValue;
	}

	public int Multiply(int iNumber){
		m_iValue = (m_iValue * iNumber) % 12;
		return m_iValue;
	}

	public int DivideBy(int iNumber){
		m_iValue = (m_iValue / iNumber) % 12;
		return m_iValue;
	}

	public int AddConst(int iNumber){
		return (m_iValue + iNumber) % 12;
	}

	public int SubtractConst(int iNumber){
		return ((m_iValue + 12) - (iNumber % 12) ) % 12;
	}

	public int MultiplyConst(int iNumber){
		return (m_iValue * iNumber) % 12;
	}

	public int DivideByConst(int iNumber){
		return (m_iValue / iNumber) % 12;
	}

	@Override
	public int compareTo(Object arg0) {
		if(!arg0.getClass().equals(this.getClass())){
			return -1;
		} else {
			return (
					new Integer(this.GetValue())).compareTo(new Integer(((ModularParameter)arg0).GetValue()));
		}
	}

}