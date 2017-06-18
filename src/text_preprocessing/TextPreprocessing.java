package text_preprocessing;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import org.apache.commons.lang3.*;
import java.util.concurrent.ThreadLocalRandom;

public class TextPreprocessing {

	
	private String resulting_string;
	private LinkedList<String> random_words;
	private int min;
	private int N;
	
	public TextPreprocessing(int min){
		
		this.min = min;
		resulting_string="";
		setN(0);
		random_words= new LinkedList<String>();
	}
	
	public void preprocessing(String text_path) throws Exception{

		BufferedReader br;
		StringBuilder sb = new StringBuilder();
		String line;
		int count =0;
		try {
			
			br = new BufferedReader(new FileReader(text_path));

			while ((line = br.readLine()) != null) {
				//System.out.println(line);
				line=standarizeString(line);
				count +=line.split(" |\t").length;
				sb.append(line);
				if(count>=min) break;
				//System.out.println(line);
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(count<min)
			throw new Exception("There's no enough words!");
		resulting_string = sb.toString();
		
	}

	public String standarizeString(String input){
		
		if(input==null) return "";
		input= input.toLowerCase();
		/*Removing accents*/
		input=StringUtils.stripAccents(input).replaceAll("[^a-zA-Z ]", "");
		input= input.replaceAll("\n", " ");
		input= input.replaceAll("\t", " ");
		Normalizer.normalize(input, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		return input;
	}
	
	public void dupText(String text_path){
		StringBuilder sb = new StringBuilder();
		sb.append(resulting_string).append(" ").append(resulting_string);
		resulting_string = sb.toString();
		//setResulting_string(new StringBuilder().append(resulting_string).append(" ").append(resulting_string).toString());
		//System.err.println(getResulting_string());
	}
	
	public void selectWords() throws Exception{
		
		random_words = null;
		System.gc();
		random_words = new LinkedList<String>();
		String [] splitted = resulting_string.split(" |\t|\n");
		//System.err.println(Math.log(splitted.length)/Math.log(2.0));
		N = splitted.length;

		int n_words = N/10;
		int rnd;
		
		for(int j=0;j<n_words;j++){
			/*Generate random numbers*/
			rnd = ThreadLocalRandom.current().nextInt(0, N);
			while(splitted[rnd].equals("")) rnd = ThreadLocalRandom.current().nextInt(0, N);
			random_words.add(splitted[rnd]);
			//System.out.println(String.format("Word: %s, number %d",splitted[rnd], rnd));
		}
		splitted=null;
		System.gc();
	}
	
	public String removeSpaces(String toRemove){
		return toRemove.replaceAll("\t", "").replaceAll(" ", "");
		
	}


	public LinkedList<String> getRandom_words() {
		return random_words;
	}

	public void setRandom_words(LinkedList<String> random_words) {
		this.random_words = random_words;
	}

	public String getResulting_string() {
		return resulting_string;
	}

	public void setResulting_string(String resulting_string) {
		this.resulting_string = resulting_string;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}
}
