package de.servicezombie.core.util;

import java.util.Iterator;

public class IterableIterator<T> implements Iterable<T> {

	private Iterator<T> iterator;
	
	public IterableIterator(Iterator<T> iterator) {
		this.iterator = iterator;
	}

	public Iterator<T> iterator() {
		return iterator;
	}

}
