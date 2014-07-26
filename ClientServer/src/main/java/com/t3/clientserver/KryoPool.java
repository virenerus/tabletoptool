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

import java.awt.Color;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.collect.HashBasedTable;
import com.t3.clientserver.serializers.HashBasedTableSerializer;

import de.javakaffee.kryoserializers.SynchronizedCollectionsSerializer;

public class KryoPool extends GenericObjectPool<Kryo> {

	public KryoPool() {
		super(new KryoFactory());
		this.setMaxTotal(5);
	}

	public static class KryoFactory extends BasePooledObjectFactory<Kryo> {
		@Override
		public PooledObject<Kryo> wrap(Kryo kryo) {
			return new DefaultPooledObject<Kryo>(kryo);
		}

		@Override
		public Kryo create() throws Exception {
			Kryo kryo = new Kryo();
			
			((Kryo.DefaultInstantiatorStrategy) kryo.getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());

			registerSerializers(kryo);
			
			return kryo;
		}

		private void registerSerializers(Kryo kryo) {
					
			//register custom serializers here
			kryo.register(Color.class, new Serializer<Color>() {
				@Override
				public void write(Kryo kryo, Output output, Color object) {
					output.writeInt(object.getRGB());
				}

				@Override
				public Color read(Kryo kryo, Input input, Class<Color> type) {
					return new Color(input.readInt(), true);
				}
			});
			kryo.register(HashBasedTable.class, new HashBasedTableSerializer());
			SynchronizedCollectionsSerializer.registerSerializers( kryo );
		}
	}
}
