package main;

import java.io.File;
import java.io.FilenameFilter;

import suffix_array.SuffixArray;
import text_preprocessing.TextPreprocessing;

public class Tests {
	
	private String path;
	final String SA_build = "SA_build.csv";
	final String DFA_build = "DFA_build.csv";
	final String SA_search = "SA_search.csv";
	final String DFA_search = "DFA_build.csv";
	
	
	private long [] SA_build_r;
	private long [] DFA_build_r;
	private long [] SA_search_r;
	private long [] DFA_search_r;
	private int [] n_words;


	
	private char [] alphabet= {'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'1','2', '3', '4', '5', '6', '7', '8', '9', '0'}; 
	
	public Tests(String path_txt){
		
		this.path = path;
		
	}
	
	

	
}
