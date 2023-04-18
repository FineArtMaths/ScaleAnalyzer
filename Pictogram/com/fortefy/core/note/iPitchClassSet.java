package com.calnrich.fortefy.core.note;

import java.util.Vector;
import java.util.Enumeration;
import java.util.Collections;
import java.util.Comparator;

/**
#TODO Move most of the transformative functions into processors
*/

public interface iPitchClassSet extends iGenericSet {

// Methods to manipulate the contents

	public int GetPitchClass(int iIndex);

	public void AddPitchClass(int iPitchClass);

	public void Append(iPitchClass objElement);

	public void Append(iPitchClassSet objElementSet);

// Transformative functions
// These ought to be Processors

	public void ShiftUp(int iShiftBy);

	public void ShiftDown(int iShiftBy);

	public void RotateForwards();

	public void RotateBackwards();

	public void Invert();

	public void Retrograde();

	public void SortByPCAscending();

	public void SortByPCDescending();

	public void SortByPCAndScopeAscending();

	public void SortByPCAndScopeDescending();

	public void SortToNormalForm();

	public void RemConsecPCDups();

	public void RemConsecPCScopeDups();

// Display methods

	public String GetPCString(boolean boolShowRegister);

	public String GetPCString();

	public String GetCTString();

}

