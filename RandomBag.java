import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Collections;
import java.util.Comparator;

public class RandomBag<T> extends ArrayList<T> {
	
	private List<T> list = new ArrayList<T>();

	public RandomBag(T ... objects) {
		for (T object: objects) {
			list.add(object);


			//What that looks like on the backend:
		
			//Iterator<T> iterator = new RandomIterator();
			// while (iterator.hasNext()) {
			// 	list.add(iterator.next());
			// }
		}
	}


	/*
		Smple way to implement an Iterator class


		private class MyIterator implements Iterator<T> {
			int cursor; // <---

			// [1,2,3,4,5]
		    //    	^
			//		^ cursor = 2 

			//grabs the next thing in the List;
			public T next() {
				T chosen = array[cursor];
				cursor++;
		     return chosen;
			}
			//am I at the last element
			public boolean hasNext() {
				return cursor<array.length;
			}
		}


		//Then you would have the iterator() method


		public Iterator<T> iterator() {
			return new MyIterator();
		}
	*/

	private class RandomIterator implements Iterator<T> {
		private int cursor = 0; //cursor to keep track of where I am in the list
		private Set<Integer> iteratedSet = new HashSet<Integer>(); //hash set to allow me to keep track of used iterated values
		private Random rand = new Random(); //new random objwct to grab iterator

		public boolean hasNext() {
			return cursor < list.size();
		}

		public T next() {
			if (!hasNext()) {
				iteratedSet.clear(); //reset the set when done iterating
				throw new NoSuchElementException();
			}
			int randNumber = rand.nextInt(list.size()); //random index to use
			while (iteratedSet.contains(randNumber)) { //if used, find new random index
				randNumber = rand.nextInt(list.size());
				

			}
			iteratedSet.add(randNumber); //add newly used index to set
			cursor++; //on to the next element 
			return list.get(randNumber); //return the element in question
		}

	}



	@Override
	public Iterator<T> iterator() {return new RandomIterator();} //using Iterable(iterator()) and Iterator(new class)

	public static void main(String[] args) {
		List<Integer>  bag = new RandomBag<Integer>(1,2,3,4,5,6);
		for (int s: bag) {
			System.out.println(s);
		}

		// to play with teh random iterator, just run java RandomBag mutltiple times

		Collections.sort(bag); // <-- uses compareTo method of Integer from Iterable
		Collections.sort(bag, new ReverseOrderComparator()); // <-- uses compare method in Iterator
	}

	//custom comparing
	private static class ReverseOrderComparator implements Comparator<Integer>  {

		public int compare(Integer a, Integer b) {
			return -a.compareTo(b);	
		}
	}
	

	//corresponding Comparable compareTo method is separate from Comparator compare(T,T) cursor++;
}