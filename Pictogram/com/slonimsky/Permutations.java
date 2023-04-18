package com.calnrich.slonimsky;

import java.util.ArrayList;

public class Permutations {

	ArrayList<ArrayList<Integer>> m_perms = new ArrayList<ArrayList<Integer>>(); 
	
	public void clear(){
		m_perms = new ArrayList<ArrayList<Integer>>();
		m_perms.clear();
	}
	
    public void initPerms(ArrayList<Integer> current, int length) {
        if (length == 1) {
            m_perms.add(current);
            return;
        }
        for (int i = 0; i < length; i++) {
        	ArrayList<Integer> tmp;
        	tmp = swap(current, i, length-1);
        	initPerms(tmp, length-1);
            current = swap(tmp, i, length-1);
        }
        return;
    }  
    
    public ArrayList<ArrayList<Integer>> getPerms(){
    	return m_perms;
    }

	public static void main(String[] args) {
		Permutations p = new Permutations();
		ArrayList<Integer> current = new ArrayList<Integer>();
		current.add(new Integer(1));
		current.add(new Integer(2));
		current.add(new Integer(3));
		current.add(new Integer(4));
		current.add(new Integer(5));
		p.initPerms(current, 5);
		ArrayList<ArrayList<Integer>> perms = p.getPerms();
		for(ArrayList<Integer> perm : perms){
			System.out.println(perm.toString());
		}
	}
	
	// swap the characters at indices i and j
    private ArrayList<Integer> swap(ArrayList<Integer> a, int i, int j) {
    	ArrayList<Integer> retVal = ArrayUtils.deepCopy(a);
        Integer c;
        c = retVal.get(i); 
        retVal.set(i, retVal.get(j)); 
        retVal.set(j, c);
        return retVal;
    }
    
}
