package com.calnrich.fortefy.core;
import java.util.Vector;

public final class MathemeVector {

// Constructors

	public MathemeVector(){
		m_vContent = new Vector();
	}

	public MathemeVector(MathemeVector objCopy){
		m_vContent = objCopy.GetData();
	}

// Accessors

	public Vector GetData(){
		return m_vContent;
	}

	public void Add(Matheme objElement){
		// and so on...
	}

	Vector m_vContent;
}