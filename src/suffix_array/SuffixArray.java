package suffix_array;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

public class SuffixArray {

	private int [] suffix_array;
	private String [] SA;
	private String token_string;
	private String [] tokenSA;
	private String [] mod12;
	private String [] mod0;
	HashMap<String, Character> mapped_token; 
	int n;
	private String input;
	private RadixSort rs;
	char d;
	private long exec_time;
	
	public SuffixArray(String input){
		d=0;
		this.input = input;
		n = input.length();
		setToken_string("");
		mapped_token = new HashMap<String, Character>();
		//suffix_array = new int [n];
	}
	
	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public void createMod120(){
		/*This could not be a multiple of 3*/
		exec_time = System.currentTimeMillis();
		LinkedList<String> mod1_s = new LinkedList<String>();
		LinkedList<String> mod2_s = new LinkedList<String>();
	
		for(int i=1;i<n;i++){
			if(i % 3 == 1 && input.substring(i).length()>=3){
				mod1_s.add(input.substring(i, i+3));
				
			}
		}
		for(int i=1;i<n ;i++){
			if(i % 3 == 2 && input.substring(i).length()>=3){
				mod2_s.add(input.substring(i, i+3));
			}
		}
		mod1_s.addAll(mod2_s);
		mod12 =(String []) mod1_s.toArray(new String[mod1_s.size()]);
		rs = new RadixSort();
		
	}
	
	public void assignToken(){
		
		String [] aux_array = new String[mod12.length];
		for(int i=0; i<mod12.length;i++)
			aux_array[i] = mod12[i];
		
		rs.sort(aux_array);
		
		for(int i = 0; i<mod12.length; i++){
			
			if(!mapped_token.containsKey(aux_array[i])){
				//System.err.println(String.format("String: %s, char: %c", aux_array[i], d));
				mapped_token.put(aux_array[i], d);
				d++;
			}
		}

	}
	

	
	public long getExec_time() {
		return exec_time;
	}

	public void setExec_time(long exec_time) {
		this.exec_time = exec_time;
	}

	public void writeTokenizedString(){
		
		for(int i=0; i<mod12.length; i++){
				token_string += mapped_token.get(mod12[i]);

		}
	}
	
	public void buildTokenStringSA(){
		
		LinkedList<String> suffixes = new LinkedList<String>();
		
		for(int i=0; i<token_string.length();i++)
			suffixes.add(token_string.substring(i));
		
		tokenSA = suffixes.toArray(new String[suffixes.size()]);
		
		
		rs.sort(tokenSA);

		
	}
	
	public void buildAndSortMod0(){
		LinkedList<String> mod0_l = new LinkedList<String>();
		for(int i=0; i<n; i++){
			if(i % 3 ==0 && input.substring(i).length()>=3)
				mod0_l.add(input.substring(i));
		}
		mod0 = (String []) mod0_l.toArray(new String [mod0_l.size()]);
		rs.sort(mod0);
	}
	
	public String getKey(char c){
		String string= null;
		for (Entry<String, Character> entry : mapped_token.entrySet()) {
            if (entry.getValue().equals(c)) {
                string=entry.getKey();
                break;
            }
		}
		return string;
	}
	
	public String getSuffixFromTokenString(int i){
		
		String tofind = tokenSA[i];
		StringBuilder sb = new StringBuilder();
		
		for(int j=0;j<tofind.length(); j++)
		{
			sb.append(getKey(tofind.charAt(j)));
		}
		return sb.toString();
	} 
	
	public void buildSA(){
		
		SA = new String[mod12.length + mod0.length];
		int index12 = 0;
		int index0 = 0;
		int delim;
		for(int i=0; i<SA.length && index12<mod12.length; i++){
			String mod12_string = getSuffixFromTokenString(index12);
			if(index0<mod0.length && mod12_string.compareTo(mod0[index0])>=0){
				delim = mod0[index0].indexOf("$");
				if(delim>-1)
					SA[i]=mod0[index0].substring(0, delim);
				else
					SA[i]=mod0[index0];
				index0++;
				
			}
			else if(index0<mod0.length && mod12_string.compareTo(mod0[index0])<0){
				delim = mod12_string.indexOf("$");
				if(delim>-1)
					SA[i] = mod12_string.substring(0, delim);
				else
					SA[i]=mod12_string;
				index12++;
				
			}
			else{
				SA[i]=mod12_string;
				index12++;
			}
		}
		exec_time = System.currentTimeMillis()-exec_time;
		System.err.println(exec_time);
	}
	
	public int patternExists(){
		
		int occur = 0;
		
		return occur;
	}
	
	
	public int [] merge(int [] S, int [] comp_S){
		return suffix_array;
	}

	public String getToken_string() {
		return token_string;
	}

	public void setToken_string(String token_string) {
		this.token_string = token_string;
	}
	
}
