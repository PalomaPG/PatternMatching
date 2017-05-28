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

	private String text_path;
	private String resulting_string;
	private LinkedList<String> random_words;

	private int N;
	
	public TextPreprocessing(String text){
		
		setText_path(text);
		resulting_string="";
		setN(0);
		random_words= new LinkedList<String>();
	}
	
	public void preprocessing(){

		BufferedReader br;
		String line;
		try {
			
			br = new BufferedReader(new FileReader(text_path));

			while ((line = br.readLine()) != null) {
				//System.out.println(line);
				line=standarizeString(line);
				resulting_string +=line;
				//System.out.println(line);
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public String standarizeString(String input){
		
		if(input==null) return "";
		input= input.toLowerCase();
		/*Removing accents*/
		input=StringUtils.stripAccents(input).replaceAll("[^a-zA-Z ]", "");
		input= input.replaceAll("\n", " ");
		Normalizer.normalize(input, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		return input;
	}
	
	public void selectWords() throws Exception{
		
		String [] splitted = resulting_string.split(" |\t");
		int len = splitted.length;
		double approx;
		int i;
		for(i = 15; i<=25;i++){
			approx = Math.pow(2.0, i);
			if(len<=approx+approx*0.5 && len>approx-approx*0.5){
				setN((int)approx);
				break;
			}
		}
		if(N==0) throw new Exception("There are not enough words or number of words exceeds the superior limit\n");
		else System.out.println(String.format("There are enough words to proceed: %d, 2^%d words", getN(),i));
		int n_words = N/10;
		int rnd;
		
		for(int j=0;j<n_words;j++){
			/*Generate random numbers*/
			rnd = ThreadLocalRandom.current().nextInt(0, len);
			while(splitted[rnd].equals("")) rnd = ThreadLocalRandom.current().nextInt(0, len);
			random_words.add(splitted[rnd]);
			//System.out.println(String.format("Word: %s, number %d",splitted[rnd], rnd));
		}
		
	}
	
	public void removeSpaces(){
		resulting_string=resulting_string.replaceAll("\t", "").replaceAll(" ", "");
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

	public String getText_path() {
		return text_path;
	}

	public void setText_path(String text_path) {
		this.text_path = text_path;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}
}
