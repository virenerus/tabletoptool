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
	
	
	public static TransferredMessage deserialize(byte[] bytes) {
		try (Input in = new Input(new InflaterInputStream(new ByteArrayInputStream(bytes)))) {
			Kryo kryo = kryoPool.borrowObject();
			Enum<? extends Command> message = (Enum<? extends Command>) kryo.readClassAndObject(in);
			LinkedList<Object> parameters = new LinkedList<Object>();
			while(!in.eof())
				parameters.add(kryo.readClassAndObject(in));
			kryoPool.returnObject(kryo);
			System.out.println("Kryo Pool:"+kryoPool.getCreatedCount());        
			return new TransferredMessage(message, parameters.toArray());
		} catch (Exception e) {
			throw new Error(e);
		}
	}
	
	 public static final byte[] serialize(Enum<? extends Command> method, Object... parameters) {
		 try {
			Kryo kryo = kryoPool.borrowObject();
	    	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	    	Output kryoOut=new Output(new DeflaterOutputStream(bout));
	    	//write Message
	    	kryo.writeClassAndObject(kryoOut, method);
	    	//write Parameters
	    	for(Object p:parameters) {
	    		kryo.writeClassAndObject(kryoOut, p);
	    		System.out.println(p.getClass().getName());
	    		System.out.println(p.toString());
	    	}
	    	kryoOut.close();
	    	kryoPool.returnObject(kryo);
	    	byte[] bytes=bout.toByteArray();
	
	    	System.out.println(bytes.length);
	    	System.out.println("Kryo Pool:"+kryoPool.getCreatedCount());        
	    	
	        return bytes;
	 	} catch (Exception e) {
			throw new Error(e);
		}
    }
	
	public static class TransferredMessage {
		private final Enum<? extends Command> message;
		private final Object[] parameters;
	
		public TransferredMessage(Enum<? extends Command> message, Object[] parameters) {
			this.message = message;
			this.parameters = parameters;
		}

		public Enum<? extends Command> getMessage() {
			return message;
		}
	
		public Object[] getParameters() {
			return parameters;
		}
	}
}
