package com.calnrich.fortefy.core.note;

public interface iPitchClass extends iGenericNoteParameter {

	public int GetPitchClass();

	public void SetPitchClass(int iPitchClass);

	public String GetPitchClassName();

	public String GetPitchClassName(boolean boolShowRegister);

	public String GetCTName();

	public void Invert(iPitchClass objPitchClass);

}

