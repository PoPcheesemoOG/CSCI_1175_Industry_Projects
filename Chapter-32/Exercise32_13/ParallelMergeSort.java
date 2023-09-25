import java.util.concurrent.RecursiveAction;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSort extends MergeSort{
	public static void main(String[] args) {
		final int SIZE = 7000000;
		Integer[] list1 = new Integer[SIZE];
		Integer[] list2 = new Integer[SIZE];

		for (int i = 0; i < list1.length; i++)
			list1[i] = list2[i] = (int)(Math.random() * 10000000);

		long startTime = System.currentTimeMillis();
		parallelMergeSort(list1); // Invoke parallel merge sort
		long endTime = System.currentTimeMillis();
		System.out.println("\nParallel time with "
			+ Runtime.getRuntime().availableProcessors() + 
			" processors is " + (endTime - startTime) + " milliseconds");

		startTime = System.currentTimeMillis();
		MergeSort.mergeSort(list2); // MergeSort is in Listing 24.5
		endTime = System.currentTimeMillis();
		System.out.println("\nSequential time is " + 
			(endTime - startTime) + " milliseconds");
	}
	
	public static <E extends Comparable<E>> void parallelMergeSort (E[] list) {
		RecursiveAction mainTask = new SortTask<E> (list);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(mainTask);
		
		
	}

	public static class SortTask<E extends Comparable<E>> extends RecursiveAction {	
		public final int THRESHOLD = 500;
		public E[] Elist;

		SortTask (E[] list1) {
			Elist =(E[]) list1;
		}
		@Override
		public void compute() {
			if (Elist.length < THRESHOLD)
				java.util.Arrays.sort(Elist);
			else {
				// Obtain the first half
				E[] firstHalf  = newArray(Elist.length/2, Elist);   //  [Elist.length / 2];   // new E[Elist.length / 2]
				System.arraycopy(Elist, 0, firstHalf, 0, Elist.length / 2);

				// Obtain the second half
				Integer secondHalfLength = Elist.length - Elist.length / 2;
				E[] secondHalf =newArray(secondHalfLength, Elist);
				System.arraycopy((E[]) Elist, Elist.length / 2, 
					secondHalf, 0, secondHalfLength);

				// Recursively sort the two halves
				invokeAll(new SortTask<E>((E[]) firstHalf), 			// Re-evaluate
					new SortTask<E>((E[]) secondHalf));				// Re-evaluate

				// Merge firstHalf with secondHalf into list
				MergeSort.merge(firstHalf, secondHalf, Elist);// Problem Line
								
			}
		}
	}
	static <E> E[] newArray(int length, E[] array)
	{
	    return Arrays.copyOf(array, length);
	}
}