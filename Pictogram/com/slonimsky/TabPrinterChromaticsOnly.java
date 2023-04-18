package com.calnrich.slonimsky;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.calnrich.fortefy.core.note.ModularParameter;

public class TabPrinterChromaticsOnly extends TabPrinter{

	public static void main(String[] args) throws IOException {
		generateAll(args);
		//generateOne(args);
		//generateNonSymmetrical(args);
	}

	public static void generateAll(String[] args) throws IOException {
		SlonimskyGenerator sg = new SlonimskyGenerator();
		sg.calculateAllPatterns();
		// Now we need to take each base in turn...
		//...apply each pattern to it in turn
		for(SlonimskyBase base : Base.BASE_ARRAY){
			sg.decorateBase(base);
		}

		// ...and print the tab
		TabPrinter tp = new TabPrinter();
		int chapter = 1;

		for(SlonimskyBase base : sg.getDecoratedBases().keySet()){
			// Create a new file
			FileWriter fw = new FileWriter(TexFileConstants.TEX_BASE_DIRECTORY 
					+ TexFileConstants.TEX_DIR_DELIMITER 
					+ TexFileConstants.TEX_OUTPUT_DIRECTORY 
					+ TexFileConstants.TEX_DIR_DELIMITER 
					+ "chromas_chapter"
					+ chapter + ".lytex");
			// Append the file header
			FileReader fr = new FileReader(TexFileConstants.TEX_BASE_DIRECTORY 
					+ TexFileConstants.TEX_DIR_DELIMITER 
					+ TexFileConstants.TEX_RESOURCE_DIRECTORY 
					+ TexFileConstants.TEX_DIR_DELIMITER 
					+ TexFileConstants.TEX_DOC_HEAD_FILENAME);
			BufferedReader br = new BufferedReader(fr);
			String s;
			while((s = br.readLine()) != null) {
				fw.append(s);
			}
			fr.close(); 

			// Print the chapter contents
			fw.append("\n");
			fw.append("\\chapter{" + base.getName() + "}");
			fw.append(tp.printChapterOpening(base));
			
			for(SlonimskyPattern pattern : sg.getDecoratedBases().get(base)){
				ArrayList<Integer> pcs = NoteAnalyser.getDiscretePitchClasses(pattern);
				if(pcs.size() == 12){
					fw.append(tp.printEntry(pattern));
				}
			}
			// Append the footer
			FileReader frf = new FileReader(TexFileConstants.TEX_BASE_DIRECTORY 
					+ TexFileConstants.TEX_DIR_DELIMITER 
					+ TexFileConstants.TEX_RESOURCE_DIRECTORY 
					+ TexFileConstants.TEX_DIR_DELIMITER 
					+ TexFileConstants.TEX_DOC_TAIL_FILENAME);
			BufferedReader brf = new BufferedReader(frf);
			while((s = brf.readLine()) != null) {
				fw.append(s);
			}
			frf.close(); 

			// Close the file
			fw.close();

			chapter++;
		}
		
		System.out.println("RUN COMPLETE: now run resources/lytex-full to create out/chapter_*.tex files. After that run the FileCombiner class");
		
	}

	
}
