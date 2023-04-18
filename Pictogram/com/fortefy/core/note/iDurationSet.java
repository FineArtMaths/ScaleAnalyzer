package com.calnrich.fortefy.core.note;

public interface iDurationSet extends iGenericSet {

	public int GetDuration(int iIndex);

	public void AddDuration(int iDuration);

	public void Append(iDuration objElement);

	public void Append(iDurationSet objElementSet);
}

