package a4;

public interface SortAlg {

	/** sort the array a in place
	 * @param a int[] the numbers to sort
	 */
	public void sort(int[] a);

	/** sets the number of threads the sort should use
	 * @param numThreads int, the number of threads to use
	 * @return int number actually used, 
	 *         single threaded implementations return 1
	 */
	public int setNumThreads(int numThreads);
}
