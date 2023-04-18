package com.calnrich.fortefy.core.note;

import java.util.Vector;
import java.util.Enumeration;
import java.util.Collections;
import java.util.Comparator;

/**
#TODO Move most of the transformative functions into processors
*/

public final class NoteEventSet implements iPitchClassSet,
											iDurationSet,
											iRegisterSet{

	public NoteEventSet(){
		m_vNoteEvents = new Vector();
	}

	public NoteEventSet(NoteEventSet objCopy){
		m_vNoteEvents = new Vector();
		this.Append(objCopy);
	}

// Methods to manipulate the contents

	public void AddPitchClass(int iPitchClass){
		m_vNoteEvents.add(new NoteEvent(iPitchClass));
	}

	public void AddPitch(int iPitchClass, int iRegister){
		m_vNoteEvents.add(new NoteEvent(iPitchClass, iRegister));
	}

	public void AddRegister(int iRegister){
		NoteEvent objNoteEvent = new NoteEvent();
		objNoteEvent.SetRegister(iRegister);
		m_vNoteEvents.add(objNoteEvent);
	}

	public void AddDuration(int iDuration){
		NoteEvent objNoteEvent = new NoteEvent();
		objNoteEvent.SetDuration(iDuration);
		m_vNoteEvents.add(objNoteEvent);
	}

	public void AddIntervalAbove(int iInterval){
		this.DuplicateLast();
		GetLastNoteEvent().Add(iInterval);
	}

	public void AddIntervalBelow(int iInterval){
		this.DuplicateLast();
		GetLastNoteEvent().Subtract(iInterval);
	}

	public void Remove (int iIndex){
		m_vNoteEvents.remove(iIndex);
	}

	public void RemoveLast (){
		m_vNoteEvents.remove(m_vNoteEvents.size() - 1);
	}

	public void DuplicateLast(){
		NoteEvent objNewNoteEvent = new NoteEvent(GetLastNoteEvent());
		m_vNoteEvents.add(objNewNoteEvent);
	}

	public void Append(NoteEvent objNoteEvent){
		m_vNoteEvents.add(objNoteEvent);
	}

	public void Append(NoteEventSet objNoteEventSet){
		for (int i = 0; i < objNoteEventSet.size(); i++ ){
			m_vNoteEvents.add( new NoteEvent(objNoteEventSet.GetNoteEvent(i)) );
		}
	}

	public void Append(iPitchClass objPitchClass){
		NoteEvent objNoteEvent = new NoteEvent(objPitchClass.GetPitchClass());
		m_vNoteEvents.add(objNoteEvent);
	}

	public void Append(iPitchClassSet objNoteEventSet){
		for (int i = 0; i < objNoteEventSet.size(); i++ ){
			m_vNoteEvents.add( new NoteEvent(objNoteEventSet.GetPitchClass(i)) );
		}
	}

	public void Append(iRegister objRegister){
		NoteEvent objNoteEvent = new NoteEvent();
		objNoteEvent.SetRegister(objRegister.GetRegister());
		m_vNoteEvents.add(objNoteEvent);
	}

	public void Append(iRegisterSet objRegisterSet){
		NoteEvent objNoteEvent;
		for (int i = 0; i < objRegisterSet.size(); i++ ){
			m_vNoteEvents.add( new NoteEvent() );
			( (NoteEvent) (m_vNoteEvents.elementAt(m_vNoteEvents.size() - 1)) ).SetRegister(objRegisterSet.GetRegister(i));
		}
	}

	public void Append(iDuration objDuration){
		NoteEvent objNoteEvent = new NoteEvent();
		objNoteEvent.SetDuration(objDuration.GetDuration());
		m_vNoteEvents.add(objNoteEvent);
	}

	public void Append(iDurationSet objDurationSet){
		NoteEvent objNoteEvent;
		for (int i = 0; i < objDurationSet.size(); i++ ){
			m_vNoteEvents.add( new NoteEvent() );
			( (NoteEvent) (m_vNoteEvents.elementAt(m_vNoteEvents.size() - 1)) ).SetDuration(objDurationSet.GetDuration(i));
		}
	}

// Accessors

	public iGenericNoteParameter GetElement(int iIndex){
		return GetNoteEvent(iIndex);
	}

	public iGenericNoteParameter GetElement(){
		return GetLastNoteEvent();
	}

	public NoteEvent GetNoteEvent(int iIndex){
		return (NoteEvent)m_vNoteEvents.elementAt(iIndex);
	}

	public NoteEvent GetLastNoteEvent(){
		return (NoteEvent)m_vNoteEvents.elementAt(m_vNoteEvents.size() - 1);
	}

	public int size(){
		return m_vNoteEvents.size();
	}

	public int GetRegister(int iIndex){
		return ( (NoteEvent) (m_vNoteEvents.elementAt(iIndex)) ).GetRegister();
	}

	public int GetPitchClass(int iIndex){
		return ( (NoteEvent) (m_vNoteEvents.elementAt(iIndex)) ).GetPitchClass();
	}

	public int GetDuration(int iIndex){
		return ( (NoteEvent) (m_vNoteEvents.elementAt(iIndex)) ).GetDuration();
	}

// Transformative functions
// These ought to be Processors

	public void ShiftUp(int iShiftBy){
		for (Enumeration eNoteEvents = m_vNoteEvents.elements(); eNoteEvents.hasMoreElements() ; ){
			((NoteEvent)eNoteEvents.nextElement()).Add(iShiftBy);
		}
	}

	public void ShiftDown(int iShiftBy){
		for (Enumeration eNoteEvents = m_vNoteEvents.elements(); eNoteEvents.hasMoreElements() ; ){
			((NoteEvent)eNoteEvents.nextElement()).Subtract(iShiftBy);
		}
	}

	public int GetInterval(int iFirstIndex, int iSecondIndex) {
		int iFirstValue = ((NoteEvent)m_vNoteEvents.elementAt(iFirstIndex)).GetPitchClass();
		int iSecondValue = ((NoteEvent)m_vNoteEvents.elementAt(iSecondIndex)).GetPitchClass();
		return ((iFirstValue + 12) - (iSecondValue % 12) ) % 12;
	}

	public void RotateForwards(){
		NoteEvent objNoteEvent = (NoteEvent)m_vNoteEvents.elementAt(0);
		m_vNoteEvents.remove(0);
		m_vNoteEvents.add(objNoteEvent);
	}

	public void RotateBackwards(){
		NoteEvent objNoteEvent = GetLastNoteEvent();
		Vector vBuffer = new Vector();
		vBuffer.add(objNoteEvent);
		Enumeration eNoteEvents = m_vNoteEvents.elements();
		for (int i = 0; i < this.size() - 1; i++){
			vBuffer.add( (NoteEvent)eNoteEvents.nextElement() );
		}
		m_vNoteEvents = vBuffer;
	}

	public void Invert(){
		NoteEvent cfPivotPoint = (NoteEvent)m_vNoteEvents.elementAt(0);
		for (Enumeration eNoteEvents = m_vNoteEvents.elements(); eNoteEvents.hasMoreElements() ; ){
			((NoteEvent)eNoteEvents.nextElement()).Invert(cfPivotPoint);
		}
	}

	public void Retrograde(){
		Vector vBuffer = new Vector();
		Enumeration eNoteEvents = m_vNoteEvents.elements();
		for (int i = this.size() - 1; i >= 0; i--){
			vBuffer.add( m_vNoteEvents.elementAt(i) );
		}
		m_vNoteEvents = vBuffer;
	}

	public void SortByPCAscending(){
		Collections.sort(m_vNoteEvents, new PCComparator());
	}

	public void SortByPCDescending(){
		this.SortByPCAscending();
		this.Retrograde();
	}

	public void SortByPCAndScopeAscending(){
		Collections.sort(m_vNoteEvents, new PCRegisterComparator());
	}

	public void SortByPCAndScopeDescending(){
		this.SortByPCAscending();
		this.Retrograde();
	}

	public void SortToNormalForm(){
		// sorts by smallest intervals first
		int i;
	}

	public void RemConsecPCDups(){
		int iLastPCNum = -99;
		int iCurrentPCNum;
		for (int i = 0; i < m_vNoteEvents.size(); i++){
			iCurrentPCNum = ( (NoteEvent)m_vNoteEvents.elementAt(i) ).GetPitchClass();
			if ( iCurrentPCNum == iLastPCNum){
				m_vNoteEvents.remove(i);
				--i;
			}
			iLastPCNum = iCurrentPCNum;
		}
	}

	public void RemConsecPCScopeDups(){
		int iLastPCNum = -99;
		int iCurrentPCNum;
		for (int i = 0; i < m_vNoteEvents.size(); i++){
			iCurrentPCNum = ((NoteEvent)m_vNoteEvents.elementAt(i)).GetPitchClassRegisterNumber();
			if ( iCurrentPCNum == iLastPCNum){
				m_vNoteEvents.remove(i);
				--i;
			}
			iLastPCNum = iCurrentPCNum;
		}
	}

// Display methods

	public String GetPCString(boolean boolShowRegister, boolean boolShowDuration){
		StringBuffer strBuffer = new StringBuffer();
		NoteEvent objNoteEvent;
		for (Enumeration eNoteEvents = m_vNoteEvents.elements(); eNoteEvents.hasMoreElements() ; ){
			objNoteEvent = ((NoteEvent)eNoteEvents.nextElement());
			strBuffer.append( objNoteEvent.GetPitchClassName(boolShowRegister) );
			strBuffer.append( "-" );
			strBuffer.append( objNoteEvent.GetDuration() );
			strBuffer.append( " " );
		}
		return strBuffer.toString();
	}

	public String GetPCString(boolean boolShowRegister){
		return GetPCString(boolShowRegister, false);
	}

	public String GetPCString(){
		return GetPCString(false, false);
	}

	public String GetCTString(){
		StringBuffer strBuffer = new StringBuffer();
		for (Enumeration eNoteEvents = m_vNoteEvents.elements(); eNoteEvents.hasMoreElements() ; ){
			strBuffer.append( ((NoteEvent)eNoteEvents.nextElement()).GetCTName() );
			strBuffer.append( " " );
		}
		return strBuffer.toString();
	}

// Private methods

	private class PCComparator implements Comparator {
		public int compare (Object objFirst, Object objSecond){
			int iReturn = 0;
			int iFirstValue = GetValue(objFirst);
			int iSecondValue = GetValue(objSecond);
			if (iFirstValue > iSecondValue){
				iReturn = 1;
			} else if (iFirstValue < iSecondValue) {
				iReturn = -1;
			}
			return iReturn;
		}
		private int GetValue(Object objSource){
			return ( (NoteEvent)objSource ).GetPitchClass();
		}
	}

	private class PCRegisterComparator implements Comparator {
		public int compare (Object objFirst, Object objSecond){
			int iReturn = 0;
			int iFirstValue = GetValue(objFirst);
			int iSecondValue = GetValue(objSecond);
			if (iFirstValue > iSecondValue){
				iReturn = 1;
			} else if (iFirstValue < iSecondValue) {
				iReturn = -1;
			}
			return iReturn;
		}
		private int GetValue(Object objSource){
			// NB we convert the number space from 0-11 to 1-12 so multiplying can't give 0
			return ( (NoteEvent)objSource ).GetPitchClassRegisterNumber();
		}
	}

	private Vector m_vNoteEvents;

}

