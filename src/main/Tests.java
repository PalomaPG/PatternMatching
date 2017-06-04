package main;

import java.io.File;
import java.io.FilenameFilter;

import suffix_array.SuffixArray;
import text_preprocessing.TextPreprocessing;

public class Tests {
	
	private File dir_txts;
	private File [] files;
	private String path_results;
	private String SA_results_csv;
	private long [] test_exec_time;
	private int [] n;
	private int n_texts;
	private SuffixArray [] SA_tests;
	private TextPreprocessing [] tps;
	
	public Tests(String path_results, String path_txts, String SA_results_csv){
		
		this.path_results = path_results;
		dir_txts = new File(path_txts);
		this.SA_results_csv = SA_results_csv;
		
	}
	
	public void setParameters(){
	
		files = dir_txts.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".txt");
		    }
		});
		n_texts = files.length;
		test_exec_time = new long[n_texts];
		n = new int[n_texts];

	}
	

	public void testBuild(String input){
		
	}
	
}
