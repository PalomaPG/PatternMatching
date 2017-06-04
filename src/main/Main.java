package main;

import text_preprocessing.*;
import suffix_array.*;

import java.io.File;

public class Main {

	public static void main(String [] args){
		
		File directory = new File(args[0]);
		//System.out.println(directory.getAbsolutePath());
		//String text_input = "siddartha.txt";
		//String input = stringPreprocess(args[0]+"/"+text_input);
		//testRadix();
		testTokenString();
		
		
	}
	
	public static void testTokenString(){
		
		String test = "chopitoeraungatoquenosabiacomocomerasiquecomiasinpararchopitoeraungatoquechopichohsjhdasoiwehjasndjsadhhuiegwehjbsfffgsfgsgsfgeeewvdfgdfgdytygsfdsfd$$";
		//String test = "mississippi$$";
		SuffixArray sa = new SuffixArray(test);
		
		sa.createMod120();
		sa.assignToken();
		sa.writeTokenizedString();
		sa.buildTokenStringSA();
		sa.buildAndSortMod0();
		sa.buildSA();
		
		//System.err.println(sa.getSuffixFromTokenString(4));
		//System.err.println(sa.getToken_string());
		//testRadix();
	}
	
	public static void testRadix(){
		
		/*String [] test = {"dab", "add",
				"cab", "fad", "fee", "bad",
				"dad", "bee", "fed", "bed", 
				"ebb", "ace"};*/
		
		
		String [] test = new String[12];
		test[0]="dab";
		test[1]="add";
		test[2]="cab";
		test[3]="fadas";
		test[4]="fee";
		test[5]="bad";
		test[6]="dad";
		test[7]="bee";
		test[8]="fed";
		test[9]="be";
		test[10]="ebb";
		test[11]="ace";
		
		for(int i=0; i<test.length; i++)
			System.err.println(test[i]);
		RadixSort rs = new RadixSort();
		rs.sort(test);
		rs.show(test);

		
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
