package suffix_array;
import java.util.Random;
public class RadixSort {

	private  Random random;
	private static long seed;
	
	public RadixSort(){
		seed = System.currentTimeMillis();
		random = new Random(seed);
		}
	
	
    public void sort(Comparable[] a) {
        shuffle(a);
        sort(a, 0, a.length - 1);
        assert isSorted(a);
    }

    public void shuffle(Object[] a) {
        if (a == null) throw new IllegalArgumentException("argument array is null");
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(n-i);     // between i and n-1
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
    // quicksort the subarray a[lo .. hi] using 3-way partitioning
    private void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, gt = hi;
        Comparable v = a[lo];
        int i = lo;
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
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
    private boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
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
