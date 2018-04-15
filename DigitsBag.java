import java.util.*;
//class to create iterator
public class DigitsBag<Integer> extends ArrayList<Integer> {
	private List<Integer> digitsList = new ArrayList<>();
	public DigitsBag(Integer ... digits) {
		for (Integer digit: digits) {
			digitsList.add(digit);
		}
	}


private static class PapasIterator implements Iterator<Integer> {
	private int cursor = 0;
	@Override
	public boolean hasNext() {
		return cursor < digitsList.size();
	}
	@Override
	public Integer next() {
		if(!hasNext()) {
			throw new NoSuchElementException();
		}
		Integer next = digitsList.get(cursor);
		cursor++;
		return next; }
	}
	@Override 
	public Iterator<Integer> iterator() {
		return new PapasIterator();
	}
	public static void main(String[] args) {
		List<Integer> l = new DigitsBag<Integer>(0,1,2,3,4,5,6,7,8,9);
		Iterator<Integer> it = l.iterator();
		System.out.println(it.next());
		System.out.println(it.next());
		System.out.println(it.hasNext());

	}

}