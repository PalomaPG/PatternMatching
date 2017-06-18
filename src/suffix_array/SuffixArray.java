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
		d=48;

		int test= (input.length()-1) % 3;
		if( test==0){
			System.err.println("Caso 0");
			this.input = new StringBuilder().append(input).append("$$$").toString();
		}
		else if(test ==1){
			System.err.println("Caso 1");
			this.input = new StringBuilder().append(input).append("$$").toString();
		}
		else {
			System.err.println("Caso 2");
			this.input = new StringBuilder().append(input).append("$").toString();
		}
		n = this.input.length();
		setToken_string("");
		mapped_token = new HashMap<String, Character>();
		this.words =words;
	}
	
	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public void createMod12(){

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
	}
	
	public void buildTokenStringSA(){
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
		if(n%3 ==0)
			mod0 = new Pair[(int)Math.floorDiv(n,3)];
		else if(n%3!=0)
			mod0 = new Pair[(int)Math.floorDiv(n,3)];
		
		
		for(int i=0; i<mod0.length; i++){
			mod0[i] = new Pair(i*3,n);
		}
		
		RadixSort rs = new RadixSort(input);
		rs.sort(mod0);
		rs = null;
		System.gc();
	}
	
	public String getKey(char look){
		String string= null;
		//System.err.println(String.format("MAppedtoken size: %d", mapped_token.size()));
		for (Entry<String, Character> entry : mapped_token.entrySet()) {
            if (entry.getValue().equals(look)) {
                string=entry.getKey();
                if(string==null) System.err.println("Es null");
                break;
            }
		}
		if(string==null) System.err.println("Es null: no encuentra llave");
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
	
	public Pair getSuffixAndClean(int i){
		String mod12_string = getSuffixFromTokenString(i);
		int delim = mod12_string.indexOf("$");
		if(delim>-1) {
			mod12_string=mod12_string.substring(0, delim);
		}
		return new Pair(input.indexOf(mod12_string), n);
		
		 
	}
	
	public void buildSA(){
		//System.err.println("buildSA");
		SA = new Pair[mod12.length + mod0.length];
		int index12 = 0;
		int index0 = 0;
		String mod12_string=null;
		String mod0_string = null;
		
		for(int i=0; i<SA.length; i++){
			if(index12<mod12.length && index0<mod0.length){
				if(mod12_string==null && mod0_string==null){
					Pair p_mod12 = getSuffixAndClean(index12);
					mod12_string = input.substring(p_mod12.x,p_mod12.y);
					mod0_string = input.substring(mod0[index0].x,mod0[index0].y);
				}
				
				else if(mod12_string==null && mod0_string!=null){
					Pair p_mod12 = getSuffixAndClean(index12);
					mod12_string = input.substring(p_mod12.x,p_mod12.y);
				}
				else{
					mod0_string = input.substring(mod0[index0].x,mod0[index0].y);
				}
				
				if(mod12_string.compareTo(mod0_string)>=0){
					SA[i]=new Pair(mod0[index0].x, mod0[index0].y);
					index0++;
					mod0_string =null;
				}
				else if(mod12_string.compareTo(mod0_string)<0){
					SA[i]=getSuffixAndClean(index12);
					index12++;
					mod12_string = null;
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
	}
	
	public void skew(){
		createMod12();
		assignToken();
		writeTokenizedString();
		buildTokenStringSA();
		buildAndSortMod0();
		buildSA();
	}
	
	public void show(Pair [] p, String s){
		
		for(int i=0;i<p.length;i++)
			System.out.println(s.substring(p[i].x, p[i].y));
	}
	
	public void search(PrintWriter pw){
		for(int i=0;i<words.length;i++){
			occurrences(words[i], pw);
		}
	}
	
	public int occurrences(String s, PrintWriter pw){
		
		int occur;
		long delta = System.currentTimeMillis();
		int min_ = searchMIN(0, SA.length, s, s.length());
		int max_ =searchMAX(0, SA.length, s, s.length());
		delta= System.currentTimeMillis()-search_time;
		occur=max_-min_-1;
		pw.print(String.format("%d, %d, %d\n",s.length(), delta, occur));
		search_time +=delta;
		return occur;
	}

	public static final int NOT_FOUND = -1;
	

	
	public int searchMIN(int init, int end, String pattern, int p_len){
		
		if(init>end) return NOT_FOUND;
		if(init==end) return init;
		if(end-init==1){
			String aux1 = input.substring(SA[init].x,SA[init].y);
			String aux2 = input.substring(SA[end].x,SA[end].y);
			if(aux1.startsWith(pattern) && init==0) return init-1;
			else{
				if(!aux1.startsWith(pattern) && aux2.startsWith(pattern)) return init;
				else if(!aux1.startsWith(pattern) && !aux2.startsWith(pattern)){
					String aux;
					if(end<SA.length-1){
						 aux= input.substring(SA[end+1].x,SA[end+1].y);
						 if(aux.startsWith(pattern)) return init;
					}
					else return NOT_FOUND;
				}
				return NOT_FOUND;
			}
		}

		int mid = init + (end-init)/2;
		String aux = input.substring(SA[mid].x,SA[mid].y);

		if(aux.length()>=p_len){
			if(aux.substring(0,p_len).compareTo(pattern)>= 0)
				return searchMIN(init, mid-1, pattern, p_len);
			else{
				aux = input.substring(SA[mid+1].x,SA[mid+1].y);
				if(aux.length()<p_len) return searchMIN(mid+1,end, pattern,p_len);
				else{
					if(aux.substring(0, p_len).compareTo(pattern)==0) return mid;
					else return searchMIN(mid+1,end, pattern,p_len);
				}
			}
		}
		else{
			if(aux.compareTo(pattern)>0) return searchMIN(init, mid-1, pattern, p_len);
			else{
				aux = input.substring(SA[mid+1].x,SA[mid+1].y);
				if(aux.length()>=p_len && aux.substring(0, p_len).compareTo(pattern)==0)
					return mid;
				else return searchMIN(mid+1,end, pattern, p_len);
			}
		}
	}

	public int searchMAX(int init, int end, String pattern, int p_len){
		
		if(init>end) return NOT_FOUND;
		if(init==end) return init;
		if(end-init==1){
			String aux1 = input.substring(SA[init].x,SA[init].y);
			String aux2 = input.substring(SA[end].x,SA[end].y);
			if(aux2.startsWith(pattern) && end==SA.length-1) return end+1;
			else if(aux1.startsWith(pattern) && !aux2.startsWith(pattern)) return end;
			else if(!aux1.startsWith(pattern) && !aux2.startsWith(pattern)){
				String aux;
				if(init>0){
					 aux= input.substring(SA[init-1].x,SA[init-1].y);
					 if(aux.startsWith(pattern)) return init;
				}
				else return NOT_FOUND;
			}
			return NOT_FOUND;
		}
		int mid = init + (end-init)/2;
		String aux = input.substring(SA[mid].x,SA[mid].y);

		if(aux.length()>=p_len){
			if(aux.substring(0,p_len).compareTo(pattern)<= 0)
				return searchMAX(mid+1, end, pattern, p_len);
			else{
				aux = input.substring(SA[mid-1].x,SA[mid-1].y);
				if(aux.length()<p_len) return searchMAX(init,mid-1, pattern,p_len);
				else{
					if(aux.substring(0, p_len).compareTo(pattern)==0) return mid;
					else return searchMAX(init,mid-1, pattern,p_len);
				}
			}
		}
		else{
			if(aux.compareTo(pattern)<0) return searchMAX(mid+1, end, pattern, p_len);
			else{
				aux = input.substring(SA[mid-1].x,SA[mid-1].y);
				if(aux.length()>=p_len && aux.substring(0, p_len).compareTo(pattern)==0)
					return mid;
				else return searchMAX(init,mid-1, pattern, p_len);
			}
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

	public Pair[] getSA() {
		// TODO Auto-generated method stub
		return SA;
	}
	
}
