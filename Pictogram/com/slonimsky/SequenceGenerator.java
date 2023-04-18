package com.calnrich.slonimsky;

import java.util.ArrayList;

public class SequenceGenerator {

	private static final int MAX_NOTES = 5;
	
	private ArrayList<ArrayList<ArrayList<Integer>>> m_sequences = new ArrayList<ArrayList<ArrayList<Integer>>>();

	public SequenceGenerator(){
		initSequences();
	}
	
	public ArrayList<ArrayList<Integer>> getAllSequences(int numNotes){
		return m_sequences.get(numNotes - 1);
	}
	

	private void initSequences(){
		m_sequences.add(new ArrayList<ArrayList<Integer>>()); // to make the array 1-based we add this empty array
		for( int numNotes = 1; numNotes < MAX_NOTES; numNotes++){
			m_sequences.add(new ArrayList<ArrayList<Integer>>());
			//get every legal combination of numNotes notes
			Permutations p = new Permutations();
			ArrayList<ArrayList<Integer>> noteCombos = getNoteCombos(numNotes);
			for(ArrayList<Integer> noteCombo : noteCombos){
				p.clear();
				p.initPerms(noteCombo, noteCombo.size());
				ArrayList<ArrayList<Integer>> seqs = p.getPerms();
				for(ArrayList<Integer> seq : seqs){
					if(sequenceIsLegal(seq)){
						if(!m_sequences.get(numNotes - 1).contains(seq)){
							m_sequences.get(numNotes - 1).add(seq);
						}
					}
				}
			}
		}
	}
	
	private ArrayList<ArrayList<Integer>> getNoteCombos(int numNotes) {
		ArrayList<ArrayList<Integer>> noteCombos = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> baseCombo = new ArrayList<Integer>(); 
		for(int i = 0; i < numNotes; i++){
			baseCombo.add(i + 1);
		}
		noteCombos.add(baseCombo); // the basic combo of numNotes notes
		// we now add repeated notes
		for(int i = numNotes; i < 4; i++){
			int comboCount = noteCombos.size(); // this will be modified later, so store it here
			for( int j = 0; j < comboCount; j++){
				ArrayList<Integer> combo = noteCombos.get(j);
				for(int k = 0; k < numNotes; k++){
					ArrayList<Integer> newCombo = ArrayUtils.deepCopy(combo);
					newCombo.add(k + 1);
					noteCombos.add(newCombo);
				}
			}
		}
		return noteCombos;
	}

	private boolean sequenceIsLegal(ArrayList<Integer> seq){
		if(!seq.get(0).equals(1)){
			return false; // sequences must start on the base note
		}
		for(int i = 1; i < seq.size(); i++){
			if(seq.get(i).equals(seq.get(i-1))){
				// we don't allow consecutive notes to be the same!
				return false;
			}
		}
		if(seq.size() > 1 && seq.subList(seq.size()/2, seq.size() - 1).equals(seq.subList(0, seq.size()/2 - 1))){
			return false;
		}

		return true;
	}
	
	
	public static void main(String[] args) {
		SequenceGenerator sg = new SequenceGenerator();
		for(int i = 2; i < MAX_NOTES; i++){
		//int i = 2;
			System.out.println(sg.getAllSequences(i));
		}
	}
	
}
