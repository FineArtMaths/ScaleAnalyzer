package com.calnrich.slonimsky;

import java.util.ArrayList;

public class ArrayUtils {

    public static ArrayList<Integer> deepCopy(ArrayList<Integer> a){
    	ArrayList<Integer> retVal = new ArrayList<Integer>();
    	for(Integer i : a){
    		retVal.add(i);
    	}
    	return retVal;
    }


	
}
