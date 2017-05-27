package text_preprocessing;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class TextPreprocessing {

	private String text_path;
	private String resulting_string;
	private LinkedList<String> random_words;
	private char [] alphabet = {'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 
			'q','r','s','t','u', 'v', 'w', 'x', 'y', 'z'};
	private int N;
	
	public TextPreprocessing(String text){
		
		setText_path(text);
		resulting_string="";
		setN(0);
	}
	
	public void preprocessing(){

		BufferedReader br;
		String line;
		try {
			
			br = new BufferedReader(new FileReader(text_path));

			while ((line = br.readLine()) != null) {
				System.out.println(line);
				line=standarizeString(line);
				resulting_string +=line;
				System.out.println(line);
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
		
		input= input.toLowerCase();
		/*Replacing 'accented' a with a simple a*/
		input = input.replace('á', 'a').replace('a', 'a');
		/*Replacing 'accented' e with a simple e*/
		
		/*Replacing 'accented' i with a simple i*/
		
		/*Replacing 'accented' o with a simple o*/
		
		
		/*Replacing 'accented' u with a simple u*/
		
		input= input.replace('\n', '\t');
		
		return input;
	}
	
	public void selectWords() throws Exception{
		
		String [] splitted = resulting_string.split(" |\t");
		int len = splitted.length;
		double approx;
		
		for(int i = 15; i<=25;i++){
			approx = Math.pow(2.0, i);
			if(len<=approx+approx*0.5 && len>approx-approx*0.5){
				setN((int)approx);
				break;
			}
		}
		if(N==0) throw new Exception("There are not enough words");
		
		int n_words = N/10;
		
		for(int i=0;i<n_words;i++){
			
		}
		
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
