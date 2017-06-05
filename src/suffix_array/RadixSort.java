package suffix_array;
import java.util.Random;
public class RadixSort {

	private  Random random;
	private static long seed;
	String in;

	
	public RadixSort(String in){
		seed = System.currentTimeMillis();
		random = new Random(seed);
		this.in = in;
	
		}
	
	
    public void sort(Pair[] a) {
        shuffle(a);
        sort(a, 0, a.length -1);
        assert isSorted(a);
    }

    public void shuffle(Pair [] a) {
        if (a == null) throw new IllegalArgumentException("argument array is null");
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(n-i);     // between i and n-1
            Pair temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
    // quicksort the subarray a[lo .. hi] using 3-way partitioning
    private void sort(Pair[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, gt = hi;
        String v = in.substring(a[lo].x, a[lo].y);
        int i = lo;
        while (i <= gt) {
        	String ai = in.substring(a[i].x, a[i].y);
            int cmp = ai.compareTo(v);
            if      (cmp < 0) exch(a, lt++, i++);
            else if (cmp > 0) exch(a, i, gt--);
            else              i++;
        }

        // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi]. 
        sort(a, lo, lt-1);
        sort(a, gt+1, hi);
        assert isSorted(a, lo, hi);
    }



   /***************************************************************************
    *  Helper sorting functions.
    ***************************************************************************/
    
    // is v < w ?
    private boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
        
    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


   /***************************************************************************
    *  Check if array is sorted - useful for debugging.
    ***************************************************************************/
    private boolean isSorted(Pair[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private boolean isSorted(Pair[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++){
        	String ai = in.substring(a[i].x, a[i].y+1);
        	String ai1 = in.substring(a[i-1].x, a[i-1].y+1);
            if (less(ai, ai1)) return false;
        }
        return true;
    }

    public int uniform(int n) {
        if (n <= 0) throw new IllegalArgumentException("argument must be positive");
        return random.nextInt(n);
    }


    public void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.err.println(a[i]);
        }
    }

	
}
