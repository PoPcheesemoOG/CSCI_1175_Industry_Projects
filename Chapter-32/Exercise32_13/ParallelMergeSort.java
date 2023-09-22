import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;


public class ParallelMergeSort <E> {
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

//	public static void parallelMergeSort(int[] list) {
//		RecursiveAction mainTask = new SortTask(list);
//		ForkJoinPool pool = new ForkJoinPool();
//		pool.invoke(mainTask);
//	}
	
	public static <E extends Comparable<E>> void parallelMergeSort (E[] list) {
		RecursiveAction mainTask = new SortTask <E> (list);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(mainTask);
		
		
	}
	
//	private static class SortTask<E> extends RecursiveAction {
//
//		@Override
//		protected void compute() {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}

	protected static class SortTask <E> extends RecursiveAction {	
		private final int THRESHOLD = 500;
		protected E[] list;

		SortTask (E[] list1) {
			list = list1;
		}
		@Override
		protected void compute() {
			if (list.length < THRESHOLD)
				java.util.Arrays.sort(list);
			else {
				// Obtain the first half
				E[] firstHalf  = (E[]) new Object[list.length / 2];
				System.arraycopy(list, 0, firstHalf, 0, list.length / 2);

				// Obtain the second half
				Integer secondHalfLength = list.length - list.length / 2;
				E[] secondHalf =(E[])  new Object[secondHalfLength];
				System.arraycopy(list, list.length / 2, 
					secondHalf, 0, secondHalfLength);

				// Recursively sort the two halves
				invokeAll(new SortTask<E>(firstHalf), 			// Re-evaluate
					new SortTask<E>(secondHalf));				// Re-evaluate

				// Merge firstHalf with secondHalf into list
				MergeSort.merge(firstHalf, secondHalf, list);	// Problem Line
								
			}
		}
	}
}