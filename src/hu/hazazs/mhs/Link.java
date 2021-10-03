package hu.hazazs.mhs;

public final class Link<E> {

	private E value;
	private Link<E> next;

	Link() {
	}

	Link(E value, Link<E> next) {
		this.value = value;
		this.next = next;
	}

	E getValue() {
		return value;
	}

	Link<E> getNext() {
		return next;
	}

	void setNext(Link<E> next) {
		this.next = next;
	}

}