package main;

import text_preprocessing.*;

import java.io.File;

public class Main {

	public static void main(String [] args){
		
		File directory = new File(args[0]);
		//System.out.println(directory.getAbsolutePath());
		String text_input = "siddartha.txt";
		String input = stringPreprocess(args[0]+"/"+text_input);
		
	}
	
	public static String stringPreprocess(String in_str){
		
		TextPreprocessing tpp = new TextPreprocessing(in_str);
		tpp.preprocessing();
		//System.err.println(tpp.getResulting_string());
		try {
			tpp.selectWords();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tpp.removeSpaces();
		return tpp.getResulting_string();
	}
	
}
