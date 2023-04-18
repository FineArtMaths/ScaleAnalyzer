package com.calnrich.slonimsky;

import java.util.ArrayList;

import com.calnrich.fortefy.core.note.ModularParameter;

public class SlonimskyBase {

	private ArrayList<ModularParameter> m_notes;
	private String m_name;
	private int m_numOctavesToPrint;
	
	public SlonimskyBase(ArrayList<ModularParameter> notes, String name, int numOctavesToPrint){
		m_notes = notes;
		m_name = name;
		m_numOctavesToPrint = numOctavesToPrint;
	}
	
	public SlonimskyBase(ArrayList<ModularParameter> notes, String name){
		m_notes = notes;
		m_name = name;
		m_numOctavesToPrint = 1;
	}
	
	public SlonimskyBase(){
		m_notes = new ArrayList<ModularParameter>();
		m_name = "";
	}

	public ArrayList<ModularParameter> getNotes() {
		return m_notes;
	}

	public void setNotes(ArrayList<ModularParameter> notes) {
		this.m_notes = notes;
	}

	public String getName() {
		return m_name;
	}

	public void setName(String name) {
		this.m_name = name;
	}
	
	public void setNumOctavesToPrint(int numOctaves){
		m_numOctavesToPrint = numOctaves;
	}

	public int getNumOctavesToPrint() {
		return m_numOctavesToPrint;
	}
	
}
