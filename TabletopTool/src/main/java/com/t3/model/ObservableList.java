/*
 * Copyright (c) 2014 tabletoptool.com team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     rptools.com team - initial implementation
 *     tabletoptool.com team - further development
 */
package com.t3.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;

public class ObservableList<K> extends Observable implements List<K> {

    private List<K> list;
    
    public enum Event {
        add,
        append,
        remove,
        clear,
    }

    public ObservableList() {
        list = new ArrayList<K>();
    }
    
    public ObservableList(List<K> list) {
        assert list != null : "List cannot be null";
        
        this.list = list;
    }

    @Override
	public List<K> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }
    
    // Improper Override annotation @Override
	public void sort(Comparator<? super K> comparitor) {
        Collections.sort(list, comparitor);
    }
    
    @Override
	public boolean contains(Object item) {
        return list.contains(item);
    }

    @Override
	public int indexOf(Object item) {
    	return list.indexOf(item);
    }

    @Override
	public K get(int i) {
        return list.get(i);
    }
    
    @Override
	public boolean add(K item) {
        boolean b=list.add(item);
        fireUpdate(Event.append, item);
        return b;
    }

    @Override
	public void add(int index, K element) {
        list.add(index, element);
        fireUpdate((index == list.size() ? Event.append : Event.add), element);
    }
    
    @Override
	public boolean remove(Object item) {
        boolean b=list.remove(item);
        fireUpdate(Event.remove, item);
        return b;
    }
    
    @Override
	public K remove(int i) {
        K source = list.remove(i);
        fireUpdate(Event.remove, source);
        return source;
    }
    
    @Override
	public void clear() {
        list.clear();
        fireUpdate(Event.clear, null);
    }
    
    @Override
	public int size() {
        return list.size();
    }
    
    ////
    // INTERNAL
    protected void fireUpdate(Event event, Object source) {
        setChanged();
        notifyObservers(event);
    }

    public class ObservableEvent {
        private Event event;
        private K source;
        
        public ObservableEvent(Event event, K  source) {
            this.event = event;
            this.source = source;
        }
        
        public Event getEvent() {
            return event;
        }
        
        public K getSource() {
            return source;
        }
    }
    
    /**
     * Get an iterator over the items in the list.
     * 
     * @return An iterator over the displayed list.
     */
    @Override
	public Iterator<K> iterator() {
      return list.iterator(); 
    }

	@Override
	public boolean addAll(Collection<? extends K> c) {
		boolean b=list.addAll(c);
		for(K k:c)
			fireUpdate(Event.add, c);
		return b;
	}

	@Override
	public boolean addAll(int index, Collection<? extends K> c) {
		boolean b=list.addAll(index,c);
		for(K k:c)
			fireUpdate(Event.add, c);
		return b;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<K> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<K> listIterator(int index) {
		return list.listIterator(index);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean b=list.removeAll(c);
		for(Object o:c)
			fireUpdate(Event.remove, o);
		return b;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public K set(int index, K element) {
		K old=list.set(index, element);
		if(old!=null)
			fireUpdate(Event.remove, old);
		fireUpdate(Event.add, element);
		return old;
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}
}
