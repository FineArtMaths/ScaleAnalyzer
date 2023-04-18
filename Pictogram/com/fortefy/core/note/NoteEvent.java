package com.calnrich.fortefy.core.note;
import com.calnrich.fortefy.core.Matheme;

public final class NoteEvent implements Matheme, iPitchClass, iRegister, iDuration {

// Constructors

	public NoteEvent(){
		m_pPitchClass = new ModularParameter();
		m_pRegister = new LinearParameter(m_iDefaultRegister);
		m_pDuration = new LinearParameter(m_iDefaultDuration);
	}

	public NoteEvent(int iPitchClass){
		m_pPitchClass = new ModularParameter(iPitchClass);
		m_pRegister = new LinearParameter(m_iDefaultRegister);
		m_pDuration = new LinearParameter(m_iDefaultDuration);
	}

	public NoteEvent(int iPitchClass, int iRegister){
		m_pPitchClass = new ModularParameter(iPitchClass);
		m_pRegister = new LinearParameter(iRegister);
		m_pDuration = new LinearParameter(m_iDefaultDuration);
	}

	public NoteEvent(int iPitchClass, int iRegister, int iDuration){
		m_pPitchClass = new ModularParameter(iPitchClass);
		m_pRegister = new LinearParameter(iRegister);
		m_pDuration = new LinearParameter(iDuration);
	}

	public NoteEvent(NoteEvent objCopy){
		m_pPitchClass = new ModularParameter(objCopy.GetPitchClass());
		m_pRegister = new LinearParameter(objCopy.GetRegister());
		m_pDuration = new LinearParameter(objCopy.GetDuration());
	}

// Accessors

	public int GetPitchClass(){
		return m_pPitchClass.GetValue();
	}

	public int GetRegister(){
		return m_pRegister.GetValue();
	}

	public int GetDuration(){
		return m_pDuration.GetValue();
	}

	public void SetPitchClass(int iPitchClass){
		m_pPitchClass = new ModularParameter (iPitchClass);
	}

	public void SetRegister(int iRegister){
		m_pRegister = new LinearParameter (iRegister);
	}

	public void SetDuration(int iDuration){
		m_pDuration = new LinearParameter (iDuration);
	}

// Display methods (could go into another class for rendering objects)

	public int GetPitchClassRegisterNumber(){
		return ( (int)Math.pow(13, m_pRegister.GetValue()) ) * (m_pPitchClass.GetValue() + 1);
	}

	public String GetPitchClassName() {
		return GetPitchClassName(false);
	}

	public String GetPitchClassName(boolean boolShowRegister) {
		if (boolShowRegister){
			return m_strPCNames[m_pPitchClass.GetValue()] + m_pRegister.GetValue();
		} else {
			return m_strPCNames[m_pPitchClass.GetValue()];
		}
	}

	public String GetCTName() {
		return m_strCTNames[m_pPitchClass.GetValue()];
	}

	public String GetMXMLName() {
		return m_strMXMLNames[m_pPitchClass.GetValue()];
	}

	public String GetMXMLAlterElement() {
		return m_strMXMLAlterElements[m_pPitchClass.GetValue()];
	}


	public String toString(){
		return new String (GetPitchClassName(true) + ": " + m_pDuration.GetValue());
	}

// Matheme

	public int Add(int iNumber){
		m_pRegister.Add(Math.floor( (m_pPitchClass.GetValue() + iNumber) / 12 ));
		m_pPitchClass.SetValue((m_pPitchClass.GetValue() + iNumber) % 12);
		return m_pPitchClass.GetValue();
	}

	public int Add(NoteEvent objNoteEvent){
		return this.Add(objNoteEvent.GetPitchClass());
	}

	public int Subtract(int iNumber){
		m_pRegister.Add((m_pPitchClass.GetValue() - iNumber - 11) / 12);
		m_pPitchClass.SetValue (((m_pPitchClass.GetValue() + 12) - (iNumber % 12) ) % 12);
		return m_pPitchClass.GetValue();
	}

	public int Subtract(NoteEvent objNoteEvent){
		return this.Subtract(objNoteEvent.GetPitchClass());
	}

	public int Multiply(int iNumber){
		m_pRegister.Add(Math.floor( (m_pPitchClass.GetValue() * iNumber) / 12 ));
		m_pPitchClass.SetValue ((m_pPitchClass.GetValue() * iNumber) % 12);
		return m_pPitchClass.GetValue();
	}

	public int Multiply(NoteEvent objNoteEvent){
		return this.Multiply(objNoteEvent.GetPitchClass());
	}

	public int DivideBy(int iNumber){
		m_pRegister.Add(Math.floor( (m_pPitchClass.GetValue() / iNumber) / 12 ));
		m_pPitchClass.SetValue ((m_pPitchClass.GetValue() / iNumber) % 12);
		return m_pPitchClass.GetValue();
	}

	public int DivideBy(NoteEvent objNoteEvent){
		return this.DivideBy(objNoteEvent.GetPitchClass());
	}

	public void Invert(iPitchClass objPitchClass){
		this.Add(objPitchClass.GetPitchClass());
		m_pPitchClass.SetValue ((12 - m_pPitchClass.GetValue()) % 12);
	}

// Private members

	private static String[] m_strPCNames = {"C", "C#", "D", "Eb", "E", "F", "F#", "G", "G#", "A", "Bb", "B"};
	private static String[] m_strCTNames = {"1", "b2", "2", "b3", "3", "4", "s4", "5", "b6", "6", "b7", "7"};
	private static String[] m_strMXMLNames = {"C", "C", "D", "E", "E", "F", "F", "G", "G", "A", "B", "B"};
	private static String[] m_strMXMLAlterElements = {"", "1", "", "-1", "", "", "1", "", "1", "", "-1", ""};

	private ModularParameter m_pPitchClass;
	private LinearParameter m_pRegister;
	private LinearParameter m_pDuration;

	private static int m_iDefaultRegister = 4;
	private static int m_iDefaultDuration = 128;

}

