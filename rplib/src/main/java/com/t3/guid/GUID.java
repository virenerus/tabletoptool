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
package com.t3.guid;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.t3.xstreamversioned.version.SerializationVersion;

/**
 * Global unique identificator object.
 */
@SerializationVersion(0)
public class GUID extends Object implements Serializable, Comparable<GUID> {

    /** Serial version unique identifier. */
    private static final long serialVersionUID = 6361057925697403643L;

    /** GUIDs always have 16 bytes. */ 
    public static final int GUID_LENGTH = 16;

    // NOTE: THIS CAN NEVER BE CHANGED, OR IT WILL AFFECT ALL THINGS THAT PREVIOUSLY USED IT
    public static final int GUID_BUCKETS = 100; 
    // NOTE: THIS CAN NEVER BE CHANGED, OR IT WILL AFFECT ALL THINGS THAT PREVIOUSLY USED IT

    private byte[] baGUID;
    static byte[] ip;
    
    static {
	    try {
	        InetAddress id = InetAddress.getLocalHost();
	        ip = id.getAddress(); // 192.168.0.14
	    } catch (UnknownHostException e) {
	    	// Default to something known
	    	ip = new byte[]{127, 0, 0, 1};
	    }
    }
    
    // Cache of the hashCode for a GUID
    private transient int hash;
    
    public GUID() {
        this.baGUID = generateGUID();
        validateGUID();
    }
  
    /** Creates a new GUID based on the specified GUID value. */ 
    public GUID(byte[] baGUID) throws InvalidGUIDException {
        this.baGUID = baGUID;
        validateGUID();
    }

    /** Creates a new GUID based on the specified hexadecimal-code string. */ 
    public GUID(String strGUID) {
        if (strGUID == null) throw new InvalidGUIDException("GUID is null");

        try {
			this.baGUID = Hex.decodeHex(strGUID.toCharArray());
		} catch (DecoderException e) {
			throw new Error(e);
		}
        validateGUID();
    }

    /** Ensures the GUID is legal. */
    private void validateGUID() throws InvalidGUIDException {
        if (baGUID == null) throw new InvalidGUIDException("GUID is null");
        if (baGUID.length != GUID_LENGTH) throw new InvalidGUIDException("GUID length is invalid");
    }

    /** Returns the GUID representation of the {@link byte} array argument. */
    public static GUID valueOf(byte[] bits) {
        if (bits == null) return null;
        return new GUID(bits);
    }

    /** Returns the GUID representation of the {@link String} argument. */
    public static GUID valueOf(String s) {
        if (s == null) return null;
        return new GUID(s);
    }

    /** Determines whether two GUIDs are equal. */
    @Override
	public boolean equals(Object object) {
        if (object == null) {
            return this == null;
        }

        Class<?> objClass = object.getClass();

        GUID guid;
        try {
            if (objClass == String.class) {   // string
                guid = new GUID((String)object);
            } else {                          // try to cast to a GUID
                guid = (GUID)object;
            }
        } catch (ClassCastException e) {    // not a GUID
            return false;
        }

        // Compare bytes.
        for (int i = 0; i < GUID_LENGTH; i++) {
            if (this.baGUID[i] != guid.baGUID[i])
            return false; 
        }

        // All tests pass.
        return true;
    }

    public byte[] getBytes() { return baGUID; }

    /** Returns a string for the GUID. */
    @Override
	public String toString() {
        return Hex.encodeHexString(baGUID);
    }

    /**
    * Returns a hashcode for this GUID. This function is based on the algorithm that JDK 1.3 uses for a String.
    * @return  a hash code value for this object.
    */
    @Override
	public int hashCode() {
        int h = hash;
        if (h == 0) {
            byte val[] = baGUID;
            int len = GUID_LENGTH;

            for (int i = 0; i < len; i++)
            h = 31*h + val[i];
            hash = h;
        }
        return h;
    }
    
    private static long guidGenerationCounter = 0;
    
    public static byte[] generateGUID() throws InvalidGUIDException{
        byte[] guid = new byte[16];
        
        
        System.currentTimeMillis();
        
        long time = System.currentTimeMillis();
        
        guidGenerationCounter++;

        int n = 0;
        guid[n++] = ip[0];
        guid[n++] = ip[1];
        guid[n++] = ip[2];
        guid[n++] = ip[3];
        guid[n++] = (byte) (time       & 0xFF);
        guid[n++] = (byte) (time >> 8  & 0xFF);
        guid[n++] = (byte) (time >> 16 & 0xFF);
        guid[n++] = (byte) (time >> 24 & 0xFF);
        guid[n++] = (byte) (guidGenerationCounter       & 0xFF);
        guid[n++] = (byte) (guidGenerationCounter >> 8  & 0xFF);
        guid[n++] = (byte) (guidGenerationCounter >> 16 & 0xFF);
        guid[n++] = (byte) (guidGenerationCounter >> 24 & 0xFF);
        guid[n++] = (byte) ((time >> 24  & 0xFF) & ip[0]);
        guid[n++] = (byte) ((time >> 16  & 0xFF) & ip[1]);
        guid[n++] = (byte) ((time >>  8  & 0xFF) & ip[2]);
        guid[n++] = (byte) ((time >>  0  & 0xFF) & ip[3]);
        
        return guid;
    }
    
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            GUID guid = new GUID();
            //System.out.println("insert into sys_guids values ('" + guid.toString() + "');");
            System.out.println(guid.toString());
        }
    }

    @Override
	public int compareTo(GUID o) {
		if (o != this) {
			for (int i = 0; i < GUID_LENGTH; i++) {
				if (this.baGUID[i] != o.baGUID[i])
					return this.baGUID[i] - o.baGUID[i];
			}
		}
		return 0;
	}
}
