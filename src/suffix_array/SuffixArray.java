package suffix_array;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.ArrayUtils;

public class SuffixArray {

	
	private Pair [] SA;
	private String token_string;
	private Pair [] tokenSA;
	private Pair []  mod12;
	private Pair [] mod0;
	protected String [] words; 
	HashMap<String, Character> mapped_token; 
	int n;
	private String input;
	char d;
	private long build_time, search_time;
	
	public SuffixArray(String input, String [] words){
		d=100;
		this.input = input;
		n = input.length();
		setToken_string("");
		mapped_token = new HashMap<String, Character>();
		this.words =words;
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
		//System.err.println("createMod120");
		build_time = System.currentTimeMillis();
		Pair [] mod1, mod2;
		
		if(n%3==0){
			mod1 = new Pair[(int)Math.floorDiv(n, 3)-1];
			mod2 = new Pair[(int)Math.floorDiv(n, 3)-1];
			
			for(int i=0; i<mod1.length; i++){
				mod1[i] = new Pair(3*i+1, 3*(i+1)+1);
				mod2[i] = new Pair(3*i+2, 3*(i+1)+2);
			}
		}
		
		else if(n%3==1){
			mod1 = new Pair[(int)Math.floorDiv(n, 3)];
			mod2 = new Pair[(int)Math.floorDiv(n, 3)-1];
			int i;
			for(i=0; i<mod2.length; i++){
				mod1[i] = new Pair(3*i+1, 3*(i+1)+1);
				mod2[i] = new Pair(3*i+2, 3*(i+1)+2);
			}
			mod1[i] = new Pair(3*i+1, 3*(i+1)+1);
		}
		
		else{
			mod1 = new Pair[(int)Math.floorDiv(n, 3)-1];
			mod2 = new Pair[(int)Math.floorDiv(n, 3)];
			
			int i;
			for(i=0; i<mod1.length; i++){
				mod1[i] = new Pair(3*i+1, 3*(i+1)+1);
				mod2[i] = new Pair(3*i+2, 3*(i+1)+2);
			}
			mod2[i] = new Pair(3*i+1, 3*(i+1)+1);
		}
		
		mod12 = ArrayUtils.addAll(mod1,mod2);
		
		mod1 = null;
		mod2 =null;
		System.gc();
		
	}
	
	public void assignToken(){
		
		//System.err.println("assignToken");
		Pair [] aux_mod12 = mod12.clone();
		
		RadixSort rs = new RadixSort(input);
		rs.sort(aux_mod12);

		
		for(int i = 0; i<aux_mod12.length; i++){
			String s = input.substring(aux_mod12[i].getX(), aux_mod12[i].getY());
			if(!mapped_token.containsKey(s)){
				mapped_token.put(s, d);
				d++;
			}
		}
		
		aux_mod12 =null;
		rs =null;
		System.gc();
	}
	

	
	public long getbuild_time() {
		return build_time;
	}

	public void setbuild_time(long build_time) {
		this.build_time = build_time;
	}

	public void writeTokenizedString(){
		//System.err.println("writeTokenizedString");
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<mod12.length; i++){
			String s = input.substring(mod12[i].getX(), mod12[i].getY());
			sb.append(mapped_token.get(s));

		}
		token_string= sb.toString();
		
		//mod12 = null;
		//System.gc();
	}
	
	public void buildTokenStringSA(){
		//System.err.println("buildTokenStringSA");
		tokenSA = new Pair[token_string.length()];
		for(int i=0; i<token_string.length();i++){
			tokenSA[i] = new Pair(i, token_string.length());

		}
		RadixSort rs = new RadixSort(token_string);
		rs.sort(tokenSA);

		rs =null;
		System.gc();
	}
	
	public void buildAndSortMod0(){
		//System.err.println("buildAndSortMod0");
		if(n%3 ==0)
			mod0 = new Pair[(int)Math.floorDiv(n,3)];
		else if(n%3!=0)
			mod0 = new Pair[(int)Math.floorDiv(n,3)-1];
		
		
		for(int i=0; i<mod0.length; i++){
			mod0[i] = new Pair(i*3,n);
		}
		
		RadixSort rs = new RadixSort(input);
		rs.sort(mod0);
		
		rs = null;
		System.gc();
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
		
		String tofind = token_string.substring(tokenSA[i].x, tokenSA[i].y);
		StringBuilder sb = new StringBuilder();
		
		for(int j=0;j<tofind.length(); j++)
		{
			sb.append(getKey(tofind.charAt(j)));
		}
		return sb.toString();
	} 
	
	public void seeMod12(){
		
		int x;
		for(int i=0; i<mod12.length; i++){
			String mod12_string = getSuffixFromTokenString(i);
			int delim = mod12_string.indexOf("$$");
			if(delim>-1)
				mod12_string = mod12_string.substring(0, delim+1);
			
			x=input.indexOf(mod12_string);
			mod12[i].setX(x);
			mod12[i].setY(input.length());
		}
		
	}
	
	public void buildSA(){
		//System.err.println("buildSA");
		SA = new Pair[mod12.length + mod0.length];
		int index12 = 0;
		int index0 = 0;

		
		for(int i=0; i<SA.length; i++){
			if(index12<mod12.length && index0<mod0.length){
				String mod12_string =input.substring(mod12[index12].x,mod12[index12].y);
				String mod0_string = input.substring(mod0[index0].x,mod0[index0].y);
				
				if(mod12_string.compareTo(mod0_string)>=0){
					SA[i]=new Pair(mod0[index0].x, mod0[index0].y);
					index0++;
				}
				else if(mod12_string.compareTo(mod0_string)<0){

					SA[i]=new Pair(mod12[index12].x, mod12[index12].y);
					index12++;
					
				}
			}
			
			else if(index0<mod0.length && index12>=mod12.length){
				SA[i]=new Pair(mod0[index0].x, mod0[index0].y);
				index0++;
			}
			
			else if(index12<mod12.length && index0>=mod0.length){
				SA[i]=new Pair(mod12[index12].x, mod12[index12].y);
				index12++;
			}
			
			else break;
		}

		build_time = System.currentTimeMillis()-build_time;
		//System.err.println(build_time);
	}
	
	public void skew(){
		createMod120();
		buildAndSortMod0();
		assignToken();
		writeTokenizedString();
		buildTokenStringSA();
		buildSA();
	}
	
	public void show(Pair [] p, String s){
		
		for(int i=0;i<p.length;i++)
			System.out.println(s.substring(p[i].x, p[i].y));
	}
	
	public void search(PrintWriter pw){
		//System.err.println("Searching words in search method");
		for(int i=0;i<words.length;i++){
			occurrences(words[i], pw);
		}
	}
	
	public int occurrences(String s, PrintWriter pw){
		
		int occur = 0;
		long delta = System.currentTimeMillis();
		int min_ = searchMIN(0, SA.length, s, s.length());
		int max_ = searchMAX(0, SA.length, s, s.length());
		delta= System.currentTimeMillis()-search_time;
		pw.print(String.format("%d, %d, %d\n",s.length(), delta, occur));
		occur=max_-min_-1;
		search_time +=delta;
		return occur;
	}
	
	public int searchMAX(int init,int end, String pattern, int pattern_length){
		
		int mid = (end-init)/2;
		String aux = input.substring(SA[mid].x, SA[mid].y);
		if(aux.substring(0,pattern_length).compareTo(pattern)>0){
			aux = input.substring(SA[mid-1].x, SA[mid-1].y);
			if(aux.substring(0,pattern_length).compareTo(pattern)==0)
				return mid;
			else return searchMAX(init, mid, pattern, pattern_length);
		}
		else if(aux.substring(0,pattern_length).compareTo(pattern)<0){
			return searchMAX(mid, end, pattern, pattern_length);
		}
		else{
			aux = input.substring(SA[mid+1].x, SA[mid+1].y);
			if(aux.substring(0,pattern_length).compareTo(pattern)>0)
				return mid+1;
			else return searchMAX(mid, end, pattern, pattern_length);
		}

	}

	public int searchMIN(int init,int end, String pattern, int pattern_length){
		
		int mid = (end-init)/2;
		String aux = input.substring(SA[mid].x, SA[mid].y);
		if(aux.substring(0,pattern_length).compareTo(pattern)<0){
			aux = input.substring(SA[mid+1].x, SA[mid+1].y);
			if(aux.substring(0,pattern_length).compareTo(pattern)==0)
				return mid;
			else return searchMAX(mid, end, pattern, pattern_length);
		}
		else if(aux.substring(0,pattern_length).compareTo(pattern)>0){
			return searchMAX(init, mid, pattern, pattern_length);
		}
		else{
			aux = input.substring(SA[mid-1].x, SA[mid-1].y);
			if(aux.substring(0,pattern_length).compareTo(pattern)<0)
				return mid-1;
			else return searchMAX(init, mid, pattern, pattern_length);
		}
	}

	public long getSearch_time() {
		return search_time;
	}

	public void setSearch_time(long search_time) {
		this.search_time = search_time;
	}

	public String getToken_string() {
		return token_string;
	}

	public void setToken_string(String token_string) {
		this.token_string = token_string;
	}
	
}
