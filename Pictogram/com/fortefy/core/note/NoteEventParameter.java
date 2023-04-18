package com.calnrich.fortefy.core.note;
import com.calnrich.fortefy.core.Matheme;

public abstract class NoteEventParameter implements Matheme {

	public NoteEventParameter (NoteEventParameter objCopy){
		m_iValue = objCopy.GetValue();
	}

	public NoteEventParameter (){
		m_iValue = 0;
	}

	public int GetValue() {
		return m_iValue;
	}

	public String toString(){
		return "" + m_iValue;
	}

	public abstract void SetValue(int iNumber);

	public abstract int Add(int iNumber);

	public abstract int Subtract(int iNumber);

	public abstract int Multiply(int iNumber);

	public abstract int DivideBy(int iNumber);

	public void SetValue(double iNumber){
		this.SetValue((int)(iNumber));
	}

	public int Add(double iNumber){
		return this.Add ((int)(iNumber));
	}

	public int Subtract(double iNumber){
		return this.Subtract((int)(iNumber));
	}

	public int Multiply(double iNumber){
		return this.Multiply((int)(iNumber));
	}

	public int DivideBy(double iNumber){
		return this.DivideBy((int)(iNumber));
	}

	protected int m_iValue;

}