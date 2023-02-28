package prog09;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

public class SortTest<E extends Comparable<E>> {
  public static void main (String[] args) {
    // tests(new InsertionSort<Integer>());
    // tests(new HeapSort<Integer>());
    // tests(new QuickSort<Integer>());
    tests(new MergeSort<Integer>());
  }

  public static void tests (Sorter<Integer> sorter) {
    test(sorter, 10);
    test(sorter, 100);
    test(sorter, 1000);
    test(sorter, 10000);
    test(sorter, 100000);
  }
  
  public static void test (Sorter<Integer> sorter, int n) {
    if (sorter instanceof InsertionSort && n > 100)
      n /= 100;

    Integer[] array = new Integer[n];
    Random random = new Random(0);
    for (int i = 0; i < n; i++)
      array[i] = random.nextInt(n);

    SortTest<Integer> tester = new SortTest<Integer>();
    tester.test(sorter, array);
  }

  public void test (Sorter<E> sorter, E[] array) {
    System.out.println(sorter + " on array of length " + array.length);

    if (!notSorted(array))
      System.out.println("array is already sorted!");

    E[] copy = array.clone();
    long time1 = System.nanoTime();
    sorter.sort(copy);
    long time2 = System.nanoTime();
    double c = (double) (( (time2 - time1 ) / 1000 ) / sorter.O(array.length)); 
    System.out.println((time2-time1)/1000.0 + " microseconds with constant " + c);

    if (differentElements(array, copy))
      System.out.println("sorted array does not have the same elements!");

    if (notSorted(copy))
      System.out.println("sorted array is not sorted");

    if (array.length < 100) {
      print(array);
      print(copy);
    }
  }

  public void print (E[] array) {
    String s = "";
    for (E e : array)
      s += e + " ";
    System.out.println(s);
  }

  /** Check if array is sorted. */
  public boolean notSorted (E[] array) {
    for (int i = 1; i < array.length; i++) {  	
    	if (array[i].compareTo(array[i-1]) < 0)
    	return true;
    }


    return false;
  }
 
  /* Check if arrays have the same elements. */
  public boolean differentElements (E[] array1, E[] array2) {
	// EXERCISE
	    // Create two Map from E to Integer, using the HashMap implementation.
		  HashMap<E, Integer> mapInital = new HashMap();
		  HashMap<E, Integer> mapCheck = new HashMap();
		  
	    // For each element of the first array, if it is not a key in the
	    // first map, make it map to 1.  If it is already a key, increment
	    // the integer it maps to.
		  for (E data : array1) {
			  if (mapInital.containsKey(data) == false) {
				  mapInital.put(data, 1);
			  }
			  else {
				int value = (int) mapInital.get(data);
			  	mapInital.put(data, value+1);
			  }
		  }
		  
	    // Ditto the second array and second map.
		  for (E data : array2) {
			  if (mapCheck.containsKey(data) == false) {
				  mapCheck.put(data, 1);
			  }
			  else {
				int value = (int) mapCheck.get(data);
				mapCheck.put(data, value+1);
			}  
		  }
		  
	    // For each element of the second array, check that it maps to the
	    // same integer in both maps.  If not, return true.
		  for (E data : array2) {
			  if (mapInital.get(data) != mapCheck.get(data)) {
				  return true;
			  }
		  }
	  
    return false;
  }
}

class InsertionSort<E extends Comparable<E>> implements Sorter<E> {
  public double O (int n) { return n*n; }

  public void sort (E[] array) {
    for (int n = 0; n < array.length; n++) {
      E data = array[n];
      int i = n;

      // EXERCISE
      // while array[i-1] > data move array[i-1] to array[i] and
      // decrement i

      while (i > 0 && array[i-1].compareTo(data) > 0) {
    	  array[i] = array[i-1];
    	  i--;
      }

      array[i] = data;
    }
  }
}

class HeapSort<E extends Comparable<E>>
  implements Sorter<E> {

  public double O (int n) { return n * Math.log(n); }

  private E[] array;
  private int size;

  public void sort (E[] array) {
    this.array = array;
    this.size = array.length;

    for (int i = getParent(array.length - 1); i >= 0; i--) {
      swapDown(i);
    }

    while (size > 1) {
      swap(0, size-1);
      size--;
      swapDown(0);
    }
  }

  public void swapDown (int index) {
    // EXERCISE

    // While the element at index is smaller than one of its children,
    // swap it with its larger child.  Use the helper methods provided
    // below: compare, hasLeft, getLeft, hasRight, and getRight.
	  while(hasLeft(index) == true && compare(index, getLeft(index)) > 0 || 
			  hasRight(index) == true && compare(index, getRight(index)) > 0) {
		  
	      	if (hasRight(index) == true && compare(getRight(index), getLeft(index)) < 0) {
	      		swap(index, getRight(index));
	      		index = getRight(index);
	      	}
	      	
	      	else {		      		
	      		swap(index, getLeft(index));
	      		index = getLeft(index);
	      	}

	      }

  }

  private void swap (int i, int j) {
    E data = array[i];
    array[i] = array[j];
    array[j] = data;
  }

  private int compare (int i, int j) { return array[j].compareTo(array[i]); }
  private boolean hasLeft (int i) { return 2 * i + 1 < size; }
  private int getLeft (int i) { return 2 * i + 1; }
  private boolean hasRight (int i) { return 2 * i + 2 < size; }
  private int getRight (int i) { return 2 * i + 2; }
  private int getParent (int i) { return (i - 1) / 2; }
  private boolean isNull (int i) { return i >= size; }
}

class QuickSort<E extends Comparable<E>>
  implements Sorter<E> {

  public double O (int n) { return n*n; }

  private E[] array;

  private InsertionSort<E> insertionSort = new InsertionSort<E>();

  private void swap (int i, int j) {
    E data = array[i];
    array[i] = array[j];
    array[j] = data;
  }

  public void sort (E[] array) {
    this.array = array;
    sort(0, array.length-1);
  }

  private void sort(int first, int last) {
    if (first >= last)
      return;

    swap(first, (first + last) / 2);
    E pivot = array[first];

    int a = first + 1;
    int b = last;
    while (a <= b) {
      // EXERCISE

      // Move a forward if array[a] <= pivot
      // Otherwise move b backward if array[b] > pivot
      // Otherwise swap array[a] and array[b] and move both a and b.
    	if(array[a].compareTo(pivot) <= 0)
    		a++;
    	else if (array[b].compareTo(pivot) > 0)
    		b--;
    	else {
    	swap(a, b);
    	a++;
    	b--;
    	}
    }

    swap(first, b);

    sort(first, b-1);
    sort(b+1, last);
  }
}

class MergeSort<E extends Comparable<E>>
  implements Sorter<E> {

  public double O (int n) { return n * Math.log(n); }

  private E[] array, array2;

  public void sort (E[] array) {
    this.array = array;
    array2 = array.clone();
    sort(0, array.length-1);
  }

  private void sort(int first, int last) {
    if (first >= last)
      return;

    int middle = (first + last) / 2;
    sort(first, middle);
    sort(middle+1, last);

    int i = first;
    int a = first;
    int b = middle+1;
    while (a <= middle && b <= last) {
      // EXERCISE

      // Copy the smaller of array[a] or array[b] to array2[i]
    	if (array[a].compareTo(array[b]) <= 0) {
    		array2[i] = array[a];
    		i++;
    		a++;
    	}
    	else {
    		array2[i] = array[b];
    		i++;
    		b++;
    	}
      // (in the case of a tie, copy array[a] to keep it stable)
      // and increment i and a or b (the one you copied).

    }

    // Copy the rest of a or b, whichever is not at the end.
    while (a <= middle) {
    	array2[i] = array[a];
    	i++;
    	a++;
    }
    while (b <= last) {
    	array2[i] = array[b];
    	i++;
    	b++;
    }
    
    System.arraycopy(array2, first, array, first, last - first + 1);
  }
}
