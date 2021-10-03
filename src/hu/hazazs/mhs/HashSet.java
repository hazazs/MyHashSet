package hu.hazazs.mhs;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public final class HashSet<E> implements Set<E> {

	private LinkedList<Object>[] elements;
	private int size;
	private boolean resize = true;

	HashSet() {
		this(16);
	}

	@SuppressWarnings("unchecked")
	HashSet(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("Capacity must be greater than zero!");
		}
		elements = new LinkedList[capacity];
		for (int i = 0; i < capacity; i++) {
			elements[i] = new LinkedList<Object>();
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean add(E element) {
		if (contains(element)) {
			return false;
		}
		int index = Math.abs(element.hashCode()) % elements.length;
		elements[index].add(element);
		size++;
		resizeIfNecessary();
		return true;
	}

	@Override
	public boolean contains(Object element) {
		int index = Math.abs(element.hashCode()) % elements.length;
		return elements[index].contains(element);
	}

	@Override
	public boolean remove(Object element) {
		int index = Math.abs(element.hashCode()) % elements.length;
		if (elements[index].remove(element)) {
			size--;
			resizeIfNecessary();
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		for (int i = 0; i < elements.length; i++) {
			elements[i] = new LinkedList<Object>();
		}
		size = 0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[");
		for (E element : this) {
			builder.append(element).append(", ");
		}
		if (size > 0) {
			builder.delete(builder.length() - 2, builder.length());
		}
		return builder.append("]").toString();
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private int indexOfArray;
			private int indexOfLinkedList;
			private int number;

			@Override
			public boolean hasNext() {
				return number < size;
			}

			@SuppressWarnings("unchecked")
			@Override
			public E next() {
				number++;
				if (indexOfLinkedList == elements[indexOfArray].getSize()) {
					indexOfLinkedList = 0;
					do {
						indexOfArray++;
					} while (elements[indexOfArray].getSize() == 0);
				}
				return (E) elements[indexOfArray].get(indexOfLinkedList++);
			}
		};
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		for (Object element : collection) {
			if (!contains(element)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		int size = size();
		for (E element : collection) {
			add(element);
		}
		return size() > size;
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		Set<E> elementsToRemove = new HashSet<>(elements.length);
		for (E element : this) {
			if (!collection.contains(element)) {
				elementsToRemove.add(element);
			}
		}
		return removeAll(elementsToRemove);
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		int size = size();
		for (Object element : collection) {
			remove(element);
		}
		return size() < size;
	}

	@Override
	public Object[] toArray() {
		Object[] elements = new Object[size];
		int i = 0;
		for (E element : this) {
			elements[i++] = element;
		}
		return elements;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] elements) {
		if (elements.length < size) {
			elements = (T[]) Array.newInstance(Object.class, size);
		}
		int i = 0;
		for (E element : this) {
			elements[i++] = (T) element;
		}
		return elements;
	}

	private void resizeIfNecessary() {
		if (!resize) {
			return;
		}
		if (size > elements.length) {
			resize(elements.length * 2);
		}
		if (size * 3 < elements.length) {
			resize(elements.length / 2);
		}
	}

	private void resize(int capacity) {
		HashSet<E> temp = new HashSet<>(capacity);
		temp.resize = false;
		temp.addAll(this);
		elements = temp.elements;
	}

}