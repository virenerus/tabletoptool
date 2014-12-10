package com.t3.model.tokenproperties.instance.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.t3.model.tokenproperties.PropertyType;
import com.t3.model.tokenproperties.PropertyTypeReference;
import com.t3.model.tokenproperties.ValueType;
import com.t3.xstreamversioned.version.SerializationVersion;

/**This is an immutable list of TokenProperty instances*/
@SerializationVersion(0)
public class TokenPropertiesListImpl<T> implements TokenPropertiesList<T> {
	
	private final Object[] 		elements;
	private PropertyTypeReference	type;

	public TokenPropertiesListImpl(PropertyType type) {
		this(new PropertyTypeReference(type), new Object[0]);
	}
	
	public TokenPropertiesListImpl(PropertyType type, List<T> elements) {
		this(new PropertyTypeReference(type), elements.toArray());
	}
	
	public TokenPropertiesListImpl(PropertyType newType, TokenPropertiesListImpl<T> content) {
		this(new PropertyTypeReference(newType), content.elements);
	}
	
	private TokenPropertiesListImpl(PropertyTypeReference type, Object[] elements) {
		if(type.value().getType()!=ValueType.LIST)
			throw new IllegalArgumentException("The property type of a list must be of LIST");
		this.type=type;
		this.elements=elements;
	}
	
	/* (non-Javadoc)
	 * @see com.t3.model.tokenproperties.instance.ITokenPropertiesList#toString()
	 */
	@Override
	public String toString() {
		if(type!=null && type.value().getSubType()!=null)
			return type.value().getSubType().toString()+"s"+Arrays.toString(elements);
		else
			return Arrays.toString(elements);
	}
	
	/* (non-Javadoc)
	 * @see com.t3.model.tokenproperties.instance.ITokenPropertiesList#size()
	 */
	@Override
	public int size() {
		return elements.length;
	}
	
	/* (non-Javadoc)
	 * @see com.t3.model.tokenproperties.instance.ITokenPropertiesList#get(int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T get(int i) {
		return (T)this.getElementType().convert(type.value().getSubType(), elements[i]);
	}
	
	/* (non-Javadoc)
	 * @see com.t3.model.tokenproperties.instance.ITokenPropertiesList#withAdded(T)
	 */
	@Override
	public TokenPropertiesListImpl<T> withAdded(T tp) {
		if(!this.getElementType().isInstance(tp))
			throw new IllegalArgumentException("Was expecting an element of type "+this.getElementType().getType().getSimpleName()+", but got "+tp.getClass().getSimpleName());
		return new TokenPropertiesListImpl<T>(type, ArrayUtils.add(elements, tp));
	}
	
	public ValueType getElementType() {
		return type.value().getSubType().getType();
	}

	/* (non-Javadoc)
	 * @see com.t3.model.tokenproperties.instance.ITokenPropertiesList#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return new TokenPropertyIterator();
	}
	
	private class TokenPropertyIterator implements Iterator<T> {

		private int i=0;
		
		@Override
		public boolean hasNext() {
			return i<elements.length;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T next() {
			return (T)getElementType().convert(type.value(), elements[i++]);
		}
	}

	/* (non-Javadoc)
	 * @see com.t3.model.tokenproperties.instance.ITokenPropertiesList#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return elements.length==0;
	}

	/* (non-Javadoc)
	 * @see com.t3.model.tokenproperties.instance.ITokenPropertiesList#without(int)
	 */
	@Override
	public TokenPropertiesList<T> without(int index) {
		return new TokenPropertiesListImpl<>(type, ArrayUtils.remove(elements, index));
	}

	public PropertyType getPropertyType() {
		return type.value();
	}
}
