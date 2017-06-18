package main;

import text_preprocessing.*;
import suffix_array.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import automata.DFA;

public class Main {

	
	static String path;
	static long [] SA_build_r;
	static long [] DFA_build_r;
	static long [] SA_search_r;
	static long [] DFA_search_r;
	static int [] n_words;
	
	final static char [] alphabet= {'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			}; 

	public static void main(String [] args){
		
		path = args[0];
		int min = Integer.parseInt(args[2]);
		int n_tests = Integer.parseInt(args[3]);
		
		n_words = new int [n_tests];
		SA_build_r = new long[n_tests];
		DFA_build_r= new long[n_tests];
		SA_search_r = new long [n_tests];
		DFA_search_r = new long[n_tests];

		try {

			bigTest(args[1], min, n_tests);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public static void bigTest(String in_str, int min, int n_tests) throws Exception{
		
		TextPreprocessing tpp = new TextPreprocessing((int) Math.pow(2, min));
		tpp.preprocessing(in_str);
		PrintWriter SA_writer, FDA_writer;
		try {
			
			for(int i=0; i<n_tests; i++){
				n_words[i]=i+min;
				tpp.selectWords();
				String [] words = (String [])tpp.getRandom_words().toArray(new String[tpp.getRandom_words().size()]);
				String input = tpp.getResulting_string();
				input = tpp.removeSpaces(input);
				tpp.setRandom_words(null);

				{/*Building and search: SuffixArray*/
					
					SA_writer = new PrintWriter(String.format("%s/SA_search_time%d.csv",path, i), "UTF-8");
					SuffixArray sa = new SuffixArray(input, words);
					sa.skew();
					SA_build_r[i]=sa.getbuild_time();
					
					sa.search(SA_writer);
					SA_search_r[i]=sa.getSearch_time();
					SA_writer.close();
					SA_writer = null;
					sa = null;
					System.gc();
					
				}
				
				
				{/*Building and search: DFA */
					FDA_writer = new PrintWriter(String.format("%s/FDA_search_time%d.csv",path, i), "UTF-8");

					for(int j=0;j<words.length; j++){
						DFA dfa = new DFA(words[j], input, alphabet);
						dfa.buildAutomaton();
						DFA_build_r[i] +=dfa.getBuild_time();
						int occur =dfa.search();
						FDA_writer.print(String.format("%d, %d, %d\n", words[j].length(), dfa.getSearch_time(), occur));

						DFA_search_r[i] +=dfa.getSearch_time();
					}

					FDA_writer.close();
				}
				tpp.dupText(in_str);
			}
			
			writeResultsLong(SA_build_r, DFA_build_r, String.format("%s/build_performance.csv", path));
			writeResultsLong(SA_search_r, DFA_build_r, String.format("%s/search_performance.csv", path));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	
	public static void writeResultsLong(long [] array1, long [] array2, String file_path){
		
		try {
			PrintWriter pw = new PrintWriter(file_path, "UTF-8");
			for(int i=0; i<array1.length; i++){
				pw.print(String.format("%d,%d,%d\n", n_words[i], array1[i], array2[i]));
			}
			pw.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
