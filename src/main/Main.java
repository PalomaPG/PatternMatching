package main;

import text_preprocessing.*;
import suffix_array.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import automata.KMP;

public class Main {
	
	final String SA_build = "SA_build.csv";
	final String DFA_build = "DFA_build.csv";
	final String SA_search = "SA_search.csv";
	final String DFA_search = "DFA_build.csv";
	
	static String path;
	static long [] SA_build_r;
	static long [] DFA_build_r;
	static long [] SA_search_r;
	static long [] DFA_search_r;
	static int [] n_words;
	
	final static char [] alphabet= {'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'1','2', '3', '4', '5', '6', '7', '8', '9', '0'}; 

	public static void main(String [] args){

		String text_input = "dorian_gray.txt";
		path = args[0];
		String input = path+"/"+text_input;
		
		
		n_words = new int [11];
		SA_build_r = new long[11];
		DFA_build_r= new long[11];
		SA_search_r = new long [11];
		DFA_search_r = new long[11];
		
		stringPreprocess(input);
		
	}
	
	public static void testFA(){
		//String pattern="ACACAGA";
		String pattern ="chopito";
		char [] alphabet = {'A', 'C', 'G', 'T'};
		
		String input ="elgatochopitocomemuchoasiquelecambiaronlacomidachopitonolegustochopitocadavezpediamascomida";
		
		KMP kmp = new KMP(pattern, input, alphabet);
		kmp.buildAutomaton();
		kmp.showFA();
		kmp.search();
	}
	

	public static void testRadix(){
		
		String input = "holaquetalcomoestasyomuybien";
		Pair [] p = new Pair[input.length()];
		
		for(int i=0; i<input.length(); i++){
			p[i] = new Pair(i, input.length());
			
		}
		
		for(int i=0; i<input.length(); i++){
			System.err.println(input.substring(p[i].getX(), p[i].getY()));
		}
		
		RadixSort rs = new RadixSort(input);
		rs.sort(p);
		
		for(int i=0; i<input.length(); i++){
			System.err.println(input.substring(p[i].getX(), p[i].getY()));
		}
	}
	public static void stringPreprocess(String in_str){
		
		TextPreprocessing tpp = new TextPreprocessing();
		tpp.preprocessing(in_str);
		PrintWriter SA_writer, FDA_writer;
		//System.err.println(tpp.getResulting_string());
		try {
			tpp.minWords();
			
			for(int i=0; i<=10; i++){
				n_words[i]=i+15;
				tpp.selectWords();
				String [] words = (String [])tpp.getRandom_words().toArray(new String[tpp.getRandom_words().size()]);
				String input = tpp.getResulting_string();
				input = tpp.removeSpaces(input);
				tpp.setRandom_words(null);

				{/*Building and search: SuffixArray*/
					
					SA_writer = new PrintWriter(String.format("%s/SA_m_search_time%d.csv",path, i), "UTF-8");
					SuffixArray sa = new SuffixArray(input, words);
					sa.createMod120();
					sa.assignToken();
					sa.writeTokenizedString();
					sa.buildTokenStringSA();
					sa.buildAndSortMod0();
					sa.buildSA();
					SA_build_r[i]=sa.getbuild_time();
					
					sa.search(SA_writer);
					SA_search_r[i]=sa.getSearch_time();
					//System.err.println(SA_build_r[i]);
					//System.err.println(SA_search_r[i]);
					SA_writer.close();
					
				}
				
				
				{/*Building and search: DFA */
					FDA_writer = new PrintWriter(String.format("%s/FDA_m_search_time%d.csv",path, i), "UTF-8");

					for(int j=0;j<words.length; j++){
						KMP kmp = new KMP(words[j], input, alphabet);
						kmp.buildAutomaton();
						DFA_build_r[i] +=kmp.getBuild_time();
						int occur =kmp.search();
						FDA_writer.print(String.format("%d, %d, %d\n", words[i].length(), kmp.getSearch_time(), occur));

						DFA_search_r[i] +=kmp.getSearch_time();
					}
					//System.err.println(DFA_build_r[i]);
					//System.err.println(DFA_search_r[i]);
					FDA_writer.close();
				}
				
				tpp.dupText();
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
