package com.t3.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.t3.model.Token;
import com.t3.script.api.TokenView;

public class TypeUtil {
	/**
     * Convert an object into a boolean value.
     * 
     * @param value Convert this object. Must be {@link Boolean}, {@link BigDecimal}, or a can 
     * have its string value be converted to one of those types.
     * @return The boo
     */
    public static boolean getBooleanValue(Object value) {
        boolean set = false;        
        if (value instanceof Boolean) {
            set = ((Boolean)value).booleanValue();
        } else if (value instanceof Number) {
            set = ((Number)value).doubleValue() != 0;
        } else if (value == null) {
            set = false;
        } else {
            try {
                set = !new BigDecimal(value.toString()).equals(BigDecimal.ZERO);
            } catch (NumberFormatException e) {
                set = Boolean.parseBoolean(value.toString());
            } // endif
        } // endif
        return set;
    }
    
    /**
	 * Convert the passed object into a big decimal value.
	 * 
	 * @param value The value to be converted
	 * @return The {@link BigDecimal} version of the value. The <code>null</code> value and strings
	 * that can't be converted to numbers return {@link BigDecimal#ZERO}
	 */
	public static BigDecimal getBigDecimalValue(Object value) {
		BigDecimal val = null;
		if (value instanceof BigDecimal) {
			val = (BigDecimal)value;
		} else if (value == null) {
			val = BigDecimal.ZERO;
		} else {
			try {
				val = new BigDecimal(value.toString());
			} catch (NumberFormatException e) {
				val = BigDecimal.ZERO;
			} // endtry
		} // endif
		return val;
	}

	public static List<TokenView> makeTokenViewList(List<Token> list) {
		ArrayList<TokenView> l=new ArrayList<TokenView>(list.size());
		for(Token t:list)
			l.add(new TokenView(t));
		return l;
	}
}
