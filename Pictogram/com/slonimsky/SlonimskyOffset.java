package com.calnrich.slonimsky;

import java.util.ArrayList;

public class SlonimskyOffset {

	private ArrayList<Integer> m_offsetPattern;
	private String m_layout;
	private ArrayList<Integer> m_sequence;
	
	public SlonimskyOffset(ArrayList<Integer> offsetPattern, String layout, ArrayList<Integer> sequence){
		setOffsetPattern(offsetPattern);
		setLayout(layout);
		setSequence(sequence);
	}
	
	public ArrayList<Integer> getSequence() {
		return m_sequence;
	}

	public void setSequence(ArrayList<Integer> sequence) {
		this.m_sequence = sequence;
	}

	public ArrayList<Integer> getOffsetPattern() {
		return m_offsetPattern;
	}
	public void setOffsetPattern(ArrayList<Integer> offsetPattern) {
		this.m_offsetPattern = offsetPattern;
	}
	public String getLayout() {
		return m_layout;
	}
	public void setLayout(String layout) {
		this.m_layout = layout;
	}
	
	
}
