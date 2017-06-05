package main;

import java.io.File;
import java.io.FilenameFilter;

import suffix_array.SuffixArray;
import text_preprocessing.TextPreprocessing;

public class Tests {
	
	private File dir_txts;
	private File [] files;
	private String SA_results_csv;
	private long [] test_exec_time;
	private int [] n;
	private int n_texts;

	
	public Tests(String path_txts, String path_results){
		
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
	
	public void iterTests(){
		
		int i = 0;
		for(File f : files){
			testBuild(f.getAbsolutePath(), i);
			i++;
		}
	}
	
	public void testBuild(String input,int i){
		
		TextPreprocessing tp = new TextPreprocessing(input);
		try {
			tp.preprocessText();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Something wrong happened at proprocessing phase");
			e.printStackTrace();
		}
		SuffixArray sa = new SuffixArray(tp.getResulting_string(), tp.getRandom_words().toArray(new String [tp.getRandom_words().size()]));
		sa.skew();
		n[i] = sa.getN();
		test_exec_time[i]=sa.getExec_time();
		
	}
	
}
