package com.calnrich.fortefy.core.note;

import java.util.Vector;
import java.util.Enumeration;
import java.util.Collections;
import java.util.Comparator;

/**
#TODO Move most of the transformative functions into processors
*/

public interface iGenericSet {

	public void Remove (int iIndex);

	public void RemoveLast ();

	public void DuplicateLast();

	public iGenericNoteParameter GetElement(int iIndex);

	public iGenericNoteParameter GetElement();

	public int size();

}

