package hu.hazazs.mhs;

public final class LinkedList<E> {

	private final Link<E> start = new Link<>();

	int getSize() {
		int size = 0;
		Link<E> current = start.getNext();
		while (current != null) {
			size++;
			current = current.getNext();
		}
		return size;
	}

	boolean contains(E element) {
		Link<E> current = start.getNext();
		while (current != null) {
			if (current.getValue().equals(element)) {
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	boolean add(E element) {
		Link<E> newLink = new Link<>(element, start.getNext());
		start.setNext(newLink);
		return true;
	}

	E get(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException(String.format("The index %d is out of bounds.", index));
		}
		Link<E> current = start.getNext();
		int i = 0;
		int size = getSize();
		while (current != null) {
			if (i == size - index - 1) {
				return current.getValue();
			}
			current = current.getNext();
			i++;
		}
		throw new IndexOutOfBoundsException(String.format("The index %d is out of bounds.", index));
	}

	boolean remove(E element) {
		int size = getSize();
		Link<E> previous = start;
		Link<E> current = start.getNext();
		while (current != null) {
			if (current.getValue().equals(element)) {
				previous.setNext(current.getNext());
				break;
			} else {
				previous = current;
				current = current.getNext();
			}
		}
		return getSize() == --size;
	}

	boolean removeAll(E element) {
		int size = getSize();
		Link<E> previous = start;
		Link<E> current = start.getNext();
		while (current != null) {
			if (current.getValue().equals(element)) {
				previous.setNext(current.getNext());
			} else {
				previous = current;
			}
			current = current.getNext();
		}
		return getSize() < size;
	}

}