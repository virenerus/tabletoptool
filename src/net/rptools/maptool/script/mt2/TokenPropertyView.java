package net.rptools.maptool.script.mt2;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.rptools.maptool.client.MapTool;
import net.rptools.maptool.model.Token;
import net.rptools.maptool.util.StringUtil;

/**
 * This class makes the TokenView a bad MapView on the token properties. The 
 * advantage of this that we can now access properties from groovy via beans
 * e.g.: token.HP="test" 
 * @author Virenerus
 *
 */
public abstract class TokenPropertyView implements Map<String, String>{

	protected Token token;

	public TokenPropertyView(Token token) {
		this.token=token;
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsKey(Object key) {
		if(key==null)
			return false;
		Object val = token.getProperty(key.toString());
		if (val == null) {
			return false;
		}

		if (StringUtil.isEmpty(val.toString())) {
			return false;
		}

		return true;
	}

	@Override
	public boolean containsValue(Object value) {
		for(String pn:token.getPropertyNames())
			if(token.getProperty(pn).equals(value))
				return true;
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String get(Object key) {
		if(key==null)
			throw new NullPointerException();
		else
			return (String)token.getProperty(key.toString());
	}

	@Override
	public boolean isEmpty() {
		return token.getPropertyNames().size()==0;
	}

	/**
	 * This is NOT backed by the properties.
	 */
	@Override
	public Set<String> keySet() {
		return new HashSet<String>(token.getPropertyNames());
	}

	@Override
	public String put(String key, String value) {
		String old=(String)token.setProperty(key, value);
		sendUpdate();
		return old;
	}

	private void sendUpdate() {
		MapTool.serverCommand().putToken(
				MapTool.getFrame().getCurrentZoneRenderer().getZone().getId(), 
				token);
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
		for(Entry<? extends String, ? extends String> e:m.entrySet()) {
			token.setProperty(e.getKey(), e.getValue());
		}
		sendUpdate();
	}

	@Override
	public String remove(Object key) {
		if(key==null)
			throw new NullPointerException();
		String o=(String)token.getProperty(key.toString());
		token.resetProperty(key.toString());
		return o;
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<String> values() {
		throw new UnsupportedOperationException();
	}
	
}
