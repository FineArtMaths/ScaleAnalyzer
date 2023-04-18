package com.calnrich.fortefy.core.note;

public interface iRegisterSet extends iGenericSet {

	public int GetRegister(int iIndex);

	public void AddRegister(int iRegister);

	public void Append(iRegister objElement);

	public void Append(iRegisterSet objElementSet);
}

