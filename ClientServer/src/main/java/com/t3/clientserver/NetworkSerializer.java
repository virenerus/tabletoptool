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
package com.t3.clientserver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;


public class NetworkSerializer {
	private static final KryoPool kryoPool=new KryoPool();
	
	
	public static <T extends Enum<T> & Command> TransferredMessage<T> deserialize(byte[] bytes) {
		try (Input in = new Input(new InflaterInputStream(new ByteArrayInputStream(bytes)))) {
			Kryo kryo = kryoPool.borrowObject();
			T message = (T) kryo.readClassAndObject(in);
			LinkedList<Object> parameters = new LinkedList<Object>();
			while(!in.eof())
				parameters.add(kryo.readClassAndObject(in));
			kryoPool.returnObject(kryo);
			return new TransferredMessage<T>(message, parameters.toArray());
		} catch (Exception e) {
			throw new Error(e);
		}
	}
	
	 public static final byte[] serialize(Enum<? extends Command> method, Object... parameters) {
		 try {
			Kryo kryo = kryoPool.borrowObject();
	    	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	    	try(Output kryoOut=new Output(new DeflaterOutputStream(bout))) {
		    	//write Message
		    	kryo.writeClassAndObject(kryoOut, method);
		    	//write Parameters
		    	for(Object p:parameters) {
		    		kryo.writeClassAndObject(kryoOut, p);
		    		//System.out.println(p.getClass().getName());
		    		//System.out.println(p.toString());
		    	}
	    	}
	    	kryoPool.returnObject(kryo);
	    	return bout.toByteArray();
	 	} catch (Exception e) {
			throw new Error(e);
		}
    }
	
	public static class TransferredMessage<T extends Enum<T> & Command> {
		private final T message;
		private final Object[] parameters;
	
		public TransferredMessage(T message, Object[] parameters) {
			this.message = message;
			this.parameters = parameters;
		}

		public T getMessage() {
			return message;
		}
	
		public Object[] getParameters() {
			return parameters;
		}
	}
}
