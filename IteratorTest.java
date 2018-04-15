import java.util.*;
//class to create iterator
public class DigitsBag {
	private List<Integer> digitList = new ArrayList<>();
	public DigitsBag(Integer ...) {
		for (Integer digit: digits) {
			digitsList.add(digit);
		}
	}

}
private class PapasIterator implements Iterator<String> {
	private int cursor = 0;
	@Override
	public boolean hasNext() {
		return cursor < digitsList.size();
	}
	@Override
	public int next() {
		if(!hasNext()) {
			throw new NoSuchElementException();
		}
		int next = digitsList.get(cursor);
		cursor++;
		return next;
	}


}