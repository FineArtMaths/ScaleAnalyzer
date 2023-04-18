package com.calnrich.slonimsky.book;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.calnrich.slonimsky.Base;
import com.calnrich.slonimsky.SlonimskyBase;
import com.calnrich.slonimsky.SlonimskyGenerator;
import com.calnrich.slonimsky.TexFileConstants;

public class FileCombiner {

	public static void main(String[] args) throws IOException {

		SlonimskyGenerator sg = new SlonimskyGenerator();
		sg.calculateAllPatterns();
		// Now we need to take each base in turn...
		//...apply each pattern to it in turn
		for(SlonimskyBase base : Base.BASE_ARRAY){
			sg.decorateBase(base);
		}
		int chapter = 1;
		// Create a new file
		FileWriter fw = new FileWriter(TexFileConstants.TEX_BASE_DIRECTORY 
				+ TexFileConstants.TEX_DIR_DELIMITER 
				+ TexFileConstants.TEX_OUTPUT_DIRECTORY 
				+ TexFileConstants.TEX_DIR_DELIMITER 
				+ TexFileConstants.TEX_DOC_BOOK_FILENAME);

		// Append the file header
		FileReader fr = new FileReader(TexFileConstants.TEX_BASE_DIRECTORY 
				+ TexFileConstants.TEX_DIR_DELIMITER 
				+ TexFileConstants.TEX_RESOURCE_DIRECTORY 
				+ TexFileConstants.TEX_DIR_DELIMITER 
				+ TexFileConstants.TEX_DOC_HEAD_FILENAME);
		BufferedReader br = new BufferedReader(fr);
		String s;
		while((s = br.readLine()) != null) {
			fw.append(s + "\n");
		}
		fr.close(); 
		
		for(@SuppressWarnings("unused") SlonimskyBase base : sg.getDecoratedBases().keySet()){
			// Open the chapter file
			String filename = TexFileConstants.TEX_BASE_DIRECTORY 
			+ TexFileConstants.TEX_DIR_DELIMITER 
			+ TexFileConstants.TEX_OUTPUT_DIRECTORY 
			+ TexFileConstants.TEX_DIR_DELIMITER 
			+ TexFileConstants.TEX_OUTPUT_OUTPUT_DIRECTORY 
			+ TexFileConstants.TEX_DIR_DELIMITER 
			+ TexFileConstants.TEX_OUTPUT_FILENAME_BASE 
			+ chapter + ".tex.tex";
			System.out.println("Appending file: " + filename);
			try{
				fr = new FileReader(filename);
			}catch( FileNotFoundException e){
				System.out.println(filename + " not found: skipping!");
				chapter++;
				continue;
			}
			br = new BufferedReader(fr);
			boolean writing = false;
			while((s = br.readLine()) != null) {
				// ignore the very last line
				if(writing && s.contains("end{document}")){
					writing = false;
				}
				// This is the first line of the text, so start writing 
				if(!writing && s.contains("chapter")){
					writing = true;
					fw.append("\n\n"); // create some space
				}
				if(writing){	// false by default
					fw.append(s + "\n");
				}
			}
			fr.close(); 

			chapter++;
		}
		fw.append("\n\n\\end{document}\n");
		// Close the file
		fw.close();
		System.out.println("\nDone -- now copy slonimsky_book.tex to the out directory and run pdflatex on it");
	}
}
