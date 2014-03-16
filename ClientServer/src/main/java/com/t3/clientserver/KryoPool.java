package com.t3.clientserver;

import java.awt.Color;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.shaded.org.objenesis.strategy.StdInstantiatorStrategy;

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
			kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());

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
			SynchronizedCollectionsSerializer.registerSerializers( kryo );
		}
	}
}
