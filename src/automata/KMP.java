package automata;

public class KMP {

	private String pattern;
	private String input;
	final char [] alphabet;
	
	private int [][] FA;
	
	public KMP(String pattern, String input, char [] alphabet){
		this.pattern = pattern;
		this.input = input;
		FA = new int [alphabet.length][pattern.length()+1];
		this.alphabet = alphabet;
	}
	
	public void buildAutomaton(){
		
		for(int i=0;i<=pattern.length(); i++){
			for(int j=0;j<alphabet.length; j++){
				if(i<pattern.length() && pattern.charAt(i)==alphabet[j] ){
					FA[j][i] = i+1;
				}
				else{
					//theres no such character in prefix P[0,i]
					if(i<pattern.length() &&pattern.substring(0, i+1).indexOf(alphabet[j])<0) FA[j][i] = 0;
					else{
						String prev = pattern.substring(0,i);
						FA[j][i] = getState(prev.substring(1)+alphabet[j], prev);
					}
				}
			}
		}
	}
	
	public int getState(String substring, String string){
		if(substring.length()==1){
			if(string.charAt(0)==substring.charAt(0)) return 1;
			else return 0;
		}
		else{
			if(substring.compareTo(string.substring(0, substring.length()))!=0)
				return getState(substring.substring(1), string);
			else return substring.length();
		}
	}
	
	public void search(){
		int state = 0;
		int occur = 0;
		for(int i=0; i<input.length();i++){
			state = FA[charInAlphabet(input.charAt(i))][state];
			if(state==pattern.length()) occur++;
		}
		System.err.println(occur);
	}
	
	public int charInAlphabet(char c){
		
		int index = 0;
		for(int i=0; i<alphabet.length; i++){
			if(alphabet[i]==c){
				index=i;
			}
		}
		return index;
	}
	
	public void showFA(){
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<=pattern.length(); i++){
			for(int j=0; j<alphabet.length; j++){
				sb.append(FA[j][i]);
				sb.append(" ");
			}
			sb.append("\n");
		}
		
		System.err.println(sb.toString());
	}
}
