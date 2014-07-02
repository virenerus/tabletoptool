package com.t3.xstreamversioned.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.t3.xstreamversioned.SerializationVersion;
import com.t3.xstreamversioned.VersionedMarshallingStrategy;
import com.thoughtworks.xstream.XStream;

public class SerializationTest {
  @Test
  public void trySimpleSerialization() {
	  TestClass tc=new TestClass();
	  tc.subTest=new TestClass2();
	  tc.subTest.number=7;
	  tc.subTest.stringField="This class is below";
	  tc.subTest.subTest=tc; //circle reference
	  
	  XStream xstream=new XStream();
	  xstream.setMarshallingStrategy(new VersionedMarshallingStrategy());
	  String xml=xstream.toXML(tc);
	  TestClass reser=(TestClass) xstream.fromXML(xml);
	  Assert.assertEquals(reser, tc);
	  Assert.assertEquals(reser.subTest, tc.subTest);
	  Assert.assertEquals(reser.subTest.getClass(), TestClass2.class);
	  Assert.assertEquals(xstream.toXML(reser), xml);
  }
  
  @SerializationVersion(0)
  private static class TestClass {
	  private int number=4;
	  private String stringField="Hello World! with <b>bold</b> tags.";
	  private TestClass subTest;
	  
	  
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" [number=" + number + ", stringField=" + stringField
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		result = prime * result
				+ ((stringField == null) ? 0 : stringField.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestClass other = (TestClass) obj;
		if (number != other.number)
			return false;
		if (stringField == null) {
			if (other.stringField != null)
				return false;
		} else if (!stringField.equals(other.stringField))
			return false;
		return true;
	}
  }
  
  @SerializationVersion(2)
  private static class TestClass2 extends TestClass {}
}
