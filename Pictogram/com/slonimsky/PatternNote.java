package com.calnrich.slonimsky;

import java.util.ArrayList;

public class PatternNote {

	private Integer m_pitchClass;
	private Integer m_intervalStep;
	private Integer m_sequenceStep;
	private boolean m_isBaseNote;
	private String m_lilyPondPitchName = "";
	private int m_lilyPondPitchOctave = 0;
	private ArrayList<Integer> m_sequence = null;  
	
	public PatternNote(Integer pitchClass, Integer intervalStep, Integer sequenceStep, boolean isBaseNote){
		m_pitchClass = pitchClass;
		m_intervalStep = intervalStep;
		m_sequenceStep = sequenceStep;
		m_isBaseNote = isBaseNote;	
	}

	public PatternNote(Integer pitchClass, boolean isBaseNote){
		m_pitchClass = pitchClass;
		m_intervalStep = 0;
		m_sequenceStep = 0;
		m_isBaseNote = isBaseNote;	
	}

	public PatternNote(Integer pitchClass){
		m_pitchClass = pitchClass;
		m_intervalStep = 0;
		m_sequenceStep = 0;
		m_isBaseNote = true;	// all notes are base notes by default	
	}
	
	public void setSequence(ArrayList<Integer> sequence){
		m_sequence = sequence;
	}

	public ArrayList<Integer> getSequence(){
		return m_sequence;
	}

	public Integer getPitchClass() {
		return m_pitchClass;
	}

	public Integer getAbsolutePitch(){
		return m_pitchClass + (m_lilyPondPitchOctave * 12);
	}
	
	public Integer getIntervalStep() {
		return m_intervalStep;
	}

	public Integer getSequenceStep() {
		return m_sequenceStep;
	}

	public boolean isBaseNote() {
		return m_isBaseNote;
	}
	
	public String getLilyPondPitchName(){
		return m_lilyPondPitchName;
	}
	
	public int getLilyPondPitchOctave(){
		return m_lilyPondPitchOctave;
	}
	
	public void setLilyPondPitchName(String name){
		m_lilyPondPitchName = name;
	}
	
	public void setLilyPondPitchOctave(int octave){
		m_lilyPondPitchOctave = octave;
	}
	
}
