package main;

import text_preprocessing.*;
import suffix_array.*;

import java.io.File;

public class Main {

	public static void main(String [] args){
		
		//File directory = new File(args[0]);
		//System.out.println(directory.getAbsolutePath());
		//String text_input = "siddartha.txt";
		//String input = stringPreprocess(args[0]+"/"+text_input);
		//testRadix();
		//testMods();
		//testTokenString();
		testTests(args[0], args[1]);
		
		
	}
	
	public static void testMods(){
		String input = "Holaquehacetodoeldiatrabajandocomounallamahjjfhjhewihdjasabachchopitoeraungatoqienodjsasjnewnjksdjfbbweiuweidnejw$$";
		//String input = "mississippi$$";
		String [] words = {"que", "hew"};
		SuffixArray sa = new SuffixArray(input,words );
		sa.createMod120();
		sa.assignToken();
		sa.writeTokenizedString();
		sa.buildTokenStringSA();
		sa.buildAndSortMod0();
		sa.seeMod12();
		sa.buildSA();
		sa.search();
	}
	
	public static void testTests(String path_text, String path_res){
		
		Tests ts = new Tests(path_text, path_res);
		ts.setParameters();
		ts.iterTests();
		
	}
	
	public static void testTokenString(){
		
		String test = "chopitoeraungatoquenosabiacomocomerasiquecomiasinpararchopitoeraungatoquechopichohsjhdasoiwehjasndjsadhhuiegwehjbsfffgsfgsgsfgeeewvdfgdfgdytygsfdsfd$$";
		//String test = "mississippi$$";
		String [] words = {"mi", "i$$"};
		SuffixArray sa = new SuffixArray(test, words);
		
		sa.createMod120();
		sa.assignToken();
		sa.writeTokenizedString();
		sa.buildTokenStringSA();
		sa.buildAndSortMod0();
		sa.buildSA();
		sa.search();
		
		//System.err.println(sa.getSuffixFromTokenString(4));
		//System.err.println(sa.getToken_string());
		//testRadix();
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
