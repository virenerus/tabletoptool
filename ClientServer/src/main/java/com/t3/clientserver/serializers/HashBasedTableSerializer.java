package com.t3.clientserver.serializers;

import java.util.Map;
import java.util.Map.Entry;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

@SuppressWarnings("rawtypes")
public class HashBasedTableSerializer extends Serializer<HashBasedTable> {

	@Override
	public void write(Kryo kryo, Output output, HashBasedTable table) {
		
		Map<?, Map<?,?>> rows=table.rowMap();
		
		output.writeInt(rows.size());
		for(Entry<?, Map<?,?>> rowEntry:rows.entrySet()) {
			kryo.writeClassAndObject(output, rowEntry.getKey());
			output.writeInt(rowEntry.getValue().size());
			for(Entry<?, ?> entry:rowEntry.getValue().entrySet()) {
				kryo.writeClassAndObject(output, entry.getKey());
				kryo.writeClassAndObject(output, entry.getValue());
			}
		}
	}
	@Override
	public HashBasedTable read(Kryo kryo, Input input, Class<HashBasedTable> type) {
		int numberOfRows=input.readInt();
		HashBasedTable table=HashBasedTable.create(numberOfRows, 10);
		
		for(int i=0;i<numberOfRows;i++) {
			Object rowKey=kryo.readClassAndObject(input);
			int numberOfEntries=input.readInt();
			for(int j=0;j<numberOfEntries;j++) {
				Object key=kryo.readClassAndObject(input);
				Object value=kryo.readClassAndObject(input);
				table.put(rowKey, key, value);
			}
		}
		return table;
	}

}
