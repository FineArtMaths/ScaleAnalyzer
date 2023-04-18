package com.calnrich.slonimsky;

import java.util.ArrayList;
import java.util.HashMap;

import com.calnrich.fortefy.core.note.ModularParameter;

public class SlonimskyGenerator {

	private LayoutGenerator m_lgen = new LayoutGenerator();
	private SequenceGenerator m_sgen = new SequenceGenerator();
	private ArrayList<SlonimskyOffset> m_offsets = new ArrayList<SlonimskyOffset>();
	private HashMap<SlonimskyBase, ArrayList<SlonimskyPattern>> m_decoratedBases = new HashMap<SlonimskyBase, ArrayList<SlonimskyPattern>>(); 
	
	public void decorateBase(SlonimskyBase base) {
		if(m_decoratedBases.containsKey(base.getNotes())){
			// base has already been decorated -- do nothing
			return;
		}
		ArrayList<SlonimskyPattern> decorations = new ArrayList<SlonimskyPattern>();
		// now iterate through all the offsets, adding to this new arraylist with each result
		for(SlonimskyOffset offset : m_offsets){
			if(offsetIsValid(base.getNotes(), offset)){
				SlonimskyPattern pattern = new SlonimskyPattern(applyOffset(base, offset), offset.getLayout(), base, offset.getSequence());
				if(patternIsValid(pattern)){
					decorations.add(pattern);
				}
			}
		}
		m_decoratedBases.put(base, decorations);
	}

	private boolean patternIsValid(SlonimskyPattern pattern) {
		// After application, check there are no repeated pitch classes
		int prevPitch = -1;
		int currPitch = -1;
		for(PatternNote pn : pattern.getNotes()){
			currPitch = pn.getPitchClass().intValue();
			if(currPitch == prevPitch){
				return false;
			}
			prevPitch = currPitch;
		}
		return true;
	}

	private boolean offsetIsValid(ArrayList<ModularParameter> base,
			SlonimskyOffset offset) {
		// Quickly eliminate obviously inapplicable offsets
		// Strictly speaking this is redundant, but it's quicker than patternIsValid()
		if(offset.getOffsetPattern().get(offset.getOffsetPattern().size() - 1).equals(base.get(1).GetValue())){
			return false;
		}
		return true;
	}
	
	public HashMap<SlonimskyBase, ArrayList<SlonimskyPattern>> getDecoratedBases(){
		return m_decoratedBases;
	}

	private ArrayList<PatternNote> applyOffset(SlonimskyBase base,
			SlonimskyOffset offset) {
		ArrayList<ModularParameter> baseNotes = base.getNotes(); 
		ArrayList<PatternNote> decoration = new ArrayList<PatternNote>();
		int baseIndex = 0;
		for(int i = 0; i < baseNotes.size() * base.getNumOctavesToPrint(); i++){
			for(int j = 0; j < offset.getOffsetPattern().size(); j++){
				Integer offsetInterval = offset.getOffsetPattern().get(j);
				Integer pitchClass = baseNotes.get(baseIndex).AddConst(offsetInterval);
				PatternNote pn = new PatternNote(pitchClass, offsetInterval, offset.getSequence().get(j), j==0);
				decoration.add(pn);
			}
			baseIndex++;
			if (baseIndex >= baseNotes.size()){
				baseIndex = 0;
			}
		}
		return decoration;
	}

	public void calculateAllPatterns(){
		int numNoteSizes = m_lgen.getNumNoteSizes();
		for (int numNotes = 2; numNotes < numNoteSizes; numNotes++){
			ArrayList<ArrayList<Integer>> sequences = m_sgen.getAllSequences(numNotes);
			ArrayList<String> layouts = m_lgen.getLayouts(numNotes);
			for(String layout: layouts){
				ArrayList<Integer> offsets = buildSequencableOffsets(layout);
				for(ArrayList<Integer> seq : sequences){
					// apply the sequence to the layout to get the pattern of notes
					SlonimskyOffset sequencedOffsets = getSequencedOffsets(offsets, seq, layout);
					if(!m_offsets.contains(sequencedOffsets)){
						m_offsets.add(sequencedOffsets);
					}
				}
			}
		}		
	}

	private SlonimskyOffset getSequencedOffsets(ArrayList<Integer> offsets,
			ArrayList<Integer> seq, String layout) {
		ArrayList<Integer> soff = new ArrayList<Integer>();
		for(int i = 0; i < seq.size(); i++){
				soff.add(offsets.get(seq.get(i) - 1));
		}
		return new SlonimskyOffset(soff, layout, seq);
	}
	
	private ArrayList<Integer> buildSequencableOffsets(String layout){
		ArrayList<Integer> offsets = new ArrayList<Integer>();
		for(int i = 0; i < layout.length(); i++){
			if(layout.substring(i, i+1).equals("1")){
				offsets.add(i);
			}
		}
		return offsets;
			
	}

}
