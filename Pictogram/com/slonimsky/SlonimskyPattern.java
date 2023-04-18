package com.calnrich.slonimsky;

import java.util.ArrayList;

public class SlonimskyPattern {

	private ArrayList<PatternNote> m_notes;
	private String m_layout;
	private SlonimskyBase m_base;
	private ArrayList<Integer> m_sequence;
	
	public SlonimskyPattern(ArrayList<PatternNote> notes, 
			String layout, 
			SlonimskyBase base, 
			ArrayList<Integer> sequence){
		setNotes(notes);
		setLayout(layout);
		setBase(base);
		setSequence(sequence);
	}

	public ArrayList<Integer> getSequence() {
		return m_sequence;
	}

	protected void setSequence(ArrayList<Integer> sequence) {
		this.m_sequence = sequence;
	}

	public ArrayList<PatternNote> getNotes() {
		return m_notes;
	}
	
	protected void setNotes(ArrayList<PatternNote> notes) {
		this.m_notes = notes;
	}
	
	public String getLayout() {
		return m_layout;
	}
	
	protected void setLayout(String layout) {
		this.m_layout = layout;
	}

	public SlonimskyBase getBase() {
		return m_base;
	}
	
	protected void setBase(SlonimskyBase base) {
		this.m_base = base;
	}
	
	public String toString(){
		return m_notes.toString();
	}
	
	
}
