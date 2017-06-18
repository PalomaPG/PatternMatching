package suffix_array;
public class RadixSort {

	String in;

	
	public RadixSort(String in){
		this.in = in;
	}
	
    public void sort(Pair[] a) {
        sort(a, 0, a.length -1);
    }

    private void sort(Pair[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, gt = hi;
        String v = in.substring(a[lo].x, a[lo].y);
        int i = lo;
        while (i <= gt) {
        	String ai = in.substring(a[i].x, a[i].y);
            int cmp = ai.compareTo(v);
            if      (cmp < 0) swap(a, lt++, i++);
            else if (cmp > 0) swap(a, i, gt--);
            else              i++;
        }

        sort(a, lo, lt-1);
        sort(a, gt+1, hi);
    }

        
    private static void swap(Pair[] a, int i, int j) {
        Pair aux = a[i];
        a[i] = a[j];
        a[j] = aux;
    }
}
