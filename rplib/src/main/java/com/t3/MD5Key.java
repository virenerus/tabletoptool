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
package com.t3;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.t3.xstreamversioned.SerializationVersion;

/**
 * Represents the MD5 key for a certain set of data.
 * Can be used in maps as keys.
 */
@SerializationVersion(0)
public class MD5Key implements Serializable {

    private static MessageDigest md5Digest;
    
    String id;
    
    static {
        try {
            md5Digest = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            // TODO: handle this more gracefully
            e.printStackTrace();
        }
    }
    
    public MD5Key(){}
    
    public MD5Key(String id) {
    	this.id = id;
    }
    
    public MD5Key (byte[] data) {
        id = encodeToHex(digestData(data));
    }
    
    public MD5Key(File file) throws FileNotFoundException, IOException {
    	try(BufferedInputStream data=new BufferedInputStream(new FileInputStream(file))) {
    		id = encodeToHex(digestData(data));
    	}
    }
    
    @Override
	public String toString() {
        return id;
    }
    
    @Override
	public boolean equals(Object obj) {
        if (!(obj instanceof MD5Key)) {
            return false;
        }
        
        return id.equals(((MD5Key) obj).id);
    }
    
    @Override
	public int hashCode() {
        return id.hashCode();
    }
    
    private static synchronized byte[] digestData(byte[] data) {
        
        md5Digest.reset();
        
        md5Digest.update(data);
        
        return md5Digest.digest();
    }
    
    private static synchronized byte[] digestData(InputStream data) {
    	
    	md5Digest.reset();
    	
    	int b;
    	try {
	    	while (((b = data.read()) >= 0)) {
	    		md5Digest.update((byte)b);
	    	}
    	} catch (IOException ioe) {
    		ioe.printStackTrace();
    	}
    	
    	return md5Digest.digest();
    }

    private static String encodeToHex(byte[] data) {
        
        StringBuilder strbuild = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            
            String hex = Integer.toHexString(data[i]);
            if (hex.length() < 2) {
                strbuild.append("0");
            }
            if (hex.length() > 2) {
                hex = hex.substring(hex.length()-2);
            }
            strbuild.append(hex);
        }
        
        return strbuild.toString();
    }
}
